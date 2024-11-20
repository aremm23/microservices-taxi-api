CREATE TABLE reviews
(
    id          BIGINT                              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    reviewer_id BIGINT                              NOT NULL,
    reviewed_id BIGINT                              NOT NULL,
    ride_id     VARCHAR(255)                        NOT NULL,
    rate        INT                                 NOT NULL,
    comment     TEXT,
    review_type ENUM ('DRIVER', 'PASSENGER')        NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
)