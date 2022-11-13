-- post
CREATE TABLE post(
    id INT AUTO_INCREMENT PRIMARY KEY,
    version INT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    published_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP
);