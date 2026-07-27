package controller;

import dao.CarritoDAO;
import model.carrito.Carrito;
import model.producto.Producto;
import model.usuario.Cliente;

public class CarritoController {

    private CarritoDAO carritoDAO;


    public CarritoController(CarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
    }


    public Carrito obtenerCarritoDeCliente(Cliente cliente) {

        Carrito carrito = carritoDAO.buscarPorClienteId(cliente.getId());

        if (carrito == null) {

            carrito = new Carrito(cliente);
            carritoDAO.guardar(carrito);
        }

        return carrito;
    }

    public void agregarProducto(Cliente cliente, Producto producto, int cantidad) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        carrito.agregarProducto(producto, cantidad);
        carritoDAO.actualizar(carrito);
    }

    public void eliminarProducto(Cliente cliente, Producto producto) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        carrito.eliminarProducto(producto);
        carritoDAO.actualizar(carrito);
    }

    public void modificarCantidad(Cliente cliente, Producto producto, int nuevaCantidad) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        carrito.modificarCantidad(producto, nuevaCantidad);
        carritoDAO.actualizar(carrito);
    }

    public void vaciar(Cliente cliente) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        carrito.vaciar();
        carritoDAO.actualizar(carrito);
    }

    public double calcularTotal(Cliente cliente) {
        return obtenerCarritoDeCliente(cliente).calcularTotal();
    }
}
