package com.example.onlinefir.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.onlinefir.LayoutManagerActivity;
import com.example.onlinefir.MainActivity;
import com.example.onlinefir.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private TextView tvSign;
    private ProgressBar progressBar;
    private TextView textviewAuthenticationFail;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String MyPREFERENCES = "MyPrefs";
    private static final String SPEmail = "emailKey";
    private static final String SPPassword = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email = (EditText) findViewById(R.id.editTextLoginEmail);
        password = (EditText) findViewById(R.id.editTextLoginpassword);
        tvSign = (TextView) findViewById(R.id.textViewSign);
        textviewAuthenticationFail = (TextView) findViewById(R.id.textviewAuthenticationFail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        CheckBox checkboxShowPassword = (CheckBox) findViewById(R.id.checkboxShowPassword);
        Button login = (Button) findViewById(R.id.buttonLogin);

        checkboxShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        login.setOnClickListener(this);
        tvSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter valid email");
        }
        switch (v.getId()) {
            case R.id.buttonLogin:
                if (email.getText().toString() == "" || password.getText().toString() == "") {
                    email.setError("Please enter your email");
                    password.setError("Enter a password");
                } else if (password.getText().toString().length() < 5) {
                    password.setError("Enter password having length more than 5 characters");
                } else {
                    AddData(Email, Password);
                }
                break;
            case R.id.textViewSign:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            default:
                return;
        }
    }

    public void AddData(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    // hide the progress bar
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), LayoutManagerActivity.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    // hide the progress bar
                    progressBar.setVisibility(View.GONE);
                    textviewAuthenticationFail.setVisibility(View.VISIBLE);
                    textviewAuthenticationFail.setTextColor(R.color.red);
                    textviewAuthenticationFail.setText("Authentication failed");
                    Toast.makeText(AdminLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}