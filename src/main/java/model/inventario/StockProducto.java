package model.inventario;

import exceptions.StockInsuficienteException;
import exceptions.StockNegativoException;
import model.producto.Producto;

public class StockProducto {

    private Producto producto;
    private int cantidad;


    public StockProducto(Producto producto, int cantidad) {

        if(cantidad < 0){
            throw new StockNegativoException("El stock no puede ser negativo.");
        }

        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void sumar(int cantidad) {

        if(cantidad <= 0){
            throw new StockNegativoException("La cantidad debe ser mayor a cero.");
        }

        this.cantidad += cantidad;
    }

    public void restar(int cantidad) {

        if(cantidad <= 0){
            throw new StockNegativoException("La cantidad debe ser mayor a cero.");
        }

        if(cantidad > this.cantidad){
            throw new StockInsuficienteException("No hay stock suficiente.");
        }

        this.cantidad -= cantidad;
    }

    public void ajustarCantidad(int cantidad) {

        if(cantidad < 0){
            throw new StockNegativoException("El stock no puede ser negativo.");
        }

        this.cantidad = cantidad;
    }


    @Override
    public String toString() {

        return "StockProducto{" +
                "producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                '}';
    }
}