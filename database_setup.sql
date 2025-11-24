-- ====================================================
-- HORSE RACING GAME - DATABASE SETUP SCRIPT
-- ====================================================
-- Author: Your Name
-- Date: 2025-11-24
-- Description: SQL script untuk membuat database dan tabel untuk Horse Racing Game
--
-- CARA MENGGUNAKAN:
-- 1. Buka MySQL Command Line atau phpMyAdmin
-- 2. Login dengan user root (atau user dengan privilege CREATE DATABASE)
-- 3. Copy-paste script ini atau import file ini
-- 4. Atau jalankan: mysql -u root -p < database_setup.sql
-- ====================================================

-- Hapus database jika sudah ada (hati-hati, ini akan menghapus semua data!)
-- Uncomment baris di bawah jika ingin reset database
-- DROP DATABASE IF EXISTS horse_racing_db;

-- Buat database baru
CREATE DATABASE IF NOT EXISTS horse_racing_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Gunakan database yang baru dibuat
USE horse_racing_db;

-- ====================================================
-- TABEL 1: USERS
-- ====================================================
-- Menyimpan data akun user/player
CREATE TABLE IF NOT EXISTS users (
    id INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    coins INT DEFAULT 500,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- TABEL 2: HORSES
-- ====================================================
-- Menyimpan data kuda milik setiap user
CREATE TABLE IF NOT EXISTS horses (
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(20) NOT NULL,
    speed INT DEFAULT 50,
    stamina INT DEFAULT 50,
    acceleration INT DEFAULT 50,
    level INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- TABEL 3: RACE_HISTORY
-- ====================================================
-- Menyimpan riwayat balapan setiap user
CREATE TABLE IF NOT EXISTS race_history (
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11) NOT NULL,
    horse_name VARCHAR(50) NOT NULL,
    position INT NOT NULL,
    total_horses INT NOT NULL,
    coins_earned INT NOT NULL,
    race_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_race_date (race_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================
-- DATA SAMPLE (OPTIONAL)
-- ====================================================
-- Uncomment untuk menambahkan data sample untuk testing

-- Sample User 1
-- INSERT INTO users (username, password, coins) 
-- VALUES ('player1', 'password123', 1000);

-- Sample User 2
-- INSERT INTO users (username, password, coins) 
-- VALUES ('player2', 'password456', 1500);

-- Sample Horse untuk User 1 (user_id = 1)
-- INSERT INTO horses (user_id, name, color, speed, stamina, acceleration, level)
-- VALUES (1, 'Thunder', 'brown', 75, 70, 65, 5);

-- Sample Horse untuk User 2 (user_id = 2)
-- INSERT INTO horses (user_id, name, color, speed, stamina, acceleration, level)
-- VALUES (2, 'Lightning', 'black', 80, 75, 70, 6);

-- Sample Race History
-- INSERT INTO race_history (user_id, horse_name, position, total_horses, coins_earned)
-- VALUES (1, 'Thunder', 1, 5, 500);

-- INSERT INTO race_history (user_id, horse_name, position, total_horses, coins_earned)
-- VALUES (2, 'Lightning', 2, 5, 300);

-- ====================================================
-- VERIFIKASI DATABASE
-- ====================================================
-- Tampilkan semua tabel yang telah dibuat
SHOW TABLES;

-- Tampilkan struktur tabel users
DESCRIBE users;

-- Tampilkan struktur tabel horses
DESCRIBE horses;

-- Tampilkan struktur tabel race_history
DESCRIBE race_history;

-- ====================================================
-- SELESAI!
-- ====================================================
-- Database dan tabel sudah siap digunakan.
-- Anda bisa langsung menjalankan aplikasi Horse Racing Game.
-- ====================================================
