package com.example.she_safe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton; // Add this import
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_page);

        // Assuming you have references to your TextViews in the layout
        TextView TVPost1 = findViewById(R.id.TVPost1);
        TextView TVPost2 = findViewById(R.id.TVPost2);

        // Set text or perform any other actions with your TextViews here
        // Example of setting text
        TVPost1.setText("jsadfnkaf sdkjfasdkfj fsadfk sdalkjsadfljkash skd jahskd fj kdsa ksdj faasdf sdaf sdfsdf");
        TVPost2.setText("Another announcement text here");

        // Set up click listener for the ImageButton
        ImageButton buttonPost = findViewById(R.id.ButtonPost); // Change the type to ImageButton
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the NewPostActivity when ButtonPost is clicked
                Intent intent = new Intent(CommunityActivity.this, NewPostActivity.class);
                startActivity(intent);
            }
        });
    }
}
