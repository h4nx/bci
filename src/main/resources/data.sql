-- SQL statements to create tables
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    token VARCHAR(255),
    is_active BOOLEAN
);

CREATE TABLE phones (
    id SERIAL PRIMARY KEY,
    number VARCHAR(255),
    citycode VARCHAR(255),
    contrycode VARCHAR(255),
    user_id INT REFERENCES users(id)
);
