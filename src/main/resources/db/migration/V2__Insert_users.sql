CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Insert ADMIN user
INSERT INTO users (id, username, password, role)
VALUES (
    '01K0X6QT0J92RF52TGNHXEG2GN',
    'jose.vasconcelos',
    crypt('Str0ngP4ss', gen_salt('bf', 10)),
    'ROLE_ADMIN'
);

-- Insert USER user
INSERT INTO users (id, username, password, role)
VALUES (
    '01K0X6WZ5XAXZPCN4TYVE6G5GK',
    'ludmilla.ribeiro',
    crypt('weakpass', gen_salt('bf', 10)),
    'ROLE_USER'
);