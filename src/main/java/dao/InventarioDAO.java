package dao;

import model.inventario.Inventario;
import java.util.List;

public interface InventarioDAO {

    void guardar(Inventario inventario);
    void actualizar(Inventario inventario);
    Inventario obtenerInventario();
    void eliminar(int id);
}
