# Guía de Agentes y Desarrolladores — Java-SpringBoot-WebFlux

Este documento define el stack tecnológico, la arquitectura reactiva, los patrones de diseño y las reglas críticas que **TODOS** los agentes de inteligencia artificial y desarrolladores deben seguir obligatoriamente al trabajar en este proyecto.

---

## 1. Stack Tecnológico

El proyecto está diseñado bajo una **Arquitectura Reactiva de Extremo a Extremo (End-to-End Reactive Architecture)**, totalmente no bloqueante.

---

## Project Skills & Knowledge Base

Este proyecto utiliza un sistema modular de "Skills" ubicado en [.agents/skills/](.agents/skills/). Estos documentos contienen guías especializadas y patrones para tareas específicas del proyecto.

**El agente DEBE seguir este protocolo:**

1. **Discovery**: Consultar `.agents/skills/` para identificar documentación relevante.
2. **Activation**: Leer el archivo `SKILL.md` de las skills relevantes:
   - [Contenedorización con Docker](.agents/skills/spring-implements-docker/SKILL.md) (`spring-implements-docker`)
   - [Configuración de Maven](.agents/skills/spring-implements-maven/SKILL.md) (`spring-implements-maven`)
3. **Compliance**: Aplicar los patrones definidos en la skill correspondiente.

| Categórica | Tecnología / Librería | Versión / Detalle |
| :--- | :--- | :--- |
| **Lenguaje & Runtime** | Java 17 | JDK 17 con `--enable-preview` en compilación y ejecución |
| **Framework Base** | Spring Boot | `2.5.4` (`spring-boot-starter-parent`) |
| **Paradigma Reactivo** | Project Reactor | `Flux<T>` (secuencias) y `Mono<T>` (0 o 1 elemento) |
| **Capa Web** | Spring WebFlux | `spring-boot-starter-webflux` sobre servidor Netty no bloqueante |
| **Documentación API** | SpringFox Swagger | `3.0.0` (`springfox-swagger2`, `springfox-swagger-ui`) |
| **Acceso a Datos** | Spring Data R2DBC | `spring-boot-starter-data-r2dbc` |
| **Driver BD** | R2DBC PostgreSQL | `io.r2dbc:r2dbc-postgresql` (asíncrono y reactivo) |
| **Base de Datos** | PostgreSQL | Acceso mediante URL R2DBC (`r2dbc:postgresql://localhost/demo`) |
| **Mapeo y Boilerplate** | Lombok & MapStruct | Lombok `1.18.22` y MapStruct `1.4.2.Final` |
| **Calidad de Código** | Checkstyle | `maven-checkstyle-plugin` `3.1.2` con Checkstyle `8.42` (`checkstyle.xml`) |
| **Testing** | JUnit 5 / Reactor Test / REST Assured | `reactor-test` (`StepVerifier`) y `rest-assured` `4.4.0` |
| **Despliegue & CI/CD** | Docker & Kubernetes | `Dockerfile` (openjdk:17) y GitHub Actions |

---

## 2. Reglas Críticas e Inviolables (MANDATORY)

1. **Reactividad Estricta y Non-Blocking (Cero Bloqueos):**
   - **NUNCA** usar operaciones bloqueantes como `.block()`, `Thread.sleep()`, `CompletableFuture.get()`, ni controladores JDBC tradicionales.
   - Retornar siempre tipos reactivos (`Mono<T>` o `Flux<T>`) desde los controladores y servicios.
   - Gestionar estados vacíos y errores de forma reactiva (`switchIfEmpty`, `onErrorResume`, `onErrorReturn`).

2. **Inyección de Dependencias por Constructor:**
   - Todas las dependencias en `@Service`, `@RestController` o `@Configuration` deben inyectarse a través del constructor annotate con `@Autowired`.
   - **EVITAR** `@Autowired` directo sobre campos de clase (`field injection`).

3. **Cumplimiento Estricto de Checkstyle y Javadoc:**
   - Todo cambio de código debe pasar la validación `mvn checkstyle:check` sin errores ni advertencias.
   - **TODAS** las clases, interfaces, métodos (públicos y protegidos) y campos privados deben tener sus respectivos comentarios Javadoc.
   - Todos los paquetes de código dentro de `src/main/java/` deben contener un archivo `package-info.java` documentado.

