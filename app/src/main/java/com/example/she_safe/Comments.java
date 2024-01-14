// Comment.java
package com.example.she_safe;

public class Comments {
    private String userId;
    private String content;
    private String commentId;

    // Constructors, getters, and setters

    public Comments() {
        // Default constructor required for Firestore
    }

    public Comments(String userId, String content) {
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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
