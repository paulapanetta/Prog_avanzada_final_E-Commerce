package model.carrito;

import model.producto.Producto;

import exceptions.DatosInvalidosException;

public class ItemCarrito {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;


    public ItemCarrito(Producto producto, int cantidad) {

        if (producto == null) {
            throw new DatosInvalidosException(
                    "El item tiene que tener un producto asociado"
            );
        }

        if (cantidad <= 0) {
            throw new DatosInvalidosException(
                    "La cantidad tiene que ser mayor a cero"
            );
        }

        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.calcularPrecioFinal();
    }


    public Producto getProducto() { return producto; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) {

        if (cantidad <= 0) {
            throw new DatosInvalidosException(
                    "La cantidad tiene que ser mayor a cero"
            );
        }

        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() { return precioUnitario; }


    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public String toString() {
        return "ItemCarrito{" +
                "producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + calcularSubtotal() +
                '}';
    }
}
