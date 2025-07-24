CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Insert ADMIN user
INSERT INTO users (id, username, password, role)
VALUES (
    '01K0X6QT0J92RF52TGNHXEG2GN',
    'jose.vasconcelos',
    crypt('dc872b11dd2089a3edc513b0d5a725e0b41380c180b11b0f9a8784ee714d189b', gen_salt('bf', 10)),
    'ADMIN'
);

-- Insert USER user
INSERT INTO users (id, username, password, role)
VALUES (
    '01K0X6WZ5XAXZPCN4TYVE6G5GK',
    'ludmilla.ribeiro',
    crypt('ee76f6e173d84fe789a43a1042d535a423f2c06eaf971f4b1b3556ceea202aac', gen_salt('bf', 10)),
    'USER'
);