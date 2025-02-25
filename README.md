# Tenpo Challenge
Este proyecto es una aplicación desarrollada en **Spring Boot 3.4.2** que utiliza:
- **Java 21+**
- **Gradle 8.12.1+**
- **PostgreSQL 17.2+** como base de datos relacional principal.
- **Redis 7.4.2+** como sistema de almacenamiento en caché.
- **Flyway** para la gestión y control de versiones de la base de datos.
- **Swagger** para la documentación interactiva de la API.

Incluye un archivo ```docker-compose.yml``` para desplegar los servicios de forma rápida.

### **Lista de Endpoints**

| **Endpoint**              | **Método** | **Descripción**                                              | **Body**                 | **Params**         |
|---------------------------|------------|--------------------------------------------------------------|--------------------------|--------------------|
| `/actuator/health`        | `GET`      | Recupera el estado de la aplicación                          | -                        | -                  |
| `/api/v1/math/binary-sum` | `POST`     | Calcula la suma de dos números y le agrega un porcentaje.    | `{"num1": 5,"num2": 5}`  | -                  |
| `/api/v1/math/sum`        | `POST`     | Calcula la suma de varios números y le agrega un porcentaje. | `{"numbers": [5,5,5,5]}` | -                  |
| `/api/v1/percentage`      | `GET`      | Recupera el porcentaje actual.                               | -                        | -                  |
| `/api/v1/api-logs`        | `GET`      | Recupera un listado de logs generados por la API.            | -                        | `?page=27&size=10` |
| `/api/v1/cache/expire`    | `PUT`      | Expira las claves del caché para forzar su recarga.          | -                        | -                  |

---

### Swagger

La documentación interactiva de la API generada por Swagger está disponible en:

http://localhost:9090/swagger-ui.html

### Postman Collection

Puedes encontrar una colección de postman con el siguiente nombre:

```tenpo-challenge-collection.postman_collection.json ```

### Desplegar aplicación localmente con Docker (docker-compose)

Puedes deplegar la aplicación localmente con este simple comando:
```
docker compose up -d
```
El comando levantará automáticamente Redis, PostgreSQL y la aplicación API.

### Desplegar PostgreSQL y Resdis con Docker (docker-compose)

Si solamente necesitas desplegar Postges y Redis lo puedes realizar con el siguiente comando
```
docker compose up -d postgres redis
```

### Desplegar aplicación desde Docker Hub

```
docker run -d --name tenpo-challenge -p 9090:9090 --env DATASOURCE_URL=jdbc:postgresql://192.168.1.90:5432/postgres --env DATASOURCE_USERNAME=postgres --env DATASOURCE_PASSWORD=postgres --env REDIS_HOST=192.168.1.90 --env REDIS_PORT=6379 andresmunozb/tenpo-challenge:latest
```

### Variables de entorno para imagen Docker Hub

Configura las siguientes variables de entorno según tus necesidades:

- **PostgreSQL**:
  - `DATASOURCE_URL`: URL de conexión a la base de datos. Incluye el protocolo JDBC, la dirección del servidor, el puerto y el nombre de la base de datos (por defecto ```jdbc:postgresql://localhost:5432/postgres```)
  - `DATASOURCE_USERNAME`: Usuario de la base de datos (por defecto ```postgres```)
  - `DATASOURCE_PASSWORD`: Contraseña del usuario (por defecto ```postgres```)

- **Redis**:
  - `REDIS_HOST`: Dirección del servidor Redis (por defecto `localhost`).
  - `REDIS_PORT`: Puerto del servidor Redis (por defecto `6379`).


### Análisis de decisiones técnicas

* Sobre estructura de archivos:
  * Se intenta llevar un clean architecture sin aplicar arquitectura hexagonal o algo por el estilo, ya que no valdría la pena para un proyecto tan pequeño.
    * `Core`: Contiene las principales clases utilizadas por las otras capas.
    * `Controller`: Contienen los endpoints que se exponen.
    * `Service`: Contienen la lógica de negocio (simple)
    * `Repository`: Implementan la comunicación con la base de datos.
    * `Event`: Maneja eventos de forma asíncrona.
  * Se  implementa manejo global de excepciones centralizada con `@ControllerAdvice`
  * 
    
* Sobre caché:
  * 
* Sobre imágenes docker:
  * Se utiliza imagen [21-jdk-slim-buster](https://hub.docker.com/layers/library/openjdk/21-jdk-slim-buster/images/sha256-4d4212d0216b3846a3afa1b65de764f4a76313ab8753e3c05590f187b2c253ee) porque actualmente tiene la menor cantidad de vulnerabilidades.
  * Para las imagenes de redis y postgres se utiliza imagenes basadas en alpine ya que por norma general son mas ligeras.


