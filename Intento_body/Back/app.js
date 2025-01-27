const express = require("express");
const {Pool} = require("pg");

const app = express();
const port = 3311;

const pool = new Pool({
    user : "postgres",
    host : "localhost",
    database : "intento1",
    password : "postgres",
    port : 5432,
});

app.use(express.json());

app.get("/productos", async(req, res)=> {
    try{
        const result = await pool.query(
            "SELECT * FROM productos ORDER BY id ASC"
        );
        res.json(result.rows);
    } catch (err) {
        console.error("Error: ", err.message);
        res.status(500).send(`Error: ${err.message}`);
    }
});

app.get("/categorias", async(req, res)=> {
    try{
        const result = await pool.query(
            "SELECT * FROM categorias ORDER BY id ASC"
        );
        res.json(result.rows);
    } catch (err) {
        console.error("Error: ", err.message);
        res.status(500).send(`Error: ${err.message}`);
    }
});

app.post("/productos/categoria", async(req, res)=> { // Quitar el /:id y hacerlo POST
    try{
        const { categoria_id } = req.body; // Extrae el id de la categoría del cuerpo
        const result = await pool.query(
            "SELECT * FROM productos WHERE categoria_id = $1 ORDER BY id ASC",
            [categoria_id]
        );
        res.json(result.rows);
    } catch (err) {
        console.error("Error: ", err.message);
        res.status(500).send(`Error: ${err.message}`);
    }
});

app.listen(port);