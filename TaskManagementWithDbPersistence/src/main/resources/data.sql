-- data.sql
INSERT INTO users (username, email) VALUES ('alice', 'alice@example.com');
INSERT INTO users (username, email) VALUES ('bob', 'bob@example.com');

INSERT INTO category (name, description) VALUES ('Development', 'Dev tasks');
INSERT INTO category (name, description) VALUES ('Testing', 'QA tasks');

INSERT INTO task (title, description, status, priority, user_id, category_id, created_at)
VALUES ('Set up Docker environment', 'Configure Docker', 'IN_PROGRESS', 'HIGH', 1, 1, GETDATE());

INSERT INTO task (title, description, status, priority, user_id, category_id, created_at)
VALUES ('Write unit tests', 'Write tests for service', 'TODO', 'MEDIUM', 2, 2, GETDATE());