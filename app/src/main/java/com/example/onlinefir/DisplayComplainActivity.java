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

public class DisplayComplainActivity extends AppCompatActivity {

    private TextView textViewId, textViewUsername, textViewEmail, textViewCrimeSpot, textViewPincode, textViewDescription, textViewCategory, textViewDateofIncident, textViewTimeofIncident, textViewStatus;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complain);
        textViewId = findViewById(R.id.textViewId);
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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference().child("COMPLAIN");
        table_user.keepSynced(true);
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Complain complain = ds.getValue(Complain.class);
                        String User_name = complain.getUser_name();
                        String Email = complain.getEmail();
                        String Crime_spot = complain.getCrime_spot();
                        String Pincode = complain.getPincode();
                        String Description = complain.getDescription();
                        String Category = complain.getCategory();
                        String Date_of_incident = complain.getDate_of_incident();
                        String Time_of_incident = complain.getTime_of_incident();
                        String currentuser = complain.getCurrentuser();

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
                }  else {
                    Toast.makeText(DisplayComplainActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayComplainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayComplainActivity.this, LayoutManagerActivity.class);
                startActivity(intent);
            }
        });
    }

}