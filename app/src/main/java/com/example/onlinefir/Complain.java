package com.example.onlinefir;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class Complain implements Serializable {
    String User_name;
    String Email;
    String Crime_spot;
    String Pincode;
    String Description;
    String Category;
    String Date_of_incident;
    String Time_of_incident;
    String currentuser;

    public Complain() {
        User_name = "";
        Email = "";
        Crime_spot = "";
        Pincode = "";
        Description = "";
        Category = "";
        Date_of_incident = "";
        Time_of_incident = "";
        currentuser = "";
    }

    public Complain(String User_name, String Email, String Crime_spot, String Pincode, String Description, String Category, String Date_of_incident, String Time_of_incident, String currentuser) {
        this.User_name = User_name;
        this.Email = Email;
        this.Crime_spot = Crime_spot;
        this.Pincode = Pincode;
        this.Description = Description;
        this.Category = Category;
        this.Date_of_incident = Date_of_incident;
        this.Time_of_incident = Time_of_incident;
        this.currentuser = currentuser;
    }

    @PropertyName("User_name")
    public String getUser_name() {
        return User_name;
    }

    @PropertyName("Email")
    public String getEmail() {
        return Email;
    }

    @PropertyName("Crime_spot")
    public String getCrime_spot() {
        return Crime_spot;
    }

    @PropertyName("Pincode")
    public String getPincode() {
        return Pincode;
    }

    @PropertyName("Description")
    public String getDescription() {
        return Description;
    }

    @PropertyName("Category")
    public String getCategory() {
        return Category;
    }

    @PropertyName("Date_of_incident")
    public String getDate_of_incident() {
        return Date_of_incident;
    }

    @PropertyName("Time_of_incident")
    public String getTime_of_incident() {
        return Time_of_incident;
    }

    @PropertyName("currentuser")
    public String getCurrentuser() {
        return currentuser;
    }
}
