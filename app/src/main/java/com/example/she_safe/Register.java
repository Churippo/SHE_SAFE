package com.example.she_safe;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText editName, editEmail, editPassword, editPasswordConf;
    private Button btnRegister, btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.name);
        editEmail = findViewById(R.id.email_register);
        editPassword = findViewById(R.id.password_register);
        editPasswordConf = findViewById(R.id.password_conf);
        btnRegister = findViewById(R.id.button_register);
        btnLogin = findViewById(R.id.button_login);
//        ImgUserPhoto = findViewById(R.id.regUserPhoto);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        // Set a click listener for changing the profile picture

        btnLogin.setOnClickListener(v -> {
            finish();
//        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= 22) {
//                    checkAndRequestForPermission();
//                } else {
//                    openGallery();
//                }
//            }
        });

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        btnRegister.setOnClickListener(v -> {
            if (editName.getText().length() > 0 && editEmail.getText().length() > 0 && editPassword.getText().length() > 0 && editPasswordConf.getText().length() > 0) {
                if(editPassword.getText().toString().equals(editPasswordConf.getText().toString())){
                    register(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString() );
                }else {
                    Toast.makeText(getApplicationContext(), "Your password are not same", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(getApplicationContext(), "Please fill all the data", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void register(String name, String email, String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if(firebaseUser!=null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                addUserToFirestore(firebaseUser.getUid(), name, email,password);
                                reload();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void addUserToFirestore(String userId, String name, String email,String password) {
        // Assuming you have a "users" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        Map<String, Object> user = new HashMap<>();
        user.put("userId", userId);
        user.put("username", name);
        user.put("email", email);
        user.put("password",password);
        user.put("profilePictureUrl","");

        usersCollection.document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document added successfully
                        Toast.makeText(getApplicationContext(), "User added to Firestore", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        reload();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to add user to Firestore
                        Toast.makeText(getApplicationContext(), "Failed to add user to Firestore", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }



//    private void openGallery() {
//        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent, REQUESCODE);
//    }
//
//    private void checkAndRequestForPermission() {
//        if (ContextCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                Toast.makeText(Register.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
//            } else {
//                ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
//            }
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
//            pickedImgUri = data.getData();
//            ImgUserPhoto.setImageURI(pickedImgUri);
//        }
//    }

//    private void register(String name, String email, String password) {
//        progressDialog.show();
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful() && task.getResult() != null) {
//                    FirebaseUser firebaseUser = task.getResult().getUser();
//                    if (firebaseUser != null) {
//                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(name)
//                                .build();
//                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                addUserToFirestore(firebaseUser.getUid(), name, email,password);
//                                reload();
//                            }
//                        });
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void addUserToFirestore(String userId, String name, String email,String password) {
        // Assuming you have a "users" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        Map<String, Object> user = new HashMap<>();
        user.put("userId", userId);
        user.put("username", name);
        user.put("email", email);
        user.put("password",password);
        user.put("profilePictureUrl","");

        usersCollection.document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document added successfully
                        Toast.makeText(getApplicationContext(), "User added to Firestore", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        reload();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to add user to Firestore
                        Toast.makeText(getApplicationContext(), "Failed to add user to Firestore", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }
}
