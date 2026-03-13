package com.blog.service;

import com.blog.dto.request.CreatePostRequest;
import com.blog.dto.response.PostListResponse;
import com.blog.dto.response.PostResponse;
import com.blog.model.Post;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceUnitTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost() {
        // Given
        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Test Title");
        request.setText("Test Content");
        request.setTags(Arrays.asList("tag1", "tag2"));

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setText("Test Content");
        post.setTags(Arrays.asList("tag1", "tag2"));
        post.setLikesCount(0);
        post.setCommentsCount(0);

        when(postRepository.save(any(Post.class))).thenReturn(post);

        // When
        PostResponse response = postService.createPost(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Title", response.getTitle());
        assertEquals("Test Content", response.getText());
        assertEquals(0, response.getLikesCount());
        assertEquals(0, response.getCommentsCount());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testGetPost() {
        // Given
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setTitle("Test Title");
        post.setText("Test Content");
        post.setTags(Arrays.asList("tag1", "tag2"));
        post.setLikesCount(5);
        post.setCommentsCount(3);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // When
        PostResponse response = postService.getPost(postId);

        // Then
        assertNotNull(response);
        assertEquals(postId, response.getId());
        assertEquals("Test Title", response.getTitle());
        assertEquals("Test Content", response.getText());
        assertEquals(5, response.getLikesCount());
        assertEquals(3, response.getCommentsCount());

        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void testGetPostNotFound() {
        // Given
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postService.getPost(postId);
        });

        assertEquals("Post not found with id: " + postId, exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
    }
}