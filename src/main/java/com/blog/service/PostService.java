package com.blog.service;

import com.blog.dto.request.CreatePostRequest;
import com.blog.dto.request.UpdatePostRequest;
import com.blog.dto.response.PostListResponse;
import com.blog.dto.response.PostListItemResponse;
import com.blog.dto.response.PostResponse;
import com.blog.model.Post;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.util.TextTruncator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public PostListResponse getPosts(String search, int pageNumber, int pageSize) {
        List<Post> posts;
        int totalCount;

        if (search == null || search.isEmpty()) {
            posts = postRepository.findAll(pageNumber, pageSize);
            totalCount = postRepository.getTotalCount();
        } else {
            posts = postRepository.findByTitleOrTextContainingIgnoreCase(search, pageNumber, pageSize);
            totalCount = postRepository.countByTitleOrTextContainingIgnoreCase(search);
        }

        List<PostListItemResponse> postItems = posts.stream()
                .map(this::convertToListItemResponse)
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        return new PostListResponse(
                postItems,
                pageNumber > 1,
                pageNumber < totalPages,
                totalPages
        );
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return convertToResponse(post, false);
    }

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setText(request.getText());
        post.setTags(request.getTags());
        post.setLikesCount(0);
        post.setCommentsCount(0);

        // Добавим логирование для отладки
        System.out.println("Creating post with title: " + post.getTitle());
        System.out.println("Post created at: " + post.getCreatedAt());
        System.out.println("Post updated at: " + post.getUpdatedAt());

        Post savedPost = postRepository.save(post);

        System.out.println("Saved post ID: " + savedPost.getId());

        return convertToResponse(savedPost, false);
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        post.setTitle(request.getTitle());
        post.setText(request.getText());
        post.setTags(request.getTags());

        Post updatedPost = postRepository.save(post);
        return convertToResponse(updatedPost, false);
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }

    @Transactional
    public int incrementLikes(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
        return post.getLikesCount();
    }

    @Transactional
    public void updateImage(Long id, byte[] image) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        post.setImage(image);
        postRepository.save(post);
    }

    public byte[] getImage(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        return post.getImage();
    }

    private PostResponse convertToResponse(Post post, boolean truncate) {
        String text = truncate ? TextTruncator.truncate(post.getText(), 128) : post.getText();

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                text,
                post.getTags(),
                post.getLikesCount(),
                post.getCommentsCount()
        );
    }

    private PostListItemResponse convertToListItemResponse(Post post) {
        return new PostListItemResponse(
                post.getId(),
                post.getTitle(),
                TextTruncator.truncate(post.getText(), 128),
                post.getTags(),
                post.getLikesCount(),
                post.getCommentsCount()
        );
    }
}