package com.example.onlinefir.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinefir.LayoutManagerActivity;
import com.example.onlinefir.R;
import com.example.onlinefir.complainStatus.DisplayInListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDisplayUserActivity extends AppCompatActivity {
    private TextView  textViewUsername, textViewEmail, textViewCrimeSpot, textViewPincode, textViewDescription, textViewCategory, textViewDateofIncident, textViewTimeofIncident, textViewStatus;
    Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_display_user);

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewCrimeSpot = findViewById(R.id.textViewCrimeSpot);
        textViewPincode = findViewById(R.id.textViewPincode);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewCategory = findViewById(R.id.textViewCategory);
        textViewDateofIncident = findViewById(R.id.textViewDateofIncident);
        textViewTimeofIncident = findViewById(R.id.textViewTimeofIncident);
        textViewStatus = findViewById(R.id.textViewStatus);
        buttonBack = findViewById(R.id.buttonBack);
        String s = getIntent().getStringExtra("uid");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference().child("COMPLAIN");

        table_user.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String User_name = dataSnapshot.child("F_NAME").getValue().toString();
                    String Email = dataSnapshot.child("L_NAME").getValue().toString();
                    String Crime_spot = dataSnapshot.child("EMAIL").getValue().toString();
                    String Pincode = dataSnapshot.child("CITY").getValue().toString();
                    String Description = dataSnapshot.child("PINCODE").getValue().toString();
                    String Category = dataSnapshot.child("ADDRESS").getValue().toString();
                    String Date_of_incident = dataSnapshot.child("GENDER").getValue().toString();
                    String Time_of_incident = dataSnapshot.child("PHONE").getValue().toString();
                    String currentuser = dataSnapshot.child("ADHARCARD_NO").getValue().toString();


                    textViewUsername.setText(User_name);
                    textViewEmail.setText(Email);
                    textViewCrimeSpot.setText(Crime_spot);
                    textViewPincode.setText(Pincode);
                    textViewDescription.setText(Description);
                    textViewCategory.setText(Category);
                    textViewDateofIncident.setText(Date_of_incident);
                    textViewTimeofIncident.setText(Time_of_incident);


                }  else {
                    Toast.makeText(AdminDisplayUserActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminDisplayUserActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDisplayUserActivity.this, AdminLayoutActivity.class);
                startActivity(intent);
            }
        });

    }
}