from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

items = []

class Item(BaseModel):
    name: str
    price: float

@app.get("/items")
def get_items():
    return {"items": items}

@app.post("/items")
def add_item(item: Item):
    items.append(item.dict())
    return {"message": "Item agregado", "item": item}
