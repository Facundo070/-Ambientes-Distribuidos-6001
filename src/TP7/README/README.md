# Trabajo Práctico N° 7  
**Integración entre Capas y Consumo de APIs / Servicios Distribuidos**  

**Cátedra:** Desarrollo de Aplicaciones para Ambientes Distribuidos  
**Docente:** Lic. Gabriel Artaza  
**Unidad Temática:** Unidad 3 - Middleware y Tecnologías de Abstracción Distribuida  
**Modalidad de Entrega:** Estrictamente Individual  
**Formato de Evaluación:** Evaluación de Repositorio + Demostración/Ejecución en Vivo al Cierre del Cursado  

---

## 1. Carátula e Identificación
- **Nombre y Apellido:** Jorge Facundo Díaz  
- **Materia:** Desarrollo de Aplicaciones para Ambientes Distribuidos  
- **Tecnologías utilizadas:**
  - **Servidor:** Python 3.11, FastAPI, Uvicorn, Pydantic, CORS Middleware.
  - **Cliente Web (UI Independiente):** HTML5, Bootstrap 5, JavaScript (Fetch API async/await con manejo robusto de excepciones `try...catch`).
  - **Cliente Consola:** Python 3.11, Librería `requests` con captura de `HTTPError` y `ConnectionError`.
  - **Contenedores:** Docker & Docker Compose.

---

## 2. Diagrama de Arquitectura

El siguiente diagrama ilustra la interacción distribuida entre las dos variantes de clientes independientes, el canal de comunicación HTTP con soporte CORS y el servidor backend:

```mermaid
graph TD
    subgraph Capa Clientes Independientes
        W[Cliente Web - index.html <br/> Puerto 3000 o Browser local]
        C[Cliente Consola - cliente.py <br/> Contenedor o Script CLI]
    end

    subgraph Canal de Comunicación
        W -->|Peticiones HTTP Fetch / JSON <br/> Control de CORS y try-catch| API[API Gateway / Router FastAPI]
        C -->|HTTP 1.1 Requests / JSON <br/> Captura HTTPError y ConnectionError| API
    end

    subgraph Capa Servidor (Backend :8000)
        API --> M[main.py: /items, /items/:id, /simular-error-500]
        M --> EX[Manejador de Excepciones HTTP: 400, 404, 409, 500]
    end
```

---

## 3. Consigna 2: Implementación de la Aplicación Cliente y Captura de Excepciones

Para dar cumplimiento estricto a la **Consigna 2**, se implementó un **Cliente Web interactivo e independiente**, complementado con un cliente de consola, los cuales demuestran en vivo la captura de excepciones y fallas distribuidas:

1. **Captura de Excepción 400 (Bad Request):**
   - Se valida el ingreso de precios negativos o nombres vacíos. El cliente captura el error y despliega el mensaje de validación devuelto por la API.
2. **Captura de Excepción 409 (Conflicto / Recurso Duplicado):**
   - Cuando se intenta registrar un producto existente, la API rechaza la solicitud y el cliente notifica el conflicto sin colapsar.
3. **Captura de Excepción 404 (Not Found):**
   - Al consultar un ID inexistente (`/items/9999`), el cliente captura el estado 404 y presenta el aviso correspondiente.
4. **Captura de Excepción 500 (Internal Server Error):**
   - Mediante el endpoint de diagnóstico `/simular-error-500`, se verifica la tolerancia a fallos ante una excepción interna no prevista en el backend.
5. **Captura de Excepción de Red (Servidor Caído / Desconectado):**
   - Si el servidor backend no responde o el puerto está inaccesible, el cliente atrapa la excepción de red (`Failed to fetch` o `ConnectionError`) e indica al usuario el estado fuera de línea de la API.

---

## 4. Instrucciones de Ejecución

### Opción A: Ejecución Completa con Docker Compose (Recomendada)

1. **Ubicarse en el directorio del TP7:**
   ```bash
   cd src/TP7
   ```

2. **Construir y levantar todos los servicios:**
   ```bash
   docker-compose up --build
   ```

3. **Acceder a las interfaces:**
   - **Cliente Web UI:** Abrir en el navegador [http://localhost:3000](http://localhost:3000)
   - **Servidor API:** [http://localhost:8000](http://localhost:8000)
   - **Documentación Swagger interactiva:** [http://localhost:8000/docs](http://localhost:8000/docs)
   - **Cliente Consola:** Sus resultados y capturas de excepciones se observan en los logs de Docker (`tp7_cliente_consola`).

---

### Opción B: Ejecución Local Directa (Sin Docker)

1. **Levantar el Servidor FastAPI:**
   ```bash
   cd src/TP7/Servidor
   uvicorn main:app --reload --port 8000
   ```

2. **Abrir el Cliente Web:**
   - Hacer doble clic directamente sobre el archivo `src/TP7/Cliente/index.html` para abrirlo en cualquier navegador web moderno (Chrome, Edge, Firefox).

3. **Ejecutar el Cliente de Consola:**
   ```bash
   cd src/TP7/Cliente
   python cliente.py http://localhost:8000
   ```

---

## 5. Estructura del Proyecto

```text
TP7/
 ├── Servidor/
 │    ├── main.py              # API FastAPI con CORS y excepciones HTTP (400, 404, 409, 500)
 │    └── Dockerfile           # Imagen Docker para el servidor backend
 ├── Cliente/
 │    ├── index.html           # Interfaz Web UI interactiva con captura de excepciones
 │    ├── cliente.py           # Script cliente en consola con manejo de RequestException
 │    ├── Dockerfile           # Contenedor para el cliente consola
 │    └── Dockerfile.web       # Contenedor HTTP para servir la interfaz web
 ├── docker-compose.yml        # Orquestación de servidor, cliente web y cliente consola
 └── README/
      ├── README.md            # Documentación del trabajo práctico
      ├── image.png            # Evidencias de pruebas de la API
      └── image-1.png          # Evidencias de ejecución del cliente
```
