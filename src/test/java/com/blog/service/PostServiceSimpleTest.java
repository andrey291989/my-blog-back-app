package com.blog.service;

import com.blog.dto.request.CreatePostRequest;
import com.blog.dto.response.PostResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceSimpleTest {

    @Test
    void testCreatePostRequest() {
        // Given
        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Test Title");
        request.setText("Test Content");
        request.setTags(Arrays.asList("tag1", "tag2"));

        // When & Then
        assertEquals("Test Title", request.getTitle());
        assertEquals("Test Content", request.getText());
        assertEquals(Arrays.asList("tag1", "tag2"), request.getTags());
    }

    @Test
    void testPostResponse() {
        // Given
        PostResponse response = new PostResponse();
        response.setId(1L);
        response.setTitle("Test Title");
        response.setText("Test Content");
        response.setTags(Arrays.asList("tag1", "tag2"));
        response.setLikesCount(5);
        response.setCommentsCount(3);

        // When & Then
        assertEquals(1L, response.getId());
        assertEquals("Test Title", response.getTitle());
        assertEquals("Test Content", response.getText());
        assertEquals(Arrays.asList("tag1", "tag2"), response.getTags());
        assertEquals(5, response.getLikesCount());
        assertEquals(3, response.getCommentsCount());
    }
}