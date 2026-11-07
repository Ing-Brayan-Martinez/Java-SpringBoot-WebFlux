# Java-SpringBoot-WebFlux

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.4-green.svg)](https://spring.io/projects/spring-boot)
[![Project Reactor](https://img.shields.io/badge/Project%20Reactor-WebFlux-blue.svg)](https://projectreactor.io/)
[![R2DBC](https://img.shields.io/badge/Spring%20Data-R2DBC-teal.svg)](https://spring.io/projects/spring-data-r2dbc)

Aplicación demostrativa y plantilla base desarrollada con **Java 17** y **Spring Boot 2.5.4 WebFlux**, implementando una **Arquitectura Reactiva No Bloqueante (End-to-End Reactive)** con persistencia asíncrona sobre **PostgreSQL** mediante **Spring Data R2DBC**.

---

## 🚀 Características Principales

- **Paradigma Reactivo:** Flujos asíncronos y no bloqueantes con `Mono<T>` y `Flux<T>` sobre servidor Netty.
- **Acceso a Datos Asíncrono:** Persistencia no bloqueante con **Spring Data R2DBC** y driver `r2dbc-postgresql`.
- **Documentación OpenAPI / Swagger:** Documentación e interfaz interactiva de endpoints con **SpringFox Swagger UI 3.0.0**.
- **Control de Calidad Estricto:** Verificación automatizada de código y formato Javadoc mediante **Checkstyle 8.42** (`checkstyle.xml`).
- **Contenedorización y Despliegue:** Preparado para entornos de producción con **Dockerfile** y manifiestos de **Kubernetes**.
- **Integración con IA / Agentes:** Sistema modular de *Skills* para agentes en `.agents/skills/` e instrucciones centralizadas en `AGENTS.md`.

---

## 🛠️ Stack Tecnológico

| Componente | Tecnología | Versión / Detalle |
| :--- | :--- | :--- |
| **Lenguaje** | Java | 17 |
| **Framework Base** | Spring Boot | 2.5.4 |
| **Capa Reactiva** | Spring WebFlux / Project Reactor | Reactor Core |
| **Persistencia** | Spring Data R2DBC | Driver `io.r2dbc:r2dbc-postgresql` |
| **Base de Datos** | PostgreSQL | 14+ |
| **Documentación** | SpringFox Swagger | 3.0.0 (`swagger-ui`) |
| **Mapeo / Boilerplate** | Lombok & MapStruct | Lombok 1.18.22, MapStruct 1.4.2.Final |
| **Linter / Estilo** | Checkstyle Plugin | 3.1.2 (Reglas Sun/Google en `checkstyle.xml`) |
| **Contenedores** | Docker & Kubernetes | Dockerfile (OpenJDK 17) |

---

## 📂 Arquitectura del Proyecto

El proyecto sigue una arquitectura reactiva en capas bien diferenciadas:

```text
Java-SpringBoot-WebFlux/
├── .agents/skills/            # Skills y guías de conocimiento para agentes de IA
│   ├── spring-implements-docker/ # Skill para contenedorización con Docker
│   └── spring-implements-maven/  # Skill para configuración de pom.xml
├── .github/
│   ├── command/               # Scripts para Docker y ejecución
│   └── workflows/             # Pipelines de CI/CD (GitHub Actions)
├── src/main/java/com/example/spring/
│   ├── config/                # Configuraciones (R2DBC populator, Swagger)
│   ├── domain/                # Entidades reactivas R2DBC (Order, Product)
│   ├── repository/            # Repositorios ReactiveCrudRepository
│   ├── service/               # Interfaces y lógica de negocio reactiva
│   │   └── impl/              # Implementaciones de servicios
│   ├── web/                   # Controladores REST WebFlux (@RestController)
│   └── DemoApplication.java    # Clase principal de arranque
├── src/main/resources/
│   ├── application.yml        # Configuración del servidor y URL R2DBC
│   ├── schema.sql             # Definición DDL de las tablas
│   └── data.sql               # Datos iniciales de prueba (DML)
├── AGENTS.md                  # Guía y directrices para desarrolladores y agentes IA
├── Checkstyle.xml             # Reglas estrictas de calidad de código
├── Dockerfile                 # Imagen Docker de la aplicación
└── pom.xml                    # Configuración de dependencias y build Maven
```

---

## 📡 Endpoints Disponibles

La aplicación expone servicios REST reactivos bajo el prefijo `/api/v1`:

### 📦 Módulo de Órdenes (`/api/v1/order`)
- `GET /api/v1/order`: Listar todas las órdenes de compra.

### 🏷️ Módulo de Productos (`/api/v1/product`)
- `GET /api/v1/product`: Listar todos los productos.
- `GET /api/v1/product/{id}`: Obtener producto por ID.
- `POST /api/v1/product`: Crear un nuevo producto.
- `PUT /api/v1/product/{id}`: Actualizar un producto existente.
- `DELETE /api/v1/product/{id}`: Eliminar un producto por ID.

---

## ⚙️ Guía de Inicio Rápido

### Requisitos Previos
- **JDK 17**
- **Maven 3.6+**
- **Docker** (para la base de datos PostgreSQL)

### 1. Iniciar la Base de Datos PostgreSQL
Ejecutar el contenedor de PostgreSQL con los parámetros de conexión por defecto configurados en `application.yml`:

```bash
docker run -d -p 5432:5432 \
  --name postgresql \
  -e POSTGRES_PASSWORD=postgres \
  postgres:14
```

### 2. Validar Calidad del Código (Checkstyle)
```bash
mvn checkstyle:check
```

### 3. Compilar el Proyecto
```bash
mvn clean compile
```

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación iniciará en `http://localhost:8080`.

---

## 📖 Documentación interactiva (Swagger UI)

Una vez iniciada la aplicación, se puede acceder a la interfaz interactiva de Swagger UI en:

```text
http://localhost:8080/swagger-ui/
```


