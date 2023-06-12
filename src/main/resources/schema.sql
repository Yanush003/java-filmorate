DROP ALL OBJECTS;

-- Создание таблицы жанров фильмов
CREATE TABLE GENRES
(
    id   LONG AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE USERS
(
    id       LONG AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    login    VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    birthday DATE         NOT NULL
);
-- Создание таблицы фильмов
CREATE TABLE FILMS
(
    id           LONG AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(200),
    release_date DATE,
    duration     INT
);
-- Создание таблицы рейтингов фильмов
CREATE TABLE RATINGS
(
    id      LONG AUTO_INCREMENT PRIMARY KEY,
    film_id LONG,
    user_id LONG,
    FOREIGN KEY (user_id) REFERENCES USERS (id),
    FOREIGN KEY (film_id) REFERENCES FILMS (id),
    name    VARCHAR(255) NOT NULL,
    CONSTRAINT name_unique UNIQUE (name)
);


-- Создание таблицы рейтингов фильмов
CREATE TABLE film_ratings
(
    id        LONG AUTO_INCREMENT PRIMARY KEY,
    film_id   LONG NOT NULL,
    rating_id LONG NOT NULL,
    FOREIGN KEY (film_id) REFERENCES films (id),
    FOREIGN KEY (rating_id) REFERENCES ratings (id)
);

-- Создание таблицы жанров фильмов
CREATE TABLE film_genres
(
    id       LONG AUTO_INCREMENT PRIMARY KEY,
    genre_id LONG NOT NULL,
    film_id  LONG NOT NULL,
    FOREIGN KEY (film_id) REFERENCES films (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

-- Создание таблицы пользователей

-- Создание таблицы друзей
CREATE TABLE friends
(
    id         LONG AUTO_INCREMENT PRIMARY KEY,
    friendship BOOLEAN NOT NULL,
    user_id    LONG    NOT NULL,
    friend_id  LONG    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

create table likes
(
    id      LONG AUTO_INCREMENT PRIMARY KEY,
    film_id LONG NOT NULL,
    user_id LONG NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (film_id) REFERENCES films (id)
);
