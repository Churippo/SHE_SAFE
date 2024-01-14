package com.example.she_safe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Emergency_Contact extends AppCompatActivity {

    private EditText emergencyContactNameEditText;
    private EditText emergencyContactEditText;
    private TextView displayEmergencyContactTextView;

    private ImageView bekbekbek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        emergencyContactNameEditText = findViewById(R.id.emergencyContactNameEditText);
        emergencyContactEditText = findViewById(R.id.emergencyContactEditText);
        displayEmergencyContactTextView = findViewById(R.id.displayEmergencyContactTextView);
        bekbekbek = findViewById(R.id.back);

        bekbekbek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Emergency_Contact.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Load the saved emergency contact information
        loadEmergencyContact();

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmergencyContact();
            }
        });
    }

    private void loadEmergencyContact() {
        // Retrieve the saved emergency contact information from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String emergencyContactName = sharedPreferences.getString("emergencyContactName", "");
        String emergencyContact = sharedPreferences.getString("emergencyContact", "");

        emergencyContactNameEditText.setText(emergencyContactName);
        emergencyContactEditText.setText(emergencyContact);

        // Display the saved emergency contact information in the TextView
        displayEmergencyContactTextView.setText("Emergency Contact: " + emergencyContactName + " - " + emergencyContact);
    }

    private void saveEmergencyContact() {
        // Save the entered emergency contact information to SharedPreferences
        String emergencyContactName = emergencyContactNameEditText.getText().toString().trim();
        String emergencyContact = emergencyContactEditText.getText().toString().trim();

        if (!emergencyContact.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("emergencyContactName", emergencyContactName);
            editor.putString("emergencyContact", emergencyContact);
            editor.apply();

            // Provide feedback to the user
            // You can use a Toast or any other UI element to inform the user
            // that the emergency contact has been saved.

            // Display the saved emergency contact information in the TextView
            displayEmergencyContactTextView.setText("Emergency Contact: " + emergencyContactName + " - " + emergencyContact);
        } else {
            // Handle the case where the entered emergency contact is empty
        }
    }
}