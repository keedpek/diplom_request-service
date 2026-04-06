CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    category_id SMALLINT NOT NULL,
    created_by_user_id UUID NOT NULL,
    assigned_to_user_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deadline TIMESTAMP,
    CONSTRAINT fk_request_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
);