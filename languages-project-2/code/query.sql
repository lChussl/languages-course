create user lenguajes_2@localhost
    identified by '1234';
GRANT ALL PRIVILEGES ON *.* TO 'lenguajes_2'@'localhost';FLUSH PRIVILEGES;

INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('person', 'USA', 'john.doe@example.com', 'John', '+1-555-555-5555', NULL, 'Doe');
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('person', 'USA', 'jane.smith@example.com', 'Jane', '+1-555-555-5556', NULL, 'Smith');
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('person', 'UK', 'david.jones@example.com', 'David', '+44-555-555-5555', NULL, 'Jones');
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('person', 'UK', 'emma.watson@example.com', 'Emma', '+44-555-555-5556', NULL, 'Watson');
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('person', 'Germany', 'max.mustermann@example.com', 'Max', '+49-555-555-5555', NULL, 'Mustermann');

-- Insert 5 Institution records
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('institution', 'USA', 'info@usa-institution.com', 'USA Institution', '+1-555-555-5557', 'https://www.usa-institution.com', NULL);
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('institution', 'UK', 'info@uk-institution.com', 'UK Institution', '+44-555-555-5557', 'https://www.uk-institution.com', NULL);
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('institution', 'Germany', 'info@germany-institution.com', 'Germany Institution', '+49-555-555-5556', 'https://www.germany-institution.com', NULL);
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('institution', 'France', 'info@france-institution.com', 'France Institution', '+33-555-555-5555', 'https://www.france-institution.com', NULL);
INSERT INTO owner (ownertype, country, email, name, phone, website, lastname) VALUES ('institution', 'Japan', 'info@japan-institution.com', 'Japan Institution', '+81-555-555-5555', 'https://www.japan-institution.com', NULL);