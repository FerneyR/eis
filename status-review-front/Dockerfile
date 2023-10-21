# Usar una imagen base de Node.js
FROM node:18-alpine
# Establecer el directorio de trabajo en la imagen
WORKDIR /app

# Copiar el resto de los archivos de la aplicación
COPY . .

# Instalar las dependencias del proyecto
RUN npm install

# Exponer un puerto en el contenedor (el puerto en el que funciona tu aplicación Node.js)
EXPOSE 3000

# Comando para ejecutar la aplicación
CMD [ "npm", "start" ]
