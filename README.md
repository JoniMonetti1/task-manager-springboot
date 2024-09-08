# Task Manager CLI

Un gestor de tareas con interfaz de línea de comandos (CLI) y API REST, implementado en Java con Spring Boot.

## Tabla de Contenidos
- [Instalación](#instalación)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [API Endpoints](#api-endpoints)
- [Contribución](#contribución)

## Instalación

1. Clona este repositorio:
    git clone https://github.com/JoniMonetti1/task-manager-springboot
2. Navega al directorio del proyecto:
   cd task-manager-cli
3. Compila el proyecto:
   ./mvnw clean install

## Uso
Para usar el CLI, ejecuta el siguiente comando:
java -jar target/task-manager-cli.jar <comando> [< argumentos >]

Comandos disponibles:
- `list`: Lista todas las tareas
- `add <descripción>`: Añade una nueva tarea
- `update <id> <nueva descripción>`: Actualiza una tarea existente
- `delete <id>`: Elimina una tarea
- `mark-in-progress <id>`: Marca una tarea como en progreso
- `mark-done <id>`: Marca una tarea como completada
- `list <status>`: Lista todas las tareas filtrando por el estado

Ejemplo:
java -jar target/task-manager-cli.jar add "Completar el README del proyecto"

## Estructura del Proyecto
- `src/main/java/com/joni/task_manager_springboot/`: Contiene el código fuente del proyecto
    - `cli/`: Contiene la implementación del CLI
    - `controllers/`: Contiene los controladores de la API REST
    - `models/`: Contiene las clases de modelo
    - `repositories/`: Contiene los repositorios para acceso a datos
    - `services/`: Contiene la lógica de negocio

## API Endpoints
- GET `/tasks`: Obtiene todas las tareas
- GET `/status/{status}`: Obtiene todas las tareas filtrando por su estado
- GET `/tasks/{id}`: Obtiene una tarea específica
- POST `/tasks`: Crea una nueva tarea
- PUT `/tasks/{id}`: Actualiza una tarea existente
- DELETE `/tasks/{id}`: Elimina una tarea
- PUT `/tasks/{id}/mark-in-progress`: Marca una tarea como en progreso
- PUT `/tasks/{id}/mark-done`: Marca una tarea como completada

## Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue para discutir los cambios propuestos antes de hacer un pull request.
