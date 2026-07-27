package dao;

import model.postventa.Calificacion;

import java.util.List;

public interface CalificacionDAO {

    void guardar(Calificacion calificacion);
    Calificacion buscarPorId(int id);
    List<Calificacion> obtenerTodos();
    List<Calificacion> obtenerPorProducto(int codigoProducto);
    void actualizar(Calificacion calificacion);
    void eliminar(int id);

}
