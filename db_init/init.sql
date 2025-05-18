CREATE USER 'crm_admin'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON crm_db.* TO 'crm_admin'@'%';
FLUSH PRIVILEGES;

USE crm_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255) NOT NULL
);

INSERT INTO users (name, email, password, role) 
VALUES ('Admin User', 'admin@admin.com', 'admin', 'ADMIN'),
       ('Student User', 'student@student.com', 'student', 'STUDENT'),
       ('Teacher User', 'teacher@teacher.com', 'teacher', 'TEACHER');
