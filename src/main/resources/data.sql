-- to insert only if table is empty
INSERT INTO role (name) SELECT 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_ADMIN');
INSERT INTO role (name) SELECT 'ROLE_RECEIVER' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_RECEIVER');
INSERT INTO role (name) SELECT 'ROLE_TRANSMITTER' WHERE NOT EXISTS (SELECT * FROM role WHERE role.name='ROLE_TRANSMITTER');

-- FROM role;
--INSERT INTO role (name) VALUES ('ROLE_ADMIN');
--INSERT INTO role (name) VALUES ('ROLE_USER');