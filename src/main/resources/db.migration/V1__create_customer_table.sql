CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE customer(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    specialities VARCHAR(255) NOT NULL
);