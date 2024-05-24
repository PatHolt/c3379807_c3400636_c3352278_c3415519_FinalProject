-- CREATE DATABASE SENG2050_DB

CREATE TABLE User_Role (
    role_id INT PRIMARY KEY IDENTITY(1,1),
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE Users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    contact_number VARCHAR(20),
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES User_Role(role_id)
);

CREATE TABLE Issue_State (
    state_id INT PRIMARY KEY IDENTITY(1,1),
    state_name VARCHAR(50) NOT NULL
);

INSERT INTO Issue_State (state_name) VALUES
('New'), ('In Progress'), ('Waiting on Third Party'),
('Waiting on Reporter'), ('Completed'), ('Not Accepted'), ('Resolved');

CREATE TABLE Issue_Category (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    category_name VARCHAR(50) NOT NULL
);

INSERT INTO Issue_Category (category_name) VALUES
('Network'), ('Software'), ('Hardware'), ('Email'), ('Account')

INSERT INTO User_Role (role_name) VALUES
('User'), ('IT Staff'), ('IT Manager');

CREATE TABLE Issues (
    issue_id INT PRIMARY KEY IDENTITY(1,1),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    resolution_details TEXT,
    category_id INT,
    state_id INT,
    date_reported DATETIME DEFAULT GETDATE(),
    date_resolved DATETIME,
    reported_by INT,
    assigned_staff_id INT,
    FOREIGN KEY (category_id) REFERENCES Issue_Category(category_id),
    FOREIGN KEY (state_id) REFERENCES Issue_State(state_id),
    FOREIGN KEY (reported_by) REFERENCES Users(user_id),
    FOREIGN KEY (assigned_staff_id) REFERENCES Users(user_id)
);

CREATE TABLE Keywords (
    keyword_id INT PRIMARY KEY IDENTITY(1,1),
    keyword_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Issue_Keywords (
    issue_keyword_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    keyword_id INT,
    FOREIGN KEY (issue_id) REFERENCES Issues(issue_id) ON DELETE CASCADE,
    FOREIGN KEY (keyword_id) REFERENCES Keywords(keyword_id) ON DELETE CASCADE
);

CREATE TABLE Comments (
    comment_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    user_id INT,
    comment_text TEXT NOT NULL,
    comment_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (issue_id) REFERENCES Issues(issue_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE Knowledge_Base (
    kb_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    resolution_details TEXT NOT NULL,
    date_resolved DATETIME,
    FOREIGN KEY (issue_id) REFERENCES Issues(issue_id) ON DELETE CASCADE
);

CREATE TABLE Files (
    file_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    FOREIGN KEY (issue_id) REFERENCES Issues(issue_id) ON DELETE CASCADE
);

-- CREATE LOGIN db_Admin WITH PASSWORD = 'password
-- CREATE USER db_Admin FOR LOGIN db_Admin;  

--GRANT SELECT ON OBJECT::User_Role TO db_Admin;