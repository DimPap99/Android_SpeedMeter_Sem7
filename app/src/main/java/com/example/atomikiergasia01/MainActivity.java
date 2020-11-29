package com.example.atomikiergasia01;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener {
    SharedPreferences preferences;
    EditText editText;
    SQLiteDatabase db;
    TextView textView;
    LocationManager locationManager;
    Button set_speed_limit;
    Button check_speed_violations;

    public static boolean created_button = false;

    public static final String SHARED_PREFS = "speed_limit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        check_speed_violations = findViewById(R.id.button4);
        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.constraint_layout);
        ConstraintSet set = new ConstraintSet();
        db = openOrCreateDatabase("GeoDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Location(timestamp INTEGER ,latitude REAL,longtitude REAL, speed REAL )");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        set_speed_limit = findViewById(R.id.button2);

        check_speed_violations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        set_speed_limit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if(created_button == false) {
                     //a constraint layout pre-made in design view

                    EditText editText = Create_Component.create_EditText(set_speed_limit.getWidth() + 20,set_speed_limit.getHeight() - 10,
                            R.color.black, getApplicationContext(), constraintLayout, set_speed_limit.getLeft(), set_speed_limit.getTop() + 200, set );

                    Button button = Create_Component.create_Button(set_speed_limit.getWidth() , set_speed_limit.getHeight(),
                            R.color.purple_other, getApplicationContext(), constraintLayout,set_speed_limit.getLeft() + 9, set_speed_limit.getTop() + 400, set );
                    created_button = true;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String limit = editText.getText().toString();
                            try {
                                float limit_f = Float.parseFloat(limit);
                                save(limit_f);
                                created_button = false;
                                constraintLayout.removeView(button);
                                constraintLayout.removeView(editText);

                            }catch (Exception NumberFormatException){
                                editText.setText("Invalid Input!");
                            }
                        }
                    });

                }

            }
        });
    }


public void check_speed_violations(View view){
        Intent intent = new Intent(view.getContext(), MainActivity2.class);
        startActivity(intent);
}


    public void gps(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},234);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);}

    @Override
    public void onLocationChanged(Location location) {
        double  x = location.getLatitude();
        double y = location.getLongitude();
        double speed = location.getSpeed();
        Long tsLong = System.currentTimeMillis()/1000;

        textView.setText(String.valueOf(speed));
        if(preferences.contains("limit")){
            float limit = preferences.getFloat("limit", 0);
            if(speed > limit){
                textView.setText("STAMATA RE MALAKA");
                save(x, y, tsLong, speed);
            }
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void save(double x, double y, Long time, double speed){
        ContentValues values = new ContentValues();
        values.put("timestamp", time);
        values.put("latitude", x);
        values.put("longtitude", y);
        values.put("speed", speed);
        db.insert("Location",null, values);
    }
    public void save(float limit){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("limit", limit );
        editor.apply();
        //textView.setText(String.valueOf(preferences.getFloat("limit", 0)));
    }
}
