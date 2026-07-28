package menu;

import java.util.Scanner;

public class MenuOrdenes {

    private final Scanner scanner;

    public MenuOrdenes(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("ORDENES DE COMPRA");
            System.out.println("1. Generar orden desde el carrito");
            System.out.println("2. Buscar orden por ID");
            System.out.println("3. Buscar orden por numero");
            System.out.println("4. Listar ordenes");
            System.out.println("5. Listar ordenes por cliente");
            System.out.println("6. Cambiar estado de la orden");
            System.out.println("7. Cancelar orden");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> generarOrden();
                    case 2 -> buscarPorId();
                    case 3 -> buscarPorNumero();
                    case 4 -> listarOrdenes();
                    case 5 -> listarPorCliente();
                    case 6 -> cambiarEstado();
                    case 7 -> cancelarOrden();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void generarOrden() {
        System.out.println("Orden generada correctamente. Número: (ejemplo)");
    }

    private void buscarPorId() {
        System.out.print("ID de la orden: ");
        int id = leerEntero();
    }

    private void buscarPorNumero() {
        System.out.print("Número de la orden: ");
        String numero = scanner.nextLine().trim();
    }

    private void listarOrdenes() {
        System.out.println("Listado de ordenes");
    }

    private void listarPorCliente() {
        System.out.print("ID del cliente: ");
        int clienteId = leerEntero();
    }

    private void cambiarEstado() {
        System.out.print("ID de la orden: ");
        int id = leerEntero();
        System.out.println("Nuevo estado (CREADA, PENDIENTE_PAGO, PAGADA, PREPARACION,");
        System.out.println("DESPACHADA, EN_TRANSITO, ENTREGADA, CANCELADA, DEVUELTA): ");
        String estado = scanner.nextLine().trim();
        System.out.println("Estado actualizado correctamente.");
    }

    private void cancelarOrden() {
        System.out.print("ID de la orden: ");
        int id = leerEntero();
        System.out.println("Orden cancelada.");
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
