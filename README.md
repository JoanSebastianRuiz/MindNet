# MindNet

## Descripción del Proyecto

En la era digital, las redes sociales han revolucionado la forma en que las personas se comunican e interactúan. **MindNet** es una plataforma que permite a los usuarios compartir pensamientos, interactuar con otros y mantenerse conectados de una manera dinámica e intuitiva.

Este proyecto consiste en desarrollar una red social donde los usuarios puedan:

- Crear publicaciones con texto e imágenes (a través de enlaces).
- Comentar en las publicaciones de otros usuarios.
- Reaccionar a publicaciones.
- Seguir y ser seguidos por otros usuarios.
- Recibir notificaciones en tiempo real.

El desarrollo de MindNet se basa en una arquitectura **full stack**, utilizando **Spring Boot** para el backend, **PostgreSQL** para la base de datos y **Next.js** para el frontend.

---

## Tecnologías Utilizadas

### Backend

- **Spring Boot 3.2.3** con **Java 17**.
- **Spring Data JPA** con **Hibernate**.
- **PostgreSQL**.
- **Swagger** para documentar la API.
- **Spring Security** con **JWT** para autenticación segura.
- Arquitectura modular con DTOs, Services, Entities, etc.
- WebSocket para las notificaciones

### Frontend

- **Next.js**.

---

## Instalación y Configuración

### Requisitos previos

- Tener **Java 17** y **Maven** instalados.
- Tener **Node.js** y **npm** instalados.
- Tener **PostgreSQL o MySQL** configurado.

### Creacion de la base de datos
- En un cliente de PostgreSQL crear la base de datos mindnet y ejecutar el archivo ddl que se encuentra en la carpeta database.

### Instalación del Backend

```sh
# Clonar el repositorio
$ git clone https://github.com/JoanSebastianRuiz/MindNet
$ cd mindnet-backend

# Configurar las variables de entorno en application.properties
# Ejecutar la aplicación
$ mvn spring-boot:run
```

### Instalación del Frontend

```sh
# Clonar el repositorio
$ git clone https://github.com/JoanSebastianRuiz/MindNet-Front
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

## Endpoints  principales

### API Documentation

#### Base URL
```
http://localhost:8080
```

---

### User Endpoints

#### Seguir a un usuario

**Endpoint:**
```http
POST /api/users/follow
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Request Body:**
```json
{
  "username": "john_doe",
  "usernameFollowed": "jane_doe"
}
```

**Responses:**
- **200 OK**: 
```json
"User followed"
```

---

#### Obtener usuario por nombre de usuario

**Endpoint:**
```http
GET /api/users/username/{username}
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Path Parameters:**
- `username` (string) - Nombre de usuario a consultar.

**Ejemplo de uso:**
```http
GET /api/users/username/john_doe
```

**Responses:**
- **200 OK**:
```json
{
  "id": 1,
  "fullname": "John Doe",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "imageUrl": "https://example.com/image.jpg",
  "biography": "Software developer and tech enthusiast.",
  "cellphone": "+1234567890",
  "birthday": "1990-01-01",
  "password": "securepassword"
}
```

---

#### Dejar de seguir a un usuario

**Endpoint:**
```http
DELETE /api/users/unfollow
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Request Body:**
```json
{
  "username": "john_doe",
  "usernameUnfollowed": "jane_doe"
}
```

**Responses:**
- **200 OK**:
```json
"User unfollowed successfully"
```

---

#### Actualizar usuario

**Endpoint:**
```http
PUT /api/users/{id}
```

**Descripción:** Actualizar un usuario por ID.

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>",
  "Content-Type": "application/json"
}
```

**Request Body:**
```json
{
  "fullname": "John Doe",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "imageUrl": "https://example.com/image.jpg",
  "biography": "Software developer and tech enthusiast.",
  "cellphone": "+1234567890",
  "birthday": "1990-01-01",
  "password": "securepassword"
}
```

**Responses:**
- **200 OK**:
```json
"User updated successfully"
```

---

### Post Endpoints

#### Actualizar un post

**Endpoint:**
```http
PUT /api/posts/{id}
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>",
  "Content-Type": "application/json"
}
```

**Path Parameters:**
- `id` (integer) - ID del post a actualizar.

**Request Body:**
```json
{
  "username": "john_doe",
  "body": "Updated post content",
  "imageUrl": "https://example.com/image.jpg",
  "datetime": "2025-02-18T12:00:00Z"
}
```

**Responses:**
- **200 OK**:
```json
"Post updated"
```

---

#### Eliminar un post

**Endpoint:**
```http
DELETE /api/posts/{id}
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Path Parameters:**
- `id` (integer) - ID del post a eliminar.

**Responses:**
- **200 OK**:
```json
"Post deleted"
```

---

#### Dar like a un post

**Endpoint:**
```http
PUT /api/posts/{id}/like
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Path Parameters:**
- `id` (integer) - ID del post a dar like.

**Request Body:**
```json
{
  "idUser": 1
}
```

**Responses:**
- **200 OK**:
```json
"Post liked"
```
- **200 OK**:
```json
"Post unliked"
```

---

#### Obtener posts con filtros

**Endpoint:**
```http
GET /api/posts
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Query Parameters:**
- `filter` (string) - Filtro para la búsqueda.
- `scope` (string) - Alcance de la búsqueda.
- `iduser` (string) - ID del usuario.

