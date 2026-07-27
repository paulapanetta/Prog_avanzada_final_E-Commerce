package model.inventario;

import exceptions.StockInsuficienteException;
import exceptions.StockNegativoException;
import model.interfaces.Mostrable;
import model.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class Inventario implements Mostrable {

    private int id;
    private Producto producto;
    private int stockActual;
    private List<MovimientoStock> movimientos;


    public Inventario(Producto producto) {

        this.producto = producto;
        this.stockActual = 0;
        this.movimientos = new ArrayList<>();
    }


    // Recuperado de la BD
    public Inventario(int id, Producto producto, int stockActual) {

        this.id = id;
        this.producto = producto;
        this.stockActual = stockActual;
        this.movimientos = new ArrayList<>();
    }


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto(){ return producto; }

    public int getStockActual(){ return stockActual; }

    public void setStockActual(int stockActual){

        if(stockActual < 0){

            throw new StockNegativoException(
                    "El stock no puede ser negativo"
            );
        }

        this.stockActual = stockActual;
    }


    public void ingresarStock(int cantidad) {

        validarCantidad(cantidad);

        stockActual += cantidad;

        registrarMovimiento(
                TipoMovimiento.INGRESO,
                cantidad,
                "Ingreso de stock"
        );
    }

    public void egresarStock(int cantidad) {

        validarCantidad(cantidad);

        if(cantidad > stockActual){

            throw new StockInsuficienteException(
                    "No hay stock suficiente para realizar la operación"
            );
        }

        stockActual -= cantidad;

        registrarMovimiento(
                TipoMovimiento.EGRESO,
                cantidad,
                "Egreso de stock"
        );
    }

    public void ajustarStock(int nuevoStock) {

        if(nuevoStock < 0){

            throw new StockNegativoException(
                    "El stock no puede ser negativo"
            );
        }

        int diferencia = nuevoStock - stockActual;

        stockActual = nuevoStock;

        registrarMovimiento(
                TipoMovimiento.AJUSTE,
                diferencia,
                "Ajuste manual de stock"
        );
    }

    public boolean tieneStock(){ return stockActual > 0; }

    public List<MovimientoStock> getMovimientos(){ return movimientos; }


    private void validarCantidad(int cantidad){

        if(cantidad <= 0){

            throw new StockNegativoException(
                    "La cantidad debe ser mayor a cero"
            );
        }
    }

    private void registrarMovimiento(
            TipoMovimiento tipo,
            int cantidad,
            String descripcion
    ){

        MovimientoStock movimiento =
                new MovimientoStock(
                        producto.getCodigo(),
                        tipo,
                        cantidad,
                        descripcion
                );


        movimientos.add(movimiento);
    }


    @Override
    public void mostrarInformacion(){

        System.out.println(this);
    }

    @Override
    public String toString(){

        return "Inventario{" +
                "id=" + id +
                ", codigoProducto=" + producto.getCodigo() +
                ", producto=" + producto.getNombre() +
                ", stockActual=" + stockActual +
                '}';
    }

}
