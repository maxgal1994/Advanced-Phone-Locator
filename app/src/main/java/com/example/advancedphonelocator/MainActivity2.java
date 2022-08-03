package com.example.advancedphonelocator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    TextView tvCoordinates;

    LocationManager lm;
    LocationListener locationGPSListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
//            Toast.makeText(MainActivity2.this, location.toString(), Toast.LENGTH_SHORT).show();
            if(location != null) {
                tvCoordinates.setText(""+location.getLatitude()+","+ location.getLongitude());
            }
        }
    };


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvCoordinates = findViewById(R.id.tvCoordinates);

        ActivityCompat.requestPermissions(MainActivity2.this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                123);

        startLocationCheck();

    }

    private void startLocationCheck() {


        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       //premmission is missing for using gps, turn on gps, and check if there's gps 25:00 on s2 video
        lm.requestLocationUpdates( LocationManager.GPS_PROVIDER,2000,100, locationGPSListener);

    }

    }