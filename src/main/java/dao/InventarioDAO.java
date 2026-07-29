package dao;

import model.inventario.Inventario;

public interface InventarioDAO {

    void guardar(Inventario inventario);
    void actualizar(Inventario inventario);
    Inventario obtenerInventario();
    void eliminar(int id);
}
