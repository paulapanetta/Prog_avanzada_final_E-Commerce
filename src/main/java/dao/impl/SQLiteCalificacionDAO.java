package dao.impl;

import dao.CalificacionDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import database.DatabaseManager;
import model.postventa.Calificacion;
import model.producto.Producto;
import model.usuario.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SQLiteCalificacionDAO implements CalificacionDAO {


    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;


    public SQLiteCalificacionDAO(
            UsuarioDAO usuarioDAO,
            ProductoDAO productoDAO
    ){
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
    }


    @Override
    public void guardar(Calificacion calificacion) {

        String sql = """
            INSERT INTO calificaciones(
                cliente_id,
                producto_codigo,
                puntuacion,
                comentario,
                fecha
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

            ps.setInt(1, calificacion.getCliente().getId());
            ps.setInt(2, calificacion.getProducto().getCodigo());
            ps.setInt(3, calificacion.getPuntuacion());
            ps.setString(4, calificacion.getComentario());
            ps.setString(5, calificacion.getFecha().toString());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                calificacion.setId(keys.getInt(1));
            }


        }catch(SQLException e){

            throw new RuntimeException("Error al guardar calificación", e);
        }
    }


    @Override
    public Calificacion buscarPorId(int id) {

        String sql = """
            SELECT *
            FROM calificaciones
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();


            if(rs.next()){
                return mapearCalificacion(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar calificación", e);
        }

        return null;
    }


    @Override
    public List<Calificacion> obtenerTodos() {

        List<Calificacion> calificaciones = new ArrayList<>();

        String sql = """
            SELECT *
            FROM calificaciones
            """;


        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){

                calificaciones.add(
                        mapearCalificacion(rs)
                );
            }


        }catch(SQLException e){

            throw new RuntimeException("Error al obtener calificaciones", e);
        }

        return calificaciones;
    }


    @Override
    public List<Calificacion> obtenerPorProducto(int codigoProducto) {

        List<Calificacion> calificaciones = new ArrayList<>();

        String sql = """
            SELECT *
            FROM calificaciones
            WHERE producto_codigo = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,codigoProducto);

            ResultSet rs = ps.executeQuery();


            while(rs.next()){

                calificaciones.add(
                        mapearCalificacion(rs)
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener calificaciones del producto", e);
        }

        return calificaciones;
    }


    @Override
    public void actualizar(Calificacion calificacion) {

        String sql = """
            UPDATE calificaciones
            SET puntuacion = ?,
                comentario = ?
            WHERE id = ?
            """;


        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(
                    1,
                    calificacion.getPuntuacion()
            );

            ps.setString(
                    2,
                    calificacion.getComentario()
            );

            ps.setInt(
                    3,
                    calificacion.getId()
            );


            ps.executeUpdate();


        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar calificación", e);
        }
    }


    @Override
    public void eliminar(int id) {

        String sql = """
            DELETE FROM calificaciones
            WHERE id = ?
            """;


        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ps.executeUpdate();


        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar calificación", e);
        }
    }


    private Calificacion mapearCalificacion(ResultSet rs)
            throws SQLException {


        Cliente cliente =
                (Cliente) usuarioDAO.buscarPorId(
                        rs.getInt("cliente_id")
                );


        Producto producto =
                productoDAO.buscarPorId(
                        rs.getInt("producto_codigo")
                );


        return new Calificacion(
                rs.getInt("id"),
                cliente,
                producto,
                rs.getInt("puntuacion"),
                rs.getString("comentario"),
                LocalDate.parse(
                        rs.getString("fecha")
                )
        );
    }
}