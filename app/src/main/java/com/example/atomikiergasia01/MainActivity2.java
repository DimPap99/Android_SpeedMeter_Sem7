package com.example.atomikiergasia01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements OnMapReadyCallback {
    public List<String> timestamps;
    public List<String> latitude;
    public List<String> longtitude;
    public List<String> speed;
    SQLiteDatabase db;
    public ListView listView;
    Button back;
    private MapView mapView;
    private GoogleMap gmap;
    private static boolean map_ready = false;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        timestamps = new ArrayList<String>();
        latitude = new ArrayList<String>();
        longtitude = new ArrayList<String>();
        speed = new ArrayList<String>();
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        db = openOrCreateDatabase("GeoDB", Context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("SELECT * FROM Location", null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                //timestamps.add(cursor.getString(0));
                timestamps.add(String.valueOf(cursor.getString(0))) ;
                latitude.add(String.valueOf(cursor.getString(1)));
                longtitude.add(String.valueOf(cursor.getString(2)));
                speed.add(String.valueOf(cursor.getString(3)));
            }

        }
        listView = findViewById(R.id.listview);
        listView.setAdapter(new SPListAdapter(this, timestamps, latitude, longtitude, speed));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String timestamp = ((TextView) view.findViewById(R.id.timestamp)).getText().toString();
                String lat = ((TextView) view.findViewById(R.id.latitude)).getText().toString();
                String lgt = ((TextView) view.findViewById(R.id.longtitude)).getText().toString();
                String speed = ((TextView) view.findViewById(R.id.speed)).getText().toString();
                String toast_str = "Location for timestamp: " + timestamp + " and Speed(km/h): " + speed;
                Toast toast = Toast.makeText(getApplicationContext(), toast_str, Toast.LENGTH_SHORT);
                toast.show();
                if(map_ready == true){
                    LatLng pos = new LatLng(Double.parseDouble(lat), Double.parseDouble(lgt));
                    gmap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title("lat: " + lat + " lgt: " + lgt ));
                    gmap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                }
            }
        });
        back = findViewById(R.id.button3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        map_ready = true;
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}