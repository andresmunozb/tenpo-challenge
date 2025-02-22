CREATE TABLE api_logs (
    id BIGSERIAL PRIMARY KEY,
    call_date_time TIMESTAMP NOT NULL,
    http_method VARCHAR(10) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    query_string VARCHAR(500),
    request_body TEXT,
    response_body TEXT
);