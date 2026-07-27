package dao;

import model.carrito.Carrito;

public interface CarritoDAO {

    void guardar(Carrito carrito);
    Carrito buscarPorId(int id);
    Carrito buscarPorClienteId(int clienteId);
    void actualizar(Carrito carrito);
    void eliminar(int id);

}
