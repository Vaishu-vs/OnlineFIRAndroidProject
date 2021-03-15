package com.example.onlinefir;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ShowStatusActivity extends AppCompatActivity {
    public BackgroundWorker DB;
    public ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status);
        lv = (ListView)findViewById(R.id.viewData);
        DB = new BackgroundWorker(this);
        Intent i=getIntent();
        String vi=i.getExtras().getString("victimid");
        populateListView(vi);
    }
    public void populateListView(String id)
    {
        Cursor cursor = DB.getUserData(id);
        ArrayList<String> listData=new ArrayList<>();
        while(cursor.moveToNext())
        {
            listData.add(cursor.getString(1));
            listData.add(cursor.getString(2));
            listData.add(cursor.getString(3));
            listData.add(cursor.getString(4));
            listData.add(cursor.getString(5));
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        lv.setAdapter(adapter);
    }
}