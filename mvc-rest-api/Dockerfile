# Stage 1: Preparar las dependencias (solo se reconstruye cuando cambian las dependencias)
FROM gradle:8.12.1-jdk21-alpine AS dependencies
WORKDIR /app

# Copiar solo los archivos relacionados con las dependencias
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle build --no-daemon --parallel --continue --dry-run

# Stage 2: Construcción (solo se reconstruye cuando cambia el código fuente)
FROM gradle:8.12.1-jdk21-alpine AS builder
WORKDIR /app

# Copiar las dependencias cacheadas del primer stage
COPY --from=dependencies /home/gradle/.gradle /home/gradle/.gradle

# Copiar el resto del código fuente
COPY src ./src
COPY build.gradle settings.gradle ./

# Compilar el código fuente y generar el JAR
RUN gradle clean build -x test --no-daemon

# Stage 3: Runtime (imagen ligera para ejecución)
FROM openjdk:21-jdk-slim-buster AS runtime
WORKDIR /app

# Copiar el JAR generado
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 9090

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/app.jar"]