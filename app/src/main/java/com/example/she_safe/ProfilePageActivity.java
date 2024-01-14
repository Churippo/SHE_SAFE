package com.example.she_safe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfilePageActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profilePictureImageView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button editProfilePictureButton;
    private Button saveButton;

    private Uri imageUri;

    private FirebaseFirestore db;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        editProfilePictureButton = findViewById(R.id.editProfilePictureButton);
        saveButton = findViewById(R.id.saveButton);

        // Load user data
        loadUserData();

        editProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });
    }

    private void loadUserData() {
        // Retrieve user data from Firestore and update UI
        // Assuming your user data is stored in a document named "profile"
        DocumentReference userRef = db.collection("Users").document("nxRrSpdgwWTDuwMQI9Wx");

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // User data found
                    String username = documentSnapshot.getString("username");
                    String password = documentSnapshot.getString("password");
                    String profilePictureUrl = documentSnapshot.getString("profilePictureUrl");

                    usernameEditText.setText(username);
                    passwordEditText.setText(password);

                    // Load profile picture using an image loading library or your preferred method
                    // For simplicity, you can use Glide or Picasso
                    Glide.with(ProfilePageActivity.this).load(profilePictureUrl).into(profilePictureImageView);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failure to retrieve user data
                Toast.makeText(ProfilePageActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profilePictureImageView.setImageURI(imageUri);
        }
    }

    private void saveChanges() {
        DocumentReference userRef = db.collection("Users").document("nxRrSpdgwWTDuwMQI9Wx");

        // Update username and password
        String newUsername = usernameEditText.getText().toString();
        String newPassword = passwordEditText.getText().toString();

        userRef.update("username", newUsername, "password", newPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Update successful
                        Toast.makeText(ProfilePageActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                        // If a new profile picture is selected, upload it to Firebase Storage
                        if (imageUri != null) {
                            uploadProfilePicture("nxRrSpdgwWTDuwMQI9Wx");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to update user data
                        Toast.makeText(ProfilePageActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadProfilePicture(String userId) {
        // Upload the new profile picture to Firebase Storage
        StorageReference profilePictureRef = storageRef.child("profile_pictures/" + userId);

        profilePictureRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        profilePictureRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Update the profile picture URL in Firestore
                                db.collection("Users").document("nxRrSpdgwWTDuwMQI9Wx")
                                        .update("profilePictureUrl", uri.toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Profile picture URL updated successfully
                                                Toast.makeText(ProfilePageActivity.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle failure to update profile picture URL
                                                Toast.makeText(ProfilePageActivity.this, "Failed to update profile picture URL", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to upload the new profile picture
                        Toast.makeText(ProfilePageActivity.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
