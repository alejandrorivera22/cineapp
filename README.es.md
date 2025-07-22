# CineApp

> Sistema de gestión de películas para cines desarrollado con Spring Boot y Thymeleaf.

Este README se encuentra disponible [Inges EN](./README.md)

**Cineapp** es una aplicación web + backend para getionar carteleras,
horarios, banners, noticias y usuarios en un entorno de cine.
Ofrece acceso basado en roles, validación de formularios y funcionalidades
administrativas para gestionar el contenido de manera eficiente a través
de una interfaz web.

---

## Technologies Used

| Tool              | Purpose                              |
|-------------------|--------------------------------------|
| Java 17           | 	Lenguaje principal                  |
| Spring Boot 3.3.2 | Framework backend                    |
| Spring Web        | REST/web endpoints                   |
| Thymeleaf         | Motor de plantillas HTML server-side |
| Spring Data JPA   | ORM y capa de persistencia           |
| Spring Security   | Autenticación y gestión de roles     |
| Spring Validation | Validaciones de entrada              |
| MySQL 8           | Base de datos relacional             |
| H2                | Base de datos en memoria para tests  |
| Docker Compose    | Contenedor para MySQL                |
| JUnit 5           | Pruebas unitarias y de integración   |

---

##  Funcionalidades

- **Autenticación y autorización** con Spring Security
-  **Gestión de banners** (agregar, listar, eliminar)
-  **Gestión de películas** (CRUD, filtro por género, estado)
-  **Horarios** por película y fecha
-  **Sección de noticias** (CRUD, recientes)
-  **Gestión de usuarios y perfiles**
-  **Mostrar películas por fecha**
-  **Listados paginados** de películas y horarios

---

##  Validaciones y Reglas
### Ejemplos

-  **Acceso restringido** for protected routes
-  **Peliculas activas** Solo se muestran películas activas al público
-  **Horarios** Filtrado de horarios por fecha y película
-  **Lista de géneros** Lista de géneros predefinida para consistencia
-  **Noticias filtradas**  para mostrar solo las activas y recientes
-  **Contraseñas cifradas** con BCrypt (si está habilitado)

---

## Estructura del proyecto

- controllers/     — Controladores web (manejan vistas y rutas)
- config/          — Configuración general (seguridad, propiedades, etc.)
- domain/          — Entidades y repositorios JPA
- infrastructure/  —  Interfaces y lógica de negocio
- resources/       —  Archivos de configuración (application.properties, etc.)
- templates/       — Plantillas HTML Thymeleaf
- static/          — Recursos estáticos (CSS, JS, imágenes)
- util/            — Utilidades (roles, validadores, helpers)
- test/            — Pruebas unitarias e integrales

### Arquitectura
El proyecto sigue una arquitectura en capas basada en el patrón MVC.
Permite una separación clara entre la presentación, la lógica de negocio y la persistencia de datos.
Esta estructura mejora la mantenibilidad, modularidad y escalabilidad.

---
##  Instalación Local

### 1. Requisitos Previos

- Java 17 
- Docker y Docker Compose
- Maven

### 2. Clonar el repositorio
git clone https://github.com/alejandrorivera22/cineapp.git  
cd cineapp

### 3. Configuración personalizada (Ruta de imágenes)
- En Windows:
  **ciena.ruta.images=C:\\Users\\YourUser\\cineapp\\images\\**
- En Linux/macOS:
  **ciena.ruta.images=/home/youruser/cineapp/images/**

### 4. Copiar imágenes de ejemplo
- **IMPORTANTE:** Debes copiar estas imágenes en la carpeta que definiste
en ciena.ruta.images para que la aplicación pueda mostrarlas 
correctamente en tiempo de ejecución.

### 5. Levantar MySQL
docker-compose up -d

### 6. Compilar y ejecutar la aplicación
- ./mvnw clean install
- ./mvnw spring-boot:run

### 7. Acceder a la aplicación web
- http://localhost:8080/welcome

---
 ## Usuarios de prueba
Estos usuarios vienen precargados desde data.sql y
permiten probar el sistema según los distintos roles:

| Rol      | Username       | Contraseña |
|----------|----------------|------------|
| EDITOR    | `luis`         | `luis123`  |
| GERENTE   | `marisol `     | `mari123`  |

> Las contraseñas están cifradas con BCrypt. 
> Estos accesos son solo para pruebas locales.

---
**Alejandro Rivera**
- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](https://www.linkedin.com/in/alejandro-rivera-verdayes-443895375/)
- [![GitHub](https://img.shields.io/badge/GitHub-000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/alejandrorivera22)