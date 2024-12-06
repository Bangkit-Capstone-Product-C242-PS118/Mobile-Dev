CREATE DATABASE Harga_Kebutuhan;

USE Harga_Kebutuhan;

CREATE TABLE Harga_Kebutuhan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_daerah VARCHAR(255) NOT NULL,
    harga_cabe DECIMAL(10, 2) NOT NULL,
    harga_bawang_merah DECIMAL(10, 2) NOT NULL,
    harga_bawang_putih DECIMAL(10, 2) NOT NULL,
    harga_daging_ayam DECIMAL(10, 2) NOT NULL
);

INSERT INTO Harga_Kebutuhan (nama_daerah, harga_cabe, harga_bawang_merah, harga_bawang_putih, harga_daging_ayam)
VALUES
('Jakarta', 25000.00, 18000.00, 20000.00, 35000.00),
('Bandung', 23000.00, 17000.00, 19000.00, 33000.00),
('Surabaya', 24000.00, 17500.00, 18500.00, 34000.00),
('Medan', 26000.00, 18500.00, 21000.00, 36000.00),
('Yogyakarta', 22000.00, 16000.00, 18000.00, 32000.00),
('Semarang', 23500.00, 16500.00, 19500.00, 32500.00),
('Makassar', 25500.00, 19000.00, 21500.00, 35500.00),
('Malang', 24500.00, 18000.00, 20000.00, 34500.00),
('Palembang', 25000.00, 17000.00, 19000.00, 33000.00),
('Batam', 24000.00, 17500.00, 18500.00, 34000.00),
('Bali', 23000.00, 16500.00, 18000.00, 31500.00);
