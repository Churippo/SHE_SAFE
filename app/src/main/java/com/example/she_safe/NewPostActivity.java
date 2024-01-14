package com.example.she_safe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewPostActivity extends AppCompatActivity {

    private TextView TVNewPost;
    private TextView textViewPostLabel;
    private EditText editTextPostContent;
    private Button buttonSubmitPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // Initialize views
        TVNewPost = findViewById(R.id.TVNewPost);
        textViewPostLabel = findViewById(R.id.textViewPostLabel);
        editTextPostContent = findViewById(R.id.editTextPostContent);
        buttonSubmitPost = findViewById(R.id.buttonSubmitPost);

        // Set onClickListener for the button
        buttonSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the submit post button click event
                String postContent = editTextPostContent.getText().toString();
                // You can add your logic to handle the post content here
            }
        });
    }
}
