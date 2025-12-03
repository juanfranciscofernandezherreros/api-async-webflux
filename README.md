## 🚀 API Asíncrona de Alto Rendimiento con Spring WebFlux

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
