CREATE DATABASE IF NOT EXISTS dulce_conexion
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE dulce_conexion;

CREATE TABLE IF NOT EXISTS cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100)
);
