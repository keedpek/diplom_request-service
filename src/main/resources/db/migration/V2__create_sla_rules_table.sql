CREATE TABLE sla_rules (
    id SMALLSERIAL PRIMARY KEY,
    category_id SMALLINT NOT NULL,
    priority VARCHAR(20) NOT NULL CHECK (priority IN ('LOW','MEDIUM','HIGH','CRITICAL')),
    response_time_minutes INTEGER NOT NULL,
    execution_time_minutes INTEGER NOT NULL,
    CONSTRAINT fk_sla_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE CASCADE,
    CONSTRAINT uq_sla_category_priority
        UNIQUE (category_id, priority)
);