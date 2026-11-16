import requests
import json
import sys

# URL base del servidor de API
# Por defecto dentro de la red docker bridge es http://servidor:8000
# Si se ejecuta fuera de Docker, se puede usar http://localhost:8000
DEFAULT_URL = "http://servidor:8000"

def get_base_url():
    if len(sys.argv) > 1:
        return sys.argv[1].rstrip("/")
    return DEFAULT_URL

def print_separator(title):
    print("\n" + "=" * 65)
    print(f" >>> {title}")
    print("=" * 65)

def test_get_items(base_url):
    print_separator("1. OBTENER LISTA DE PRODUCTOS (GET /items)")
    try:
        url = f"{base_url}/items"
        print(f"[CLIENTE] Enviando petición a: {url}")
        resp = requests.get(url, timeout=3)
        resp.raise_for_status()
        data = resp.json()
        print(f"[STATUS] HTTP {resp.status_code} OK")
        print(f"[RESPUESTA] Cantidad de ítems recibidos: {len(data.get('items', []))}")
        for item in data.get("items", []):
            print(f"   - ID #{item.get('id')}: {item.get('name')} | ${item.get('price')}")
    except requests.exceptions.RequestException as exc:
        print(f"[EXCEPCIÓN CAPTURADA EN CLIENTE]: Error al consultar ítems: {exc}")

def test_post_item_valido(base_url):
    print_separator("2. REGISTRAR PRODUCTO VÁLIDO (POST /items)")
    nuevo = {"name": "Laptop Lenovo ThinkPad", "price": 1250.0}
    try:
        url = f"{base_url}/items"
        print(f"[CLIENTE] Enviando POST con payload: {nuevo}")
        resp = requests.post(url, json=nuevo, timeout=3)
        resp.raise_for_status()
        print(f"[STATUS] HTTP {resp.status_code} Created")
        print(f"[RESPUESTA]: {resp.json()}")
    except requests.exceptions.RequestException as exc:
        print(f"[EXCEPCIÓN CAPTURADA EN CLIENTE]: {exc}")

def test_excepcion_400_precio_negativo(base_url):
    print_separator("3. PRUEBA EXCEPCIÓN 400: VALIDACIÓN DE PRECIO INVÁLIDO")
    item_invalido = {"name": "Mouse Gamer", "price": -20.0}
    try:
        url = f"{base_url}/items"
        print(f"[CLIENTE] Enviando POST con precio negativo: {item_invalido}")
        resp = requests.post(url, json=item_invalido, timeout=3)
        
        # raise_for_status() lanza HTTPError si el código es 4xx o 5xx
        resp.raise_for_status()
        print("[AVISO]: La petición no falló cuando debía fallar.")
    except requests.exceptions.HTTPError as exc:
        print(f"[✓ EXCEPCIÓN 400 CAPTURADA POR EL CLIENTE]")
        print(f"   Código HTTP: {resp.status_code} Bad Request")
        print(f"   Mensaje del Servidor: {resp.json().get('detail')}")

def test_excepcion_404_recurso_inexistente(base_url):
    print_separator("4. PRUEBA EXCEPCIÓN 404: RECURSO NO ENCONTRADO")
    id_falso = 9999
    try:
        url = f"{base_url}/items/{id_falso}"
        print(f"[CLIENTE] Consultando GET para ID #{id_falso}")
        resp = requests.get(url, timeout=3)
        resp.raise_for_status()
    except requests.exceptions.HTTPError as exc:
        print(f"[✓ EXCEPCIÓN 404 CAPTURADA POR EL CLIENTE]")
        print(f"   Código HTTP: {resp.status_code} Not Found")
        print(f"   Mensaje del Servidor: {resp.json().get('detail')}")

def test_excepcion_500_error_servidor(base_url):
    print_separator("5. PRUEBA EXCEPCIÓN 500: ERROR INTERNO DEL SERVIDOR")
    try:
        url = f"{base_url}/simular-error-500"
        print(f"[CLIENTE] Consultando endpoint crítico: {url}")
        resp = requests.get(url, timeout=3)
        resp.raise_for_status()
    except requests.exceptions.HTTPError as exc:
        print(f"[✓ EXCEPCIÓN 500 CAPTURADA POR EL CLIENTE]")
        print(f"   Código HTTP: {resp.status_code} Internal Server Error")
        print(f"   Mensaje del Servidor: {resp.json().get('detail')}")

def test_excepcion_conexion():
    print_separator("6. PRUEBA EXCEPCIÓN DE RED: SERVIDOR INACCESIBLE")
    url_falsa = "http://127.0.0.1:59999/items"
    try:
        print(f"[CLIENTE] Intentando conectar a un puerto apagado: {url_falsa}")
        requests.get(url_falsa, timeout=2)
    except requests.exceptions.ConnectionError as exc:
        print(f"[✓ EXCEPCIÓN DE CONEXIÓN CAPTURADA POR EL CLIENTE]")
        print("   Tipo de Excepción: requests.exceptions.ConnectionError")
        print("   Diagnóstico: El servidor remoto no está disponible o el puerto es inaccesible.")

if __name__ == "__main__":
    base_url = get_base_url()
    print("=================================================================")
    print(" TP 7: CLIENTE CONSUMIDOR DISTRIBUIDO (CONSOLA)")
    print(f" Conectándose a: {base_url}")
    print("=================================================================")

    # Si estamos en localhost y no en docker, o viceversa, intentamos probar:
    try:
        test_get_items(base_url)
        test_post_item_valido(base_url)
        test_excepcion_400_precio_negativo(base_url)
        test_excepcion_404_recurso_inexistente(base_url)
        test_excepcion_500_error_servidor(base_url)
        test_excepcion_conexion()
    except requests.exceptions.ConnectionError:
        print(f"\n[ALERTA]: No se pudo conectar a {base_url}.")
        print("Asegúrate de que el servidor esté levantado con 'docker-compose up' o 'uvicorn main:app'.")
        print("Ejecutando prueba de desconexión...")
        test_excepcion_conexion()

    print("\n[FIN DE LA EJECUCIÓN DEL CLIENTE]")
