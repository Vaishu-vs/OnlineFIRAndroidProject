package com.example.onlinefir;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    private Button signin, btnLogin;
    private ProgressBar progressBar;
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
                } else {
                    AddData();
                }
        }
    }

    public void AddData() {
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
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
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
        //first we will do the validations
        if (TextUtils.isEmpty(F_NAME)) {
            editTextfirst_name.setError("Please enter first name");
            editTextfirst_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(M_NAME)) {
            editTextmiddle_name.setError("Please enter middle name");
            editTextmiddle_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(L_NAME)) {
            editTextlast_name.setError("Please enter last name");
            editTextlast_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(EMAIL)) {
            editTextemail.setError("Please enter your email");
            editTextemail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
            editTextemail.setError("Enter a valid email");
            editTextemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PASSWORD)) {
            editTextpassword.setError("Enter a password");
            editTextpassword.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(PHONE).matches()) {
            editTextphone_no.setError("Enter a valid phone number");
            editTextphone_no.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PHONE)) {
            editTextphone_no.setError("Enter a phone number");
            editTextphone_no.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(ADDRESS)) {
            editTextaddress.setError("Enter address");
            editTextaddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(CITY)) {
            editTextcity.setError("Enter city");
            editTextcity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(PINCODE)) {
            editTextpincode.setError("Enter pincode");
            editTextpincode.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(BIRTHDATE)) {
            editTextbirthdate.setError("Enter birthdate");
            editTextbirthdate.requestFocus();
            return;
        }

//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);

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
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
       // progressBar.setVisibility(View.GONE);
        myRef.child(currentuser).setValue(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

