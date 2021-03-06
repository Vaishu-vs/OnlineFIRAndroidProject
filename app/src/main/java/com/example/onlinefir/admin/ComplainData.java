package com.example.onlinefir.admin;

import com.google.firebase.database.PropertyName;

public class ComplainData {
    String User_name;
    String Email;
    String Crime_spot;
    String Pincode;
    String Description;
    String Category;
    String Date_of_incident;
    String Time_of_incident;
    String Status;
    String UID;
    String key;

    public ComplainData() {
        User_name = "";
        Email = "";
        Crime_spot = "";
        Pincode = "";
        Description = "";
        Category = "";
        Date_of_incident = "";
        Time_of_incident = "";
        UID = "";
        Status = "";
        key = "";
    }

    public ComplainData(String User_name, String Email, String Crime_spot, String Pincode, String Description, String Category, String Date_of_incident, String Time_of_incident, String UID, String Status,String key) {
        this.User_name = User_name;
        this.Email = Email;
        this.Crime_spot = Crime_spot;
        this.Pincode = Pincode;
        this.Description = Description;
        this.Category = Category;
        this.Date_of_incident = Date_of_incident;
        this.Time_of_incident = Time_of_incident;
        this.UID = UID;
        this.Status = Status;
        this.key = key;
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

    @PropertyName("UID")
    public String getUID() {
        return UID;
    }
    @PropertyName("Status")
    public String getStatus() {
        return Status;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @PropertyName("key")
    public String getKey() {
        return key;
    }
}
