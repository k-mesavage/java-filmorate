# java-filmorate
Репозиторий шаблонов для проекта Filmorate.

![kandinsky-download-1696760013544.jpg](kandinsky-download-1696760013544.jpg)

## Схема БД и примеры запросов
<a>
<img src="sql_schema.jpg" />
</a>

* получение списка всех пользователей
```SQL
SELECT *
FROM users
```
* добавление в друзья
```SQL
INSERT IGNORE INTO friends (user_id, friend_id)
VALUES (?, ?)
```
* лайк фильму
```SQL
INSERT IGNORE INTO likes (film_id, user_id)
VALUES (?, ?)
```
