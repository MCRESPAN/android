const express = require("express");
const { Pool } = require("pg");

// Para encriptar
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");


const app = express();
const port = 3311;

// Conexión a PostgreSQL
const pool = new Pool({
    user: "postgres",
    host: "localhost",
    database: "ExamenAndroid",
    password: "postgres",
    port: 5432,
});

app.use(express.json());

// Clave secreta para JWT (normalmente se almacena en variables de entorno)
const SECRET_KEY = "mysecretkey";

// Endpoint para listar productos
app.get("/productos", authenticateToken, async (req, res) => {      //////////// El authenticateToken es sólo para el Token, para el 5 NO PONERLO!
    try {
        const result = await pool.query(
            "SELECT * FROM productos ORDER BY id ASC"
        );
        res.json(result.rows);
    } catch (err) {
        console.error("Error detallado:", err.message);
        res.status(500).send(`Error al obtener productos: ${err.message}`);
    }
});

// Endpoint para listar categorias
app.get("/categorias", async (req, res) => {
    try {
        const result = await pool.query(
            "SELECT * FROM categorias ORDER BY id ASC"
        );
        res.json(result.rows);
    } catch (err) {
        console.error("Error detallado:", err.message);
        res.status(500).send(`Error al obtener categorias: ${err.message}`);
    }
});

// Endpoint para listar productos segun categoria
app.get("/productos/categoria/:id", async (req, res) => {
    try {
        const categoriaId = req.params.id;
        const result = await pool.query(
            "SELECT * FROM productos WHERE categoria_id = $1",
            [categoriaId]
        );
        res.json(result.rows);
    } catch (err) {
        console.error("Error detallado:", err.message);
        res.status(500).send(`Error al obtener productos por categoria: ${err.message}`);
    }
});

app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
});

////////////////////////////////////////////////  EXTRA SEGURIDAD ////////////////////////////////////////////////////////////////

// **1. Endpoint para registrar usuarios**
app.post("/register", async (req, res) => {
    const { username, password } = req.body;
    try {
        // Encriptar la contraseña antes de guardarla
        const hashedPassword = await bcrypt.hash(password, 10);
        // Guardar en la base de datos
        await pool.query(
            "INSERT INTO public.users (username, password) VALUES ($1, $2)",
            [username, hashedPassword]
        );
        res.status(201).send("Usuario registrado correctamente");
    } catch (err) {
        res.status(500).send(`Error al registrar usuario: ${err.message}`);
    }
});

// **2. Endpoint para iniciar sesión**
app.post("/login", async (req, res) => {
    const { username, password } = req.body;
    try {
        // Buscar el usuario en la base de datos
        const result = await pool.query(
        "SELECT * FROM public.users WHERE username = $1",
        [username]
    );
    if (result.rows.length === 0) {
        return res.status(404).send("Usuario no encontrado");
    }
    const user = result.rows[0];

    // Comparar la contraseña proporcionada con la almacenada
    const validPassword = await bcrypt.compare(password, user.password);
    if (!validPassword) {
        return res.status(401).send("Contraseña incorrecta");
    }

    // Generar un token JWT
    const token = jwt.sign({ userId: user.id }, SECRET_KEY, { expiresIn: "1h" });
    res.json({ token });
    } catch (err) {
        res.status(500).send(`Error al iniciar sesión: ${err.message}`);
    }
});


// Middleware para verificar el token JWT
function authenticateToken(req, res, next) {
    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];
    if (!token) return res.status(401).send("Token no proporcionado");
        jwt.verify(token, SECRET_KEY, (err, user) => {
    if (err) return res.status(403).send("Token no válido");
    req.user = user; // Agregar datos del usuario al request
    next();
    });
}

 // **4. Endpoint protegido para obtener datos del usuario actual**
 app.get("/me", authenticateToken, async (req, res) => {
    try {
        const result = await pool.query(
        "SELECT id, username FROM public.users WHERE id = $1",
        [req.user.userId]
    );
    res.json(result.rows[0]);
    } catch (err) {
        res.status(500).send(`Error al obtener datos del usuario: ${err.message}`);
    }
});

module.exports = app;