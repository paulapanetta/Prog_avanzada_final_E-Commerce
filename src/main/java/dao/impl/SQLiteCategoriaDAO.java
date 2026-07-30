package dao.impl;

import dao.CategoriaDAO;
import database.DatabaseManager;
import model.producto.Categoria;
import model.producto.EstadoCategoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteCategoriaDAO implements CategoriaDAO {

    @Override
    public void guardar(Categoria categoria) {

        String sql = """
            INSERT INTO categorias(nombre, descripcion, estado)
            VALUES (?, ?, ?)
            """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setString(3, categoria.getEstado().name());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                categoria.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la categoría.", e);
        }

    }

    @Override
    public Categoria buscarPorId(int id) {

        String sql = """
            SELECT *
            FROM categorias
            WHERE id = ?
            """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return new Categoria(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            EstadoCategoria.valueOf(rs.getString("estado"))
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la categoría.", e);
        }
    }

    @Override
    public Categoria buscarPorNombre(String nombre) {

        String sql = """
            SELECT *
            FROM categorias
            WHERE nombre = ?
            """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, nombre);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return new Categoria(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            EstadoCategoria.valueOf(rs.getString("estado"))
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la categoría.", e);
        }
    }

    @Override
    public List<Categoria> obtenerTodos() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categorias";

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                categorias.add(
                        new Categoria(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("descripcion"),
                                EstadoCategoria.valueOf(rs.getString("estado"))
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener las categorías.", e);
        }

        return categorias;
    }

    @Override
    public void actualizar(Categoria categoria) {
        String sql = """
            UPDATE categorias
            SET nombre = ?,
                descripcion = ?,
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
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setString(3, categoria.getEstado().name());
            pstmt.setInt(4, categoria.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la categoría.", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = """
            DELETE FROM categorias
            WHERE id = ?
            """;

        Connection conn;
        try {
            conn = DatabaseManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos.", e);
        }

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la categoría.", e);
        }
    }
}