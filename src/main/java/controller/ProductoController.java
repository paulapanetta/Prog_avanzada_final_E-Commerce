package controller;

import dao.ProductoDAO;
import model.producto.EstadoProducto;
import model.producto.Producto;

import exceptions.DatosInvalidosException;
import exceptions.ProductoDuplicadoException;
import exceptions.ProductoNoEncontradoException;

import java.util.List;

public class ProductoController {

    private ProductoDAO productoDAO;


    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }


    public void guardar(Producto producto) {

        if (producto == null) {
            throw new DatosInvalidosException("El producto no puede estar vacio");
        }

        if (productoDAO.buscarPorNombre(producto.getNombre()) != null) {
            throw new ProductoDuplicadoException(
                    "Ya existe un producto con el nombre '" + producto.getNombre() + "'"
            );
        }

        productoDAO.guardar(producto);
    }

    public Producto buscarPorId(int codigo) {

        Producto producto = productoDAO.buscarPorId(codigo);

        if (producto == null) {
            throw new ProductoNoEncontradoException(
                    "No existe un producto con codigo " + codigo
            );
        }

        return producto;
    }

    public List<Producto> listar() {
        return productoDAO.obtenerTodos();
    }

    public void modificar(int codigo, String nombre, String descripcion, double peso) {

        if (nombre == null || nombre.isBlank()) {
            throw new DatosInvalidosException("El nombre no puede estar vacio");
        }

        Producto producto = buscarPorId(codigo);

        Producto existente = productoDAO.buscarPorNombre(nombre);

        if (existente != null && existente.getCodigo() != codigo) {
            throw new ProductoDuplicadoException(
                    "Ya existe otro producto con el nombre '" + nombre + "'"
            );
        }

        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPeso(peso);

        productoDAO.actualizar(producto);
    }

    public void eliminar(int codigo) {

        buscarPorId(codigo);
        productoDAO.eliminar(codigo);
    }

    public void activar(int codigo) {

        Producto producto = buscarPorId(codigo);
        producto.setEstado(EstadoProducto.ACTIVO);
        productoDAO.actualizar(producto);
    }

    public void desactivar(int codigo) {

        Producto producto = buscarPorId(codigo);
        producto.setEstado(EstadoProducto.INACTIVO);
        productoDAO.actualizar(producto);
    }

    public void suspender(int codigo) {

        Producto producto = buscarPorId(codigo);
        producto.setEstado(EstadoProducto.SUSPENDIDO);
        productoDAO.actualizar(producto);
    }

    public void aplicarDescuento(int codigo, double porcentaje) {

        Producto producto = buscarPorId(codigo);
        producto.aplicarDescuento(porcentaje);
        productoDAO.actualizar(producto);
    }
}
