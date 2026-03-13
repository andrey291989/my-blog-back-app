package com.blog.service;

import com.blog.dto.request.CreateCommentRequest;
import com.blog.dto.request.UpdateCommentRequest;
import com.blog.dto.response.CommentResponse;
import com.blog.model.Comment;
import com.blog.model.Post;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public CommentResponse getComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (!comment.getPostId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to post with id: " + postId);
        }

        return convertToResponse(comment);
    }

    @Transactional
    public CommentResponse createComment(Long postId, CreateCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setPostId(postId);

        Comment savedComment = commentRepository.save(comment);

        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);

        return convertToResponse(savedComment);
    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (!comment.getPostId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to post with id: " + postId);
        }

        comment.setText(request.getText());

        Comment updatedComment = commentRepository.save(comment);
        return convertToResponse(updatedComment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (!comment.getPostId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to post with id: " + postId);
        }

        commentRepository.deleteById(commentId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        post.setCommentsCount(Math.max(0, post.getCommentsCount() - 1));
        postRepository.save(post);
    }

    private CommentResponse convertToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getPostId()
        );
    }
}