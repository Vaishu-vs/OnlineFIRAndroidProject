package com.example.onlinefir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayIdActivity extends AppCompatActivity {
    private TextView textViewId, textViewUsername, textViewEmail, textViewCrimeSpot, textViewPincode, textViewDescription, textViewCategory, textViewDateofIncident, textViewTimeofIncident, textViewStatus;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_id);
        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewCrimeSpot = (TextView) findViewById(R.id.textViewCrimeSpot);
        textViewPincode = (TextView) findViewById(R.id.textViewPincode);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewDateofIncident = (TextView) findViewById(R.id.textViewDateofIncident);
        textViewTimeofIncident = (TextView) findViewById(R.id.textViewTimeofIncident);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference().child("COMPLAIN");
        table_user.keepSynced(true);
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String User_name = ds.child("User_name").getValue().toString();
                    String Email = ds.child("Email").getValue().toString();
                    String Crime_spot = ds.child("Crime_spot").getValue().toString();
                    String Pincode = ds.child("Pincode").getValue().toString();
                    String Description = ds.child("Description").getValue().toString();
                    String Category = ds.child("Category").getValue().toString();
                    String Date_of_incident = ds.child("Date_of_incident").getValue().toString();
                    String Time_of_incident = ds.child("Time_of_incident").getValue().toString();
                    String currentuser = ds.child("currentuser").getValue().toString();

                    textViewUsername.setText(User_name);
                    textViewEmail.setText(Email);
                    textViewCrimeSpot.setText(Crime_spot);
                    textViewPincode.setText(Pincode);
                    textViewDescription.setText(Description);
                    textViewCategory.setText(Category);
                    textViewDateofIncident.setText(Date_of_incident);
                    textViewTimeofIncident.setText(Time_of_incident);
                    textViewId.setText(currentuser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayIdActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayIdActivity.this, LayoutManagerActivity.class);
                startActivity(intent);
            }
        });
    }

}