package com.example.atomikiergasia01;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
    TextView textView;
    LocationManager locationManager;
    Button set_speed_limit;
    public static boolean created_button = false;

    public static final String SHARED_PREFS = "speed_limit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        set_speed_limit = findViewById(R.id.button2);

        set_speed_limit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if(created_button == false) {
                    ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.constraint_layout); //a constraint layout pre-made in design view
                    EditText editText = new EditText(getApplicationContext());
                    editText.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    editText.setWidth(set_speed_limit.getWidth() + 20 );
                    editText.setHeight(set_speed_limit.getHeight() - 10);
                    editText.setId(View.generateViewId());
                    constraintLayout.addView(editText);
                    //button.setId();
                    int x = set_speed_limit.getLeft() ;
                    int y = set_speed_limit.getTop() + 200;
                    ConstraintSet set = new ConstraintSet();
                    set.clone(constraintLayout);
                    set.connect(editText.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, x);
                    set.connect(editText.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, y);
                    set.applyTo(constraintLayout);

                    Button button = new Button(getApplicationContext());
                    button.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    button.setText("Save");
                    button.setPadding(10, 2, 10, 2);
                    button.setWidth(set_speed_limit.getWidth() - 15);
                    button.setHeight(set_speed_limit.getHeight() - 100);
                    button.setBackgroundColor(R.color.purple_other);
                    button.setTextColor(Color.parseColor("#FFFFFF"));
                    button.setId(View.generateViewId());
                    constraintLayout.addView(button);
                    //button.setId();
                    int x2 = set_speed_limit.getLeft() + 9 ;
                    int y2 = set_speed_limit.getTop() + 400;
                    //ConstraintSet set2 = new ConstraintSet();
                    set.clone(constraintLayout);
                    set.connect(button.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, x2);
                    set.connect(button.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, y2);
                    set.applyTo(constraintLayout);

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





    public void gps(View view) {
        //textView.setText("In func");
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
        textView.setText(String.valueOf(location.getSpeed()));

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


    public void save(float limit){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("limit", String.valueOf(limit) );
        editor.apply();
    }
}
