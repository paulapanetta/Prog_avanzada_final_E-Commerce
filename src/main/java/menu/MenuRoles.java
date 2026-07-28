package menu;

import java.util.Scanner;

public class MenuRoles {

    private final Scanner scanner;

    public MenuRoles(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("Roles");
            System.out.println("1. Listar roles");
            System.out.println("2. Ver permisos de un rol");
            System.out.println("3. Asignar rol a usuario");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> listarRoles();
                    case 2 -> verPermisos();
                    case 3 -> asignarRol();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void listarRoles() {
        System.out.println("Roles del sistema");
        System.out.println("CLIENTE, ADMINISTRADOR, OPERADOR_VENTAS, LOGISTICA");
    }

    private void verPermisos() {
        System.out.print("Rol: ");
        String rol = scanner.nextLine().trim();
        System.out.println("Permisos de " + rol);
    }

    private void asignarRol() {
        System.out.print("ID del usuario: ");
        int id = leerEntero();
        System.out.print("Nuevo rol: ");
        String rol = scanner.nextLine().trim();
        System.out.println("Rol asignado correctamente.");
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
