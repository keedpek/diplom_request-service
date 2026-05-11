CREATE TABLE kafka_outbox (
    id UUID PRIMARY KEY,
    topic VARCHAR(255) NOT NULL,
    message_key VARCHAR(255),
    payload JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    status VARCHAR(20) NOT NULL DEFAULT 'NEW' CHECK (status IN ('NEW', 'PROCESSING', 'SENT', 'DEAD')),
    retry_count INT NOT NULL DEFAULT 0,
    next_retry_at TIMESTAMP
);