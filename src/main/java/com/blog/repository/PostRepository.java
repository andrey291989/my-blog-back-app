package com.blog.repository;

import com.blog.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Post> findAll() {
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p ORDER BY p.createdAt DESC", Post.class);
        return query.getResultList();
    }

    public List<Post> findAll(int pageNumber, int pageSize) {
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p ORDER BY p.createdAt DESC", Post.class);
        // Исправляем расчет firstResult чтобы избежать отрицательных значений
        int firstResult = Math.max(0, pageNumber * pageSize);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Optional<Post> findById(Long id) {
        Post post = entityManager.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    public Post save(Post post) {
        System.out.println("Saving post with ID: " + post.getId());
        System.out.println("Post created at: " + post.getCreatedAt());
        System.out.println("Post updated at: " + post.getUpdatedAt());

        if (post.getId() == null) {
            System.out.println("Persisting new post");
            entityManager.persist(post);
        } else {
            System.out.println("Merging existing post");
            post = entityManager.merge(post);
        }

        // После сохранения объект может быть изменен, особенно если это новый объект
        System.out.println("Post saved with ID: " + post.getId());
        return post;
    }

    public void delete(Post post) {
        entityManager.remove(entityManager.contains(post) ? post : entityManager.merge(post));
    }

    public void deleteById(Long id) {
        Optional<Post> post = findById(id);
        if (post.isPresent()) {
            delete(post.get());
        }
    }

    public boolean existsById(Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(p) FROM Post p WHERE p.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    public List<Post> findByTitleOrTextContainingIgnoreCase(String search, int pageNumber, int pageSize) {
        TypedQuery<Post> query = entityManager.createQuery(
            "SELECT p FROM Post p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.text) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "ORDER BY p.createdAt DESC", Post.class);
        query.setParameter("search", search);
        // Исправляем расчет firstResult чтобы избежать отрицательных значений
        int firstResult = Math.max(0, pageNumber * pageSize);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int countByTitleOrTextContainingIgnoreCase(String search) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(p) FROM Post p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.text) LIKE LOWER(CONCAT('%', :search, '%'))", Long.class);
        query.setParameter("search", search);
        return query.getSingleResult().intValue();
    }

    public int getTotalCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(p) FROM Post p", Long.class);
        return query.getSingleResult().intValue();
    }
}