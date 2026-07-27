package controller;

import dao.CalificacionDAO;
import model.postventa.Calificacion;
import model.producto.Producto;
import model.usuario.Cliente;

import exceptions.DatosInvalidosException;

import java.util.List;

public class CalificacionController {

    private CalificacionDAO calificacionDAO;


    public CalificacionController(CalificacionDAO calificacionDAO) {
        this.calificacionDAO = calificacionDAO;
    }


    public Calificacion calificar(Cliente cliente, Producto producto,
                                  int puntuacion, String comentario) {

        Calificacion calificacion = new Calificacion(cliente, producto, puntuacion, comentario);
        calificacionDAO.guardar(calificacion);

        return calificacion;
    }

    public Calificacion buscarPorId(int id) {

        Calificacion calificacion = calificacionDAO.buscarPorId(id);

        if (calificacion == null) {
            throw new DatosInvalidosException("No existe una calificacion con id " + id);
        }

        return calificacion;
    }

    public List<Calificacion> listar() {
        return calificacionDAO.obtenerTodos();
    }

    public List<Calificacion> listarPorProducto(Producto producto) {
        return calificacionDAO.obtenerPorProducto(producto.getCodigo());
    }

    public double promedioPorProducto(Producto producto) {

        List<Calificacion> calificaciones = listarPorProducto(producto);

        if (calificaciones.isEmpty()) {
            return 0.0;
        }

        double suma = 0;

        for (Calificacion calificacion : calificaciones) {
            suma += calificacion.getPuntuacion();
        }

        return suma / calificaciones.size();
    }
}
