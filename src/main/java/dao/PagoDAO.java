package dao;

import model.pago.Pago;

import java.util.List;

public interface PagoDAO {

    void guardar(Pago pago);
    Pago buscarPorId(int id);
    List<Pago> obtenerTodos();
    List<Pago> obtenerPorOrden(int ordenId);
    void actualizar(Pago pago);
    void eliminar(int id);

}