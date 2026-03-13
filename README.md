# Blog Backend Application

Это бэкенд-приложение для блога.

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
3. Убедитесь, что у вас есть пользователь `postgres` с паролем `postgres` или измените настройки в файле `src/main/resources/application.yml`.

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

- `GET /api/posts` - Получить список постов с пагинацией и поиском
- `GET /api/posts/{id}` - Получить конкретный пост по ID
- `POST /api/posts` - Создать новый пост
- `PUT /api/posts/{id}` - Обновить существующий пост
- `DELETE /api/posts/{id}` - Удалить пост
- `POST /api/posts/{id}/likes` - Увеличить счетчик лайков
- `PUT /api/posts/{id}/image` - Обновить изображение поста
- `GET /api/posts/{id}/image` - Получить изображение поста

### Комментарии

- `GET /api/posts/{postId}/comments` - Получить комментарии к посту
- `GET /api/posts/{postId}/comments/{commentId}` - Получить конкретный комментарий
- `POST /api/posts/{postId}/comments` - Создать новый комментарий
- `PUT /api/posts/{postId}/comments/{commentId}` - Обновить комментарий
- `DELETE /api/posts/{postId}/comments/{commentId}` - Удалить комментарий

## Пример использования

Создание нового поста:
```bash
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Post","text":"This is a test post","tags":["test","demo"]}'
```

## Структура базы данных

Приложение работает с таблицами в PostgreSQL:
- `posts` - Хранит информацию о постах блога
- `post_tags` - Хранит теги для постов (связь один ко многим)
- `comments` - Хранит комментарии к постам

## Лицензия

Этот проект лицензирован по лицензии MIT.