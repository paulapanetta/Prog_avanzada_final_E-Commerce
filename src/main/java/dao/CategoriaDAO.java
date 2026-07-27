package dao;

import model.producto.Categoria;

import java.util.List;

public interface CategoriaDAO {

    void guardar(Categoria categoria);
    Categoria buscarPorId(int id);
    Categoria buscarPorNombre(String nombre);
    List<Categoria> obtenerTodos();
    void actualizar(Categoria categoria);
    void eliminar(int id);

}
