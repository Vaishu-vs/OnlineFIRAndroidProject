package com.example.onlinefir;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ComplainActivity extends Fragment implements View.OnClickListener {
    private EditText user_name;
    private EditText email;
    private EditText crime_spot;
    private EditText pincode;
    private EditText description;
    private EditText category;
    private EditText date_of_incident;
    private EditText time_of_incident;
    private ProgressBar progressBar;
    Button submit;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    String pattern = "[1-9][0-9]{6}";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("COMPLAIN");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_complain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_name = view.findViewById(R.id.editTextuser_name);
        email = view.findViewById(R.id.editTextemail);
        crime_spot = view.findViewById(R.id.editTextcrime_spot);
        pincode = view.findViewById(R.id.editTextpincode);
        description = view.findViewById(R.id.editTextdescription);
        category = view.findViewById(R.id.editTextcategory);
        date_of_incident = view.findViewById(R.id.editTextdate_of_incident);
        progressBar = view.findViewById(R.id.progressBar);
        time_of_incident = view.findViewById(R.id.editTexttime_of_incident);

        submit = view.findViewById(R.id.submit);


        date_of_incident.setOnClickListener(this);
        time_of_incident.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (pincode.getText().toString() == pattern) {
                    pincode.setError("Enter valid pincode");
                } else if (user_name.getText().toString() == "" || email.getText().toString() == "" || crime_spot.getText().toString() == "" || pincode.getText().toString() == "" || description.getText().toString() == "" || category.getText().toString() == "" || date_of_incident.getText().toString() == "" || time_of_incident.getText().toString() == "") {
                    user_name.setError("Please enter first name");
                    email.setError("Please enter your email");
                    crime_spot.setError("Enter a password");
                    pincode.setError("Enter a phone number");
                    description.setError("Enter address");
                    category.setError("Enter city");
                    date_of_incident.setError("Enter pincode");
                    time_of_incident.setError("Enter birthdate");
                } else {
                    registerComplain();
                }
                break;
            case R.id.editTexttime_of_incident:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_of_incident.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);  //24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                break;
            case R.id.editTextdate_of_incident:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate = dayOfMonth + "/" + month + "/" + year;
                        date_of_incident.setText(sDate);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
//
        }
    }

    private void registerComplain() {
        progressBar.setVisibility(View.VISIBLE);
        String User_name = user_name.getText().toString();
        String Email = email.getText().toString();
        String Crime_spot = crime_spot.getText().toString();
        String Pincode = pincode.getText().toString();
        String Description = description.getText().toString();
        String Category = category.getText().toString();
        String Date_of_incident = date_of_incident.getText().toString();
        String Time_of_incident = time_of_incident.getText().toString();

        String Status = "Complain Field";
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter valid email");
        }
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("User_name", User_name);
        taskMap.put("Email", Email);
        taskMap.put("Crime_spot", Crime_spot);
        taskMap.put("Pincode", Pincode);
        taskMap.put("Description", Description);
        taskMap.put("Category", Category);
        taskMap.put("Date_of_incident", Date_of_incident);
        taskMap.put("Time_of_incident", Time_of_incident);
        taskMap.put("Status", Status);
        taskMap.put("UID", currentuser);
        // hide the progress bar
        Complain complain = new Complain(User_name, Email, Crime_spot, Pincode, Description, Category, Date_of_incident, Time_of_incident, currentuser, Status);

        progressBar.setVisibility(View.GONE);
        String newChildRef = myRef.push().getKey();

        myRef.child(newChildRef).setValue(complain).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getContext(), DisplayComplainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}