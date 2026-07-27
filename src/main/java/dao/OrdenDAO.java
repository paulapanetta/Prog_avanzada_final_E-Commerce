package dao;

import model.orden.Orden;

import java.util.List;

public interface OrdenDAO {

    void guardar(Orden orden);
    Orden buscarPorId(int id);
    Orden buscarPorNumero(String numero);
    List<Orden> obtenerTodos();
    List<Orden> obtenerPorCliente(int clienteId);
    void actualizar(Orden orden);
    void eliminar(int id);

}
