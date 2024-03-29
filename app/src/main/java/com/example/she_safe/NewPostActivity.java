package com.example.she_safe;
// Import statements for necessary classes

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPostActivity extends AppCompatActivity {

    private EditText editTextPostContent;
    private Button buttonSubmitPost;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // Initialize views
        editTextPostContent = findViewById(R.id.editTextPostContent);
        buttonSubmitPost = findViewById(R.id.buttonSubmitPost);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");

        // Set onClickListener for the button
        buttonSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the submit post button click event
                String postContent = editTextPostContent.getText().toString();

                // Check if postContent is not empty
                if (!postContent.isEmpty()) {
                    // Save the post to the database
                    savePostToDatabase(postContent);
                }
            }
        });
    }

    private void savePostToDatabase(String postContent) {
        // Assume you have the user's ID, replace "user_id_placeholder" with the actual user ID
        String userId = "user_id_placeholder";

        // Generate a unique key for the post
        String postId = databaseReference.push().getKey();

        // Create a Post object with the user ID and post content
        Post post = new Post(userId, postContent);

        // Save the post to the database using the generated key
        databaseReference.child(postId).setValue(post);
    }
}

