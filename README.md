# Tenpo Challenge
Descripción

### Requisitos Previos
- Java 21 o superior
- Gradle 8.12.1 o superior
- PostgreSQL 17.2 o superior
- Redis 7.4.2 o superior
- Docker (opcional, para ejecutar la PostgreSQL y Redis)

### Ejecución con Docker

1. Levanta los servicios con docker compose:
   ```
   docker compose up -d
   ```
### Ejecución PostgreSQL y Resdis
1. Levanta los servicios con docker compose:
   ```
   docker compose up -d postgres redis
   ```
### Ejecución aplicación desde Docker Hub
1. Levanta la aplicación:
   ```
   docker run -d --name tenpo-challenge -p 9090:9090 --env DATASOURCE_URL=jdbc:postgresql://192.168.1.90:5432/postgres --env DATASOURCE_USERNAME=postgres --env DATASOURCE_PASSWORD=postgres --env REDIS_HOST=192.168.1.90 --env REDIS_PORT=6379 andresmunozb/tenpo-challenge:latest
   ```
2. Tambien puedes utilizar docker compose de la siguiente forma:
   ```
   docker run -d --name tenpo-challenge -p 9090:9090 --env DATASOURCE_URL=jdbc:postgresql://192.168.1.90:5432/postgres --env DATASOURCE_USERNAME=postgres --env DATASOURCE_PASSWORD=postgres --env REDIS_HOST=192.168.1.90 --env REDIS_PORT=6379 andresmunozb/tenpo-challenge:latest
   ```
### Consideraciones

* Se utiliza imagen [21-jdk-slim-buster](https://hub.docker.com/layers/library/openjdk/21-jdk-slim-buster/images/sha256-4d4212d0216b3846a3afa1b65de764f4a76313ab8753e3c05590f187b2c253ee) porque actualmente tiene la menor cantidad de vulnerabilidades que el resto de imagenes de jdk
* Para las imagenes de redis y postgres se utiliza imagenes basadas en alpine ya que por norma general son mas ligeras.
* Esto misma consideracion se utiliza para la selección de las capas previas a la construcicón de la imagen de la aplicacion, es decir, se utiliza gradle con alpine.
* Levantar solamente componentes externos ```docker-compose -f docker-compose-dev.yml up -d```


