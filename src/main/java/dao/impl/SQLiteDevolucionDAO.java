package dao.impl;

import dao.DevolucionDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import database.DatabaseManager;
import model.postventa.Devolucion;
import model.postventa.EstadoDevolucion;
import model.producto.Producto;
import model.usuario.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDevolucionDAO implements DevolucionDAO {


    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;

    public SQLiteDevolucionDAO(
            UsuarioDAO usuarioDAO,
            ProductoDAO productoDAO
    ){
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
    }


    @Override
    public void guardar(Devolucion devolucion) {

        String sql = """
            INSERT INTO devoluciones(
                cliente_id,
                producto_codigo,
                motivo,
                fecha,
                estado
            )
            VALUES (?, ?, ?, ?, ?)
            """;


        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            ps.setInt(1, devolucion.getCliente().getId());
            ps.setInt(2, devolucion.getProducto().getCodigo());
            ps.setString(3, devolucion.getMotivo());
            ps.setString(4, devolucion.getFecha().toString());
            ps.setString(5, devolucion.getEstado().name());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                devolucion.setId(keys.getInt(1));
            }


        }catch(SQLException e){

            throw new RuntimeException("Error al guardar devolución", e);
        }
    }


    @Override
    public Devolucion buscarPorId(int id) {

        String sql = """
            SELECT *
            FROM devoluciones
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
                return mapearDevolucion(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar devolución", e);
        }

        return null;
    }


    @Override
    public List<Devolucion> obtenerTodos() {

        List<Devolucion> devoluciones = new ArrayList<>();

        String sql = """
            SELECT *
            FROM devoluciones
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

                devoluciones.add(
                        mapearDevolucion(rs)
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener devoluciones", e);
        }

        return devoluciones;
    }


    @Override
    public List<Devolucion> obtenerPorCliente(int clienteId) {

        List<Devolucion> devoluciones = new ArrayList<>();

        String sql = """
            SELECT *
            FROM devoluciones
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


            while(rs.next()){

                devoluciones.add(
                        mapearDevolucion(rs)
                );
            }


        }catch(SQLException e){

            throw new RuntimeException("Error al obtener devoluciones del cliente", e);
        }

        return devoluciones;
    }


    @Override
    public void actualizar(Devolucion devolucion) {

        String sql = """
            UPDATE devoluciones
            SET motivo = ?,
                estado = ?
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

            ps.setString(
                    1,
                    devolucion.getMotivo()
            );

            ps.setString(
                    2,
                    devolucion.getEstado().name()
            );

            ps.setInt(
                    3,
                    devolucion.getId()
            );


            ps.executeUpdate();


        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar devolución", e);
        }
    }


    @Override
    public void eliminar(int id) {

        String sql = """
            DELETE FROM devoluciones
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

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar devolución", e);
        }
    }

    private Devolucion mapearDevolucion(ResultSet rs)
            throws SQLException {

        Cliente cliente =
                (Cliente) usuarioDAO.buscarPorId(
                        rs.getInt("cliente_id")
                );

        Producto producto =
                productoDAO.buscarPorId(
                        rs.getInt("producto_codigo")
                );

        return new Devolucion(
                rs.getInt("id"),
                cliente,
                producto,
                rs.getString("motivo"),
                LocalDate.parse(
                        rs.getString("fecha")
                ),
                EstadoDevolucion.valueOf(
                        rs.getString("estado")
                )
        );
    }
}