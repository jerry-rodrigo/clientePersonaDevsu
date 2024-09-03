# Microservicio Cliente-Persona

Este microservicio maneja la creación, actualización, eliminación y consulta de clientes y personas. 

Está construido con **Java**, **Spring Boot** y utiliza **MySQL** como base de datos.

## Requisitos

- Java 17
- Maven 3.6 o superior
- Docker (opcional, para levantar la base de datos)
- MySQL 8 o superior

## Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/jerry-rodrigo/clientePersonaDevsu.git
cd clientePersona

#mvn clean package -DskipTests
#docker build .
#docker images
#podman run -d --name db -e MYSQL_ROOT_PASSWORD=MPeru123@ -e MYSQL_DATABASE=cliente_persona_db -p 3306:3306 mysql:8
#docker run -p 8080:8080 205e870083b0

#PARA VER LOS CONTEINER:
#podman ps
#PARA DETERNLO
#podman stock 8034122361ed

#MYSQL
#url: jdbc:mysql://host.docker.internal:3306/cliente_persona_db
#podman run -d --name db -e MYSQL_ROOT_PASSWORD=MPeru123@ -e MYSQL_DATABASE=cliente_persona_db -p 3306:3306 mysql:8

#docker build -t cliente-persona:1.0 .
#docker-compose up
#docker images