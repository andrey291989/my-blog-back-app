package com.blog.service;

import com.blog.dto.request.CreatePostRequest;
import com.blog.dto.request.UpdatePostRequest;
import com.blog.dto.response.PostListResponse;
import com.blog.dto.response.PostListItemResponse;
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

class PostServiceTest {

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
    void testGetPosts() {
        // Given
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");
        post1.setText("Content 1");
        post1.setTags(Arrays.asList("tag1"));
        post1.setLikesCount(10);
        post1.setCommentsCount(5);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Post 2");
        post2.setText("Content 2");
        post2.setTags(Arrays.asList("tag2"));
        post2.setLikesCount(15);
        post2.setCommentsCount(8);

        List<Post> posts = Arrays.asList(post1, post2);
        List<PostListItemResponse> postItems = Arrays.asList(
            new PostListItemResponse(1L, "Post 1", "Content 1", Arrays.asList("tag1"), 10, 5),
            new PostListItemResponse(2L, "Post 2", "Content 2", Arrays.asList("tag2"), 15, 8)
        );

        when(postRepository.findAll(0, 10)).thenReturn(posts);
        when(postRepository.getTotalCount()).thenReturn(2);

        // When
        PostListResponse response = postService.getPosts("", 0, 10);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getPosts().size());
        assertFalse(response.isHasPrev());
        assertFalse(response.isHasNext());
        assertEquals(1, response.getLastPage());

        verify(postRepository, times(1)).findAll(0, 10);
        verify(postRepository, times(1)).getTotalCount();
    }
}