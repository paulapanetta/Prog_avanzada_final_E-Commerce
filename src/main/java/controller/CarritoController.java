package controller;

import dao.CarritoDAO;
import dao.InventarioDAO;
import model.carrito.Carrito;
import model.inventario.Inventario;
import model.producto.Producto;
import model.usuario.Cliente;

public class CarritoController {

    private CarritoDAO carritoDAO;
    private InventarioDAO inventarioDAO;

    public CarritoController(
            CarritoDAO carritoDAO,
            InventarioDAO inventarioDAO
    ) {
        this.carritoDAO = carritoDAO;
        this.inventarioDAO = inventarioDAO;
    }


    public Carrito obtenerCarritoDeCliente(Cliente cliente) {

        Carrito carrito = carritoDAO.buscarPorClienteId(cliente.getId());

        if (carrito == null) {

            carrito = new Carrito(cliente);
            carritoDAO.guardar(carrito);
        }

        return carrito;
    }

    public void agregarProducto(Cliente cliente, int codigoProducto, int cantidad) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        Inventario inventario = inventarioDAO.buscarPorProducto(codigoProducto);
        carrito.agregarProducto(inventario, cantidad);
        carritoDAO.actualizar(carrito);
    }

    public void eliminarProducto(Cliente cliente, Producto producto) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        carrito.eliminarProducto(producto);
        carritoDAO.actualizar(carrito);
    }

    public void modificarCantidad(Cliente cliente, int codigoProducto, int nuevaCantidad) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        Inventario inventario = inventarioDAO.buscarPorProducto(codigoProducto);
        carrito.modificarCantidad(inventario, nuevaCantidad);
        carritoDAO.actualizar(carrito);
    }

    public void vaciar(Cliente cliente) {

        Carrito carrito = obtenerCarritoDeCliente(cliente);
        carrito.vaciar();
        carritoDAO.actualizar(carrito);
    }
}