**Responses:**
- **200 OK**:
```json
[
    {
        "username": "john_doe",
        "body": "New post content",
        "imageUrl": "https://example.com/image.jpg",
        "datetime": "2025-02-18T12:00:00Z"
    }
]
```

---

#### Crear un post

**Endpoint:**
```http
POST /api/posts
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>",
  "Content-Type": "application/json"
}
```

**Request Body:**
```json
{
  "username": "john_doe",
  "body": "New post content",
  "imageUrl": "https://example.com/image.jpg",
  "datetime": "2025-02-18T12:00:00Z"
}
```

**Responses:**
- **200 OK**:
```json
"Post created"
```

---

#### Verificar si un usuario ha dado like a un post

**Endpoint:**
```http
GET /api/posts/{id}/is-liked
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Path Parameters:**
- `id` (integer) - ID del post.

**Query Parameters:**
- `idUser` (integer) - ID del usuario.

**Responses:**
- **200 OK**:
```json
true
```

---

#### Obtener posts por usuario

**Endpoint:**
```http
GET /api/posts/user/{username}
```

**Autenticación:** Token Bearer requerido

**Headers:**
```json
{
  "Authorization": "Bearer <TOKEN>"
}
```

**Path Parameters:**
- `username` (string) - Nombre de usuario.

**Responses:**
- **200 OK**:
```json
[
    {
        "username": "john_doe",
        "body": "New post content",
        "imageUrl": "https://example.com/image.jpg",
        "datetime": "2025-02-18T12:00:00Z"
    }
]
```

---

### Authentication Endpoints

#### Validate Token
**POST** `/auth/validate-token`

**Description:** Validar un token.

**Request:**
```http
POST /auth/validate-token HTTP/1.1
Host: localhost:8080
Authorization: Bearer <your_token_here>
```

**Response:**
- **200 OK**:
```json
"Token is valid"
```
---

#### Register
**POST** `/auth/register`

**Description:** Registrar un usuario nuevo.

**Request:**
```http
POST /auth/register HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "fullname": "John Doe",
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "securepassword",
  "imageUrl": "https://example.com/image.jpg",
  "biography": "Software developer and tech enthusiast.",
  "cellphone": "+1234567890",
  "birthday": "1990-01-01",
  "role": "USER"
}
```

**Response:**
- **200 OK**:
```json
"Register successful"
```
---

#### Login
**POST** `/auth/login`

**Description:** Autenticación de usuario y retorno de token.

**Request:**
```http
POST /auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "username": "johndoe",
  "password": "securepassword"
}
```

**Response:**
- **200 OK**:
```json
"Login successful"
```
---

#### Logout
**POST** `/auth/logout`

**Description:** Cierre de sesión de usuario.

**Request:**
```http
POST /auth/logout HTTP/1.1
Host: localhost:8080
```

**Response:**
- **200 OK**:
```json
"Logout successful"
```
---

## Modelo de Base de Datos

### Tabla: `users`

| Atributo    | Tipo            | Descripción                                             |
|-------------|-----------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único del usuario.                        |
| fullname    | VARCHAR(200), NOT NULL | Nombre completo del usuario.                           |
| username    | VARCHAR(50), UNIQUE, NOT NULL | Nombre de usuario único.                              |
| email       | VARCHAR(200), UNIQUE, NOT NULL | Correo electrónico único del usuario.                 |
| password    | TEXT, NOT NULL   | Contraseña en texto plano del usuario.                  |
| image_url   | VARCHAR(200)     | URL de la imagen de perfil del usuario.                 |
| biography   | VARCHAR(500)     | Biografía del usuario.                                 |
| cellphone   | VARCHAR(15), UNIQUE, NOT NULL | Número de teléfono móvil único del usuario.            |
| birthday    | DATE, NOT NULL   | Fecha de nacimiento del usuario.                        |
| role        | VARCHAR(20), DEFAULT 'USER' | Rol del usuario en el sistema (por defecto 'USER').    |

### Tabla: `post`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único de la publicación.                 |
| id_user     | INTEGER, NOT NULL | ID del usuario que realizó la publicación (llave foránea hacia `users`). |
| body        | VARCHAR(500)      | Contenido de la publicación.                            |
| image_url   | VARCHAR(200)      | URL de una imagen asociada a la publicación.            |
| datetime    | TIMESTAMP, DEFAULT CURRENT_TIMESTAMP | Fecha y hora de creación de la publicación. |

### Tabla: `comment`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único del comentario.                    |
| id_post     | INTEGER, NOT NULL | ID de la publicación asociada (llave foránea hacia `post`). |
| id_user     | INTEGER, NOT NULL | ID del usuario que realizó el comentario (llave foránea hacia `users`). |
| body        | VARCHAR(200)      | Contenido del comentario.                               |
| datetime    | TIMESTAMP, DEFAULT CURRENT_TIMESTAMP | Fecha y hora de creación del comentario. |

### Tabla: `reaction`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único de la reacción.                     |
| id_user     | INTEGER, NOT NULL | ID del usuario que realizó la reacción (llave foránea hacia `users`). |
| id_post     | INTEGER, NOT NULL | ID de la publicación asociada a la reacción (llave foránea hacia `post`). |
| UNIQUE      | (id_user, id_post) | Restricción única para evitar reacciones duplicadas en la misma publicación. |

### Tabla: `follower`

| Atributo            | Tipo             | Descripción                                             |
|---------------------|------------------|---------------------------------------------------------|
| id                  | SERIAL, PRIMARY KEY | Identificador único del seguidor.                       |
| id_user             | INTEGER, NOT NULL | ID del usuario seguidor (llave foránea hacia `users`).  |
| id_user_followed    | INTEGER, NOT NULL | ID del usuario seguido (llave foránea hacia `users`).   |
| UNIQUE              | (id_user, id_user_followed) | Restricción única para evitar duplicados en las relaciones de seguimiento. |

### Tabla: `tag`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único de la etiqueta.                      |
| name        | VARCHAR(100), UNIQUE, NOT NULL | Nombre único de la etiqueta.                            |

### Tabla: `tag_post`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único de la relación entre etiqueta y publicación. |
| id_post     | INTEGER, NOT NULL | ID de la publicación asociada (llave foránea hacia `post`). |
| id_tag      | INTEGER, NOT NULL | ID de la etiqueta asociada (llave foránea hacia `tag`). |

### Tabla: `tag_comment`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único de la relación entre etiqueta y comentario. |
| id_comment  | INTEGER, NOT NULL | ID del comentario asociado (llave foránea hacia `comment`). |
| id_tag      | INTEGER, NOT NULL | ID de la etiqueta asociada (llave foránea hacia `tag`). |

### Tabla: `mention_post`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único de la mención en una publicación.    |
| id_user     | INTEGER, NOT NULL | ID del usuario mencionado (llave foránea hacia `users`). |
| id_post     | INTEGER, NOT NULL | ID de la publicación asociada a la mención (llave foránea hacia `post`). |

### Tabla: `mention_comment`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único de la mención en un comentario.      |
| id_user     | INTEGER, NOT NULL | ID del usuario mencionado (llave foránea hacia `users`). |
| id_comment  | INTEGER, NOT NULL | ID del comentario asociado a la mención (llave foránea hacia `comment`). |

### Tabla: `notification_type`

| Atributo    | Tipo             | Descripción                                             |
|-------------|------------------|---------------------------------------------------------|
| id          | SERIAL, PRIMARY KEY | Identificador único del tipo de notificación.           |
| name        | VARCHAR(50), UNIQUE, NOT NULL | Nombre único del tipo de notificación.                  |

### Tabla: `notification`

| Atributo            | Tipo             | Descripción                                             |
|---------------------|------------------|---------------------------------------------------------|
| id                  | SERIAL, PRIMARY KEY | Identificador único de la notificación.                  |
| id_user             | INTEGER, NOT NULL | ID del usuario receptor de la notificación (llave foránea hacia `users`). |
| id_comment          | INTEGER          | ID del comentario asociado (llave foránea hacia `comment`). |
| id_post             | INTEGER          | ID de la publicación asociada (llave foránea hacia `post`). |
| id_user_trigger     | INTEGER, NOT NULL | ID del usuario que activó la notificación (llave foránea hacia `users`). |
| id_notification_type | INTEGER, NOT NULL | ID del tipo de notificación (llave foránea hacia `notification_type`). |
| message             | VARCHAR(200), NOT NULL | Mensaje de la notificación.                             |
| seen                | BOOLEAN, DEFAULT FALSE | Estado de lectura de la notificación (por defecto `FALSE`). |
| created_at          | TIMESTAMP, DEFAULT CURRENT_TIMESTAMP | Fecha y hora de creación de la notificación. |



---

## Repositorios

- **Backend**: [MindNet Backend](https://github.com/JoanSebastianRuiz/MindNet)
- **Frontend**: [MindNet Frontend](https://github.com/JoanSebastianRuiz/MindNet-Front)

Últimos hashes de commits:

- Backend: `5f34218f0406155d6ee46b32ea9eff093f6e15da`
- Frontend: `692f900a9f16bd5f9b9302a206c634ec16d33905`

---

## Documentación del Proyecto

- **Diagrama de base de datos (Modelo Entidad-Relación)**:

![Image](https://github.com/user-attachments/assets/ae1f470c-4dbd-4b45-a3d4-b9fa5a4f77ba)

- **Documentación de la API con Swagger**:
    La documentacion de la API con Swagger se encuentra en la carpeta Swagger, donde esta el archivo yaml y una carpeta con el html
- **Video demostrativo**: [Ver aquí](https://soysena-my.sharepoint.com/:f:/g/personal/joan_sruiz_soy_sena_edu_co/EnYeT-4wGP1HhKGqjuj67LcBnAOD0NmWgd2DRSlMtUC-MQ?e=gHQAWK)

