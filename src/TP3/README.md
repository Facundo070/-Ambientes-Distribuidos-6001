# Trabajo Práctico N°3 – Ambientes Distribuidos

**Asignatura:** Desarrollo de Aplicaciones para Ambientes Distribuidos  
**Tema:** Sockets TCP/UDP con API java.net y Servidores Multihilo  
**Docente:** Lic. Gabriel Artaza

---

## 1. Objetivos de Aprendizaje
- Comprender y aplicar el paso de mensajes a nivel de la capa de transporte/red utilizando la API `java.net`.
- Implementar servidores concurrentes multihilo capaces de atender múltiples clientes TCP en paralelo.
- Desarrollar sockets UDP utilizando `DatagramSocket` y `DatagramPacket` con control de tiempo de espera (`timeout`).
- Documentar proyectos de desarrollo distribuido en GitHub utilizando estándares profesionales.

---

## 2. Parte Práctica (Código e Implementación)

### Ejercicio 1: Servidor de Chat Multihilo (Sockets TCP)
- **Servidor:** escucha en el puerto 5000, acepta conexiones con `ServerSocket.accept()` y delega cada cliente a un hilo independiente.
- **Difusión de mensajes:** cada mensaje recibido se retransmite a todos los clientes conectados.
- **Manejo de desconexiones:** si un cliente se desconecta, el servidor continúa funcionando para los demás.

### Ejercicio 2: Receptor de Alertas y Telemetría (Sockets UDP)
- **Emisor UDP:** envía mensajes de alerta en intervalos regulares.
- **Receptor UDP:** espera datagramas con `setSoTimeout(5000)`. Si no recibe nada en 5 segundos, captura `SocketTimeoutException` y muestra advertencia.

---

## 3. Cuestionario Teórico

### 1. TCP vs UDP
- **TCP:** orientado a conexión, garantiza entrega y orden de los mensajes, incluye control de flujo. Ideal para chats y aplicaciones críticas.
- **UDP:** sin conexión, más rápido y liviano, pero no garantiza entrega ni orden. Útil para alertas, telemetría o streaming.

### 2. ServerSocket.accept()
- Bloquea al servidor hasta que un cliente se conecta.
- Devuelve un objeto `Socket` que representa esa conexión.
- Es indispensable usar un **hilo por cliente**, porque si no el servidor quedaría bloqueado atendiendo a uno solo y no podría aceptar más conexiones en paralelo.

### 3. Paquete UDP perdido
- UDP no garantiza entrega, por lo que un paquete puede perderse sin aviso.
- Con `setSoTimeout()`, el receptor espera un máximo de 5 segundos.
- Si no llega nada en ese tiempo, se lanza `SocketTimeoutException`, lo que permite registrar la ausencia y seguir escuchando.

---
