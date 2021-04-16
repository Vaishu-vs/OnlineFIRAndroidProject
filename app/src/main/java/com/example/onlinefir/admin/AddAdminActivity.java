package com.example.onlinefir.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinefir.LoginActivity;
import com.example.onlinefir.MainActivity;
import com.example.onlinefir.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddAdminActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextPoliceStationName;
    private EditText editTextAdminEmail;
    private EditText editTextAdminpassword;
    private EditText editTextConfirmPassword;
    private Button buttonAddAdmin;
    private ProgressBar progressBar;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ADMIN");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        editTextPoliceStationName = findViewById(R.id.editTextPoliceStationName);
        editTextAdminEmail = findViewById(R.id.editTextAdminEmail);
        editTextAdminpassword = findViewById(R.id.editTextAdminpassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        progressBar = findViewById(R.id.progressBar);
        buttonAddAdmin = findViewById(R.id.signin);

        buttonAddAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddAdmin:
                if (editTextAdminpassword.getText().toString().length() < 5) {
                    editTextAdminpassword.setError("Enter password having length more than 5 characters");
                } else if (editTextAdminpassword.getText().toString() != editTextConfirmPassword.getText().toString()) {
                    editTextConfirmPassword.setError("Password and confirm password must be same");
                } else if (editTextPoliceStationName.getText().toString() == "" || editTextAdminEmail.getText().toString() == "" || editTextAdminpassword.getText().toString() == "" || editTextConfirmPassword.getText().toString() == "") {
                    editTextPoliceStationName.setError("Please enter police station name");
                    editTextAdminpassword.setError("Please enter password");
                    editTextConfirmPassword.setError("Please enter confirm password");
                    editTextAdminEmail.setError("Please enter your email");
                } else {
                    AddData();
                }
                break;
        }
    }

    public void AddData() {
        progressBar.setVisibility(View.VISIBLE);
        String emailStr = editTextAdminEmail.getText().toString();
        String passwordStr = editTextAdminpassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(AddAdminActivity.this, "Generating Profile.",
                            Toast.LENGTH_SHORT).show();
                    pushData();
                    // hide the progress bar
                    progressBar.setVisibility(View.GONE);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(AddAdminActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    // hide the progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void pushData() {
        String POLICE_STATION = editTextPoliceStationName.getText().toString();
        String ADMIN_EMAIL = editTextAdminEmail.getText().toString();
        String PASSWORD = editTextAdminpassword.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(ADMIN_EMAIL).matches()) {
            editTextAdminEmail.setError("Enter valid email");
        }

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("POLICE_STATION", POLICE_STATION);
        taskMap.put("ADMIN_EMAIL", ADMIN_EMAIL);
        taskMap.put("PASSWORD", PASSWORD);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {

                }
            }
        };
        myRef.child(currentuser).setValue(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(AddAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}