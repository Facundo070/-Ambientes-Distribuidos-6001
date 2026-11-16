from fastapi import FastAPI, HTTPException, status
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import Optional

app = FastAPI(
    title="API de Gestión de Items - TP 7",
    description="Servidor distribuido para el TP 7 - Consumo de APIs y Manejo de Excepciones",
    version="2.0.0"
)

# Configuración de CORS para permitir peticiones desde clientes web independientes (Browser / UI)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Modelo de datos con validaciones
class Item(BaseModel):
    name: str
    price: float

# Base de datos en memoria con datos iniciales
items = [
    {"id": 1, "name": "Notebook Dell Inspiron", "price": 1200.0},
    {"id": 2, "name": "Mouse Inalámbrico Logitech", "price": 25.5},
    {"id": 3, "name": "Teclado Mecánico RGB", "price": 85.0}
]
current_id = 4

@app.get("/")
def read_root():
    return {
        "status": "online",
        "mensaje": "Servidor de TP7 en línea y escuchando peticiones",
        "endpoints": ["/items (GET, POST)", "/items/{id} (GET)", "/simular-error-500 (GET)"]
    }

@app.get("/items")
def get_items():
    """Retorna la lista completa de ítems disponibles"""
    return {"items": items}

@app.get("/items/{item_id}")
def get_item(item_id: int):
    """Retorna un ítem por su ID o lanza una excepción HTTP 404 si no existe"""
    for item in items:
        if item["id"] == item_id:
            return {"item": item}
    
    # Lanzar excepción distribuida HTTP 404
    raise HTTPException(
        status_code=status.HTTP_404_NOT_FOUND,
        detail=f"Excepción 404: El ítem con ID {item_id} no fue encontrado en el servidor."
    )

@app.post("/items", status_code=status.HTTP_201_CREATED)
def add_item(item: Item):
    """
    Agrega un nuevo ítem validando reglas de negocio:
    - El nombre no puede estar vacío (400)
    - El precio debe ser estrictamente mayor a 0 (400)
    - No puede haber nombres duplicados (409)
    """
    global current_id

    # Validación 1: Nombre vacío
    if not item.name or not item.name.strip():
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Excepción 400: El nombre del producto no puede estar vacío."
        )

    # Validación 2: Precio menor o igual a cero
    if item.price <= 0:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f"Excepción 400: El precio ingresado (${item.price}) es inválido. Debe ser estrictamente mayor a 0."
        )

    # Validación 3: Ítem duplicado (Conflicto 409)
    for existing in items:
        if existing["name"].lower() == item.name.strip().lower():
            raise HTTPException(
                status_code=status.HTTP_409_CONFLICT,
                detail=f"Excepción 409: Ya existe un producto registrado con el nombre '{item.name}'."
            )

    nuevo_item = {
        "id": current_id,
        "name": item.name.strip(),
        "price": float(item.price)
    }
    current_id += 1
    items.append(nuevo_item)
    return {"message": "Item agregado exitosamente", "item": nuevo_item}

@app.get("/simular-error-500")
def simular_error():
    """Endpoint para provocar intencionalmente una excepción interna de servidor (500)"""
    raise HTTPException(
        status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
        detail="Excepción 500: Falla interna crítica simulada en el servicio backend."
    )
