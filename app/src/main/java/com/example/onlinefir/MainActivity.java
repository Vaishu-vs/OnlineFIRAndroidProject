package com.example.onlinefir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextfirst_name;
    private EditText editTextmiddle_name;
    private EditText editTextlast_name;
    private EditText editTextemail;
    private EditText editTextpassword;
    private EditText editTextcpassword;
    private EditText editTextphone_no;
    private EditText editTextaddress;
    private EditText editTextcity;
    private EditText editTextpincode;
    private RadioButton radiobuttonGender;
    private RadioGroup radioGroupgender;
    private EditText editTextbirthdate;
    private EditText editTextAdharCard;
    private Button signin, btnLogin;
    private ProgressBar progressBar;
    private SharedPreferences sharedPref;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("PROFILE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextfirst_name = (EditText) findViewById(R.id.editTextfirst_name);
        editTextmiddle_name = (EditText) findViewById(R.id.editTextmiddle_name);
        editTextlast_name = (EditText) findViewById(R.id.editTextlast_name);
        editTextemail = (EditText) findViewById(R.id.editTextemail);
        editTextpassword = (EditText) findViewById(R.id.editTextpassword);
        editTextcpassword = (EditText) findViewById(R.id.editTextcpassword);
        editTextphone_no = (EditText) findViewById(R.id.editTextphone_no);
        editTextaddress = (EditText) findViewById(R.id.editTextaddress);
        editTextcity = (EditText) findViewById(R.id.editTextcity);
        editTextpincode = (EditText) findViewById(R.id.editTextpincode);
        radioGroupgender = (RadioGroup) findViewById(R.id.radioGroupgender);
        editTextbirthdate = (EditText) findViewById(R.id.editTextbirthdate);
        editTextAdharCard = (EditText) findViewById(R.id.editTextAdharCard);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        int selectedInt = radioGroupgender.getCheckedRadioButtonId();
        radiobuttonGender = (RadioButton) findViewById(selectedInt);
        signin = (Button) findViewById(R.id.signin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("PROFILE");

        editTextbirthdate.setOnClickListener(this);
        signin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    //Button Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTextbirthdate:
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate = dayOfMonth + "/" + month + "/" + year;
                        editTextbirthdate.setText(sDate);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
            case R.id.btnLogin:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.signin:
                if (editTextpassword.getText().toString().length() < 5) {
                    editTextpassword.setError("Enter password having length more than 5 characters");
                } else if (editTextphone_no.getText().toString().length() == 10) {
                    editTextphone_no.setError("Enter valid phone no");
                } else if (editTextpincode.getText().toString().length() == 6) {
                    editTextpincode.setError("Enter valid pincode");
                } else if (editTextpassword.getText().toString() != editTextcpassword.getText().toString()) {
                    editTextpassword.setError("Password and confirm password must be same");
                } else if(editTextfirst_name.getText().toString() == "" || editTextmiddle_name.getText().toString() == "" || editTextlast_name.getText().toString() == "" || editTextpassword.getText().toString() == "" || editTextbirthdate.getText().toString() == "" || editTextpincode.getText().toString() == "" || editTextcity.getText().toString() == "" || editTextaddress.getText().toString() == "" || editTextphone_no.getText().toString() == "" || editTextemail.getText().toString() == "" || editTextAdharCard.getText().toString() == "") {
                    editTextfirst_name.setError("Please enter first name");
                    editTextmiddle_name.setError("Please enter middle name");
                    editTextlast_name.setError("Please enter last name");
                    editTextemail.setError("Please enter your email");
                    editTextpassword.setError("Enter a password");
                    editTextphone_no.setError("Enter a phone number");
                    editTextaddress.setError("Enter address");
                    editTextcity.setError("Enter city");
                    editTextpincode.setError("Enter pincode");
                    editTextbirthdate.setError("Enter birthdate");
                    editTextAdharCard.setError("Enter adhar card no.");
                } else {
                    AddData();
                }
                break;
        }
    }

    public void AddData() {
        progressBar.setVisibility(View.VISIBLE);
        String emailStr = editTextemail.getText().toString();
        String passwordStr = editTextpassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(MainActivity.this, "Generating Profile.",
                            Toast.LENGTH_SHORT).show();
                    pushData();
                    // hide the progress bar
                    progressBar.setVisibility(View.GONE);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    // hide the progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void pushData() {
        String F_NAME = editTextfirst_name.getText().toString();
        String M_NAME = editTextmiddle_name.getText().toString();
        String L_NAME = editTextlast_name.getText().toString();
        String EMAIL = editTextemail.getText().toString();
        String PASSWORD = editTextpassword.getText().toString();
        String PHONE = editTextphone_no.getText().toString();
        String ADDRESS = editTextaddress.getText().toString();
        String CITY = editTextcity.getText().toString();
        String PINCODE = editTextpincode.getText().toString();
        String GENDER = radiobuttonGender.getText().toString();
        String BIRTHDATE = editTextbirthdate.getText().toString();
        String ADHARCARD = editTextAdharCard.getText().toString();

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("F_NAME", F_NAME);
        taskMap.put("M_NAME", M_NAME);
        taskMap.put("L_NAME", L_NAME);
        taskMap.put("EMAIL", EMAIL);
        taskMap.put("PASSWORD", PASSWORD);
        taskMap.put("PHONE", PHONE);
        taskMap.put("ADDRESS", ADDRESS);
        taskMap.put("CITY", CITY);
        taskMap.put("PINCODE", PINCODE);
        taskMap.put("GENDER", GENDER);
        taskMap.put("BIRTHDATE", BIRTHDATE);
        taskMap.put("ADHARCARD_NO", ADHARCARD);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();
                    String userEmail = firebaseUser.getEmail();
                    sharedPref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("firebasekey", userId);
                    editor.commit();
                }
            }
        };
        myRef.child(currentuser).setValue(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

