CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    fullname VARCHAR(200) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    image_url VARCHAR(200),
    biography VARCHAR(500),
    cellphone VARCHAR(15) UNIQUE NOT NULL,
    birthday DATE NOT NULL,
    role VARCHAR(20) DEFAULT 'USER'
);

CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    body VARCHAR(500),
    image_url VARCHAR(200),
    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    id_post INTEGER NOT NULL,
    id_user INTEGER NOT NULL,
    body VARCHAR(200),
    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_post) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE reaction (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_post INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_post) REFERENCES post(id) ON DELETE CASCADE,
    UNIQUE (id_user, id_post)
);

CREATE TABLE follower (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_user_followed INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_user_followed) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (id_user, id_user_followed)
);

CREATE TABLE tag (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE tag_post (
    id SERIAL PRIMARY KEY,
    id_post INTEGER NOT NULL,
    id_tag INTEGER NOT NULL,
    FOREIGN KEY (id_post) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (id_tag) REFERENCES tag(id) ON DELETE CASCADE
);

CREATE TABLE tag_comment (
    id SERIAL PRIMARY KEY,
    id_comment INTEGER NOT NULL,
    id_tag INTEGER NOT NULL,
    FOREIGN KEY (id_comment) REFERENCES comment(id) ON DELETE CASCADE,
    FOREIGN KEY (id_tag) REFERENCES tag(id) ON DELETE CASCADE
);

CREATE TABLE mention_post (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_post INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_post) REFERENCES post(id) ON DELETE CASCADE
);

CREATE TABLE mention_comment (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_comment INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_comment) REFERENCES comment(id) ON DELETE CASCADE
);

CREATE TABLE notification_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE notification (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_comment INTEGER,
    id_post INTEGER,
    id_user_trigger INTEGER NOT NULL,
    id_notification_type INTEGER NOT NULL,
    message VARCHAR(200) NOT NULL,
    seen BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_post) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (id_user_trigger) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (id_comment) REFERENCES comment(id) ON DELETE CASCADE,
    FOREIGN KEY (id_notification_type) REFERENCES notification_type(id) ON DELETE CASCADE
);
