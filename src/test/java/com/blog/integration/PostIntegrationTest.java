package com.blog.integration;

import com.blog.BlogApplication;
import com.blog.dto.request.CreatePostRequest;
import com.blog.dto.response.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BlogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAndGetPost() {
        // Given
        CreatePostRequest request = new CreatePostRequest();
        request.setTitle("Integration Test Post");
        request.setText("This is a test post for integration testing");
        request.setTags(Arrays.asList("integration", "test"));

        // When - Create post
        ResponseEntity<PostResponse> createResponse = restTemplate.postForEntity(
                "/api/posts", request, PostResponse.class);

        // Then - Verify creation
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getTitle()).isEqualTo("Integration Test Post");
        assertThat(createResponse.getBody().getText()).isEqualTo("This is a test post for integration testing");
        assertThat(createResponse.getBody().getId()).isNotNull();

        Long postId = createResponse.getBody().getId();

        // When - Get post
        ResponseEntity<PostResponse> getResponse = restTemplate.getForEntity(
                "/api/posts/" + postId, PostResponse.class);

        // Then - Verify retrieval
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getId()).isEqualTo(postId);
        assertThat(getResponse.getBody().getTitle()).isEqualTo("Integration Test Post");
    }
}