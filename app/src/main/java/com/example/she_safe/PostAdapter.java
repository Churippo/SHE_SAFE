// PostAdapter.java
package com.example.she_safe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    private FirebaseAuth auth;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
        this.auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        });

        holder.btnDelete.setOnClickListener(view -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;
        private TextView contentTextView;
        private Button btnDelete;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(Post post) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                userNameTextView.setText(currentUser.getDisplayName());
            }
            contentTextView.setText(post.getContent());
        }
    }
}
