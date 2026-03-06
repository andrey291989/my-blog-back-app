-- Инициализация базы данных тестовыми данными
-- Удаляем существующие данные перед вставкой
DELETE FROM comments;
DELETE FROM post_tags;
DELETE FROM posts;

-- Сброс последовательностей
ALTER SEQUENCE posts_id_seq RESTART WITH 4;
ALTER SEQUENCE comments_id_seq RESTART WITH 4;

-- Вставка тестовых данных
INSERT INTO posts (id, title, text, likes_count, comments_count, created_at, updated_at) VALUES
(1, 'Первый пост 1', 'Это текст первого поста блога.', 5, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Второй пост 2', 'Это текст второго поста блога.', 10, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Третий пост 3', 'Это текст третьего поста блога.', 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO post_tags (post_id, tag) VALUES
(1, 'первый'),
(1, 'тест'),
(2, 'второй'),
(2, 'демо'),
(3, 'третий');

INSERT INTO comments (id, text, post_id, created_at, updated_at) VALUES
(1, 'Отличный пост! Спасибо за информацию.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'У меня возникли вопросы по этой теме.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Спасибо, это очень полезно!', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);