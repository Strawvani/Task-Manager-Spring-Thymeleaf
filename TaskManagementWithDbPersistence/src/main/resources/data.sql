-- data.sql — loaded by Spring Boot on startup
-- Runs AFTER Hibernate creates the schema (ddl-auto=update)

-- Seed users
IF NOT EXISTS (SELECT 1 FROM users)
BEGIN
INSERT INTO users (username, email) VALUES ('alice', 'alice@example.com');
INSERT INTO users (username, email) VALUES ('bob', 'bob@example.com');
END

-- Seed categories
IF NOT EXISTS (SELECT 1 FROM category)
BEGIN
INSERT INTO category (name, description) VALUES ('Development', 'Dev tasks');
INSERT INTO category (name, description) VALUES ('Testing', 'QA tasks');
END

-- Seed tasks
IF NOT EXISTS (SELECT 1 FROM task)
BEGIN
INSERT INTO task (title, description, status, priority, user_id, category_id, created_at)
VALUES ('Set up Docker environment', 'Configure Docker for the project', 'IN_PROGRESS', 'HIGH', 1, 1, GETDATE());

INSERT INTO task (title, description, status, priority, user_id, category_id, created_at)
VALUES ('Write unit tests', 'Write tests for task service', 'TODO', 'MEDIUM', 2, 2, GETDATE());
END