4. **Inviolabilidad de Arquitectura en Capas:**
   - **Controlador (`web`)** $\rightarrow$ delega únicamente al **Servicio (`service`)**.
   - **Servicio (`service`)** $\rightarrow$ implementa la lógica de negocio y consulta al **Repositorio (`repository`)**.
   - **Repositorio (`repository`)** $\rightarrow$ hereda de `ReactiveCrudRepository` y mapea entidades de **Dominio (`domain`)**.
   - No saltearse capas ni colocar lógica de persistencia en los controladores.

5. **Verificación Obligatoria de Calidad:**
   - Antes de dar por completada cualquier tarea, se deben ejecutar los comandos de compilación, linter y tests.

---

## 3. Estructura del Proyecto

```text
Java-SpringBoot-WebFlux/
├── .github/
│   ├── k8s/                       # Scripts y manifiestos de despliegue en Kubernetes
│   └── workflows/                 # CI/CD pipelines (docker-publish.yml)
├── src/
│   ├── main/
│   │   ├── java/com/example/spring/
│   │   │   ├── config/            # Configuraciones (R2DBC, Swagger, Seguridad)
│   │   │   │   └── package-info.java
│   │   │   ├── domain/            # Entidades de dominio mapeadas a R2DBC (@Table)
│   │   │   │   └── package-info.java
│   │   │   ├── repository/        # Repositorios reactivos (ReactiveCrudRepository)
│   │   │   │   └── package-info.java
│   │   │   ├── service/           # Interfaces de servicios reactivos
│   │   │   │   ├── impl/          # Implementaciones de servicios
│   │   │   │   │   └── package-info.java
│   │   │   │   └── package-info.java
│   │   │   ├── web/               # Controladores REST WebFlux (@RestController)
│   │   │   │   └── package-info.java
│   │   │   ├── DemoApplication.java # Clase principal de arranque
│   │   │   └── package-info.java
│   │   └── resources/
│   │       ├── application.yml    # Propiedades del servidor y R2DBC
│   │       ├── schema.sql         # Definición DDL de tablas PostgreSQL
│   │       └── data.sql           # Inserción DML de datos iniciales
│   └── test/
│       └── java/com/example/      # Tests unitarios y de integración reactivos
├── checkstyle.xml                 # Reglas estrictas de Checkstyle Sun/Google
├── Dockerfile                     # Construcción de imagen de contenedor
├── pom.xml                        # Configuración de dependencias y plugins Maven
└── AGENTS.md                      # Instrucciones y guía para agentes de IA
```

---

## 4. Patrones de Implementación (Código de Referencia)

### A. Entidad de Dominio (`domain`)
Las entidades de dominio deben usar anotaciones R2DBC (`@Table`, `@Id`, `@Column`) y Lombok (`@Data`).

```java
package com.example.spring.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa la entidad de dominio para las órdenes de compra.
 */
@Data
@Table("order")
public final class Order {

    /**
     * Identificador único de la orden.
     */
    @Id
    private Long orderId;

    /**
     * Nombre o descripción de la orden.
     */
    @Column
    private String name;

    /**
     * Monto total de la orden.
     */
    @Column
    private BigDecimal amount;

    /**
     * Descuento aplicado.
     */
    @Column
    private BigDecimal discount;

    /**
     * Fecha y hora de creación.
     */
    @Column
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de última actualización.
     */
    @Column
    private LocalDateTime updatedAt;
}
```

---

### B. Repositorio Reactivo (`repository`)
Heredar de `ReactiveCrudRepository<Entity, ID>`. Usar `@Query` si se requieren consultas SQL personalizadas en R2DBC.

```java
package com.example.spring.repository;

import com.example.spring.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio reactivo para la entidad Order.
 */
@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    /**
     * Busca y retorna todas las órdenes almacenadas en la base de datos.
     * @return Flux con todas las órdenes.
     */
    @Query("SELECT * FROM \"order\"")
    Flux<Order> findAll();
}
```

---

