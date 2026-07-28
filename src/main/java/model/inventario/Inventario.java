package model.inventario;

import exceptions.ProductoDuplicadoException;
import exceptions.ProductoNoEncontradoException;
import exceptions.StockNegativoException;
import model.interfaces.Mostrable;

import java.util.ArrayList;
import java.util.List;

public class Inventario implements Mostrable {

    private int id;
    private List<StockProducto> stocks;
    private List<MovimientoStock> movimientos;


    public Inventario() {

        stocks = new ArrayList<>();
        movimientos = new ArrayList<>();
    }

    public Inventario(int id) {

        this.id = id;
        stocks = new ArrayList<>();
        movimientos = new ArrayList<>();
    }


    public int getId() {return id;}

    public List<StockProducto> getStocks() {return stocks;}

    public List<MovimientoStock> getMovimientos() {return movimientos;}

    public void agregarProducto(StockProducto stockProducto){

        StockProducto existente =
                buscarStock(stockProducto.getProducto().getCodigo());

        if(existente != null){
            throw new ProductoDuplicadoException("El producto ya existe en el inventario.");
        }

        stocks.add(stockProducto);
    }


    public StockProducto buscarStock(
            int codigoProducto
    ){

        for(StockProducto stock : stocks){

            if(stock.getProducto()
                    .getCodigo() == codigoProducto){

                return stock;
            }
        }

        return null;
    }

    public void ingresarStock(
            int codigoProducto,
            int cantidad
    ){

        validarCantidad(cantidad);

        StockProducto stock =
                buscarStock(codigoProducto);

        if(stock == null){

            throw new ProductoNoEncontradoException("El producto no existe en inventario.");
        }

        stock.sumar(cantidad);


        registrarMovimiento(
                codigoProducto,
                TipoMovimiento.INGRESO,
                cantidad,
                "Ingreso de stock"
        );
    }

    public void egresarStock(
            int codigoProducto,
            int cantidad
        ){

        StockProducto stock = buscarStock(codigoProducto);

        if(stock == null){
            throw new ProductoNoEncontradoException("El producto no existe en inventario.");
        }

        stock.restar(cantidad);

        registrarMovimiento(
                codigoProducto,
                TipoMovimiento.EGRESO,
                cantidad,
                "Egreso de stock"
        );
    }

    public void ajustarStock(
            int codigoProducto,
            int nuevoStock
    ){

        if(nuevoStock < 0){

            throw new StockNegativoException("El stock no puede ser negativo.");
        }

        StockProducto stock = buscarStock(codigoProducto);

        if(stock == null){
            throw new ProductoNoEncontradoException("El producto no existe en inventario.");
        }

        stock.ajustarCantidad(nuevoStock);

        registrarMovimiento(
                codigoProducto,
                TipoMovimiento.AJUSTE,
                nuevoStock,
                "Ajuste manual"
        );
    }

    private void validarCantidad(int cantidad){

        if(cantidad <= 0){

            throw new StockNegativoException("La cantidad debe ser mayor a cero.");
        }
    }

    private void registrarMovimiento(
            int codigoProducto,
            TipoMovimiento tipo,
            int cantidad,
            String descripcion
    ){

        movimientos.add(
                new MovimientoStock(
                        codigoProducto,
                        tipo,
                        cantidad,
                        descripcion
                )
        );
    }

    public int consultarStock(int codigoProducto){

        StockProducto stock = buscarStock(codigoProducto);

        if(stock == null){
            throw new ProductoNoEncontradoException("El producto no existe en inventario.");
        }

        return stock.getCantidad();
    }

    @Override
    public void mostrarInformacion(){

        System.out.println(this);
    }


    @Override
    public String toString() {

        return "Inventario{" +
                "id=" + id +
                ", stocks=" + stocks +
                ", movimientos=" + movimientos +
                '}';
    }

}
