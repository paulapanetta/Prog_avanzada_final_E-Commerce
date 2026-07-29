package dao.impl;

import dao.UsuarioDAO;
import database.DatabaseManager;
import exceptions.UsuarioNoEncontradoException;
import model.usuario.Administrador;
import model.usuario.Cliente;
import model.usuario.EstadoUsuario;
import model.usuario.Rol;
import model.usuario.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLiteUsuarioDAO implements UsuarioDAO {

    @Override
    public void guardar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios(
                    nombre,
                    apellido,
                    email,
                    password,
                    fecha_alta,
                    estado,
                    rol
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getFechaAlta().toString());
            ps.setString(6, usuario.getEstado().name());
            ps.setString(7, usuario.getRol().name());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                usuario.setId(keys.getInt(1));
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al guardar usuario.", e);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE id = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearUsuario(rs);
            }

        }catch(SQLException e){

            throw new UsuarioNoEncontradoException("Error al buscar usuario.");
        }

        return null;
    }

    @Override
    public Usuario buscarPorEmail(String email) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE email = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearUsuario(rs);
            }

        }catch(SQLException e){

            throw new UsuarioNoEncontradoException("Error al buscar usuario.");
        }

        return null;
    }

    @Override
    public List<Usuario> obtenerTodos() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT *
                FROM usuarios
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){

                usuarios.add(
                        mapearUsuario(rs)
                );
            }

        }catch(SQLException e){

            throw new UsuarioNoEncontradoException("Error al obtener usuarios.");
        }

        return usuarios;
    }

    @Override
    public void actualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET nombre = ?,
                    apellido = ?,
                    email = ?,
                    password = ?,
                    estado = ?
                WHERE id = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getEstado().name());
            ps.setInt(6, usuario.getId());

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar usuario.", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = """
                DELETE FROM usuarios
                WHERE id = ?
                """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, id);

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar usuario.", e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs)
            throws SQLException {

        Rol rol = Rol.valueOf(
                rs.getString("rol")
        );

        int id = rs.getInt("id");

        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String email = rs.getString("email");
        String password = rs.getString("password");

        LocalDate fechaAlta =
                LocalDate.parse(
                        rs.getString("fecha_alta")
                );

        EstadoUsuario estado =
                EstadoUsuario.valueOf(
                        rs.getString("estado")
                );

        if(rol == Rol.CLIENTE){

            return new Cliente(
                    id,
                    nombre,
                    apellido,
                    email,
                    password,
                    fechaAlta,
                    estado
            );
        }

        return new Administrador(
                id,
                nombre,
                apellido,
                email,
                password,
                fechaAlta,
                estado
        );
    }
}