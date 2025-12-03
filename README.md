Markdown# 🚀 API Asíncrona de Alto Rendimiento con Spring WebFlux

API asíncrona de ejemplo construida con **Spring WebFlux** que permite manejar solicitudes HTTP de manera no bloqueante y reactiva. Este proyecto sirve como una base robusta para desarrollar servicios REST modernos, optimizados para la **alta concurrencia** y el **bajo consumo de recursos** al utilizar el paradigma de programación reactiva.

## ✨ Características Principales

* **Programación Reactiva:** Basado en **Spring WebFlux** (Reactor Core) para un modelo de concurrencia no bloqueante.
* **Endpoints Asíncronos:** Uso de tipos reactivos clave como `Mono` (0 o 1 elemento) y `Flux` (0 a N elementos) en los controladores.
* **Mensajería Reactiva:** Integración con **Kafka** utilizando librerías reactivas para una comunicación *end-to-end* no bloqueante.
* **Serialización Segura:** Uso de **Apache Avro** y **Schema Registry** para una serialización de mensajes en Kafka robusta y con gestión de esquema.
* **Pruebas Profesionales:** Incluye tests unitarios con **JUnit 5** y tests de integración con **WebTestClient**.

## ⚙️ Requisitos

Asegúrate de tener instalados los siguientes componentes antes de comenzar:

* **Java 17+** (Recomendado: OpenJDK 17)
* **Maven 3.8+**
* **IDE con soporte Java:** IntelliJ IDEA, Eclipse o VS Code.
* **Docker y Docker Compose:** Necesarios para ejecutar la infraestructura de Kafka y Schema Registry.

## 📦 Instalación y Ejecución

Sigue estos pasos para poner en marcha la aplicación en tu entorno local.

### 1. Clonar el Repositorio

```bash
git clone [https://github.com/juanfranciscofernandezherreros/api-async-webflux.git](https://github.com/juanfranciscofernandezherreros/api-async-webflux.git)
cd api-async-webflux
```

### 2. Iniciar la Infraestructura de Mensajería (Kafka y Schema Registry)Usando Docker Compose, levanta Zookeeper, Kafka y el Schema Registry:Bashdocker-compose up -d
Nota: Espera unos segundos a que todos los servicios estén completamente operativos antes de arrancar la aplicación Spring.3. Compilar el ProyectoCompila todas las dependencias y clases (esto también generará las clases Java a partir de los esquemas Avro):Bashmvn clean install

### 3. Ejecutar la AplicaciónEjecuta la aplicación usando el plugin de Spring Boot:Bashmvn spring-boot:run
   
ℹ️ Por defecto, la API se ejecutará en http://localhost:8080.🏗️ Estructura del ProyectoBashapi-async-webflux
├── src/main/java
│   └── com.example.webflux
│       ├── controller   # Endpoints REST y Producción/Consumo de Kafka
│       ├── service      # Lógica de negocio reactiva
│       ├── model        # Clases de datos
│       └── config       # Configuraciones de WebFlux/Kafka
├── src/main/resources
│   ├── avro        # Esquemas Avro (.avsc)
│   └── application.yml # Configuración de Spring
├── src/test/java        # Clases de Pruebas
├── pom.xml              # Dependencias (incluye `avro-maven-plugin`)
└── docker-compose.yml  # Infraestructura local
