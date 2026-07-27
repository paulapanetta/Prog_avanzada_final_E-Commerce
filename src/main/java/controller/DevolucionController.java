package controller;

import dao.DevolucionDAO;
import model.postventa.Devolucion;
import model.producto.Producto;
import model.usuario.Cliente;

import exceptions.DatosInvalidosException;

import java.util.List;

public class DevolucionController {

    private DevolucionDAO devolucionDAO;


    public DevolucionController(DevolucionDAO devolucionDAO) {
        this.devolucionDAO = devolucionDAO;
    }


    public Devolucion solicitar(Cliente cliente, Producto producto, String motivo) {

        if (motivo == null || motivo.isBlank()) {
            throw new DatosInvalidosException("El motivo de la devolucion no puede estar vacio");
        }

        Devolucion devolucion = new Devolucion(cliente, producto, motivo);
        devolucionDAO.guardar(devolucion);

        return devolucion;
    }

    public Devolucion buscarPorId(int id) {

        Devolucion devolucion = devolucionDAO.buscarPorId(id);

        if (devolucion == null) {
            throw new DatosInvalidosException("No existe una devolucion con id " + id);
        }

        return devolucion;
    }

    public List<Devolucion> listar() {
        return devolucionDAO.obtenerTodos();
    }

    public List<Devolucion> listarPorCliente(Cliente cliente) {
        return devolucionDAO.obtenerPorCliente(cliente.getId());
    }

    public void aprobar(int id) {

        Devolucion devolucion = buscarPorId(id);
        devolucion.aprobar();
        devolucionDAO.actualizar(devolucion);
    }

    public void rechazar(int id) {

        Devolucion devolucion = buscarPorId(id);
        devolucion.rechazar();
        devolucionDAO.actualizar(devolucion);
    }
}
