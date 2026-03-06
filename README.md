# Blog Backend Application

Это бэкенд-приложение для блога, которое использует Spring Boot и PostgreSQL для хранения данных.

## Требования

- Java 21
- Maven 3.8+
- PostgreSQL 13+

## Настройка базы данных

1. Установите PostgreSQL на ваш компьютер.
2. Создайте базу данных с именем `blogdb`:
   ```sql
   CREATE DATABASE blogdb;
   ```
3. Создайте пользователя `bloguser` с паролем `blogpass`:
   ```sql
   CREATE USER bloguser WITH PASSWORD 'blogpass';
   ```
4. Дайте пользователю права доступа к базе данных:
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE blogdb TO bloguser;
   ```

## Сборка и запуск приложения

### Запуск как Spring Boot приложение

1. Соберите проект с помощью Maven:
   ```
   mvn clean package
   ```

2. Запустите приложение:
   ```
   mvn spring-boot:run
   ```

   Или запустите JAR-файл напрямую:
   ```
   java -jar target/ROOT.war
   ```

### Развертывание в Apache Tomcat

Подробные инструкции по развертыванию в Tomcat смотрите в файле [README_TOMCAT.md](README_TOMCAT.md).

## API Endpoints

### Посты

- `GET /blog/api/posts` - Получить список постов с пагинацией и поиском
- `GET /blog/api/posts/{id}` - Получить конкретный пост по ID
- `POST /blog/api/posts` - Создать новый пост
- `PUT /blog/api/posts/{id}` - Обновить существующий пост
- `DELETE /blog/api/posts/{id}` - Удалить пост
- `POST /blog/api/posts/{id}/likes` - Увеличить счетчик лайков
- `PUT /blog/api/posts/{id}/image` - Обновить изображение поста
- `GET /blog/api/posts/{id}/image` - Получить изображение поста

### Комментарии

- `GET /blog/api/posts/{postId}/comments` - Получить комментарии к посту
- `GET /blog/api/posts/{postId}/comments/{commentId}` - Получить конкретный комментарий
- `POST /blog/api/posts/{postId}/comments` - Создать новый комментарий
- `PUT /blog/api/posts/{postId}/comments/{commentId}` - Обновить комментарий
- `DELETE /blog/api/posts/{postId}/comments/{commentId}` - Удалить комментарий

## Пример использования

Создание нового поста:
```bash
curl -X POST http://localhost:8080/blog/api/posts \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Post","text":"This is a test post","tags":["test","demo"]}'
```

## Структура базы данных

Приложение создает следующие таблицы в PostgreSQL:

- `posts` - Хранит информацию о постах блога
- `post_tags` - Хранит теги для постов (связь один ко многим)
- `comments` - Хранит комментарии к постам

## Лицензия

Этот проект лицензирован по лицензии MIT.