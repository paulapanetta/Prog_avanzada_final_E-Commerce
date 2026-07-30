package dao.impl;

import dao.CarritoDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import database.DatabaseManager;
import exceptions.PermisoDenegadoException;
import exceptions.ProductoNoEncontradoException;
import model.carrito.Carrito;
import model.carrito.ItemCarrito;
import model.producto.Producto;
import model.usuario.Cliente;
import model.usuario.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteCarritoDAO implements CarritoDAO {


    private ProductoDAO productoDAO;
    private UsuarioDAO usuarioDAO;


    public SQLiteCarritoDAO(
            ProductoDAO productoDAO,
            UsuarioDAO usuarioDAO
    ) {
        this.productoDAO = productoDAO;
        this.usuarioDAO = usuarioDAO;
    }


    @Override
    public void guardar(Carrito carrito) {

        String sqlCarrito = """
                INSERT INTO carritos(cliente_id)
                VALUES (?)
                """;

        String sqlItem = """
                INSERT INTO items_carrito(
                    carrito_id,
                    codigo_producto,
                    cantidad,
                    precio_unitario
                )
                VALUES (?, ?, ?, ?)
                """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement psCarrito = conn.prepareStatement(sqlCarrito, Statement.RETURN_GENERATED_KEYS)
        ){

            psCarrito.setInt(
                    1,
                    carrito.getCliente().getId()
            );

            psCarrito.executeUpdate();


            ResultSet keys = psCarrito.getGeneratedKeys();

            int idCarrito;

            if (keys.next()) {
                idCarrito = keys.getInt(1);
            } else {
                throw new RuntimeException("No se pudo obtener el ID del carrito.");
            }

            carrito.setId(idCarrito);


            PreparedStatement psItem = conn.prepareStatement(sqlItem);

            for(ItemCarrito item : carrito.getItems()){

                psItem.setInt(
                        1,
                        idCarrito
                );

                psItem.setInt(
                        2,
                        item.getProducto().getCodigo()
                );

                psItem.setInt(
                        3,
                        item.getCantidad()
                );

                psItem.setDouble(
                        4,
                        item.getPrecioUnitario()
                );

                int filas = psItem.executeUpdate();

                if (filas == 0) {
                    throw new RuntimeException("No se pudo guardar un item del carrito.");
                }
            }


        } catch(SQLException e){

            throw new RuntimeException("Error al guardar carrito.", e);
        }
    }

    @Override
    public Carrito buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM carritos
                WHERE id = ?
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

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();


            if(rs.next()){

                int clienteId = rs.getInt("cliente_id");

                Usuario usuario = usuarioDAO.buscarPorId(clienteId);

                if(!(usuario instanceof Cliente)){
                    throw new PermisoDenegadoException("El usuario no es un cliente.");
                }

                Cliente cliente = (Cliente) usuario;
                List<ItemCarrito> items = obtenerItems(id);

                return new Carrito(
                        id,
                        cliente,
                        items
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar carrito.", e);
        }

        return null;
    }

    @Override
    public Carrito buscarPorClienteId(int clienteId) {

        String sql = """
                SELECT *
                FROM carritos
                WHERE cliente_id = ?
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

            ps.setInt(1,clienteId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                return buscarPorId(
                        rs.getInt("id")
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar carrito del cliente.", e);
        }

        return null;
    }


    private List<ItemCarrito> obtenerItems(int carritoId){

        List<ItemCarrito> items = new ArrayList<>();

        String sql = """
                SELECT *
                FROM items_carrito
                WHERE carrito_id = ?
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

            ps.setInt(1,carritoId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Producto producto = productoDAO.buscarPorId(
                        rs.getInt("codigo_producto")
                );

                if(producto == null){
                    throw new ProductoNoEncontradoException("El producto no existe.");
                }

                ItemCarrito item =
                        new ItemCarrito(
                                producto,
                                rs.getInt("cantidad"),
                                rs.getDouble("precio_unitario")
                        );

                items.add(item);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener items del carrito.", e);
        }

        return items;
    }

    @Override
    public void actualizar(Carrito carrito) {

        eliminarItems(carrito.getId());

        String sql = """
                INSERT INTO items_carrito(
                    carrito_id,
                    codigo_producto,
                    cantidad,
                    precio_unitario
                )
                VALUES (?, ?, ?, ?)
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

            for(ItemCarrito item : carrito.getItems()){

                ps.setInt(
                        1,
                        carrito.getId()
                );

                ps.setInt(
                        2,
                        item.getProducto().getCodigo()
                );

                ps.setInt(
                        3,
                        item.getCantidad()
                );

                ps.setDouble(
                        4,
                        item.getPrecioUnitario()
                );

                int filas = ps.executeUpdate();

                if (filas == 0) {
                    throw new RuntimeException("No existe el registro a actualizar.");
                }
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar carrito.", e);
        }
    }

    private void eliminarItems(int carritoId){

        String sql = """
                DELETE FROM items_carrito
                WHERE carrito_id = ?
                """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    carritoId
            );

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No existe el item a eliminar.");
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar items del carrito.", e);
        }
    }

    @Override
    public void eliminar(int id) {

        eliminarItems(id);

        String sql = """
                DELETE FROM carritos
                WHERE id = ?
                """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(
                    1,
                    id
            );

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new RuntimeException("No existe el carrito a eliminar.");
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar carrito.", e);
        }
    }
}