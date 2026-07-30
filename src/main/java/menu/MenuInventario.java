package menu;

import controller.InventarioController;
import model.inventario.Inventario;
import model.inventario.MovimientoStock;
import model.inventario.StockProducto;

import java.util.Scanner;

public class MenuInventario {

    private final Scanner scanner;
    private final InventarioController inventarioController;

    public MenuInventario(Scanner scanner, InventarioController inventarioController) {
        this.scanner = scanner;
        this.inventarioController = inventarioController;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("INVENTARIO");
            System.out.println("1. Ingreso de stock");
            System.out.println("2. Egreso de stock");
            System.out.println("3. Ajuste de stock");
            System.out.println("4. Consultar stock de un producto");
            System.out.println("5. Historial de movimientos");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> ingresoStock();
                    case 2 -> egresoStock();
                    case 3 -> ajusteStock();
                    case 4 -> consultarStock();
                    case 5 -> historialMovimientos();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void ingresoStock() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Cantidad a ingresar: ");
        int cantidad = leerEntero();

        inventarioController.ingresarStock(codigo, cantidad);

        System.out.println("Stock actualizado correctamente.");
    }

    private void egresoStock() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Cantidad a egresar: ");
        int cantidad = leerEntero();

        inventarioController.egresarStock(codigo, cantidad);

        System.out.println("Stock actualizado correctamente.");
    }

    private void ajusteStock() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Nuevo stock: ");
        int nuevoStock = leerEntero();

        inventarioController.ajustarStock(codigo, nuevoStock);

        System.out.println("Ajuste realizado correctamente.");
    }

    private void consultarStock() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();

        StockProducto stock = inventarioController.buscarStock(codigo);
        System.out.println(stock);
    }

    private void historialMovimientos() {
        Inventario inventario = inventarioController.obtenerInventario();

        if (inventario.getMovimientos().isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }

        System.out.println("Historial de movimientos");
        for (MovimientoStock movimiento : inventario.getMovimientos()) {
            System.out.println(movimiento);
        }
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido, se usara -1.");
            return -1;
        }
    }
}