package com.example.she_safe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

public class SettingPageActivity extends AppCompatActivity {

    private Switch darkModeSwitch;
    private Button shareAppButton, helpButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        // Initialize Dark Mode Toggle
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        // Initialize Share App Button
        shareAppButton = findViewById(R.id.shareAppButton);

        // Initialize Help Button
        helpButton = findViewById(R.id.helpButton);

        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SettingPageActivity.this, Login.class));
            finish(); // Close the current activity to prevent returning to it with the back button
        });
        // Set Click Listeners
        shareAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Share App Button Click
                shareAppClick();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Help Button Click
                helpClick();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Logout Button Click
                logoutClick();
            }
        });


        // Set Dark Mode Switch listener
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle dark mode based on the switch state
            if (isChecked) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Disable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate(); // Apply the changes
        });
    }

    // Functionality for Share App Button Click
    private void shareAppClick() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I found a great app called SheSafe. Check it out!");
        startActivity(Intent.createChooser(shareIntent, "Share SheSafe with friends"));
    }

    // Functionality for Help Button Click
    private void helpClick() {
        Toast.makeText(this, "Navigate to Help Section", Toast.LENGTH_SHORT).show();
    }

    // Functionality for Logout Button Click

    private void logoutClick() {
        Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
    }
}
