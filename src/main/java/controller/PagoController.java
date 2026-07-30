package controller;

import dao.PagoDAO;
import model.orden.EstadoOrden;
import model.orden.Orden;
import model.pago.Pago;
import strategy.ProcesadorPago;

import exceptions.PagoNoEncontradoException;

import java.util.List;

public class PagoController {

    private PagoDAO pagoDAO;
    private OrdenController ordenController;


    public PagoController(PagoDAO pagoDAO, OrdenController ordenController) {
        this.pagoDAO = pagoDAO;
        this.ordenController = ordenController;
    }


    public Pago procesarPago(int ordenId, ProcesadorPago metodo) {

        Orden orden = ordenController.buscarPorId(ordenId);

        Pago pago = new Pago(ordenId, orden.getTotal(), metodo);

        pago.procesar();

        pagoDAO.guardar(pago);
        ordenController.cambiarEstado(ordenId, EstadoOrden.PAGADA);

        return pago;
    }

    public Pago buscarPorId(int id) {

        Pago pago = pagoDAO.buscarPorId(id);

        if (pago == null) {
            throw new PagoNoEncontradoException("No existe un pago con id " + id);
        }

        return pago;
    }

    public List<Pago> listar() {
        return pagoDAO.obtenerTodos();
    }

    public List<Pago> listarPorOrden(int ordenId) {
        return pagoDAO.obtenerPorOrden(ordenId);
    }
}
