const express = require("express");
const https = require("https");
const fs = require("fs");
const jwt = require("jsonwebtoken");
const auth = require("./middleware/auth");

const SECRET = "mi_secreto_super_seguro";

const app = express();

app.use(express.json());

app.get("/", (req, res) => {
  res.json({
    mensaje: "Servidor HTTPS funcionando"
  });
});

const options = {
  key: fs.readFileSync("./cert/key.pem"),
  cert: fs.readFileSync("./cert/cert.pem")
};

const usuarios = [
  {
    id: 1,
    username: "admin",
    password: "1234",
    rol: "admin"
  },
  {
    id: 2,
    username: "user",
    password: "1234",
    rol: "user"
  }
];

app.post("/login", (req, res) => {

  const { username, password } = req.body;

  const usuario = usuarios.find(
    u =>
      u.username === username &&
      u.password === password
  );

  if (!usuario) {
    return res.status(401).json({
      mensaje: "Credenciales inválidas"
    });
  }

  const token = jwt.sign(
    {
      sub: usuario.id,
      rol: usuario.rol
    },
    SECRET,
    {
      expiresIn: "1h"
    }
  );

  res.json({ token });
});

app.get("/perfil", auth, (req, res) => {
  res.json({
    mensaje: "Acceso autorizado",
    usuario: req.user
  });
});

function soloAdmin(req, res, next) {

  if (req.user.rol !== "admin") {
    return res.status(403).json({
      mensaje: "Acceso denegado"
    });
  }

  next();
}

app.get("/admin", auth, soloAdmin, (req, res) => {
  res.json({
    mensaje: "Bienvenido administrador"
  });
});

https.createServer(options, app).listen(3000, () => {
  console.log("Servidor HTTPS en puerto 3000");
});