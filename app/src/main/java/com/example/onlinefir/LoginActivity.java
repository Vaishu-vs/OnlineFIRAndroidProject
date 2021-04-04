package com.example.onlinefir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private TextView tvSign;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String SPEmail = "emailKey";
    public static final String SPPassword = "passwordKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        tvSign = (TextView) findViewById(R.id.tvSign);
        Button login = (Button) findViewById(R.id.login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        login.setOnClickListener(this);
        tvSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SPEmail, Email);
        editor.putString(SPPassword, Password);
        editor.commit();
        switch (v.getId()) {
            case R.id.login:
                if (password.getText().toString().length() < 5) {
                    password.setError("Enter password having length more than 5 characters");
                } else {
                    AddData(Email, Password);
                }
                break;
            case R.id.tvSign:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            default:
                return;
        }
    }

    public void AddData(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success");
                    Intent intent = new Intent(getApplicationContext(), LayoutManagerActivity.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}