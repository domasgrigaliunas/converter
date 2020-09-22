CREATE TABLE fxrates (
    last_updated VARCHAR(20) NOT NULL,
    currency_a VARCHAR(100) NOT NULL,
    amount_a DOUBLE PRECISION  NOT NULL,
    currency_b VARCHAR(100) NOT NULL,
    amount_b DOUBLE PRECISION  NOT NULL
);