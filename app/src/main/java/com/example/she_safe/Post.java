// Post.java
package com.example.she_safe;

public class Post {
    private String userId;
    private String content;
    private String postId;

    // Constructors, getters, and setters

    public Post() {
        // Default constructor required for Firestore
    }

    public Post(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
