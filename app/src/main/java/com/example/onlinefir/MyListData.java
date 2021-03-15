package com.example.onlinefir;

public class MyListData {
    private String title, description, date;
    public MyListData(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
    public String gettitle() {
        return title;
    }
    public void settitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getdate() {
        return date;
    }
    public void setdate(String date) {
        this.date = date;
    }
}
