package dao;

import model.postventa.Devolucion;

import java.util.List;

public interface DevolucionDAO {

    void guardar(Devolucion devolucion);
    Devolucion buscarPorId(int id);
    List<Devolucion> obtenerTodos();
    List<Devolucion> obtenerPorCliente(int clienteId);
    void actualizar(Devolucion devolucion);
    void eliminar(int id);

}