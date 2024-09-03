# Utiliza una imagen base de OpenJDK con JDK 17
FROM openjdk:17.0.2

# Establece un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo .jar generado por Maven al contenedor
COPY ./target/clientePersona-0.0.1-SNAPSHOT.jar /app/clientePersona.jar

# Expone el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/clientePersona.jar"]
