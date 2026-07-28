package controller;

import dao.InventarioDAO;
import exceptions.ProductoNoEncontradoException;
import model.inventario.Inventario;
import model.inventario.StockProducto;

public class InventarioController {

    private InventarioDAO inventarioDAO;

    public InventarioController(
            InventarioDAO inventarioDAO
    ){

        this.inventarioDAO = inventarioDAO;
    }

    public Inventario obtenerInventario(){

        return inventarioDAO.obtenerInventario();
    }


    public void ingresarStock(
            int codigoProducto,
            int cantidad
    ){

        Inventario inventario =
                obtenerInventario();

        inventario.ingresarStock(
                codigoProducto,
                cantidad
        );

        inventarioDAO.actualizar(inventario);
    }

    public void egresarStock(
            int codigoProducto,
            int cantidad
    ){

        Inventario inventario =
                obtenerInventario();

        inventario.egresarStock(
                codigoProducto,
                cantidad
        );

        inventarioDAO.actualizar(inventario);
    }

    public void ajustarStock(
            int codigoProducto,
            int cantidad
    ){

        Inventario inventario =
                obtenerInventario();

        inventario.ajustarStock(
                codigoProducto,
                cantidad
        );

        inventarioDAO.actualizar(inventario);
    }


    public StockProducto buscarStock(int codigoProducto){

        Inventario inventario = inventarioDAO.obtenerInventario();

        StockProducto stock = inventario.buscarStock(codigoProducto);

        if(stock == null){

            throw new ProductoNoEncontradoException("El producto no existe en inventario.");
        }

        return stock;
    }
}