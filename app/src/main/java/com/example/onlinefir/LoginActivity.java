package com.example.onlinefir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.onlinefir.admin.AddAdminActivity;
import com.example.onlinefir.admin.ViewComplainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private TextView textViewSign;
    private TextView textViewAdminPanel;
    private ProgressBar progressBar;
    private TextView textviewAuthenticationFail;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (mAuth.getCurrentUser() != null) {
            // User is logged in
            startActivity(new Intent(LoginActivity.this, LayoutManagerActivity.class));
            finish();
        }

        email = findViewById(R.id.editTextLoginEmail);
        password = findViewById(R.id.editTextLoginpassword);
        textViewSign = findViewById(R.id.textViewSign);
        textViewAdminPanel = findViewById(R.id.textViewAdminPanel);
        textviewAuthenticationFail = findViewById(R.id.textviewAuthenticationFail);
        progressBar = findViewById(R.id.progressBar);
        CheckBox checkboxShowPassword = findViewById(R.id.checkboxShowPassword);
        Button login = findViewById(R.id.buttonLogin);
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
        textViewSign.setOnClickListener(this);
        textViewAdminPanel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        switch (v.getId()) {
            case R.id.buttonLogin:
                if (email.getText().toString() == "" || password.getText().toString() == "") {
                    email.setError("Please enter your email");
                    password.setError("Enter a password");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Enter valid email");
                } else if (password.getText().toString().length() < 5) {
                    password.setError("Enter password having length more than 5 characters");
                } else {
                    AddData(Email, Password);
                }
                break;
            case R.id.textViewSign:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.textViewAdminPanel:
                Intent intents = new Intent(LoginActivity.this, AddAdminActivity.class);
                startActivity(intents);
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
                    textviewAuthenticationFail.setText("Authentication failed");
                    textviewAuthenticationFail.setTextColor(getResources().getColor(R.color.red));
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}