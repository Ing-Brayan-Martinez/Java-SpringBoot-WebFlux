---
name: spring-implements-maven
description: Guía paso a paso para configurar y generar el archivo pom.xml exacto en proyectos Spring Boot WebFlux con Maven. Incluye la plantilla XML completa con todos sus comentarios de sección, propiedades, dependencias organizadas y plugins de compilación (Checkstyle, Java 17 Preview, Lombok, MapStruct, Surefire, Javadoc, Source y Spring Boot). Usar al crear, restaurar o modificar el archivo pom.xml.
---

# Guía de Configuración y Estructura Exacta de Maven (`pom.xml`)

## Contenido

- [Resumen y Propósito](#resumen-y-propósito)
- [Plantilla Completa del `pom.xml`](#plantilla-completa-del-pomxml)
- [Desglose Estructurado de Secciones](#desglose-estructurado-de-secciones)
  - [1. Declaración del Proyecto y Parent](#1-declaración-del-proyecto-y-parent)
  - [2. Propiedades Globales (`<properties>`)](#2-propiedades-globales-properties)
  - [3. Organización de Dependencias (`<dependencies>`)](#3-organización-de-dependencias-dependencies)
  - [4. Plugins de Compilación y Calidad (`<build><plugins>`)](#4-plugins-de-compilación-y-calidad-buildplugins)
  - [5. Repositorios (`<repositories>`)](#5-repositorios-repositories)
- [Reglas Críticas de Mantenimiento](#reglas-críticas-de-mantenimiento)
- [Referencias](#referencias)

---

## Resumen y Propósito

Esta skill especifica la plantilla exacta, la organización y los comentarios de sección requeridos para el archivo `pom.xml` en el proyecto reactivo `Java-SpringBoot-WebFlux`. Permite a cualquier agente o desarrollador reconstruir o actualizar el archivo respetando la estructura, sangrado y comentarios originales.

---

## Plantilla Completa del `pom.xml`

A continuación se presenta el contenido íntegro y exacto del archivo `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example.spring</groupId>
    <artifactId>Java-SpringBoot-WebFlux</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>${artifactId}</name>
    <description>Demo project with Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.4</version>
    </parent>

    <properties>
        <maven.version>3.3.9</maven.version>
        <encoding>UTF-8</encoding>
        <project.build.sourceEncoding>${encoding}</project.build.sourceEncoding>
        <project.reporting.outputEncoding>${encoding}</project.reporting.outputEncoding>
        <project.resources.sourceEncoding>${encoding}</project.resources.sourceEncoding>
        <jdk.version>17</jdk.version>
        <lombok.version>1.18.22</lombok.version>
        <mapstruct.version>1.4.2.Final</mapstruct.version>
    </properties>

    <dependencies>
        <!-- ========== Spring web ========== -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <!-- ========== Spring data ========== -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-r2dbc</artifactId>
        </dependency>
        <dependency>
            <groupId>io.r2dbc</groupId>
            <artifactId>r2dbc-postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- ========== Spring test ========== -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>4.4.0</version>
            <scope>test</scope>
        </dependency>
        <!-- ========== Swagger ========== -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>3.0.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>3.0.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-data-rest</artifactId>
            <version>3.0.0</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-bean-validators</artifactId>
            <version>3.0.0</version>
        </dependency>
        <!-- ========== Lombok ========== -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
        </dependency>
        <!-- ========== MapStruct ========== -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
        <!-- ========== Jetbrains ========== -->
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>22.0.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- ========== Validate ========== -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.1.2</version>
                <executions>
                    <execution>
                        <id>validate</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                        <configuration>
                            <configLocation>checkstyle.xml</configLocation>
                        </configuration>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>com.puppycrawl.tools</groupId>
                        <artifactId>checkstyle</artifactId>
                        <version>8.42</version>
                    </dependency>
                </dependencies>
            </plugin>
            <!-- ========== Compiler ========== -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${jdk.version}</source>
                    <target>${jdk.version}</target>
                    <encoding>${encoding}</encoding>
                    <compilerArgs>--enable-preview</compilerArgs>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${mapstruct.version}</version>
                        </path>
                    </annotationProcessorPaths>
                    <showDeprecation>false</showDeprecation>
                    <debug>false</debug>
                </configuration>
            </plugin>
            <!-- ========== Surefire ========== -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <argLine>--enable-preview</argLine>
                    <skip>true</skip>
                </configuration>
            </plugin>
            <!-- ========== Jar ========== -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.2.0</version>
                <executions>
                    <execution>
                        <id>default-jar</id>
                        <phase>package</phase>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!-- ========== Javadoc ========== -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>3.2.0</version>
                <executions>
                    <execution>
                        <id>attach-javadocs</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                        <configuration>
                            <javadocExecutable>${java.home}/bin/javadoc</javadocExecutable>
                            <additionalJOptions>
                                <additionalJOption>--enable-preview</additionalJOption>
                                <additionalJOption>--release=${jdk.version}</additionalJOption>
                            </additionalJOptions>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!-- ========== Source ========== -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>3.2.1</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!-- ========== Spring ========== -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.5.4</version>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>repository.spring.release</id>
            <name>Spring GA Repository</name>
            <url>http://repo.spring.io/release</url>
        </repository>
    </repositories>

</project>
```

---

## Desglose Estructurado de Secciones

### 1. Declaración del Proyecto y Parent
- **Model Version:** `4.0.0`.
- **GAV:** `com.example.spring:Java-SpringBoot-WebFlux:0.0.1-SNAPSHOT`.
- **Parent:** `org.springframework.boot:spring-boot-starter-parent:2.5.4`.

### 2. Propiedades Globales (`<properties>`)
- `encoding`: `UTF-8` para la compilación, recursos y reportes.
- `jdk.version`: `17`.
- `lombok.version`: `1.18.22`.
- `mapstruct.version`: `1.4.2.Final`.

### 3. Organización de Dependencias (`<dependencies>`)
Las dependencias se agrupan en 7 bloques claramente demarcados por comentarios:
1. `<!-- ========== Spring web ========== -->`: Core reactivo y WebFlux.
2. `<!-- ========== Spring data ========== -->`: Spring Data R2DBC y drivers `r2dbc-postgresql` y `postgresql`.
3. `<!-- ========== Spring test ========== -->`: Test starter, `reactor-test` y `rest-assured`.
4. `<!-- ========== Swagger ========== -->`: SpringFox Swagger 2, UI, Data REST y Bean Validators (versión `3.0.0`).
5. `<!-- ========== Lombok ========== -->`: Generación de boilerplate con versión `${lombok.version}`.
6. `<!-- ========== MapStruct ========== -->`: Procesador de mappers con versión `${mapstruct.version}`.
7. `<!-- ========== Jetbrains ========== -->`: Anotaciones `org.jetbrains:annotations:22.0.0`.

### 4. Plugins de Compilación y Calidad (`<build><plugins>`)
Los plugins están organizados en 7 bloques correspondientes a las fases del ciclo de vida:
1. `<!-- ========== Validate ========== -->`: `maven-checkstyle-plugin` (3.1.2) enlazado a la fase `validate` con Checkstyle `8.42` y configuración `checkstyle.xml`.
2. `<!-- ========== Compiler ========== -->`: `maven-compiler-plugin` (3.8.1) configurado con `--enable-preview`, Java 17 y `annotationProcessorPaths` para Lombok y MapStruct.
3. `<!-- ========== Surefire ========== -->`: `maven-surefire-plugin` (3.0.0-M5) con `--enable-preview`.
4. `<!-- ========== Jar ========== -->`: `maven-jar-plugin` (3.2.0) para empaquetado JAR estándar.
5. `<!-- ========== Javadoc ========== -->`: `maven-javadoc-plugin` (3.2.0) con `--enable-preview` y `--release=17`.
6. `<!-- ========== Source ========== -->`: `maven-source-plugin` (3.2.1) para adjuntar código fuente.
7. `<!-- ========== Spring ========== -->`: `spring-boot-maven-plugin` (2.5.4) para fat-JAR ejecutable.

### 5. Repositorios (`<repositories>`)
- Repositorio GA de Spring: `http://repo.spring.io/release`.

---

## Reglas Críticas de Mantenimiento

1. **Conservación de Comentarios de Sección:** Toda modificación en dependencias o plugins debe mantener los comentarios de bloque con el formato `<!-- ========== Nombre ========== -->`.
2. **Procesadores de Anotaciones Simultáneos:** En `<annotationProcessorPaths>` dentro de `maven-compiler-plugin`, Lombok y MapStruct deben declararse secuencialmente para garantizar la generación correcta de código.
3. **Fase de Validación:** El plugin de Checkstyle debe ejecutarse siempre en la fase `validate` para evitar que código inválido avance en el pipeline de build.
4. **Verificación tras Cambios:**
   - Ejecutar `mvn checkstyle:check` para asegurar conformidad de estilo.
   - Ejecutar `mvn clean compile` para validar dependencias y procesadores de anotaciones.

---

## Referencias

- Archivo de configuración POM: [pom.xml](pom.xml)
- Configuración de Checkstyle: [checkstyle.xml](checkstyle.xml)
- Guía general del proyecto: [AGENTS.md](AGENTS.md)
