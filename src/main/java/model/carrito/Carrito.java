package model.carrito;

import model.interfaces.Calculable;
import model.inventario.StockProducto;
import model.producto.Producto;
import model.inventario.Inventario;
import model.usuario.Cliente;

import exceptions.CarritoVacioException;
import exceptions.DatosInvalidosException;
import exceptions.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrito implements Calculable {

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

    public void setId(int id){
        this.id = id;
    }

    public Cliente getCliente() { return cliente; }

    public List<ItemCarrito> getItems() { return items; }


    public void agregarProducto(StockProducto stockProducto, int cantidad) {

        if (cantidad <= 0) {
            throw new DatosInvalidosException("La cantidad tiene que ser mayor a cero");
        }

        if (stockProducto.getCantidad() <= 0) {
            throw new StockInsuficienteException("El producto no tiene stock disponible");
        }

        Optional<ItemCarrito> existente = buscarItem(stockProducto.getProducto());

        int cantidadTotal = cantidad + existente.map(ItemCarrito::getCantidad).orElse(0);

        if (cantidadTotal > stockProducto.getCantidad()) {
            throw new StockInsuficienteException("No hay stock suficiente de '" + stockProducto.getProducto().getNombre() + "'"
            );
        }

        if (existente.isPresent()) {
            existente.get().setCantidad(cantidadTotal);
        } else {
            items.add(new ItemCarrito(stockProducto.getProducto(), cantidad));
        }
    }

    public void eliminarProducto(Producto producto) {
        items.removeIf(item -> item.getProducto().getCodigo() == producto.getCodigo());
    }

    public void modificarCantidad(StockProducto stockProducto, int nuevaCantidad) {

        if (nuevaCantidad <= 0) {
            throw new DatosInvalidosException("La cantidad tiene que ser mayor a cero");
        }

        if (nuevaCantidad > stockProducto.getCantidad()) {
            throw new StockInsuficienteException("No hay stock suficiente de '" + stockProducto.getProducto().getNombre() + "'"
            );
        }

        ItemCarrito item = buscarItem(stockProducto.getProducto()).orElseThrow(() ->
                new DatosInvalidosException("El producto no está en el carrito"));

        item.setCantidad(nuevaCantidad);
    }

    public void vaciar() {
        items.clear();
    }

    @Override
    public double calcularPrecioFinal() {

        if (items.isEmpty()) {
            throw new CarritoVacioException(
                    "El carrito está vacío"
            );
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
