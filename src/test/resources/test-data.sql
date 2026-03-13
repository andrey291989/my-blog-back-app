INSERT INTO posts (id, title, text, likes_count, comments_count, created_at, updated_at)
VALUES (1, 'Test Post 1', 'This is the first test post', 0, 0, NOW(), NOW());

INSERT INTO posts (id, title, text, likes_count, comments_count, created_at, updated_at)
VALUES (2, 'Test Post 2', 'This is the second test post', 0, 0, NOW(), NOW());

INSERT INTO post_tags (post_id, tag)
VALUES (1, 'test'), (1, 'first'), (2, 'test'), (2, 'second');