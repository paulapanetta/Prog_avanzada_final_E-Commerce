package model.carrito;

import model.producto.Producto;
import model.usuario.Cliente;

import exceptions.CarritoVacioException;
import exceptions.DatosInvalidosException;
import exceptions.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrito {

    private int id;
    private Cliente cliente;
    private List<ItemCarrito> items;


    public Carrito(Cliente cliente) {

        if (cliente == null) {
            throw new DatosInvalidosException(
                    "El carrito tiene que pertenecer a un cliente"
            );
        }

        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public Carrito(int id, Cliente cliente, List<ItemCarrito> items) {

        this.id = id;
        this.cliente = cliente;
        this.items = items;
    }


    public int getId() { return id; }

    public Cliente getCliente() { return cliente; }

    public List<ItemCarrito> getItems() { return items; }


    public void agregarProducto(Producto producto, int cantidad) {

        if (cantidad <= 0) {
            throw new DatosInvalidosException(
                    "La cantidad tiene que ser mayor a cero"
            );
        }

        if (!producto.validarDisponibilidad()) {
            throw new StockInsuficienteException(
                    "El producto '" + producto.getNombre() + "' no esta disponible"
            );
        }

        Optional<ItemCarrito> existente = buscarItem(producto);

        int cantidadTotal = cantidad + existente.map(ItemCarrito::getCantidad).orElse(0);

        if (cantidadTotal > producto.getStock()) {
            throw new StockInsuficienteException(
                    "No hay stock suficiente de '" + producto.getNombre() + "'"
            );
        }

        if (existente.isPresent()) {
            existente.get().setCantidad(cantidadTotal);
        } else {
            items.add(new ItemCarrito(producto, cantidad));
        }
    }

    public void eliminarProducto(Producto producto) {
        items.removeIf(item -> item.getProducto().getCodigo() == producto.getCodigo());
    }

    public void modificarCantidad(Producto producto, int nuevaCantidad) {

        if (nuevaCantidad <= 0) {
            throw new DatosInvalidosException(
                    "La cantidad tieen que ser mayor a cero"
            );
        }

        if (nuevaCantidad > producto.getStock()) {
            throw new StockInsuficienteException(
                    "No hay stock suficiente de '" + producto.getNombre() + "'"
            );
        }

        ItemCarrito item = buscarItem(producto).orElseThrow(() ->
                new DatosInvalidosException(
                        "El producto '" + producto.getNombre() + "' no esta en el carrito"
                )
        );

        item.setCantidad(nuevaCantidad);
    }

    public void vaciar() {
        items.clear();
    }

    public double calcularTotal() {

        if (items.isEmpty()) {
            throw new CarritoVacioException("El carrito está vacio");
        }

        double total = 0;

        for (ItemCarrito item : items) {
            total += item.calcularSubtotal();
        }

        return total;
    }

    private Optional<ItemCarrito> buscarItem(Producto producto) {

        return items.stream()
                .filter(item -> item.getProducto().getCodigo() == producto.getCodigo())
                .findFirst();
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "cliente=" + cliente.getEmail() +
                ", items=" + items +
                '}';
    }
}
