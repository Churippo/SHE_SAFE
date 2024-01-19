package com.example.she_safe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private ImageView btn_in, btn_out, btn_group, btn_send;
    private Button btn_back, btn_sos, btn_chat;
    private static final int YOUR_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tracking);

        btn_in = findViewById(R.id.btn_zoom_in);
        btn_out = findViewById(R.id.btn_zoom_out);
        btn_group = findViewById(R.id.btn_group);
        btn_send = findViewById(R.id.btn_send);
        btn_back = findViewById(R.id.btn_back);
        btn_sos = findViewById(R.id.btn_sos);
        btn_chat = findViewById(R.id.btn_chat);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
         // Request the device's last known location

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(LocationTrackingActivity.this);

        btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        btn_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnemergency(view);
            }

        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                triggerPanicAlarm();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLastLocation();
            }
        });

        btn_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void getLastLocation() {
        // Check if the app has permission to access the device's location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        // Get the last known location using FusedLocationProviderClient
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Update the currentLocation and move the map camera to the new location
                    currentLocation = location;
                    updateMapWithLocation(currentLocation);
                }
            }
        });
    }

    private void updateMapWithLocation(Location location) {
        // Update the map with the new location
        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        myMap.addMarker(new MarkerOptions().position(currentLatLng).title("My Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;

        LatLng CurrentLocation = new LatLng(4.2105, 101.9758);
        myMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentLocation));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 8f));
        myMap.getUiSettings().setZoomControlsEnabled(false);
        myMap.getUiSettings().setCompassEnabled(true);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }else {
                Toast.makeText(this, "Location permission is denied, please allow the permission to access", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == YOUR_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the call
                btnemergency(null); // Call the method again to initiate the call
            } else {
                // Permission denied, show a message or handle accordingly
            }

        }
    }

    public void btnemergency(View view) {
        String number = "+60179757461";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, YOUR_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, start the call
            startActivity(intent);
        }
    }

    public void triggerPanicAlarm() {
        // Retrieve the emergency contact phone number from SharedPreferences
        String emergencyPhoneNumber = getEmergencyContact();

        // Check if the emergency phone number is not empty before proceeding
        if (!emergencyPhoneNumber.isEmpty()) {
            // Get the user's current location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request location permission if not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
                return;
            }

            // Get the last known location using FusedLocationProviderClient
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Build the SMS message with the user's current location
                        String message = "Emergency! I need help! My current location is: " +
                                "Latitude: " + location.getLatitude() +
                                ", Longitude: " + location.getLongitude();

                        // Send the SMS
                        sendSms(emergencyPhoneNumber, message);
                    }
                }
            });
        } else {
            // Handle the case where the emergency contact is not set
        }
    }

    private String getEmergencyContact() {
        // Retrieve the emergency contact from SharedPreferences
        return getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getString("emergencyContact", "");
    }

    private void sendSms(String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}