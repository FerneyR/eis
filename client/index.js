const Minio = require("minio");
const express = require("express");
const fileupload = require("express-fileupload");
const bodyParser = require("body-parser");
const morgan = require("morgan");
const cors = require("cors");
const dotenv = require("dotenv");

dotenv.config();
const app = express();
const port = 5000;

app.use(morgan("combined"));

app.use(express.raw({ type: "application/octet-stream", limit: "10mb" }));
app.use(cors());
app.use(fileupload());
app.use(express.static("files"));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

console.log("Test enviroments : " + process.env.TEST);

var minioClient = new Minio.Client({
  endPoint: process.env.MINIO_ENDPOINT,
  port: +process.env.MINIO_PORT,
  useSSL: false,
  accessKey: process.env.MINIO_ACCESS_KEY,
  secretKey: process.env.MINIO_SECRET_KEY,
});

app.post("/employees/upload", (req, res) => {
  file = req.files.file;
  if (!file) {
    return res
      .status(400)
      .json({ error: "No se proporcionó ningún archivo o blob." });
  }

  minioClient.putObject(
    "profiles",
    file.name,
    file.data,
    file.size,
    file.mimetype,
    function (err, etag) {
      if (err) {
        console.error("Error al cargar la imagen en Minio:", err);
        return res.sendStatus(400);
      } else {
        return res.sendStatus(201);
      }
    }
  );
});

app.post("/attendance/upload", (req, res) => {
  file = req.files.file;
  if (!file) {
    return res
      .status(400)
      .json({ error: "No se proporcionó ningún archivo o blob." });
  }

  minioClient.putObject(
    "attendance",
    file.name,
    file.data,
    file.size,
    file.mimetype,
    function (err, etag) {
      if (err) {
        console.error("Error al cargar la imagen en Minio:", err);
        return res.sendStatus(400);
      } else {
        console.log("Imagen cargada exitosamente en Minio.");
        return res.sendStatus(201);
      }
    }
  );
});

app.listen(port, () => {
  console.log(`Service-Worker minio run`);
});
