package com.example.onlinefir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public BackgroundWorker myDb;
    String MobilePattern = "[0-9]{10}";
    String PincodePattern = "[0-9]{6}";
    private EditText first_name;
    private EditText middle_name;
    private EditText last_name;
    private EditText email;
    private EditText password;
    private EditText cpassword;
    private EditText phone_no;
    private EditText address;
    private EditText city;
    private EditText pincode;
    private RadioGroup gender;
    private EditText birthdate;
    private Button signin;
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

        myDb = new BackgroundWorker(this);
        first_name = (EditText) findViewById(R.id.first_name);
        middle_name = (EditText) findViewById(R.id.middle_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        phone_no = (EditText) findViewById(R.id.phone_no);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        pincode = (EditText) findViewById(R.id.pincode);
        gender = (RadioGroup) findViewById(R.id.gender);
        birthdate = (EditText) findViewById(R.id.birthdate);
        signin = (Button) findViewById(R.id.signin);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        birthdate.setOnClickListener(this);
        signin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    //Button Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.birthdate:
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate = dayOfMonth + "/" + month + "/" + year;
                        birthdate.setText(sDate);
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
               if (password.getText().toString().length() < 6) {
                    password.setError("Enter password having length more than 6 characters");
                } else {
                   AddData();
                }
        }
    }

    private boolean isValidEmailId(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void AddData() {
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        Toast.makeText(MainActivity.this, "Creating Account.",
                Toast.LENGTH_SHORT).show();
            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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

                            // ...
                        }
                    });

    }

    private void pushData(){
        String F_NAME = first_name.getText().toString();
        String M_NAME = middle_name.getText().toString();
        String L_NAME = last_name.getText().toString();
        String PHONE = phone_no.getText().toString();
        String ADDRESS = address.getText().toString();
        String CITY = city.getText().toString();
        String PINCODE = pincode.getText().toString();
        String GENDER = gender.toString();
        String BIRTHDATE = birthdate.getText().toString();

        Map<String,Object> taskMap = new HashMap<>();
        taskMap.put("F_NAME", F_NAME);
        taskMap.put("M_NAME", M_NAME);
        taskMap.put("L_NAME", L_NAME);
        taskMap.put("PHONE", PHONE);
        taskMap.put("ADDRESS", ADDRESS);
        taskMap.put("CITY", CITY);
        taskMap.put("L_NAME", L_NAME);
        taskMap.put("PHONE", PHONE);
        myRef.setValue(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this,"Profile Created",Toast.LENGTH_LONG);
            }
        });

    }
}

