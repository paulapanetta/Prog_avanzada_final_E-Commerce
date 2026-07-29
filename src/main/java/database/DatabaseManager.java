package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:ecommerce.db";
    private static Connection connection;


    // unica conexion a la bd
    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }

        return connection;
    }


    public static void inicializarBase() {

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS categorias(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT NOT NULL UNIQUE,
                        descripcion TEXT,
                        estado TEXT NOT NULL
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS usuarios(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT NOT NULL,
                        apellido TEXT NOT NULL,
                        email TEXT NOT NULL UNIQUE,
                        password TEXT NOT NULL,
                        fecha_alta TEXT NOT NULL,
                        estado TEXT NOT NULL,
                        rol TEXT NOT NULL
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS productos(
                        codigo INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT NOT NULL,
                        descripcion TEXT,
                        precio REAL NOT NULL,
                        categoria_id INTEGER NOT NULL,
                        peso REAL NOT NULL,
                        estado TEXT NOT NULL,
                        tipo TEXT NOT NULL,

                        FOREIGN KEY (categoria_id)
                            REFERENCES categorias(id)
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS inventario(
                        id INTEGER PRIMARY KEY
                    );
                    """);

            stmt.execute("""
                    INSERT INTO inventario(id)
                    SELECT 1
                    WHERE NOT EXISTS(
                        SELECT 1 FROM inventario
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS stock_productos(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        inventario_id INTEGER NOT NULL,
                        codigo_producto INTEGER NOT NULL,
                        cantidad INTEGER NOT NULL CHECK(cantidad >= 0),

                        FOREIGN KEY(inventario_id)
                            REFERENCES inventario(id),

                        FOREIGN KEY(codigo_producto)
                            REFERENCES productos(codigo)
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS ordenes(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        numero TEXT NOT NULL UNIQUE,
                        cliente_id INTEGER NOT NULL,
                        fecha TEXT NOT NULL,
                        total REAL NOT NULL,
                        estado TEXT NOT NULL,

                        FOREIGN KEY (cliente_id)
                            REFERENCES usuarios(id)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS items_orden(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        orden_id INTEGER NOT NULL,
                        codigo_producto INTEGER NOT NULL,
                        cantidad INTEGER NOT NULL,
                        precio_unitario REAL NOT NULL,
            
                        FOREIGN KEY(orden_id)
                            REFERENCES ordenes(id),
                    
                        FOREIGN KEY(codigo_producto)
                            REFERENCES productos(codigo)
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS pagos(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        orden_id INTEGER NOT NULL,
                        monto REAL NOT NULL,
                        fecha TEXT NOT NULL,
                        estado TEXT NOT NULL,
                        metodo TEXT NOT NULL,

                        FOREIGN KEY (orden_id)
                            REFERENCES ordenes(id)
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS envios(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        codigo_seguimiento TEXT UNIQUE,
                        orden_id INTEGER NOT NULL,
                        direccion TEXT NOT NULL,
                        provincia TEXT NOT NULL,
                        ciudad TEXT NOT NULL,
                        codigo_postal TEXT NOT NULL,
                        tipo TEXT NOT NULL,
                        estado TEXT NOT NULL,
                        costo REAL NOT NULL,

                        FOREIGN KEY (orden_id)
                            REFERENCES ordenes(id)
                    );
                    """);


            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS reclamos(
                        id INTEGER PRIMARY KEY,
                        cliente_id INTEGER NOT NULL,
                        orden_id INTEGER NOT NULL,
                        motivo TEXT NOT NULL,
                        fecha TEXT NOT NULL,
                        estado TEXT NOT NULL,

                        FOREIGN KEY (cliente_id)
                            REFERENCES usuarios(id),

                        FOREIGN KEY (orden_id)
                            REFERENCES ordenes(id)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS carritos(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        cliente_id INTEGER NOT NULL UNIQUE,
            
                        FOREIGN KEY(cliente_id)
                            REFERENCES usuarios(id)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS items_carrito(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        carrito_id INTEGER NOT NULL,
                        codigo_producto INTEGER NOT NULL,
                        cantidad INTEGER NOT NULL,
                        precio_unitario REAL NOT NULL,

                        FOREIGN KEY(carrito_id)
                            REFERENCES carritos(id),

                        FOREIGN KEY(codigo_producto)
                            REFERENCES productos(codigo)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS devoluciones(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        cliente_id INTEGER NOT NULL,
                        producto_codigo INTEGER NOT NULL,
                        motivo TEXT NOT NULL,
                        fecha TEXT NOT NULL,
                        estado TEXT NOT NULL,
                    
                        FOREIGN KEY (cliente_id)
                            REFERENCES usuarios(id),
                    
                        FOREIGN KEY (producto_codigo)
                            REFERENCES productos(codigo)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS calificaciones(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        cliente_id INTEGER NOT NULL,
                        producto_codigo INTEGER NOT NULL,
                        puntuacion INTEGER NOT NULL,
                        comentario TEXT,
                        fecha TEXT NOT NULL,
                
                        FOREIGN KEY (cliente_id)
                           REFERENCES usuarios(id),
                
                        FOREIGN KEY (producto_codigo)
                           REFERENCES productos(codigo)
                    );
                    """);

            System.out.println("Base de datos inicializada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos.");
            e.printStackTrace();
        }
    }


    public static void cerrarConexion() {

        try {

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
