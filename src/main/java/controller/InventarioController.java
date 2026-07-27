package controller;

import dao.InventarioDAO;
import model.inventario.Inventario;

import exceptions.DatosInvalidosException;
import exceptions.ProductoNoEncontradoException;

public class InventarioController {

    private InventarioDAO inventarioDAO;


    public InventarioController(InventarioDAO inventarioDAO) {
        this.inventarioDAO = inventarioDAO;
    }


    public Inventario buscarPorProducto(int codigoProducto) {

        Inventario inventario =
                inventarioDAO.buscarPorProducto(codigoProducto);

        if (inventario == null) {
            throw new ProductoNoEncontradoException(
                    "No existe inventario para el producto con codigo "
                            + codigoProducto
            );
        }

        return inventario;
    }


    public void ingresarStock(int codigoProducto, int cantidad) {

        Inventario inventario =
                buscarPorProducto(codigoProducto);

        inventario.ingresarStock(cantidad);

        inventarioDAO.actualizar(inventario);
    }


    public void egresarStock(int codigoProducto, int cantidad) {

        Inventario inventario =
                buscarPorProducto(codigoProducto);

        inventario.egresarStock(cantidad);

        inventarioDAO.actualizar(inventario);
    }


    public void ajustarStock(int codigoProducto, int cantidad) {

        Inventario inventario =
                buscarPorProducto(codigoProducto);

        inventario.ajustarStock(cantidad);

        inventarioDAO.actualizar(inventario);
    }


    public int consultarStock(int codigoProducto) {

        Inventario inventario =
                buscarPorProducto(codigoProducto);

        return inventario.getStockActual();
    }
}
