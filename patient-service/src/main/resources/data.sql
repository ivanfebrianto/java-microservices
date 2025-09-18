-- Create table if it doesn't exist
CREATE TABLE IF NOT EXISTS PATIENT_INFO (
    ROWID_OBJECT UUID PRIMARY KEY,
    FIRST_NAME VARCHAR(100),
    LAST_NAME VARCHAR(100),
    PHONE_NUMBER VARCHAR(20),
    EMAIL VARCHAR(100),
    ADDRESS VARCHAR(255),
    DATE_OF_BIRTH DATE,
    GENDER VARCHAR(20),
    REGISTERED_DATE DATE
    );
--
-- ALTER TABLE PATIENT_INFO ADD CONSTRAINT uq_patient_email UNIQUE (EMAIL);
-- -- Insert sample data
-- INSERT INTO PATIENT_INFO (ROWID_OBJECT, FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL, ADDRESS, DATE_OF_BIRTH, GENDER, REGISTERED_DATE) VALUES
-- ('f48bb9b9-a91b-47ab-9171-6e80b6a9e3ef', 'Rina', 'Wijaya', '+62 813-9876-5432', 'rina.wijaya@example.com', 'Jl. Sudirman No. 12, Bandung', '1988-03-15', 'FEMALE', DATE '2019-11-02'),
-- ('72f2629c-f039-46dc-aff8-13e751bf002b', 'Ahmad', 'Fauzi', '+62 811-2233-4455', 'ahmad.fauzi@example.com', 'Jl. Diponegoro No. 5, Surabaya', '1979-11-02', 'MALE', DATE '2021-06-27'),
-- ('5147a0eb-ea3c-42aa-b942-7e4144243038', 'Ivan', 'Santoso', '+62 812-3456-7890', 'ivan.santoso@example.com', 'Jl. Kebon Jeruk Raya No. 88, Jakarta', '1995-08-23', 'MALE', DATE '2020-04-15'),
-- ('8a61633b-fe4e-4669-b478-f95ffbf0b586', 'Siti', 'Nurhaliza', '+62 812-3344-5566', 'siti.nurhaliza@example.com', 'Jl. Gajah Mada No. 77, Yogyakarta', '1992-06-30', 'FEMALE', DATE '2023-01-10'),
-- ('35f7414c-84e3-40ea-9d3b-e2be340adff4', 'Budi', 'Hartono', '+62 815-6677-8899', 'budi.hartono@example.com', 'Jl. Thamrin No. 101, Jakarta', '1985-01-20', 'MALE', DATE '2018-08-05'),
-- ('e1b6dec4-996d-439b-b670-54485fc4d05a', 'Dewi', 'Lestari', '+62 816-7788-9900', 'dewi.lestari@example.com', 'Jl. Merdeka No. 45, Medan', '1990-09-12', 'FEMALE', DATE '2022-03-19'),
-- ('3b683bf6-5674-4444-8545-fff0c2eb29f8', 'Tono', 'Prasetyo', '+62 817-8899-0011', 'tono.prasetyo@example.com', 'Jl. Ahmad Yani No. 33, Semarang', '1983-04-05', 'MALE', DATE '2024-07-01'),
-- ('3fd2e3af-f9d1-4786-b171-cbffaa568dfa', 'Maya', 'Putri', '+62 818-9900-1122', 'maya.putri@example.com', 'Jl. Pemuda No. 66, Bali', '1997-12-18', 'FEMALE', DATE '2020-10-22'),
-- ('d5f6b5c3-9d5d-4370-a4b7-efad5775d561', 'Rizky', 'Aditya', '+62 819-1011-2233', 'rizky.aditya@example.com', 'Jl. Mangga Dua No. 99, Jakarta', '2000-07-07', 'MALE', DATE '2025-02-14'),
-- ('70dc2e30-eb57-4d10-bad4-16dc6533e791', 'Nina', 'Kusuma', '+62 820-2122-3344', 'nina.kusuma@example.com', 'Jl. Rajawali No. 22, Makassar', '1993-10-25', 'FEMALE', DATE '2019-05-30');