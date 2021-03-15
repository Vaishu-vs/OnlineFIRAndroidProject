package com.example.onlinefir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String MobilePattern = "[0-9]{10}";
    private EditText email;
    private EditText password;
    private EditText phone_no;
    Cursor cursor;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText first_name = (EditText) findViewById(R.id.first_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone_no = (EditText) findViewById(R.id.phone_no);
        Button login = (Button) findViewById(R.id.login);

        if (haveNetwork()){
            Toast.makeText(LoginActivity.this, "Network connection is available", Toast.LENGTH_SHORT).show();
        } else if (!haveNetwork()) {
            Toast.makeText(LoginActivity.this, "Network connection is not available", Toast.LENGTH_SHORT).show();
        }

        openHelper = new BackgroundWorker(this);
        db = openHelper.getReadableDatabase();

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (isValidEmailId(email.getText().toString().trim()) && password.getText().toString().length() >= 4 && isValidPassword(password.getText().toString()) && phone_no.getText().toString().matches(MobilePattern)) {
                    AddData();
                } else if (password.getText().toString().length() < 4) {
                    password.setError("Enter password having length more than 4 characters");
                } else if (isValidPassword(password.getText().toString()) == false) {
                    password.setError("at least 1 Alphabet,Number & Special Character Require");
                } else if (phone_no.getText().toString().matches(MobilePattern) == false) {
                    phone_no.setError("Number Should Contain 10 digits.");
                } else if (isValidEmailId(email.getText().toString().trim()) == false) {
                    email.setError("InValid Email Address.");
                } else {
                    Toast.makeText(getApplicationContext(), "InValid Enteries... ", Toast.LENGTH_LONG).show();
                }
        }
    }

    private boolean haveNetwork(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
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
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Phone_no = phone_no.getText().toString();
        if (Email.equals("") || Password.equals("") || Phone_no.equals(""))
            Toast.makeText(getApplicationContext(), "None of the field should be empty", Toast.LENGTH_LONG).show();
        else {
            cursor = db.rawQuery("SELECT * FROM " + BackgroundWorker.TABLE_NAME + " WHERE " + BackgroundWorker.COL_5 + "=? AND " + BackgroundWorker.COL_6 + "=?", new String[]{Email, Password});
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    email.setText("");
                    password.setText("");
                    Intent i = new Intent(getApplicationContext(), LayoutManager.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Login error  " + cursor.getCount(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
            }
        }
    }
}

