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
El comando levantará automáticamente Redis, PostgreSQL y la aplicación SpringBoot (API).

### Desplegar PostgreSQL y Resdis con Docker (docker-compose)

Si solamente necesitas desplegar Postges y Redis lo puedes realizar con el siguiente comando
```
docker compose up -d postgres redis
```

### Desplegar aplicación desde Docker Hub
Puedes utilizar este comando para obtener y correr la imagen desde Docker Hub.
Procura reemplazar correctamente las variables.

```
docker run -d --name tenpo-challenge -p <HOST_PORT>:9090 --env DATASOURCE_URL=jdbc:postgresql://<DB_HOST>:<DB_PORT>/<DB_NAME> --env DATASOURCE_USERNAME=<DB_USERNAME> --env DATASOURCE_PASSWORD=<DB_PASSWORD> --env REDIS_HOST=<REDIS_HOST> --env REDIS_PORT=<REDIS_PORT> andresmunozb/tenpo-challenge:latest
```
Ejemplo con valores:

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

### Levantar la Aplicación Localmente (Sin Docker)
1.	Asegúrate de tener Java 21 instalado.
2.	Inicia la aplicación Spring Boot con:
```
gradle bootRun
```
3.	La aplicación se levantará en el puerto configurado (por defecto, 9090).


### Ejecutar tests

#### Tests unitarios
```
gradle test
```
O tambien puedes utilizar 
```
gradle unitTest
```

#### Tests de integración
```
gradle integrationTest
```

### Análisis de decisiones técnicas

#### Sobre estructura de archivos
  * Se intenta llevar una arquitectura limpia (clean architecture) sin aplicar arquitectura hexagonal o algo sofisticado, ya que no valdría la pena para un proyecto tan pequeño.
    * `Core`: Contiene las principales clases utilizadas por las otras capas.
    * `Controller`: Contienen los endpoints que se exponen.
    * `Service`: Contienen la lógica de negocio (simple)
    * `Repository`: Implementan la comunicación con la base de datos.
    * `Event`: Maneja eventos de forma asíncrona.
  * Se  implementa manejo global de excepciones centralizada con `@ControllerAdvice`

#### Sobre Flyway vs Hibernate
  * Se prefirió utilizar Flyway en lugar de las herramientas de generación automática de esquemas de Hibernate, pars tener un mayor control de las modificaciones en la base de datos.

#### Sobre Filtros
  * Se elige implementar filtros para agregar lógica de forma centralizada para todos los requests.
  * Se implementan sobre dos filtros para los requests:
    * LoggingFilter: Contiene la lógica necesaria para guardar el historial de llamadas.
    * RateLimitFilter: Contiene la logica necesaria para manejar el limite de requests.

#### Sobre caché
  * Se utiliza Redis como cache por su simplicidad.
  * Se utiliza para manejar la cantidad de request por IP (podria adaptarse por usuario si es requerido)
    * Esto permite que si se tienen multiples instancias del micro servicio se pueda manejar de forma correcta compartiendo la cantidad de requests.
  * Tambien se utiliza para guardar en cache las llamadas a servicio externo para obtener el porcentaje, mejorando el performance.

#### Sobre reintentos
* Se elige retrayable por su simplicidad y capacidad para configurar cantidad de retries y backoff (delay y multiplier)

* Sobre eventos asíncronos:
* Se prefirió utilizar mecanismos internos para manejar eventos como ApplicationEventPublisher en lugar de Kafka.
* Ventajas:
  * Simplicidad de desarrollo
  * Menores requisitos técnicos
  * Bajo acoplamiento temporalmente
  * Menos latencia
* Limitaciones:
  * Escalabilidad, los eventos no pueden ser consumidos por otras aplicaciones de ser requerido
  * Se pierden funcionalidades de robustes: reintentos, persistencia, auditoria.

#### Sobre imágenes docker:
* Para la construcción de las imagenes se realiza por capas (layers), permitiendo que la reconstrucción sea mas rápida si es que no cambian las dependencias.
* Se utiliza imagen [21-jdk-slim-buster](https://hub.docker.com/layers/library/openjdk/21-jdk-slim-buster/images/sha256-4d4212d0216b3846a3afa1b65de764f4a76313ab8753e3c05590f187b2c253ee) porque es ligera y actualmente tiene la menor cantidad de vulnerabilidades.
* Para las imagenes de redis, postgres y gradle se utiliza imagenes basadas en alpine ya que por norma general son mas ligeras.

#### Sobre CI/CD
* Se implementa github action para publicar imagen de docker automáticamente en docker hub. Se gatilla al cuando se realiza un push a master. Permite mantener siempre actualizada la imagen de docker hub publicada.


