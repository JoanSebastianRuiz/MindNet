# MindNet

## Descripción del Proyecto

En la era digital, las redes sociales han revolucionado la forma en que las personas se comunican e interactúan. **MindNet** es una plataforma que permite a los usuarios compartir pensamientos, interactuar con otros y mantenerse conectados de una manera dinámica e intuitiva.

Este proyecto consiste en desarrollar una red social donde los usuarios puedan:

- Crear publicaciones con texto e imágenes (a través de enlaces).
- Comentar en las publicaciones de otros usuarios.
- Reaccionar a publicaciones.
- Seguir y ser seguidos por otros usuarios.
- Recibir notificaciones en tiempo real.

El desarrollo de MindNet se basa en una arquitectura **full stack**, utilizando **Spring Boot** para el backend, **PostgreSQL o MySQL** para la base de datos y **Next.js** para el frontend.

---

## Tecnologías Utilizadas

### Backend

- **Spring Boot 3.2.3** con **Java 17**.
- **Spring Data JPA** con **Hibernate**.
- **PostgreSQL o MySQL**.
- **Swagger** para documentar la API.
- **Spring Security** con **JWT** para autenticación segura.
- Arquitectura modular con DTOs, Services, Entities, etc.

### Frontend

- **Next.js**.
- **Modo oscuro y claro** con persistencia de estado.
- **Diseño responsivo** optimizado para móviles y escritorio.

---

## Instalación y Configuración

### Requisitos previos

- Tener **Java 17** y **Maven** instalados.
- Tener **Node.js** y **npm** instalados.
- Tener **PostgreSQL o MySQL** configurado.

### Instalación del Backend

```sh
# Clonar el repositorio
$ git clone https://github.com/usuario/mindnet-backend.git
$ cd mindnet-backend

# Configurar las variables de entorno en application.properties
# Ejecutar la aplicación
$ mvn spring-boot:run
```

### Instalación del Frontend

```sh
# Clonar el repositorio
$ git clone https://github.com/usuario/mindnet-frontend.git
$ cd mindnet-frontend

# Instalar dependencias
$ npm install

# Ejecutar la aplicación
$ npm run dev
```

---

## Autenticación y Seguridad

- Inicio y cierre de sesión seguro con **JWT**.
- Cifrado de contraseñas con **BCrypt**.
- Validación de edad (solo usuarios mayores de 14 años pueden registrarse).
- Restricciones en imágenes para evitar contenido malicioso.

---

## Endpoints Principales

### Autenticación

- `POST /auth/register`: Registro de usuario.
- `POST /auth/login`: Inicio de sesión.
- `POST /auth/logout`: Cierre de sesión.

### Gestión de Usuarios

- `GET /users/{id}`: Obtener perfil de usuario.
- `PUT /users/{id}`: Actualizar perfil de usuario.

### Publicaciones

- `POST /posts`: Crear una nueva publicación.
- `GET /posts`: Obtener todas las publicaciones.
- `PUT /posts/{id}`: Editar una publicación.
- `DELETE /posts/{id}`: Eliminar una publicación.

### Interacciones

- `POST /posts/{id}/like`: Dar "Me gusta" a una publicación.
- `POST /posts/{id}/comment`: Comentar en una publicación.

---

## Modelo de Base de Datos

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    fullname VARCHAR(200) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    image_url VARCHAR(200),
    biography VARCHAR(500),
    cellphone VARCHAR(15) UNIQUE NOT NULL,
    birthday DATE NOT NULL,
    role VARCHAR(20) DEFAULT 'USER'
);

CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    body VARCHAR(500),
    image_url VARCHAR(200),
    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    id_post INTEGER NOT NULL,
    id_user INTEGER NOT NULL,
    body VARCHAR(200),
    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_post) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);
```

(Continúa con el resto de las tablas...)

---

## Repositorios

- **Backend**: [MindNet Backend](https://github.com/usuario/mindnet-backend)
- **Frontend**: [MindNet Frontend](https://github.com/usuario/mindnet-frontend)

Últimos hashes de commits:

- Backend: `abc1234`
- Frontend: `def5678`

---

## Documentación del Proyecto

- **Diagrama de base de datos (Modelo Entidad-Relación)**: [Ver aquí](https://link_diagrama.com)
- **Documentación de la API con Swagger**: [Ver aquí](https://link_swagger.com)
- **Video demostrativo**: [Ver aquí](https://link_video.com)

