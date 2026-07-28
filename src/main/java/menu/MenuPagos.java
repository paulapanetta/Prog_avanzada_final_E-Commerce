package menu;

import java.util.Scanner;

public class MenuPagos {

    private final Scanner scanner;

    public MenuPagos(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("PROCESAMIENTO DE PAGOS");
            System.out.println("1. Procesar pago de una orden");
            System.out.println("2. Consultar pago por ID");
            System.out.println("3. Listar pagos de una orden");
            System.out.println("4. Listar todos los pagos");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> procesarPago();
                    case 2 -> consultarPago();
                    case 3 -> listarPagosPorOrden();
                    case 4 -> listarTodosLosPagos();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void procesarPago() {
        System.out.print("ID de la orden a pagar: ");
        int ordenId = leerEntero();

        System.out.println("Metodo de pago:");
        System.out.println("1. Tarjeta de credito");
        System.out.println("2. Tarjeta de debito");
        System.out.println("3. Transferencia bancaria");
        System.out.println("4. Billetera virtual");
        System.out.println("5. Pago en efectivo");
        System.out.print("Opcion: ");
        int metodo = leerEntero();

        System.out.println("Pago procesado correctamente.");
    }

    private void consultarPago() {
        System.out.print("ID del pago: ");
        int id = leerEntero();
    }

    private void listarPagosPorOrden() {
        System.out.print("ID de la orden: ");
        int ordenId = leerEntero();
    }

    private void listarTodosLosPagos() {
        System.out.println("Listado de pagos");
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
