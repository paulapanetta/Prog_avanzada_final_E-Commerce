package dao.impl;

import dao.CategoriaDAO;
import database.DatabaseManager;
import model.producto.Categoria;
import model.producto.EstadoCategoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteCategoriaDAO implements CategoriaDAO {

    @Override
    public void guardar(Categoria categoria) {

        String sql = """
            INSERT INTO categorias(nombre, descripcion, estado)
            VALUES (?, ?, ?)
            """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setString(3, categoria.getEstado().name());

            ps.executeUpdate();

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

        try (
                Connection conn = DatabaseManager.getConnection();
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

        try (
                Connection conn = DatabaseManager.getConnection();
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

        try (
                Connection conn = DatabaseManager.getConnection();
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

        try (
                Connection conn = DatabaseManager.getConnection();
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

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la categoría.", e);
        }
    }
}
