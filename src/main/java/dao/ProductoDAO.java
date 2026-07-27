package dao;

import model.producto.Producto;

import java.util.List;

public interface ProductoDAO {

    void guardar(Producto producto);
    Producto buscarPorId(int codigo);
    Producto buscarPorNombre(String nombre);
    List<Producto> obtenerTodos();
    void actualizar(Producto producto);
    void eliminar(int codigo);

}
