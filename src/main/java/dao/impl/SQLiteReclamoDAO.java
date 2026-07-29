package dao.impl;

import dao.ReclamoDAO;
import dao.OrdenDAO;
import dao.UsuarioDAO;
import database.DatabaseManager;
import model.orden.Orden;
import model.postventa.*;

import model.usuario.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLiteReclamoDAO implements ReclamoDAO {


    private UsuarioDAO usuarioDAO;
    private OrdenDAO ordenDAO;


    public SQLiteReclamoDAO(
            UsuarioDAO usuarioDAO,
            OrdenDAO ordenDAO
    ){
        this.usuarioDAO = usuarioDAO;
        this.ordenDAO = ordenDAO;
    }


    @Override
    public void guardar(Reclamo reclamo) {

        String sql = """
            INSERT INTO reclamos(
                cliente_id,
                orden_id,
                motivo,
                fecha,
                estado
            )
            VALUES (?, ?, ?, ?, ?)
            """;


        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            ps.setInt(1, reclamo.getCliente().getId());
            ps.setInt(2, reclamo.getOrden().getId());
            ps.setString(3, reclamo.getMotivo());
            ps.setString(4, reclamo.getFecha().toString());
            ps.setString(5, reclamo.getEstado().name());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                reclamo.setId(keys.getInt(1));
            }


        }catch(SQLException e){

            throw new RuntimeException("Error al guardar reclamo", e);
        }
    }


    @Override
    public Reclamo buscarPorId(int id) {

        String sql = """
            SELECT *
            FROM reclamos
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearReclamo(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar reclamo", e);
        }

        return null;
    }


    @Override
    public List<Reclamo> obtenerTodos() {

        List<Reclamo> reclamos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM reclamos
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){

                reclamos.add(
                        mapearReclamo(rs)
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener reclamos", e);
        }

        return reclamos;
    }


    @Override
    public List<Reclamo> obtenerPorCliente(int clienteId) {

        List<Reclamo> reclamos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM reclamos
            WHERE cliente_id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, clienteId);

            ResultSet rs = ps.executeQuery();


            while(rs.next()){

                reclamos.add(
                        mapearReclamo(rs)
                );
            }


        }catch(SQLException e){

            throw new RuntimeException("Error al obtener reclamos del cliente", e);
        }

        return reclamos;
    }


    @Override
    public void actualizar(Reclamo reclamo) {

        String sql = """
            UPDATE reclamos
            SET motivo = ?,
                estado = ?
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(
                    1,
                    reclamo.getMotivo()
            );

            ps.setString(
                    2,
                    reclamo.getEstado().name()
            );

            ps.setInt(
                    3,
                    reclamo.getId()
            );

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar reclamo", e);
        }
    }


    @Override
    public void eliminar(int id) {

        String sql = """
            DELETE FROM reclamos
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ps.executeUpdate();


        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar reclamo", e);
        }
    }

    private Reclamo mapearReclamo(ResultSet rs)
            throws SQLException {

        Cliente cliente =
                (Cliente) usuarioDAO.buscarPorId(
                        rs.getInt("cliente_id")
                );

        Orden orden =
                ordenDAO.buscarPorId(
                        rs.getInt("orden_id")
                );

        return new Reclamo(
                rs.getInt("id"),
                cliente,
                orden,
                rs.getString("motivo"),
                LocalDate.parse(
                        rs.getString("fecha")
                ),
                EstadoReclamo.valueOf(
                        rs.getString("estado")
                )
        );
    }
}