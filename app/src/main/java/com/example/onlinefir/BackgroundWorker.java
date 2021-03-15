package com.example.onlinefir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BackgroundWorker extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "onlinfirproject.db";
    public static final String TABLE_NAME = "registration_table";
    public static final String TABLE_NAME1 = "firdata";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "MIDDLENAME";
    public static final String COL_4 = "LASTNAME";
    public static final String COL_5 = "EMAIL";
    public static final String COL_6 = "PASSWORD";
    public static final String COL_7 = "PHONENO";
    public static final String COL_8 = "ADDRESS";
    public static final String COL_9 = "CITY";
    public static final String COL_10 = "PINCODE";
    public static final String COL_11 = "GENDER";
    public static final String COL_12 = "BIRTHDATE";

    public static final String COL1 = "ID";
    public static final String COL2 = "USERNAME";
    public static final String COL3 = "EMAIL";
    public static final String COL4 = "CRIMESPOT";
    public static final String COL5 = "PINCODE";
    public static final String COL6 = "DESCRIPTION";
    public static final String COL7 = "DATEOFINCIDENT";
    public static final String COL8 = "TIMEOFINCIDENT";

    private SQLiteDatabase db;
    private int oldVersion;
    private int newVersion;

    public BackgroundWorker(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,MIDDLENAME TEXT,LASTNAME TEXT,EMAIL TEXT,PASSWORD TEXT,PHONENO INTEGER,ADDRESS TEXT,CITY TEXT,PINCODE INTEGER,GENDER TEXT,BIRTHDATE TEXT)");
        db.execSQL("create table " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,CRIMESPOT TEXT,PINCODE TEXT,DESCRIPTION TEXT,DATEOFINCIDENT TEXT,TIMEOFINCIDENT TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(db);
    }

    public boolean insertData(String first_name, String middle_name, String last_name, String email, String password, String phone_no, String address, String city, String pincode, String gender, String birthdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, first_name);
        contentValues.put(COL_3, middle_name);
        contentValues.put(COL_4, last_name);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, password);
        contentValues.put(COL_7, phone_no);
        contentValues.put(COL_8, address);
        contentValues.put(COL_9, city);
        contentValues.put(COL_10, pincode);
        contentValues.put(COL_11, gender);
        contentValues.put(COL_12, birthdate);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertFirData(String user_name, String email, String crime_spot, String pincode, String description, String date_of_incident, String time_of_incident) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, user_name);
        cv.put(COL3, email);
        cv.put(COL4, crime_spot);
        cv.put(COL5, pincode);
        cv.put(COL6, description);
        cv.put(COL7, date_of_incident);
        cv.put(COL8, time_of_incident);
        long result = db.insert(TABLE_NAME1, null, cv);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getUserData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM firdata WHERE id=?", new String[]{id});
        return data;
    }

    public boolean emailpwd(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_5 + " =? AND " + COL_6 + "=?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public int get_Id() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME1, null, "ID=(SELECT MAX(ID) FROM " + TABLE_NAME1 + ")", null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            return id;
        } else {
            return 0;
        }
    }
}
