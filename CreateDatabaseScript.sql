--CREATE DATABASE SENG2050_DB
/*


Date      Name  Description
25/05/24  PH    Add new views. Add GO 
*/


CREATE TABLE User_Role (
    role_id INT PRIMARY KEY IDENTITY(1,1),
    role_name VARCHAR(50) NOT NULL
);
GO

CREATE TABLE [User] (
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
GO

CREATE TABLE Issue_State (
    state_id INT PRIMARY KEY IDENTITY(1,1),
    state_name VARCHAR(50) NOT NULL
);
GO

INSERT INTO Issue_State (state_name) VALUES
('New'), ('In Progress'), ('Waiting on Third Party'),
('Waiting on Reporter'), ('Completed'), ('Not Accepted'), ('Resolved');
GO

CREATE TABLE Issue_Category (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    category_name VARCHAR(50) NOT NULL
);
GO

INSERT INTO Issue_Category (category_name) VALUES
('Network'), ('Software'), ('Hardware'), ('Email'), ('Account')
GO

INSERT INTO User_Role (role_name) VALUES
('User'), ('IT Staff'), ('IT Manager');
GO

CREATE TABLE Issue (
    issue_id INT PRIMARY KEY IDENTITY(1,1),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    resolution_details TEXT,
    category_id INT,
    state_id INT,
    date_reported DATETIME DEFAULT GETDATE(),
    date_resolved DATETIME,
    reported_by_id INT,
    assigned_to_id INT,
    FOREIGN KEY (category_id) REFERENCES Issue_Category(category_id),
    FOREIGN KEY (state_id) REFERENCES Issue_State(state_id),
    FOREIGN KEY (reported_by_id) REFERENCES [User](user_id),
    FOREIGN KEY (assigned_to_id) REFERENCES [User](user_id)
);
GO

CREATE TABLE Keyword (
    keyword_id INT PRIMARY KEY IDENTITY(1,1),
    keyword_name VARCHAR(50) NOT NULL UNIQUE
);
GO

CREATE TABLE Issue_Keyword (
    issue_keyword_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    keyword_id INT,
    FOREIGN KEY (issue_id) REFERENCES Issue(issue_id) ON DELETE CASCADE,
    FOREIGN KEY (keyword_id) REFERENCES Keyword(keyword_id) ON DELETE CASCADE
);
GO

CREATE TABLE Comment (
    comment_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    user_id INT,
    comment_text TEXT NOT NULL,
    comment_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (issue_id) REFERENCES Issue(issue_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES [User](user_id) ON DELETE CASCADE
);
GO

CREATE TABLE Knowledge_Base (
    kb_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    resolution_details TEXT NOT NULL,
    date_resolved DATETIME,
    FOREIGN KEY (issue_id) REFERENCES Issue(issue_id) ON DELETE CASCADE
);
GO

CREATE TABLE [File] (
    file_id INT PRIMARY KEY IDENTITY(1,1),
    issue_id INT,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    FOREIGN KEY (issue_id) REFERENCES Issue(issue_id) ON DELETE CASCADE
);
GO

-- CREATE LOGIN db_Admin WITH PASSWORD = 'password
-- CREATE USER db_Admin FOR LOGIN db_Admin;  
--GRANT SELECT ON OBJECT::User_Role TO db_Admin;


-- Views
CREATE VIEW VW_User AS (
	SELECT UDTL.user_id        AS userID
          ,UDTL.username       AS username
          ,UDTL.password       AS password
          ,UDTL.first_name     AS firstName
          ,UDTL.last_name      AS lastName
          ,UDTL.email          AS email
          ,UDTL.contact_number AS contactNumber
          ,UDTL.role_id        AS roleId
		  ,USRL.role_name      AS role
	FROM dbo.[User] UDTL
	    -- Role Description
	    INNER JOIN dbo.User_Role USRL
		        ON UDTL.role_id = USRL.role_id
);
GO

CREATE VIEW VW_Issue AS (
	SELECT IDTL.issue_id   		                        AS issueId
          ,IDTL.title	   		                        AS title
          ,IDTL.description		                        AS description
          ,IDTL.resolution_details                      AS resolutionDetails
          ,IDTL.category_id                             AS categoryId
		  ,ICTG.category_name                           AS category
          ,IDTL.state_id		                        AS stateId
		  ,ISTE.state_name                              AS state
          ,IDTL.date_reported                           AS dateReported
          ,IDTL.date_resolved                           AS dateResolved
		  ,IDTL.reported_by_id                          AS reportedById
		  ,CONCAT(URPT.first_name, ' ', URPT.last_name) AS reportedByFullName
		  ,IDTL.assigned_to_id                          AS assignedToId
		  ,CONCAT(UASS.first_name, ' ', UASS.last_name) AS assignedToFullName
	FROM dbo.Issue IDTL
	    -- Category Description
	    INNER JOIN dbo.Issue_Category ICTG
		        ON IDTL.category_id = ICTG.category_id
		-- State Description
	    INNER JOIN dbo.Issue_State ISTE
		        ON IDTL.state_id = ISTE.state_id
	    -- Reported By User
	    INNER JOIN dbo.[User] URPT
		        ON IDTL.reported_by_id = URPT.user_id
		-- Assigned To User
	    INNER JOIN dbo.[User] UASS
		        ON IDTL.assigned_to_id = UASS.user_id
);
GO

CREATE VIEW VW_Issue_Keyword AS (
	SELECT IKEY.issue_keyword_id AS issueKeywordId
          ,IKEY.issue_id         AS issueId
		  ,IDTL.title            AS title
	      ,IKEY.keyword_id   	 AS keywordId
          ,KWRD.keyword_name	 AS keywordName
	FROM dbo.Issue_Keyword IKEY
	    -- Issue Title
	    INNER JOIN dbo.Issue IDTL
		        ON IKEY.issue_id = IDTL.issue_id
		-- Keyword Description
	    INNER JOIN dbo.Keyword KWRD
		        ON IKEY.keyword_id = KWRD.keyword_id
);
GO

CREATE VIEW VW_Comment AS (
	SELECT COMT.comment_id                              AS commentId
          ,COMT.issue_id                                AS issueId
		  ,IDTL.title			                        AS issueTitle
		  ,IDTL.description		                        AS issueDescription
		  ,COMT.user_id                                 AS userId
		  ,CONCAT(UDTL.first_name, ' ', UDTL.last_name) AS UserFullName
	      ,COMT.comment_text   	                        AS commentText
          ,COMT.comment_date	                        AS commentDate
	FROM dbo.Comment COMT
	    -- Issue Details
	    INNER JOIN dbo.Issue IDTL
		        ON COMT.issue_id = IDTL.issue_id
		-- User Details
	    INNER JOIN dbo.[User] UDTL
		        ON COMT.user_id = UDTL.user_id
);
GO

CREATE VIEW VW_Knowledge_Base AS (
	SELECT KBAR.kb_id              AS articleId
          ,KBAR.issue_id           AS issueId
		  ,IDTL.title		       AS issueTitle
		  ,IDTL.description	       AS issueDescription
		  ,KBAR.title		       AS articleTitle
		  ,KBAR.description	       AS articleDescription
		  ,KBAR.resolution_details AS resolutionDetails
	      ,KBAR.date_resolved      AS dateResolved
	FROM dbo.Knowledge_Base KBAR
	    -- Issue Details
	    INNER JOIN dbo.Issue IDTL
		        ON KBAR.issue_id = IDTL.issue_id
		-- User Details
	    --INNER JOIN dbo.[User] UDTL
		   --     ON KBAR.user_id = UDTL.user_id
);
GO