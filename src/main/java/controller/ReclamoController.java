package controller;

import dao.ReclamoDAO;
import model.orden.Orden;
import model.postventa.Reclamo;
import model.usuario.Cliente;

import exceptions.DatosInvalidosException;
import exceptions.OrdenNoEncontradaException;

import java.util.List;

public class ReclamoController {

    private ReclamoDAO reclamoDAO;


    public ReclamoController(ReclamoDAO reclamoDAO) {
        this.reclamoDAO = reclamoDAO;
    }


    public Reclamo generar(Cliente cliente, Orden orden, String motivo) {

        if (orden == null) {
            throw new OrdenNoEncontradaException("El reclamo debe estar asociado a un pedido");
        }

        if (motivo == null || motivo.isBlank()) {
            throw new DatosInvalidosException("El motivo del reclamo no puede estar vacio");
        }

        Reclamo reclamo = new Reclamo(cliente, orden, motivo);
        reclamoDAO.guardar(reclamo);

        return reclamo;
    }

    public Reclamo buscarPorId(int id) {

        Reclamo reclamo = reclamoDAO.buscarPorId(id);

        if (reclamo == null) {
            throw new DatosInvalidosException("No existe un reclamo con id " + id);
        }

        return reclamo;
    }

    public List<Reclamo> listar() {
        return reclamoDAO.obtenerTodos();
    }

    public List<Reclamo> listarPorCliente(Cliente cliente) {
        return reclamoDAO.obtenerPorCliente(cliente.getId());
    }

    public void ponerEnRevision(int id) {

        Reclamo reclamo = buscarPorId(id);
        reclamo.ponerEnRevision();
        reclamoDAO.actualizar(reclamo);
    }

    public void resolver(int id) {

        Reclamo reclamo = buscarPorId(id);
        reclamo.resolver();
        reclamoDAO.actualizar(reclamo);
    }

    public void rechazar(int id) {

        Reclamo reclamo = buscarPorId(id);
        reclamo.rechazar();
        reclamoDAO.actualizar(reclamo);
    }
}
