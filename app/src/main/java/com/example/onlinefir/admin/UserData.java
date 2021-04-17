package com.example.onlinefir.admin;

import com.google.firebase.database.PropertyName;

public class UserData {
    String F_NAME;
    String M_NAME;
    String L_NAME;
    String EMAIL;
    String PASSWORD;
    Long PHONE;
    String ADDRESS;
    String CITY;
    String PINCODE;
    String GENDER;
    String ADHARCARD_NO;
    String key;

//    public UserData() {
//        F_NAME = "";
//        M_NAME = "";
//        L_NAME = "";
//        EMAIL = "";
//        PASSWORD = "";
//        PHONE = "";
//        ADDRESS = "";
//        CITY = "";
//        GENDER = "";
//        PINCODE = "";
//        ADHARCARD_NO = "";
//        key = "";
//    }
    public UserData(String F_NAME, String M_NAME, String L_NAME, String EMAIL, String PASSWORD, Long PHONE, String ADDRESS, String CITY, String GENDER, String PINCODE, String ADHARCARD_NO, String key) {
        this.F_NAME = F_NAME;
        this.M_NAME = M_NAME;
        this.L_NAME = L_NAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.PHONE = PHONE;
        this.ADDRESS = ADDRESS;
        this.CITY = CITY;
        this.GENDER = GENDER;
        this.PINCODE = PINCODE;
        this.ADHARCARD_NO = ADHARCARD_NO;
        this.key = key;
    }
    @PropertyName("F_NAME")
    public String getF_NAME() {
        return F_NAME;
    }
    @PropertyName("M_NAME")
    public String getM_NAME() {
        return M_NAME;
    }
    @PropertyName("L_NAME")
    public String getL_NAME() {
        return L_NAME;
    }
    @PropertyName("EMAIL")
    public String getEMAIL() {
        return EMAIL;
    }
    @PropertyName("PASSWORD")
    public String getPASSWORD() {
        return PASSWORD;
    }
    @PropertyName("PHONE")
    public Long getPHONE() {
        return PHONE;
    }
    @PropertyName("ADDRESS")
    public String getADDRESS() {
        return ADDRESS;
    }
    @PropertyName("CITY")
    public String getCITY() {
        return CITY;
    }
    @PropertyName("PINCODE")
    public String getPINCODE() {
        return PINCODE;
    }
    @PropertyName("GENDER")
    public String getGENDER() {
        return GENDER;
    }

    @PropertyName("ADHARCARD_NO")
    public String getADHARCARD_NO() {
        return ADHARCARD_NO;
    }

    public void setKey(String key) {
        this.key = key;
    }
    @PropertyName("key")
    public String getKey() {
        return key;
    }

}
