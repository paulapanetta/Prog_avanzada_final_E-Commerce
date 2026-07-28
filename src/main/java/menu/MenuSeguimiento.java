package menu;

import java.util.Scanner;

public class MenuSeguimiento {

    private final Scanner scanner;

    public MenuSeguimiento(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("SEGUIMIENTO DE PEDIDOS");
            System.out.println("1. Consultar pedido");
            System.out.println("2. Consultar envio");
            System.out.println("3. Consultar historial de estados");
            System.out.println("4. Consultar fecha estimada de entrega");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> consultarPedido();
                    case 2 -> consultarEnvio();
                    case 3 -> consultarHistorial();
                    case 4 -> consultarFechaEstimada();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void consultarPedido() {
        System.out.print("Numero de orden: ");
        String numero = scanner.nextLine().trim();
    }

    private void consultarEnvio() {
        System.out.print("Codigo de seguimiento: ");
        String codigo = scanner.nextLine().trim();
    }

    private void consultarHistorial() {
        System.out.print("Numero de orden: ");
        String numero = scanner.nextLine().trim();
    }

    private void consultarFechaEstimada() {
        System.out.print("Codigo de seguimiento: ");
        String codigo = scanner.nextLine().trim();
        System.out.println("Fecha estimada de entrega:");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
