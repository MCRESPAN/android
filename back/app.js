const express = require("express");
const { Pool } = require("pg");

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

// Endpoint para listar productos
app.get("/productos", async (req, res) => {
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


// No poner en la chuleta el console.log que te dice que estas escuchando en el puerto 3311
app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
});