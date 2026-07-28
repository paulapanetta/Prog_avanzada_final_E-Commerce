package menu;

import java.util.Scanner;

public class MenuEnvios {

    private final Scanner scanner;

    public MenuEnvios(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("ENVIOS");
            System.out.println("1. Generar envio para una orden");
            System.out.println("2. Buscar envio por codigo de seguimiento");
            System.out.println("3. Listar envios");
            System.out.println("4. Actualizar estado de entrega");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> generarEnvio();
                    case 2 -> buscarPorCodigo();
                    case 3 -> listarEnvios();
                    case 4 -> actualizarEstado();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void generarEnvio() {
        System.out.print("ID de la orden: ");
        int ordenId = leerEntero();

        System.out.println("Tipo de envio:");
        System.out.println("1. Retiro en sucursal   2. Estandar   3. Express   4. Internacional");
        System.out.print("Opción: ");
        int tipo = leerEntero();

        System.out.print("Direccion: ");
        String direccion = scanner.nextLine().trim();
        System.out.print("Provincia: ");
        String provincia = scanner.nextLine().trim();
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine().trim();
        System.out.print("Codigo postal: ");
        String codigoPostal = scanner.nextLine().trim();

        System.out.println("Envio generado correctamente.");
    }

    private void buscarPorCodigo() {
        System.out.print("Codigo de seguimiento: ");
        String codigo = scanner.nextLine().trim();
    }

    private void listarEnvios() {
        System.out.println("Listado de envios");
    }

    private void actualizarEstado() {
        System.out.print("Codigo de seguimiento: ");
        String codigo = scanner.nextLine().trim();
        System.out.println("Nuevo estado (PENDIENTE, PREPARACION, DESPACHADO,");
        System.out.println("EN_TRANSITO, ENTREGADO, DEMORADO, CANCELADO): ");
        String estado = scanner.nextLine().trim();
        System.out.println("Estado de envio actualizado.");
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