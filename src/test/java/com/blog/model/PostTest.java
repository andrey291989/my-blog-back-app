package com.blog.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void testPostCreation() {
        // Given
        Post post = new Post();

        // When
        post.setId(1L);
        post.setTitle("Test Title");
        post.setText("Test Content");
        post.setTags(Arrays.asList("tag1", "tag2"));
        post.setLikesCount(5);
        post.setCommentsCount(3);

        // Then
        assertEquals(1L, post.getId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getText());
        assertEquals(Arrays.asList("tag1", "tag2"), post.getTags());
        assertEquals(5, post.getLikesCount());
        assertEquals(3, post.getCommentsCount());
    }

    @Test
    void testPostLifecycleMethods() {
        // Given
        Post post = new Post();

        // When
        post.onCreate();
        LocalDateTime createdAt = post.getCreatedAt();
        LocalDateTime updatedAt = post.getUpdatedAt();

        // Then
        assertNotNull(createdAt);
        assertNotNull(updatedAt);

        // When
        post.onUpdate();
        LocalDateTime newUpdatedAt = post.getUpdatedAt();

        // Then
        assertNotNull(newUpdatedAt);
    }
}