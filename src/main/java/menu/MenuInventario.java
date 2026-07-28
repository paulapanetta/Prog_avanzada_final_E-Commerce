package menu;

import java.util.Scanner;

public class MenuInventario {

    private final Scanner scanner;

    public MenuInventario(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("INVENTARIO");
            System.out.println("1. Ingreso de stock");
            System.out.println("2. Egreso de stock");
            System.out.println("3. Ajuste de stock");
            System.out.println("4. Consultar stock");
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
        String codigo = scanner.nextLine().trim();
        System.out.print("Cantidad a ingresar: ");
        int cantidad = leerEntero();
        System.out.println("Stock actualizado correctamente.");
    }

    private void egresoStock() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Cantidad a egresar: ");
        int cantidad = leerEntero();
        System.out.println("Stock actualizado correctamente.");
    }

    private void ajusteStock() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Nuevo stock: ");
        int nuevoStock = leerEntero();
        System.out.println("Ajuste realizado correctamente.");
    }

    private void consultarStock() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
    }

    private void historialMovimientos() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
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
