# 🚗 Motorbit API

API REST para la gestión de clientes, vehículos y órdenes de servicio de un taller automotriz.

Motorbit surge para reemplazar el manejo manual de información en cuadernos por un sistema centralizado, seguro y estructurado que permita registrar clientes, administrar sus vehículos y llevar trazabilidad de cada orden de servicio.

---

## 📌 Problema a resolver

El taller gestionaba manualmente clientes, vehículos y órdenes de servicio, lo que generaba problemas como:

- Pérdida de órdenes.
- Información dispersa.
- Dificultad para consultar el historial de un vehículo.
- Falta de seguimiento sobre el estado de las reparaciones.
- Ausencia de control de acceso al sistema.

Motorbit digitaliza este proceso y centraliza la operación principal del taller.

---

## 🚀 Funcionalidades principales

- Gestión de clientes.
- Gestión de vehículos asociados a clientes.
- Registro y seguimiento de órdenes de servicio.
- Consulta de órdenes por vehículo y estado.
- Autenticación con JWT.
- Autorización por roles `ADMIN` y `USER`.
- Validación de datos.
- Manejo global de excepciones.
- Logging y trazabilidad.
- Documentación con Swagger / OpenAPI.

---

## 🧩 Modelo de negocio

La aplicación maneja cuatro entidades principales:

- **Cliente**: nombre, cédula, teléfono y email.
- **Vehículo**: placa, marca, modelo, año y cliente propietario.
- **Orden de servicio**: descripción, fechas, estado, costo y vehículo asociado.
- **Usuario**: username, contraseña y rol.

Relaciones principales:

```text
Cliente 1 ─── N Vehículos
Vehículo 1 ─── N Órdenes de servicio
```

---

## 🔄 Flujo de órdenes

Las órdenes siguen un flujo controlado:

```text
RECIBIDO
   ↓
EN_PROGRESO
   ↓
FINALIZADO
   ↓
ENTREGADO
```

El sistema evita transiciones inválidas y registra automáticamente:

- `fechaFinalizacion` al pasar a `FINALIZADO`.
- `fechaEntrega` al pasar a `ENTREGADO`.

---

## 🛠️ Tecnologías

- Java
- Spring Boot
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Spring Security
- JWT
- BCrypt
- Jakarta Validation
- MapStruct
- Lombok
- Swagger / OpenAPI
- SLF4J + Logback
- Maven

---

## 🏗️ Arquitectura

El proyecto está organizado por responsabilidades:

```text
config
controller
dto
exception
mapper
model
repository
response
security
service
validators
```

Principales responsabilidades:

- `controller`: endpoints REST.
- `service`: lógica de negocio.
- `repository`: acceso a datos.
- `mapper`: transformación entre entidades y DTOs.
- `security`: autenticación, autorización y JWT.
- `validators`: validaciones personalizadas.
- `exception`: manejo de excepciones.
- `response`: estructura común de respuestas.

Se buscó aplicar principios SOLID, especialmente separación de responsabilidades y dependencia de interfaces en la capa de servicios.

---

## 📦 DTOs y MapStruct

La API no expone directamente las entidades JPA.

Se utilizan DTOs de request y response, implementados principalmente con Java Records.

MapStruct se encarga de transformar:

```text
Request DTO → Entidad
Entidad → Response DTO
```

Esto evita código repetitivo y mantiene los servicios más limpios.

---

## ✅ Validación

Se utiliza Jakarta Validation con anotaciones como:

```java
@NotNull
@NotBlank
@Size
@Pattern
@Email
@Positive
@DecimalMin
@Digits
```

También se creó la validación personalizada:

```java
@AnioVehiculo
```

que comprueba dinámicamente que:

```text
1900 <= año <= año actual + 1
```

---

## 🔐 Seguridad

Motorbit utiliza Spring Security y JWT.

Características principales:

- Autenticación stateless.
- Tokens JWT.
- Contraseñas protegidas con BCrypt.
- Roles `ADMIN` y `USER`.
- Protección de operaciones mediante:

```java
@PreAuthorize("hasRole('ADMIN')")
```

También se implementó `CustomUserDetails` para separar la entidad `Usuario` del modelo utilizado internamente por Spring Security.

---

## ⚠️ Manejo de errores

La aplicación utiliza:

```java
@RestControllerAdvice
```

para centralizar el manejo de errores.

Se manejan casos como:

- Recursos no encontrados.
- Recursos duplicados.
- Transiciones de estado inválidas.
- Errores de validación.
- Errores de autenticación y autorización.
- Conflictos de integridad de datos.
- Errores inesperados.

Las respuestas se estandarizan mediante:

```text
ApiResponse
ErrorResponse
```

---

## 📝 Logging

La aplicación utiliza SLF4J y Logback.

Se manejan niveles:

```text
DEBUG
INFO
WARN
ERROR
```

Los logs permiten registrar operaciones importantes, errores, autenticaciones fallidas y cambios de estado.

También se configuró rotación de archivos para evitar el crecimiento indefinido de los logs.

---

## ⚙️ Perfiles de configuración

El proyecto utiliza:

```text
application.properties
application-dev.properties
application-prod.properties
```

- `application.properties`: configuración común.
- `application-dev.properties`: entorno de desarrollo.
- `application-prod.properties`: entorno de producción.

En producción, credenciales y secretos se manejan mediante variables de entorno.

---

## 📚 Swagger

La API está documentada con Swagger / OpenAPI.

Permite:

- Consultar endpoints.
- Visualizar requests y responses.
- Probar operaciones.
- Autenticarse mediante Bearer Token.

---

## 📂 Estructura principal

```text
src/main/java/com/motorbit
│
├── config
├── controller
├── dto
├── exception
├── mapper
├── model
├── repository
├── response
├── security
├── service
├── validators
└── MotorbitApplication.java
```

---

## ▶️ Ejecución

Por defecto se utiliza el perfil de desarrollo.

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

Para producción:

```bash
java -jar motorbit.jar --spring.profiles.active=prod
```

---

## 👨‍💻 Autor

**Andres Ortega**

Backend desarrollado con Java, Spring Boot, PostgreSQL y Spring Security, aplicando buenas prácticas de arquitectura, seguridad, validación y manejo de errores.