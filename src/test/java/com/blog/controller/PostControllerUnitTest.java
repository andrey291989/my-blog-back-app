package com.blog.controller;

import com.blog.dto.request.CreatePostRequest;
import com.blog.dto.response.PostListResponse;
import com.blog.dto.response.PostResponse;
import com.blog.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatePost() throws Exception {
        // Given
        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Test Title");
        request.setText("Test Content");
        request.setTags(Arrays.asList("tag1", "tag2"));

        PostResponse response = new PostResponse();
        response.setId(1L);
        response.setTitle("Test Title");
        response.setText("Test Content");
        response.setTags(Arrays.asList("tag1", "tag2"));
        response.setLikesCount(0);
        response.setCommentsCount(0);

        when(postService.createPost(any(CreatePostRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.text").value("Test Content"));
    }

    @Test
    void testGetPost() throws Exception {
        // Given
        Long postId = 1L;
        PostResponse response = new PostResponse();
        response.setId(postId);
        response.setTitle("Test Title");
        response.setText("Test Content");
        response.setTags(Arrays.asList("tag1", "tag2"));
        response.setLikesCount(5);
        response.setCommentsCount(3);

        when(postService.getPost(postId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.likesCount").value(5));
    }

    @Test
    void testDeletePost() throws Exception {
        // Given
        Long postId = 1L;
        doNothing().when(postService).deletePost(postId);

        // When & Then
        mockMvc.perform(delete("/api/posts/{id}", postId))
                .andExpect(status().isOk());
    }
}