// CommentActivity.java
package com.example.she_safe;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String postId;
    private List<Comments> commentList;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        db = FirebaseFirestore.getInstance();
        postId = getIntent().getStringExtra("postId");
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);

        RecyclerView recyclerView = findViewById(R.id.commentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);

        // Retrieve and display comments for the selected post from Firestore
        db.collection("comments").whereEqualTo("postId", postId).addSnapshotListener((value, error) -> {
            if (error != null) {
                // Handle errors
                return;
            }

            commentList.clear();
            for (QueryDocumentSnapshot document : value) {
                Comments comments = document.toObject(Comments.class);
                commentList.add(comments);
            }

            commentAdapter.notifyDataSetChanged();
        });
    }
}
