package com.example.she_safe;

public class Post {
    private String content;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
