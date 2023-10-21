# Usar una imagen base de Node.js
FROM node:18-alpine

# Establecer el directorio de trabajo en la imagen
WORKDIR /app

# Copiar el archivo package.json y package-lock.json (si existe)
COPY package*.json ./

# Instalar las dependencias del proyecto
RUN npm install

# Copiar el resto de los archivos de la aplicación
COPY . .

# Exponer un puerto en el contenedor (el puerto en el que funciona tu aplicación Node.js)
EXPOSE 5000

# Comando para ejecutar la aplicación
CMD [ "npm", "start" ]
