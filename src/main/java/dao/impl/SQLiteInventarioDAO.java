package dao.impl;

import dao.InventarioDAO;
import dao.ProductoDAO;
import database.DatabaseManager;
import exceptions.ProductoNoEncontradoException;
import model.inventario.Inventario;
import model.inventario.StockProducto;
import model.producto.Producto;

import java.sql.*;

public class SQLiteInventarioDAO implements InventarioDAO {

    private ProductoDAO productoDAO;

    public SQLiteInventarioDAO(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    public void guardar(Inventario inventario) {

        String sql = """
            INSERT INTO stock_productos(
                inventario_id,
                codigo_producto,
                cantidad
            )
            VALUES (1, ?, ?)
            """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement ps = conn.prepareStatement(sql)
        ){
            for(StockProducto stock : inventario.getStocks()){

                ps.setInt(1, stock.getProducto().getCodigo());
                ps.setInt(2, stock.getCantidad());

                ps.executeUpdate();
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al guardar stock.", e);
        }
    }

    @Override
    public Inventario obtenerInventario() {

        Inventario inventario = new Inventario(1);

        String sql = """
                SELECT *
                FROM stock_productos
                """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (

                PreparedStatement ps = conn.prepareStatement(sql);

                ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){

                int codigoProducto =
                        rs.getInt("codigo_producto");

                Producto producto =
                        productoDAO.buscarPorId(
                                codigoProducto
                        );

                if(producto == null){
                    throw new ProductoNoEncontradoException("El producto no existe.");
                }

                StockProducto stock =
                        new StockProducto(
                                producto,
                                rs.getInt("cantidad")
                        );

                inventario.agregarProducto(stock);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener inventario.", e);
        }

        return inventario;
    }

    @Override
    public void actualizar(Inventario inventario) {

        String sql = """
                UPDATE stock_productos
                SET cantidad = ?
                WHERE codigo_producto = ?
                """;


        try(
                Connection conn =
                        DatabaseManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ){

            for(StockProducto stock :
                    inventario.getStocks()){

                ps.setInt(
                        1,
                        stock.getCantidad()
                );

                ps.setInt(
                        2,
                        stock.getProducto().getCodigo()
                );

                ps.executeUpdate();
            }


        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar inventario.", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = """
                DELETE FROM inventario
                WHERE id = ?
                """;

        try(
                Connection conn =
                        DatabaseManager.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ps.executeUpdate();


        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar inventario.", e);
        }
    }
}