### C. Capa de Servicio (`service` y `service.impl`)
Definir interfaz y su correspondiente implementación anotada con `@Service` e inyección por constructor.

#### Interfaz
```java
package com.example.spring.service;

import com.example.spring.domain.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio reactivo para operaciones de negocio de órdenes.
 */
public interface OrderService {

    /**
     * Obtiene todas las órdenes existentes.
     * @return Flux que emite cada una de las órdenes encontradas.
     */
    Flux<Order> findAllOrders();

    /**
     * Obtiene una orden por su ID.
     * @param id Identificador de la orden.
     * @return Mono que emite la orden encontrada o vacío si no existe.
     */
    Mono<Order> findOrderById(Long id);

    /**
     * Persiste una nueva orden.
     * @param order Entidad orden a guardar.
     * @return Mono con la orden guardada.
     */
    Mono<Order> save(Order order);

    /**
     * Edita una orden existente.
     * @param order Entidad orden con los cambios.
     * @return Mono con la orden actualizada.
     */
    Mono<Order> edit(Order order);

    /**
     * Elimina una orden según su ID.
     * @param id Identificador de la orden a eliminar.
     * @return Mono de tipo Void que indica finalización.
     */
    Mono<Void> delete(Long id);
}
```

#### Implementación
```java
package com.example.spring.service.impl;

import com.example.spring.domain.Order;
import com.example.spring.repository.OrderRepository;
import com.example.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación reactiva del servicio de órdenes.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Repositorio de órdenes.
     */
    private final OrderRepository orderRepository;

    /**
     * Constructor para inyección de dependencias.
     * @param orderRepository Repositorio de órdenes.
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Flux<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Mono<Order> save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Mono<Order> edit(Order order) {
        return orderRepository.findById(order.getOrderId())
                .switchIfEmpty(Mono.empty())
                .flatMap(origin -> {
                    origin.setName(order.getName());
                    origin.setAmount(order.getAmount());
                    origin.setDiscount(order.getDiscount());
                    return orderRepository.save(origin);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return orderRepository.deleteById(id);
    }
}
```

---

### D. Controlador REST Reactivo (`web`)
Usar `@RestController` y `@RequestMapping`. Retornar directamente `Flux` o `Mono`.

```java
package com.example.spring.web;

import com.example.spring.domain.Order;
import com.example.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Controlador REST para el recurso de órdenes.
 */
@RestController
@RequestMapping("/api/v1")
public final class OrderController {

    /**
     * Servicio de gestión de órdenes.
     */
    private final OrderService orderService;

    /**
     * Constructor para inyección de dependencias.
     * @param orderService Servicio de órdenes.
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint GET que expone el listado completo de órdenes.
     * @return Flux que emite el conjunto de órdenes disponibles.
     */
    @GetMapping("/order")
    public Flux<Order> index() {
        return this.orderService.findAllOrders();
    }
}
```

---

### E. Archivos `package-info.java`
Cada paquete Java en `src/main/java/` **DEBE** contar con un archivo `package-info.java` para cumplir con Checkstyle (`JavadocPackage`).

```java
/**
 * Paquete de controladores REST de la aplicación.
 */
package com.example.spring.web;
```

---

## 5. Pruebas y Validación Reactiva

Para probar flujos reactivos con Project Reactor, utilizar `StepVerifier` de `reactor-test`:

```java
StepVerifier.create(orderService.findOrderById(1L))
    .expectNextMatches(order -> order.getName().equals("Orden Demo"))
    .verifyComplete();
```

---

## 6. Workflow de Verificación (Pre-completion Verification)

Antes de reportar cualquier tarea como completada, el agente **DEBE** ejecutar y validar:

1. **Verificación de Estilo y Javadoc (Checkstyle):**
   ```bash
   mvn checkstyle:check
   ```
   *Debe resultar sin errores de formato, faltas de Javadoc ni violaciones de reglas.*

2. **Compilación y Pruebas Unitarias:**
   ```bash
   mvn test
   ```
   *Todos los tests deben pasar exitosamente.*

3. **Compilación Limpia y Empaquetado:**
   ```bash
   mvn clean package -DskipTests
   ```
   *Garantiza que el artefacto JAR se genere correctamente.*
