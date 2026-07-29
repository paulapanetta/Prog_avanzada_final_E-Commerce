package dao.impl;

import dao.EnvioDAO;
import database.DatabaseManager;
import exceptions.EnvioNoEncontradoException;
import model.envio.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class SQLiteEnvioDAO implements EnvioDAO {

    @Override
    public void guardar(Envio envio) {

        String sql = """
            INSERT INTO envios(
                codigo_seguimiento,
                orden_id,
                direccion,
                provincia,
                ciudad,
                codigo_postal,
                tipo,
                estado,
                costo
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            ps.setString(1, envio.getCodigoSeguimiento());
            ps.setInt(2, envio.getOrdenId());
            ps.setString(3, envio.getDireccion());
            ps.setString(4, envio.getProvincia());
            ps.setString(5, envio.getCiudad());
            ps.setString(6, envio.getCodigoPostal());
            ps.setString(7, envio.getTipoEnvio().name());
            ps.setString(8, envio.getEstado().name());
            ps.setDouble(9, envio.getCosto());

            ps.executeUpdate();


            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                envio.setId(keys.getInt(1));
            }

        }catch(SQLException e){
            throw new RuntimeException("Error al guardar envío.", e);
        }
    }

    @Override
    public Envio buscarPorId(int id) {

        String sql = """
            SELECT *
            FROM envios
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearEnvio(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar envío.", e);
        }

        throw new EnvioNoEncontradoException(
                "No existe un envío con id: " + id);
    }

    @Override
    public Envio buscarPorCodigoSeguimiento(String codigoSeguimiento) {

        String sql = """
            SELECT *
            FROM envios
            WHERE codigo_seguimiento = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(1, codigoSeguimiento);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearEnvio(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar envío.", e);
        }

        throw new EnvioNoEncontradoException(
                "No existe un envío con código de seguimiento: " + codigoSeguimiento);
    }


    @Override
    public Envio buscarPorOrden(int ordenId) {

        String sql = """
            SELECT *
            FROM envios
            WHERE orden_id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, ordenId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearEnvio(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al buscar envío de orden.", e);
        }

        return null;
    }

    @Override
    public List<Envio> obtenerTodos() {

        List<Envio> envios = new ArrayList<>();

        String sql = """
            SELECT *
            FROM envios
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){

                envios.add(
                        mapearEnvio(rs)
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener envíos.", e);
        }

        return envios;
    }

    @Override
    public void actualizar(Envio envio) {

        String sql = """
            UPDATE envios
            SET codigo_seguimiento = ?,
                direccion = ?,
                provincia = ?,
                ciudad = ?,
                codigo_postal = ?,
                estado = ?,
                costo = ?
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(1, envio.getCodigoSeguimiento());
            ps.setString(2, envio.getDireccion());
            ps.setString(3, envio.getProvincia());
            ps.setString(4, envio.getCiudad());
            ps.setString(5, envio.getCodigoPostal());
            ps.setString(6, envio.getEstado().name());
            ps.setDouble(7, envio.getCosto());
            ps.setInt(8, envio.getId());

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar envío.", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = """
            DELETE FROM envios
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, id);

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar envío.", e);
        }
    }

    private Envio mapearEnvio(ResultSet rs)
            throws SQLException {

        return  new Envio(
                rs.getInt("id"),
                rs.getString("codigo_seguimiento"),
                rs.getInt("orden_id"),
                rs.getString("direccion"),
                rs.getString("provincia"),
                rs.getString("ciudad"),
                rs.getString("codigo_postal"),
                TipoEnvio.valueOf(
                        rs.getString("tipo")
                ),
                EstadoEnvio.valueOf(
                        rs.getString("estado")
                ),
                rs.getDouble("costo")
        );
    }

}
