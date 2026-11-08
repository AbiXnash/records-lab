CREATE TABLE department (
    id   BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10)  NOT NULL UNIQUE
);

CREATE TABLE employee (
    id            BIGINT PRIMARY KEY,
    given_name    VARCHAR(100) NOT NULL,
    family_name   VARCHAR(100) NOT NULL,
    email         VARCHAR(200) NOT NULL,
    department_id BIGINT NOT NULL REFERENCES department (id),
    hired_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    salary        DECIMAL(14, 4) NOT NULL,
    national_id   VARCHAR(20) NOT NULL,
    deleted_at    TIMESTAMP WITH TIME ZONE
);
