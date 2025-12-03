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
2. Iniciar la Infraestructura de Mensajería (Kafka y Schema Registry)Usando Docker Compose, levanta Zookeeper, Kafka y el Schema Registry:Bashdocker-compose up -d
Nota: Espera unos segundos a que todos los servicios estén completamente operativos antes de arrancar la aplicación Spring.3. Compilar el ProyectoCompila todas las dependencias y clases (esto también generará las clases Java a partir de los esquemas Avro):Bashmvn clean install
4. Ejecutar la AplicaciónEjecuta la aplicación usando el plugin de Spring Boot:Bashmvn spring-boot:run
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
📝 Uso de los EndpointsUna vez que la aplicación esté en funcionamiento, puedes acceder a los siguientes endpoints:MétodoEndpointDescripciónTipo de RetornoGET/api/itemsObtiene un flujo de elementos (stream).Flux<Item>GET/api/items/{id}Obtiene un elemento específico por su ID.Mono<Item>POST/api/events/publishPublica un evento Avro en Kafka de forma no bloqueante.Mono<String>GET/api/events/subscribeObtiene un stream de eventos Kafka en tiempo real (Server-Sent Events - SSE).Flux<Event>Ejemplo de Llamada para Publicar un Evento (POST)Bashcurl -X POST http://localhost:8080/api/events/publish \
-H "Content-Type: application/json" \
-d '{"id": 101, "timestamp": "2025-01-01T10:00:00Z", "payload": "Nuevo artículo creado"}'
Ejemplo de Suscripción a Eventos (GET - SSE)Para ver el flujo de mensajes de Kafka en tiempo real, utiliza la ruta SSE:Bashcurl -i http://localhost:8080/api/events/subscribe
📈 Diagrama de Flujo de Datos ReactivoEl siguiente diagrama ilustra el flujo de datos reactivo, incluyendo la integración con Kafka:Fragmento de códigograph TD
    A[Cliente HTTP] -- 1. Petición GET/POST --> B(Controller WebFlux)
    B -- 2. Suscripción (subscribe()) --> C(Service/Publisher)
    C -- 3. Request(n) (Backpressure) --> D(Fuente de Datos / Kafka / DB)
    D -- 4. onNext(data) --> C
    C -- 5. onNext(data) --> B
    B -- 6. Respuesta HTTP / SSE Stream --> A
    
    subgraph Procesamiento de Mensajería
        E[Producer Controller] -- HTTP POST --> F(Kafka Producer Service)
        F -- Mono.fromFuture() --> G(Kafka - Topic Avro)
        G -- Reactive Kafka Consumer --> H(Consumer Service)
        H -- Flux<Event> --> I(SSE Controller / DB Reactive)
    end
Conceptos Clave del Diagrama:Control No Bloqueante: Todas las interacciones, incluyendo I/O (Red y Kafka), se manejan de forma asíncrona usando Project Reactor (Mono/Flux).Backpressure: El Request(n) (paso 3) es la clave de la programación reactiva, permitiendo al subscriptor controlar la tasa a la que recibe datos del publicador, evitando la saturación.🔒 Serialización de Mensajes (Avro)Este proyecto utiliza Apache Avro para la serialización de mensajes enviados a Kafka, en conjunto con un Schema Registry.Ventajas de Avro:Serialización binaria compacta y rápida (mejor que JSON o XML).Evolución de Esquemas: Permite que los productores y consumidores utilicen diferentes versiones del esquema sin fallar, crucial para microservicios.Configuración: El plugin avro-maven-plugin se encarga de generar automáticamente las clases Java a partir de los archivos .avsc ubicados en src/main/resources/avro.🔬 Pruebas AutomáticasLas pruebas son esenciales en un sistema reactivo para asegurar la lógica no bloqueante.1. Pruebas Unitarias (StepVerifier)Para probar la lógica de Service que devuelve Mono o Flux, se utiliza StepVerifier de Project Reactor:Java// Ejemplo de prueba reactiva en ServiceTest.java
@Test
void whenFindById_thenReturnItem() {
    Mono<Item> itemMono = itemService.findById(1);
    
    StepVerifier.create(itemMono)
        .expectNextMatches(item -> item.getId() == 1)
        .verifyComplete();
}
2. Pruebas de Integración (WebTestClient)Para probar los endpoints HTTP de forma no bloqueante, se utiliza WebTestClient:Java// Ejemplo de prueba de integración en ControllerTest.java
@Test
void whenGetItems_thenReturnsFlux() {
    webTestClient.get().uri("/api/items")
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Item.class)
        .hasSize(3); // Verifica el tamaño del Flux devuelto
}
