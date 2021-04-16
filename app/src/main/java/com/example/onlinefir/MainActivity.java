package com.example.onlinefir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
    private Button signin;
    private TextView btnLogin;
    private ProgressBar progressBar;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("PROFILE");

    String picodepattern = "[1-9][0-9]{6}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mAuth.getCurrentUser() != null) {

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        editTextfirst_name = findViewById(R.id.editTextfirst_name);
        editTextmiddle_name = findViewById(R.id.editTextmiddle_name);
        editTextlast_name = findViewById(R.id.editTextlast_name);
        editTextemail = findViewById(R.id.editTextemail);
        editTextpassword = findViewById(R.id.editTextpassword);
        editTextcpassword = findViewById(R.id.editTextcpassword);
        editTextphone_no = findViewById(R.id.editTextphone_no);
        editTextaddress =  findViewById(R.id.editTextaddress);
        editTextcity = findViewById(R.id.editTextcity);
        editTextpincode = findViewById(R.id.editTextpincode);
        radioGroupgender = findViewById(R.id.radioGroupgender);
        editTextbirthdate = findViewById(R.id.editTextbirthdate);
        editTextAdharCard = findViewById(R.id.editTextAdharCard);
        progressBar = findViewById(R.id.progressBar);

        int selectedInt = radioGroupgender.getCheckedRadioButtonId();
        radiobuttonGender =  findViewById(selectedInt);
        signin = findViewById(R.id.signin);
        btnLogin = findViewById(R.id.btnLogin);

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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.signin:
                if (editTextpassword.getText().toString().length() < 5) {
                    editTextpassword.setError("Enter password having length more than 5 characters");
                } else if (editTextpincode.getText().toString() == picodepattern) {
                    editTextpincode.setError("Enter valid pincode");
                } else if (editTextpassword.getText().toString() == editTextcpassword.getText().toString()) {
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

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
            editTextemail.setError("Enter valid email");
        }

        if (!Patterns.PHONE.matcher(PHONE).matches()) {
            editTextphone_no.setError("Enter valid phone no.");
        }

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
                }
            }
        };
//        myRef.child(currentuser).setValue(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        myRef.child(currentuser).setValue(taskMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, DisplayComplainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

