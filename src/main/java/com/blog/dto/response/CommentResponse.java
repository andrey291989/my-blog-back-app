package com.blog.dto.response;

public class CommentResponse {
    private Long id;
    private String text;
    private Long postId;

    public CommentResponse() {}

    public CommentResponse(Long id, String text, Long postId) {
        this.id = id;
        this.text = text;
        this.postId = postId;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
}