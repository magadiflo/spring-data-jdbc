-- author
CREATE TABLE author(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL
);

-- post
CREATE TABLE post(
    id INT AUTO_INCREMENT PRIMARY KEY,
    version INT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    published_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP,
    author_id INT NOT NULL,
    CONSTRAINT fk_author_post FOREIGN KEY(author_id) REFERENCES author(id)
);

-- comment
CREATE TABLE comment(
    post INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    published_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP,
    CONSTRAINT fk_post_comment FOREIGN KEY(post) REFERENCES post(id)
);