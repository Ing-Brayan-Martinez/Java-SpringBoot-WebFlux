---
name: spring-implements-docker
description: Guía paso a paso para contenedorizar aplicaciones Spring Boot WebFlux con Docker, configurar application.yml y ejecutar scripts de orquestación Docker para bases de datos (PostgreSQL, SQL Server) y la aplicación. Usar al crear/modificar el Dockerfile, configurar el entorno o ejecutar comandos Docker.
---

# Guía de Contenedorización con Docker para Spring Boot WebFlux

## Contenido

- [Resumen y Propósito](#resumen-y-propósito)
- [1. Configuración de la Aplicación (`application.yml`)](#1-configuración-de-la-aplicación-applicationyml)
- [2. Construcción de la Imagen con `Dockerfile`](#2-construcción-de-la-imagen-con-dockerfile)
- [3. Orquestación y Comandos Shell (`.github/command/docker.sh`)](#3-orquestación-y-comandos-shell-githubcommanddockersh)
  - [Gestión de Redes](#gestión-de-redes)
  - [Despliegue de Base de Datos PostgreSQL](#despliegue-de-base-de-datos-postgresql)
  - [Despliegue de Base de Datos SQL Server (Opcional)](#despliegue-de-base-de-datos-sql-server-opcional)
  - [Construcción y Verificación de la Imagen](#construcción-y-verificación-de-la-imagen)
  - [Ejecución del Contenedor de la Aplicación](#ejecución-del-contenedor-de-la-aplicación)
- [Reglas Críticas y Buenas Prácticas](#reglas-críticas-y-buenas-prácticas)
- [Referencias](#referencias)

---

## Resumen y Propósito

Esta skill proporciona las directrices y estándares para empaquetar, contenedorizar y ejecutar la aplicación reactiva Java Spring Boot WebFlux utilizando Docker. Incluye la integración de la base de datos PostgreSQL, configuración del `application.yml`, el `Dockerfile` optimizado y el uso de scripts de comandos shell.

---

## 1. Configuración de la Aplicación (`application.yml`)

Ubicación: `src/main/resources/application.yml`

El archivo de propiedades define el puerto de escucha del servidor WebFlux (Netty) y la conexión reactiva a PostgreSQL mediante R2DBC:

```yaml
server:
  port: 8080

spring:
  application:
    name: Java-SpringBoot-WebFlux

  r2dbc:
    url: r2dbc:postgresql://localhost/demo
    username: postgres
    password: postgres
```

> **Nota para Entornos de Contenedores:** Al ejecutar la aplicación dentro de una red Docker (`bridge` o personalizada), el host `localhost` en la URL de R2DBC debe sustituirse por el nombre del contenedor de la base de datos (por ejemplo, `r2dbc:postgresql://postgresql:5432/demo`) o mediante variables de entorno dinámicas.

---

## 2. Construcción de la Imagen con `Dockerfile`

Ubicación: `Dockerfile`

El `Dockerfile` utiliza OpenJDK 17 como base para ejecutar el archivo ejecutable `.jar` generado por Maven:

```dockerfile
FROM openjdk:17 as builder
LABEL maintainer="Brayan Martinez (brayanmartinez827@gmail.com)"

ENV WORKDIR_BASE=/home/application

EXPOSE 8080:8080

WORKDIR $WORKDIR_BASE

COPY ./target/Java-SpringBoot-WebFlux-0.0.1-SNAPSHOT.jar Java-SpringBoot-WebFlux-0.0.1-SNAPSHOT.jar

ENTRYPOINT java -jar Java-SpringBoot-WebFlux-0.0.1-SNAPSHOT.jar
```

---

## 3. Orquestación y Comandos Shell (`.github/command/docker.sh`)

Ubicación: `.github/command/docker.sh`

### Gestión de Redes
Inspeccionar las redes disponibles en Docker para asegurar comunicación entre contenedores:

```bash
# Listar redes existentes
docker network ls

# Inspeccionar la red seleccionada
docker network inspect bridge
```

### Despliegue de Base de Datos PostgreSQL
Lanzar el contenedor de PostgreSQL 14 necesario para el motor de persistencia R2DBC:

```bash
# Descargar la imagen oficial de PostgreSQL 14
docker pull postgres:14

# Ejecutar el contenedor de PostgreSQL
docker run -d -p 5432:5432 --hostname postgresql --network bridge \
     -e POSTGRES_PASSWORD=postgres --name postgresql postgres:14

# Acceso interactivo al contenedor
docker exec -it postgresql bash
```

### Despliegue de Base de Datos SQL Server (Opcional)
En caso de requerir integración con Microsoft SQL Server:

```bash
docker pull mcr.microsoft.com/mssql/server:2019-latest

docker run -d -p 1433:1433 --hostname mssql-server --network bridge \
  -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Br88##ss" -e "MSSQL_PID=Express" \
  --name mssql-server mcr.microsoft.com/mssql/server:2019-latest

docker exec -it mssql-server /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Br88##ss
```

### Construcción y Verificación de la Imagen

> **Paso Previo Obligatorio:** Generar el archivo `.jar` antes de construir la imagen ejecutando `mvn clean package`.

```bash
# Construir la imagen Docker de la aplicación
docker build . --tag java-springboot-webflux:0.1

# Listar imágenes locales
docker image ls

# Escanear vulnerabilidades de la imagen (requiere autenticación con scanner)
docker scan -f Dockerfile java-springboot-webflux:0.1
```

### Ejecución del Contenedor de la Aplicación

```bash
# Ejecutar el contenedor exponiendo el puerto 8080
docker run -d -p 8080:8080 --hostname java-springboot-webflux --network bridge \
  --name java-springboot-webflux java-springboot-webflux:0.1

# Verificar estado de los contenedores en ejecución
docker ps
```

---

## Reglas Críticas y Buenas Prácticas

1. **Pre-requisito de Compilación:** Siempre compilar y generar el paquete JAR con `mvn clean package` en la máquina host antes de ejecutar `docker build`.
2. **Mapeo de Puertos:** Exponer explícitamente el puerto `8080:8080` para que los clientes REST y Swagger puedan consumir los endpoints de WebFlux.
3. **Conectividad R2DBC:** Garantizar que el contenedor de la aplicación y la base de datos PostgreSQL compartan la misma red Docker (`--network bridge` o red personalizada).
4. **Seguridad y Parches:** Utilizar etiquetas explícitas en las imágenes base (ej. `postgres:14` o `openjdk:17`) y realizar escaneos con `docker scan`.

---

## Referencias

- Dockerfile del proyecto: [Dockerfile](Dockerfile)
- Configuración de la aplicación: [src/main/resources/application.yml](src/main/resources/application.yml)
- Script de comandos Docker: [.github/command/docker.sh](.github/command/docker.sh)
- Guía general del proyecto: [AGENTS.md](AGENTS.md)
