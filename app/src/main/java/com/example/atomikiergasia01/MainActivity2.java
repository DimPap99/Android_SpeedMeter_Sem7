package com.example.atomikiergasia01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    public List<String> timestamps;
    public List<String> information;
    SQLiteDatabase db;
    public ListView listView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        timestamps = new ArrayList<String>();
        information = new ArrayList<String>();
        db = openOrCreateDatabase("GeoDB", Context.MODE_PRIVATE, null);
//        timestamps.add("Timestamp");
//        information.add("Geo Info");
        Cursor cursor = db.rawQuery("SELECT * FROM Location", null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                timestamps.add(cursor.getString(0));
                information.add("Lat: " + cursor.getString(1) + "\n"
                        + "Long: " + cursor.getString(2) + "\n" + "Speed:" + cursor.getString(3));
            }

        }
        listView = findViewById(R.id.listview);
        listView.setAdapter(new SPListAdapter(this, timestamps, information));
        back = findViewById(R.id.button3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}