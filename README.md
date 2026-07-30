# 🛒 Proyecto Final - E-Commerce

Sistema de gestión de E-Commerce desarrollado en **Java** como proyecto final de Programación Avanzada. La aplicación funciona por consola y utiliza una base de datos **SQLite** para almacenar la información.

## Tecnologías utilizadas

- Java 24
- SQLite
- Maven
- IntelliJ IDEA

## Estructura del proyecto

```
src/
 ├── main/
 │   └── java/
 │       ├── controller/
 │       ├── dao/
 │       ├── database/
 │       ├── exceptions/
 │       ├── factory/
 │       ├── menu/
 │       ├── model/
 │       ├── strategy/
 │       └── Main.java
```

El proyecto sigue una arquitectura en capas:

- **Model:** Entidades del sistema.
- **DAO:** Acceso a datos mediante SQLite.
- **Controller:** Lógica de control entre la interfaz y los datos.
- **Menu:** Interfaz de usuario por consola.
- **Database:** Inicialización y administración de la base de datos.

---

## Funcionalidades

El sistema incluye la administración de:

- Usuarios
- Productos
- Categorías
- Carrito de compras
- Órdenes
- Pagos
- Envíos
- Inventario
- Calificaciones
- Reclamos
- Devoluciones
- Reportes

---

## Cómo abrir el proyecto en IntelliJ IDEA

1. Abrir IntelliJ IDEA.
2. Seleccionar **Open**.
3. Elegir la carpeta del proyecto.
4. IntelliJ detectará automáticamente el archivo `pom.xml`.
5. Esperar a que Maven descargue las dependencias.
6. Ejecutar la clase:

```
Main.java
```

---

## Dependencias

El proyecto utiliza:

- SQLite JDBC

Administradas mediante Maven.

---

## Ejecución

Desde IntelliJ:

- Abrir `Main.java`
- Presionar **Run**

Al iniciar, el sistema:

1. Inicializa la base de datos.
2. Carga los datos necesarios.
3. Muestra el menú de inicio de sesión.
4. Permite acceder al menú principal según el usuario autenticado.

---

## Base de datos

La aplicación utiliza **SQLite** como motor de base de datos.

La inicialización se realiza automáticamente mediante:

```java
DatabaseManager.inicializarBase();
```

---

## Patrón de diseño

El proyecto implementa una arquitectura basada en el patrón **DAO (Data Access Object)** para separar la lógica de negocio del acceso a datos.

---

## Autor

Camila Saavedra, Maria Paula Panetta
ACN3BV
Proyecto desarrollado como trabajo final de **Programación Avanzada**.
