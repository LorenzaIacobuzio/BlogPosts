CREATE TABLE IF NOT EXISTS post
(
    id         BIGINT NOT NULL PRIMARY KEY,
    likes      BIGINT,
    popularity DECIMAL(7, 2),
    reads      BIGINT,
    tags       VARCHAR,
    text       VARCHAR
);


CREATE TABLE IF NOT EXISTS post_sequence
(
    next_val BIGINT
);

CREATE TABLE IF NOT EXISTS user
(
    id BIGINT NOT NULL PRIMARY KEY,
    _password VARCHAR,
    _username VARCHAR
);

CREATE TABLE IF NOT EXISTS user_sequence
(
    next_val BIGINT
);

CREATE TABLE IF NOT EXISTS user_post
(
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, user_id)
);


