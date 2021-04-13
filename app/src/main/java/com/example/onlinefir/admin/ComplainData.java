package com.example.onlinefir.admin;

public class ComplainData {
    private String complaintCategory;
    private String complaintUserName;
    private String complaintDate;
    private  String complaintStatus;

    public  ComplainData(String complaintCategory, String complaintUserName, String complaintDate, String complaintStatus) {
        this.complaintCategory = complaintCategory;
        this.complaintUserName = complaintUserName;
        this.complaintStatus = complaintStatus;
        this.complaintDate = complaintDate;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    public String getComplaintUserName() {
        return complaintUserName;
    }

    public void setComplaintUserName(String complaintUserName) {
        this.complaintUserName = complaintUserName;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }
}
