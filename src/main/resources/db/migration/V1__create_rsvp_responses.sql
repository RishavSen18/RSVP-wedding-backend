-- ===================================================================
-- Flyway Migration V1: Create RSVP Responses Table
-- ===================================================================

CREATE TABLE IF NOT EXISTS rsvp_responses (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(30) NOT NULL,
    attendance VARCHAR(30) NOT NULL,
    message TEXT,
    submitted_at TIMESTAMP NOT NULL
);

-- Index for ordering by newest submissions first
CREATE INDEX IF NOT EXISTS idx_rsvp_responses_submitted_at ON rsvp_responses (submitted_at DESC);
