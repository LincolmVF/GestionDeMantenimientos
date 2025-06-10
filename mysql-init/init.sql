-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS prototipoappdb;
USE prototipoappdb;

-- Crear tabla maquina_entity
CREATE TABLE IF NOT EXISTS maquina_entity (
    id_maquina BIGINT PRIMARY KEY AUTO_INCREMENT,
    costo_equipo DOUBLE,
    descripcion VARCHAR(255),
    dimensiones VARCHAR(255),
    nombre VARCHAR(255),
    numero_de_activo VARCHAR(255),
    numerosap VARCHAR(255),
    tipo_equipo VARCHAR(255),
    imagen VARCHAR(255),
    estado VARCHAR(255)
);

-- Crear tabla mantenimiento_entity
CREATE TABLE IF NOT EXISTS mantenimiento_entity (
    id_mantenimiento BIGINT PRIMARY KEY AUTO_INCREMENT,
    autor_arreglo VARCHAR(255),
    costo DOUBLE,
    fecha_mantenimiento DATE,
    orden_de_compra VARCHAR(255),
    tipo_mantenimiento VARCHAR(255),
    maquina_id BIGINT,
    FOREIGN KEY (maquina_id) REFERENCES maquina_entity(id_maquina)
);

-- Crear tabla detalle_mantenimiento_entity
CREATE TABLE IF NOT EXISTS detalle_mantenimiento_entity (
    id_detalle_man BIGINT PRIMARY KEY AUTO_INCREMENT,
    descripcion_detalle VARCHAR(255),
    mantenimiento_id BIGINT,
    FOREIGN KEY (mantenimiento_id) REFERENCES mantenimiento_entity(id_mantenimiento)
);

-- Crear tabla tipos_maquina
CREATE TABLE IF NOT EXISTS tipos_maquina (
    id_tipo_maquina INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);



-- Crear tabla permissions
CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);


-- Crear tabla roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(255)
);


-- Crear tabla role_permission
CREATE TABLE IF NOT EXISTS role_permission (
    role_id BIGINT,
    permission_id BIGINT,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);



CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_non_expired BIT(1),
    account_non_locked BIT(1),
    credentials_non_expired BIT(1),
    is_enabled BIT(1),
    password VARCHAR(255),
    username VARCHAR(255),
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);



-- Insertar permisos si no existen
INSERT INTO permissions (name) SELECT 'READ' WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'READ');
INSERT INTO permissions (name) SELECT 'WRITE' WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'WRITE');
INSERT INTO permissions (name) SELECT 'DELETE' WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'DELETE');
INSERT INTO permissions (name) SELECT 'UPDATE' WHERE NOT EXISTS (SELECT 1 FROM permissions WHERE name = 'UPDATE');

-- Insertar roles si no existen
INSERT INTO roles (role_name) SELECT 'Admin' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'Admin');
INSERT INTO roles (role_name) SELECT 'Usuario' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'Usuario');

-- Asignar todos los permisos al rol Admin
INSERT INTO role_permission (role_id, permission_id)
SELECT 
    (SELECT id FROM roles WHERE role_name = 'Admin'),
    id
FROM permissions
WHERE NOT EXISTS (
    SELECT 1 FROM role_permission 
    WHERE role_id = (SELECT id FROM roles WHERE role_name = 'Admin')
    AND permission_id = permissions.id
);

-- Asignar solo permiso de lectura al rol Usuario
INSERT INTO role_permission (role_id, permission_id)
SELECT 
    (SELECT id FROM roles WHERE role_name = 'Usuario'),
    id
FROM permissions 
WHERE name = 'READ' AND NOT EXISTS (
    SELECT 1 FROM role_permission 
    WHERE role_id = (SELECT id FROM roles WHERE role_name = 'Usuario')
    AND permission_id = permissions.id
);
