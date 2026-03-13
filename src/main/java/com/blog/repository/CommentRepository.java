package com.blog.repository;

import com.blog.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Comment> findAll() {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c ORDER BY c.createdAt DESC", Comment.class);
        return query.getResultList();
    }

    public Optional<Comment> findById(Long id) {
        Comment comment = entityManager.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
        } else {
            comment = entityManager.merge(comment);
        }
        return comment;
    }

    public void delete(Comment comment) {
        entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
    }

    public void deleteById(Long id) {
        Optional<Comment> comment = findById(id);
        if (comment.isPresent()) {
            delete(comment.get());
        }
    }

    public List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId) {
        TypedQuery<Comment> query = entityManager.createQuery(
            "SELECT c FROM Comment c WHERE c.postId = :postId ORDER BY c.createdAt DESC", Comment.class);
        query.setParameter("postId", postId);
        return query.getResultList();
    }

    public void deleteByPostId(Long postId) {
        TypedQuery<Comment> query = entityManager.createQuery(
            "SELECT c FROM Comment c WHERE c.postId = :postId", Comment.class);
        query.setParameter("postId", postId);
        List<Comment> comments = query.getResultList();

        for (Comment comment : comments) {
            delete(comment);
        }
    }
}