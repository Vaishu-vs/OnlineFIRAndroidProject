package com.example.onlinefir;

public class ModelComplain {
    private String User_name;
    private String Email;
    private String Crime_spot;
    private String Pincode;
    private String Description;
    private String Category;
    private String Date_of_incident;
    private String Time_of_incident;
    private String currentuser;

    public ModelComplain() {
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCrime_spot() {
        return Crime_spot;
    }

    public void setCrime_spot(String crime_spot) {
        Crime_spot = crime_spot;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate_of_incident() {
        return Date_of_incident;
    }

    public void setDate_of_incident(String date_of_incident) {
        Date_of_incident = date_of_incident;
    }

    public String getTime_of_incident() {
        return Time_of_incident;
    }

    public void setTime_of_incident(String time_of_incident) {
        Time_of_incident = time_of_incident;
    }

    public String getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(String currentuser) {
        this.currentuser = currentuser;
    }

}

