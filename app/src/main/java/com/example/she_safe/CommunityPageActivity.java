package com.example.she_safe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CommunityPageActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private List<Post> postList;
    private PostAdapter postAdapter;

    private ImageView bekbekbek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_page);

        bekbekbek = findViewById(R.id.back);

        bekbekbek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList);

        EditText postEditText = findViewById(R.id.postEditText);
        Button postButton = findViewById(R.id.postButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);

        postAdapter.setOnItemClickListener((view, position) -> {
            Post post = postList.get(position);
            Intent intent = new Intent(CommunityPageActivity.this, CommentActivity.class);
            intent.putExtra("postId", post.getPostId());
            startActivity(intent);
        });

        postAdapter.setOnDeleteClickListener((view, position) -> {
            // Handle delete button click
            deletePost(position);
        });

        postButton.setOnClickListener(v -> addPost(postEditText.getText().toString()));

        // Retrieve and display posts from Firestore
        db.collection("posts").addSnapshotListener((value, error) -> {
            if (error != null) {
                // Handle errors
                return;
            }

            postList.clear();
            for (QueryDocumentSnapshot document : value) {
                Post post = document.toObject(Post.class);
                postList.add(post);
            }

            postAdapter.notifyDataSetChanged();
        });
    }

    private void addPost(String content) {
        if (!content.isEmpty()) {
            Post post = new Post();
            post.setUserId(auth.getCurrentUser().getUid());
            post.setContent(content);

            db.collection("posts").add(post)
                    .addOnSuccessListener(documentReference -> {
                        post.setPostId(documentReference.getId());
                        db.collection("posts").document(documentReference.getId()).set(post);

                        // Clear the editText
                        EditText postEditText = findViewById(R.id.postEditText);
                        postEditText.setText("");
                    })
                    .addOnFailureListener(e -> {
                        // Handle errors
                    });
        }
    }

    private void deletePost(int position) {
        if (position >= 0 && position < postList.size()) {
            Post post = postList.get(position);
            String postId = post.getPostId();

            db.collection("posts").document(postId).delete()
                    .addOnSuccessListener(aVoid -> {
                        // Post deleted successfully
                    })
                    .addOnFailureListener(e -> {
                        // Handle errors
                    });
        }
    }
}
