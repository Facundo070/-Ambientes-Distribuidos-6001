import requests

# GET
resp = requests.get("http://servidor:8000/items")
print("Items actuales:", resp.json())

# POST
nuevo = {"name": "Laptop", "price": 1200.0}
resp = requests.post("http://servidor:8000/items", json=nuevo)
print("Respuesta POST:", resp.json())
