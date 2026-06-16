-- =========================================================================
--  Base de datos: imc_db
--  Aplicacion: Calculadora de IMC (Computacion Avanzada en Java)
--  Motor: MySQL 8.0
--
--  Diseno con dos tablas: "usuario" guarda los datos que se piden una sola
--  vez, y "registro_imc" guarda una fila por cada medicion. Se relacionan
--  con una llave foranea (un usuario tiene muchas mediciones).
-- =========================================================================

DROP DATABASE IF EXISTS imc_db;
CREATE DATABASE imc_db CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE imc_db;

-- -------------------------------------------------------------------------
--  Tabla usuario: informacion capturada durante el registro.
-- -------------------------------------------------------------------------
CREATE TABLE usuario (
    id_usuario       INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo  VARCHAR(100)  NOT NULL,
    nombre_usuario   VARCHAR(50)   NOT NULL UNIQUE,   -- no se permite repetido
    contrasena       VARCHAR(255)  NOT NULL,          -- se guarda cifrada (hash)
    edad             INT           NOT NULL,
    sexo             CHAR(1)       NOT NULL,           -- 'M' o 'F'
    estatura         DECIMAL(3,2)  NOT NULL,           -- en metros (1.00 a 2.50)

    -- Validaciones a nivel de base de datos (respaldo de las del servlet).
    CONSTRAINT chk_edad     CHECK (edad >= 15),
    CONSTRAINT chk_estatura CHECK (estatura >= 1.00 AND estatura <= 2.50),
    CONSTRAINT chk_sexo     CHECK (sexo IN ('M', 'F'))
);

-- -------------------------------------------------------------------------
--  Tabla registro_imc: una fila por cada calculo que hace el usuario.
-- -------------------------------------------------------------------------
CREATE TABLE registro_imc (
    id_registro     INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario      INT           NOT NULL,
    peso            DECIMAL(5,2)  NOT NULL,            -- masa corporal en kg
    imc             DECIMAL(5,2)  NOT NULL,            -- IMC ya calculado
    fecha_medicion  DATETIME      NOT NULL,

    CONSTRAINT chk_peso CHECK (peso > 0),

    -- Llave foranea: liga cada medicion con su usuario. Si se borra el
    -- usuario, se borran en cascada sus mediciones.
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

-- -------------------------------------------------------------------------
--  Indice para acelerar la consulta del historico por usuario y fecha.
-- -------------------------------------------------------------------------
CREATE INDEX idx_registro_usuario ON registro_imc (id_usuario, fecha_medicion);
