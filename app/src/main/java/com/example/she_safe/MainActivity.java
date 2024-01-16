package com.example.she_safe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        ImageView profilePictureImageView = findViewById(R.id.profilePictureImageView);
        ImageView iconMap = findViewById(R.id.iconMap);
        TextView locationTextView = findViewById(R.id.locationTextView);
        ImageView settingImageView = findViewById(R.id.settingImageView);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        ImageView imageView7 = findViewById(R.id.imageView7);


        // Set images for the ImageViews
        iconMap.setImageResource(R.drawable.location);
        settingImageView.setImageResource(R.drawable.setting);
        imageView1.setImageResource(R.drawable.map);
        imageView2.setImageResource(R.drawable.community);
        imageView3.setImageResource(R.drawable.tips);
        imageView4.setImageResource(R.drawable.messages);
        imageView7.setImageResource(R.drawable.sos);


        usernameTextView = findViewById(R.id.usernameTextView);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null) {
            usernameTextView.setText(firebaseUser.getDisplayName());
        }else{
            usernameTextView.setText("Login Failed");
        }
        locationTextView.setText("KK12, Petaling Jaya");

        loadUserProfilePicture(profilePictureImageView);

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

    private void loadUserProfilePicture(ImageView profilePictureImageView) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // User data found
                    String profilePictureUrl = documentSnapshot.getString("profilePictureUrl");

                    // Load profile picture using Glide or your preferred method
                    Glide.with(MainActivity.this).load(profilePictureUrl).into(profilePictureImageView);
                }
            }
        });
    }

}