package com.example.she_safe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        ImageView profilePictureImageView = findViewById(R.id.profilePictureImageView);
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        ImageView iconMap = findViewById(R.id.iconMap);
        TextView locationTextView = findViewById(R.id.locationTextView);
        ImageView settingImageView = findViewById(R.id.settingImageView);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);


        // Set images for the ImageViews
        iconMap.setImageResource(R.drawable.location);
        settingImageView.setImageResource(R.drawable.setting);
        imageView1.setImageResource(R.drawable.map);
        imageView2.setImageResource(R.drawable.community);
        imageView3.setImageResource(R.drawable.tips);
        imageView4.setImageResource(R.drawable.messages);


        // Set text for TextViews
        usernameTextView.setText("Hello, Fatihah");
        locationTextView.setText("KK12, Petaling Jaya");


        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
                startActivity(intent);
            }
        });

        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingPageActivity.class);
                startActivity(intent);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationTrackingActivity.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CommunityPageActivity.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipsPageActivity.class);
                startActivity(intent);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Emergency_Contact.class);
                startActivity(intent);
            }
        });
    }


}