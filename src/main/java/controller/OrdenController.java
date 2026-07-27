package controller;

import dao.OrdenDAO;
import model.carrito.Carrito;
import model.carrito.ItemCarrito;
import model.envio.Envio;
import model.inventario.Inventario;
import model.orden.EstadoOrden;
import model.orden.Orden;
import model.pago.Pago;
import model.usuario.Cliente;

import exceptions.CarritoVacioException;
import exceptions.DatosInvalidosException;
import exceptions.OrdenNoEncontradaException;
import exceptions.StockInsuficienteException;

import java.util.List;
import java.util.UUID;

public class OrdenController {

    private OrdenDAO ordenDAO;
    private CarritoController carritoController;
    private InventarioController inventarioController;


    public OrdenController(OrdenDAO ordenDAO, CarritoController carritoController,
                           InventarioController inventarioController) {

        this.ordenDAO = ordenDAO;
        this.carritoController = carritoController;
        this.inventarioController = inventarioController;
    }

    public Orden generarOrden(Cliente cliente, Envio envio) {

        if (envio == null) {
            throw new DatosInvalidosException("Debe indicarse un envio para la orden");
        }

        Carrito carrito = carritoController.obtenerCarritoDeCliente(cliente);

        if (carrito.getItems().isEmpty()) {
            throw new CarritoVacioException("El carrito está vacio, no se puede generar la orden");
        }

        // valida stock de todos los ítems antes de descontar nada
        for (ItemCarrito item : carrito.getItems()) {

            Inventario inventario =
                    inventarioController.buscarPorProducto(
                            item.getProducto().getCodigo()
                    );

            if (item.getCantidad() > inventario.getStockActual()) {

                throw new StockInsuficienteException(
                        "No hay stock suficiente de '"
                                + item.getProducto().getNombre()
                                + "'"
                );
            }
        }

        for (ItemCarrito item : carrito.getItems()) {

            inventarioController.egresarStock(
                    item.getProducto().getCodigo(),
                    item.getCantidad()
            );
        }

        Orden orden =
                new Orden(
                        generarNumero(),
                        carrito,
                        envio
                );

        ordenDAO.guardar(orden);
        carritoController.vaciar(cliente);

        return orden;
    }


    public Orden buscarPorId(int id) {

        Orden orden = ordenDAO.buscarPorId(id);

        if (orden == null) {
            throw new OrdenNoEncontradaException("No existe una orden con id " + id);
        }

        return orden;
    }

    public Orden buscarPorNumero(String numero) {

        Orden orden = ordenDAO.buscarPorNumero(numero);

        if (orden == null) {
            throw new OrdenNoEncontradaException("No existe una orden con numero '" + numero + "'");
        }

        return orden;
    }

    public List<Orden> listar() {
        return ordenDAO.obtenerTodos();
    }

    public List<Orden> listarPorCliente(Cliente cliente) {
        return ordenDAO.obtenerPorCliente(cliente.getId());
    }

    public void asignarPago(int idOrden, Pago pago) {

        Orden orden = buscarPorId(idOrden);
        orden.asignarPago(pago);
        ordenDAO.actualizar(orden);
    }

    public void confirmarPago(int idOrden) {

        Orden orden = buscarPorId(idOrden);
        orden.confirmarPago();
        ordenDAO.actualizar(orden);
    }

    public void cambiarEstado(int idOrden, EstadoOrden nuevoEstado) {

        if (nuevoEstado == null) {
            throw new DatosInvalidosException("Debe indicarse un estado de orden valido");
        }

        Orden orden = buscarPorId(idOrden);
        orden.cambiarEstado(nuevoEstado);
        ordenDAO.actualizar(orden);
    }

    public void cancelar(int idOrden) {

        Orden orden = buscarPorId(idOrden);
        orden.cancelar();
        ordenDAO.actualizar(orden);
    }

    private String generarNumero() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

