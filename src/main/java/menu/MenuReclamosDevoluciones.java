package menu;

import java.util.Scanner;

public class MenuReclamosDevoluciones {

    private final Scanner scanner;

    public MenuReclamosDevoluciones(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("RECLAMOS Y DEVOLUCIONES");
            System.out.println("1. Generar reclamo");
            System.out.println("2. Consultar reclamo");
            System.out.println("3. Cambiar estado de reclamo");
            System.out.println("4. Registrar devolucion");
            System.out.println("5. Consultar devolucion");
            System.out.println("6. Calificar producto");
            System.out.println("7. Consultar valoraciones de un producto");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> generarReclamo();
                    case 2 -> consultarReclamo();
                    case 3 -> cambiarEstadoReclamo();
                    case 4 -> registrarDevolucion();
                    case 5 -> consultarDevolucion();
                    case 6 -> calificarProducto();
                    case 7 -> consultarValoraciones();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void generarReclamo() {
        System.out.print("ID del cliente: ");
        int clienteId = leerEntero();
        System.out.print("ID de la orden asociada: ");
        int ordenId = leerEntero();
        System.out.print("Motivo: ");
        String motivo = scanner.nextLine().trim();
        System.out.println("Reclamo generado correctamente.");
    }

    private void consultarReclamo() {
        System.out.print("Numero de reclamo: ");
        int numero = leerEntero();
    }

    private void cambiarEstadoReclamo() {
        System.out.print("Número de reclamo: ");
        int numero = leerEntero();
        System.out.println("Nuevo estado (ABIERTO, EN_REVISION, RESUELTO, RECHAZADO): ");
        String estado = scanner.nextLine().trim();
        System.out.println("Estado actualizado correctamente.");
    }

    private void registrarDevolucion() {
        System.out.print("ID del cliente: ");
        int clienteId = leerEntero();
        System.out.print("Codigo del producto: ");
        String codigoProducto = scanner.nextLine().trim();
        System.out.print("Motivo: ");
        String motivo = scanner.nextLine().trim();
        System.out.println("Devolucion registrada correctamente.");
    }

    private void consultarDevolucion() {
        System.out.print("ID de la devolucion: ");
        int id = leerEntero();
    }

    private void calificarProducto() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Puntuacion (1 a 5): ");
        int puntuacion = leerEntero();
        System.out.print("Comentario: ");
        String comentario = scanner.nextLine().trim();
        System.out.println("Calificacion registrada correctamente.");
    }

    private void consultarValoraciones() {
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
