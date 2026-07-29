package dao.impl;

import dao.CategoriaDAO;
import dao.ProductoDAO;
import database.DatabaseManager;
import exceptions.CategoriaNoEncontradaException;
import model.producto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteProductoDAO implements ProductoDAO {

    private CategoriaDAO categoriaDAO;

    public SQLiteProductoDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }


    @Override
    public void guardar(Producto producto) {

        String sql = """
        INSERT INTO productos(
            nombre,
            descripcion,
            precio,
            categoria_id,
            peso,
            estado,
            tipo
        )
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getCategoria().getId());
            ps.setDouble(5, producto.getPeso());
            ps.setString(6, producto.getEstado().name());
            ps.setString(7, obtenerTipoProducto(producto));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                producto.setCodigo(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el producto.", e);
        }
    }

    @Override
    public Producto buscarPorId(int codigo) {

        String sql = """
        SELECT *
        FROM productos
        WHERE codigo = ?
        """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int idCategoria = rs.getInt("categoria_id");

                    Categoria categoria = categoriaDAO.buscarPorId(idCategoria);

                    if (categoria == null) {
                        throw new CategoriaNoEncontradaException("No existe la categoría con id " + idCategoria);
                    }

                    EstadoProducto estado = EstadoProducto.valueOf(
                            rs.getString("estado")
                    );

                    return crearProducto(
                            rs,
                            categoria,
                            estado
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el producto.", e);
        }
    }

    @Override
    public Producto buscarPorNombre(String nombre) {

        String sql = """
        SELECT *
        FROM productos
        WHERE nombre = ?
        """;

        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int idCategoria = rs.getInt("categoria_id");

                    Categoria categoria = categoriaDAO.buscarPorId(idCategoria);

                    if (categoria == null) {
                        throw new CategoriaNoEncontradaException("No existe la categoría con id " + idCategoria);
                    }

                    EstadoProducto estado = EstadoProducto.valueOf(
                            rs.getString("estado")
                    );

                    return crearProducto(
                            rs,
                            categoria,
                            estado
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el producto.", e);
        }
    }


    @Override
    public List<Producto> obtenerTodos() {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM productos";


        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                int idCategoria = rs.getInt("categoria_id");

                Categoria categoria = categoriaDAO.buscarPorId(idCategoria);

                if (categoria == null) {
                    throw new CategoriaNoEncontradaException("No existe la categoría con id " + idCategoria);
                }

                EstadoProducto estado = EstadoProducto.valueOf(
                        rs.getString("estado")
                );


                productos.add(
                        crearProducto(
                                rs,
                                categoria,
                                estado
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener productos.", e);
        }

        return productos;
    }


    @Override
    public List<Producto> buscarPorCategoria(int idCategoria) {

        List<Producto> productos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM productos
            WHERE categoria_id = ?
            """;


        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, idCategoria);


            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    int idCategoriaProducto = rs.getInt("categoria_id");

                    Categoria categoria = categoriaDAO.buscarPorId(idCategoriaProducto);

                    if (categoria == null) {
                        throw new CategoriaNoEncontradaException(
                                "No existe la categoría con id " + idCategoriaProducto
                        );
                    }

                    EstadoProducto estado = EstadoProducto.valueOf(
                            rs.getString("estado")
                    );

                    productos.add(
                            crearProducto(
                                    rs,
                                    categoria,
                                    estado
                            )
                    );
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar productos por categoría.", e);
        }

        return productos;
    }


    @Override
    public void actualizar(Producto producto) {

        String sql = """
            UPDATE productos
            SET nombre = ?,
                descripcion = ?,
                precio = ?,
                categoria_id = ?,
                peso = ?,
                estado = ?,
                tipo = ?
            WHERE codigo = ?
            """;


        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getCategoria().getId());
            ps.setDouble(5, producto.getPeso());
            ps.setString(6, producto.getEstado().name());
            ps.setString(7, obtenerTipoProducto(producto));
            ps.setInt(8, producto.getCodigo());


            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar el producto.",
                    e
            );
        }
    }


    @Override
    public void eliminar(int codigo) {

        String sql = """
            DELETE FROM productos
            WHERE codigo = ?
            """;


        try (
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar el producto.",
                    e
            );
        }
    }


    private Producto crearProducto(
            ResultSet rs,
            Categoria categoria,
            EstadoProducto estado
    ) throws SQLException {

        String tipo = rs.getString("tipo");


        return switch (tipo) {

            case "FISICO" -> new ProductoFisico(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    categoria,
                    rs.getDouble("peso"),
                    estado
            );


            case "DIGITAL" -> new ProductoDigital(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    categoria,
                    rs.getDouble("peso"),
                    estado
            );


            case "IMPORTADO" -> new ProductoImportado(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    categoria,
                    rs.getDouble("peso"),
                    estado
            );


            default -> throw new RuntimeException(
                    "Tipo de producto desconocido: " + tipo
            );
        };
    }


    private String obtenerTipoProducto(Producto producto) {

        if (producto instanceof ProductoFisico) {
            return "FISICO";
        }

        if (producto instanceof ProductoDigital) {
            return "DIGITAL";
        }

        if (producto instanceof ProductoImportado) {
            return "IMPORTADO";
        }

        throw new RuntimeException(
                "Tipo de producto no soportado."
        );
    }
}
