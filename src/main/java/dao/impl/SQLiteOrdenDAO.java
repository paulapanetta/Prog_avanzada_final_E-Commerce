package dao.impl;

import dao.OrdenDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import database.DatabaseManager;
import model.carrito.ItemCarrito;
import model.orden.EstadoOrden;
import model.orden.Orden;
import model.producto.Producto;
import model.usuario.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SQLiteOrdenDAO implements OrdenDAO {


    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;


    public SQLiteOrdenDAO(
            UsuarioDAO usuarioDAO,
            ProductoDAO productoDAO
    ){

        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
    }


    @Override
    public void guardar(Orden orden) {

        String sql = """
                INSERT INTO ordenes(
                    numero,
                    cliente_id,
                    fecha,
                    total,
                    estado
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try(
                Connection conn = DatabaseManager.getConnection();

                PreparedStatement ps = conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ){

            ps.setString(
                    1,
                    orden.getNumero()
            );

            ps.setInt(
                    2,
                    orden.getCliente().getId()
            );

            ps.setString(
                    3,
                    orden.getFecha().toString()
            );

            ps.setDouble(
                    4,
                    orden.getTotal()
            );

            ps.setString(
                    5,
                    orden.getEstado().name()
            );

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            int idOrden = 0;

            if(keys.next()){
                idOrden = keys.getInt(1);
            }

            guardarItems(
                    idOrden,
                    orden.getItems()
            );

        }catch(SQLException e){

            throw new RuntimeException("Error al guardar orden", e);
        }
    }

    private void guardarItems(
            int ordenId,
            List<ItemCarrito> items
    ){

        String sql = """
                INSERT INTO items_orden(
                    orden_id,
                    codigo_producto,
                    cantidad,
                    precio_unitario
                )
                VALUES (?, ?, ?, ?)
                """;


        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            for(ItemCarrito item : items){


                ps.setInt(
                        1,
                        ordenId
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

                ps.executeUpdate();
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al guardar items de orden", e);
        }
    }

    @Override
    public Orden buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM ordenes
                WHERE id = ?
                """;


        try(
                Connection conn = DatabaseManager.getConnection();

                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    id
            );

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearOrden(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar orden", e);
        }

        return null;
    }

    @Override
    public Orden buscarPorNumero(String numero) {

        String sql = """
                SELECT *
                FROM ordenes
                WHERE numero = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();

                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    numero
            );

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearOrden(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar orden", e);
        }

        return null;
    }


    private Orden mapearOrden(
            ResultSet rs
    ) throws SQLException {

        int id =
                rs.getInt("id");

        Cliente cliente =
                (Cliente) usuarioDAO.buscarPorId(
                        rs.getInt("cliente_id")
                );

        return new Orden(
                id,
                rs.getString("numero"),
                cliente,
                LocalDate.parse(
                        rs.getString("fecha")
                ),
                obtenerItems(id),
                rs.getDouble("total"),
                EstadoOrden.valueOf(
                        rs.getString("estado")
                ),
                null,
                null,
                new ArrayList<>()
        );
    }

    private List<ItemCarrito> obtenerItems(

            int ordenId
    ){

        List<ItemCarrito> items =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM items_orden
                WHERE orden_id = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    ordenId
            );

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Producto producto =
                        productoDAO.buscarPorId(
                                rs.getInt("codigo_producto")
                        );

                items.add(
                        new ItemCarrito(
                                producto,
                                rs.getInt("cantidad"),
                                rs.getDouble("precio_unitario")
                        )
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener items de orden", e);
        }

        return items;
    }

    @Override
    public List<Orden> obtenerTodos() {

        List<Orden> ordenes =
                new ArrayList<>();

        String sql =
                "SELECT * FROM ordenes";

        try(
                Connection conn = DatabaseManager.getConnection();

                PreparedStatement ps = conn.prepareStatement(sql);

                ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){
                ordenes.add(mapearOrden(rs));
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al listar ordenes", e);
        }

        return ordenes;
    }


    @Override
    public List<Orden> obtenerPorCliente(
            int clienteId
    ){

        List<Orden> ordenes =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM ordenes
                WHERE cliente_id = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(
                    1,
                    clienteId
            );

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                ordenes.add(mapearOrden(rs));
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar ordenes del cliente", e);
        }
        return ordenes;
    }

    @Override
    public void actualizar(Orden orden) {

        String sql = """
                UPDATE ordenes
                SET estado = ?,
                    total = ?
                WHERE id = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();

                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    orden.getEstado().name()
            );

            ps.setDouble(
                    2,
                    orden.getTotal()
            );

            ps.setInt(
                    3,
                    orden.getId()
            );

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar orden", e
            );
        }
    }

    @Override
    public void eliminar(int id) {

        String sqlItems = "DELETE FROM items_orden WHERE orden_id = ?";

        String sqlOrden = "DELETE FROM ordenes WHERE id = ?";

        try(
                Connection conn = DatabaseManager.getConnection()
        ){

            PreparedStatement psItems = conn.prepareStatement(sqlItems);

            psItems.setInt(1,id);
            psItems.executeUpdate();

            PreparedStatement psOrden = conn.prepareStatement(sqlOrden);

            psOrden.setInt(1,id);
            psOrden.executeUpdate();


        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar orden", e);
        }
    }

}