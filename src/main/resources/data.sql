INSERT INTO department (id, name, code) VALUES
    (1, 'Engineering', 'ENG'),
    (2, 'Finance', 'FIN'),
    (3, 'People', 'HR');

INSERT INTO employee (id, given_name, family_name, email, department_id, hired_at, salary, national_id, deleted_at) VALUES
    (1, 'Asha', 'Rao', 'Asha.Rao@Example.com', 1, TIMESTAMP WITH TIME ZONE '2021-03-01 09:00:00+05:30', 1850000.00, 'IN-1234-5678', NULL),
    (2, 'ben', 'de Vries', 'ben.devries@example.com', 1, TIMESTAMP WITH TIME ZONE '2022-07-15 08:30:00+02:00', 1625000.50, 'NL-8765-4321', NULL),
    (3, 'Chen', 'Li', 'chen.li@example.com', 2, TIMESTAMP WITH TIME ZONE '2019-11-04 10:00:00+08:00', 2100000.00, 'CN-1111-2222', NULL),
    (4, 'Dara', 'O''Neill', 'dara.oneill@example.com', 3, TIMESTAMP WITH TIME ZONE '2020-01-20 09:00:00+00:00', 1400000.25, 'IE-3333-4444', NULL),
    (5, 'Emre', 'Yilmaz', 'EMRE.YILMAZ@EXAMPLE.COM', 2, TIMESTAMP WITH TIME ZONE '2023-05-02 09:00:00+03:00', 1550000.00, 'TR-5555-6666', NULL),
    (6, 'Farah', 'Khan', 'farah.khan@example.com', 1, TIMESTAMP WITH TIME ZONE '2018-02-12 09:00:00+05:00', 1990000.00, 'PK-7777-8888',
        TIMESTAMP WITH TIME ZONE '2024-06-30 17:00:00+00:00'),
    (7, '=HYPERLINK("http://x.test")', 'Test', 'test@example.com', 3, TIMESTAMP WITH TIME ZONE '2024-01-01 00:00:00+00:00', 1.00, 'XX-0000-0000', NULL),
    (8, 'Ana', 'rao', 'ana.rao@example.com', 2, TIMESTAMP WITH TIME ZONE '2024-08-19 09:15:30+00:00', 1200000.005, 'BR-9999-0000', NULL);
