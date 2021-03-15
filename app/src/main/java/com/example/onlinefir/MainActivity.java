package com.example.onlinefir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
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
                if (isValidEmailId(email.getText().toString().trim()) && password.getText().toString().length() >= 6 && isValidPassword(password.getText().toString()) && phone_no.getText().toString().matches(MobilePattern)) {
                    AddData();
                } else if (password.getText().toString().length() < 6) {
                    password.setError("Enter password having length more than 6 characters");
                } else if (isValidPassword(password.getText().toString()) == false) {
                    password.setError("at least 1 Alphabet,Number & Special Character Require");
                } else if (phone_no.getText().toString().matches(MobilePattern) == false) {
                    phone_no.setError("Number Should Contain 10 digits.");
                }else if (pincode.getText().toString().matches(PincodePattern) == false) {
                    phone_no.setError("Number Should Contain 6 digits.");
                } else if (isValidEmailId(email.getText().toString().trim()) == false) {
                    email.setError("InValid Email Address.");
                } else {
                    Toast.makeText(getApplicationContext(), "InValid Enteries... ", Toast.LENGTH_LONG).show();
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
        String s1 = first_name.getText().toString();
        String s2 = middle_name.getText().toString();
        String s3 = last_name.getText().toString();
        String s4 = email.getText().toString();
        String s5 = password.getText().toString();
        String s6 = cpassword.getText().toString();
        String s7 = phone_no.getText().toString();
        String s8 = address.getText().toString();
        String s9 = city.getText().toString();
        String s10 = pincode.getText().toString();
        String s11 = gender.toString();
        String s12 = birthdate.getText().toString();
        if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || s5.equals("") || s6.equals("") || s7.equals("") || s8.equals("") || s9.equals("") || s10.equals("") || s11.equals("") || s12.equals(""))
            Toast.makeText(getApplicationContext(), "None of the field should be empty", Toast.LENGTH_LONG).show();
        else {
            if (s5.equals(s6)) {
                boolean isInserted = myDb.insertData(
                        first_name.getText().toString(),
                        middle_name.getText().toString(),
                        last_name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        phone_no.getText().toString(),
                        address.getText().toString(),
                        city.getText().toString(),
                        pincode.getText().toString(),
                        gender.toString(),
                        birthdate.getText().toString()
                );
                if (isInserted == true) {
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Data not Inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
            }
        }
    }
}

