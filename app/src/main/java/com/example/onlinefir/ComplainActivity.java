package com.example.onlinefir;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ComplainActivity extends Fragment {
    public BackgroundWorker myDb;
    EditText user_name;
    EditText email;
    EditText crime_spot;
    EditText pincode;
    EditText description;
    EditText category;
    EditText date_of_incident;
    EditText time_of_incident;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_complain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDb = new BackgroundWorker(this.getContext());
        user_name = (EditText) view.findViewById(R.id.user_name);
        email = (EditText) view.findViewById(R.id.email);
        crime_spot = (EditText) view.findViewById(R.id.crime_spot);
        pincode = (EditText) view.findViewById(R.id.pincode);
        description = (EditText) view.findViewById(R.id.description);
        category = (EditText) view.findViewById(R.id.category);
        date_of_incident = (EditText) view.findViewById(R.id.date_of_incident);
        time_of_incident = (EditText) view.findViewById(R.id.time_of_incident);
        submit = (Button) view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User_name = user_name.getText().toString();
                String Email = email.getText().toString();
                String Crime_spot = crime_spot.getText().toString();
                String Pincode = pincode.getText().toString();
                String Description = description.getText().toString();
                String Date_of_incident = date_of_incident.getText().toString();
                String Time_of_incident = time_of_incident.getText().toString();
                if (User_name.equals("") || Email.equals("") || Crime_spot.equals("") || Pincode.equals("") || Description.equals("") || Date_of_incident.equals("") || Time_of_incident.equals("")) {
                    Toast.makeText(getContext(), "None of the field should be empty", Toast.LENGTH_LONG).show();
                } else {
                    boolean isInserted1 = myDb.insertFirData(User_name, Email, Crime_spot, Pincode, Description, Date_of_incident, Time_of_incident);
                    if (isInserted1 == true) {
                        int id = myDb.get_Id();
                        Intent i = new Intent(getContext(), StatusActivity.class);
                        i.putExtra("id", id);
                        startActivity(i);
                    } else
                        Toast.makeText(getContext(), "Data not Inserted", Toast.LENGTH_LONG).show();

                    user_name.setText("");
                    email.setText("");
                    crime_spot.setText("");
                    pincode.setText("");
                    description.setText("");
                    date_of_incident.setText("");
                    time_of_incident.setText("");
                }
            }
        });
    }
}
