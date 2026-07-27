package dao;

import model.postventa.Reclamo;

import java.util.List;

public interface ReclamoDAO {

    void guardar(Reclamo reclamo);
    Reclamo buscarPorId(int id);
    List<Reclamo> obtenerTodos();
    List<Reclamo> obtenerPorCliente(int clienteId);
    void actualizar(Reclamo reclamo);
    void eliminar(int id);

}
