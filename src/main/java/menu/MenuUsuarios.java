package menu;

import java.util.Scanner;

public class MenuUsuarios {

    private final Scanner scanner;

    public MenuUsuarios(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("Usuarios");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Modificar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Buscar usuario");
            System.out.println("5. Listar usuarios");
            System.out.println("6. Activar usuario");
            System.out.println("7. Desactivar usuario");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> registrarUsuario();
                    case 2 -> modificarUsuario();
                    case 3 -> eliminarUsuario();
                    case 4 -> buscarUsuario();
                    case 5 -> listarUsuarios();
                    case 6 -> activarUsuario();
                    case 7 -> desactivarUsuario();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void registrarUsuario() {
        System.out.println("Registrar usuario");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        System.out.print("Rol (CLIENTE / ADMINISTRADOR / OPERADOR_VENTAS / LOGISTICA): ");
        String rol = scanner.nextLine().trim();

        System.out.println("Usuario registrado correctamente.");
    }

    private void modificarUsuario() {
        System.out.println("Modificar usuario");
        System.out.print("ID del usuario a modificar: ");
        int id = leerEntero();

        System.out.println("Usuario modificado correctamente.");
    }

    private void eliminarUsuario() {
        System.out.println("Eliminar usuario");
        System.out.print("ID del usuario a eliminar: ");
        int id = leerEntero();

        System.out.println("Usuario eliminado correctamente.");
    }

    private void buscarUsuario() {
        System.out.println("Buscar usuario");
        System.out.print("ID del usuario: ");
        int id = leerEntero();

        System.out.println("Usuario encontrado.");
    }

    private void listarUsuarios() {
        System.out.println("Listado de usuarios");
    }

    private void activarUsuario() {
        System.out.println("Activar usuario");
        System.out.print("ID del usuario: ");
        int id = leerEntero();

        System.out.println("Usuario activado.");
    }

    private void desactivarUsuario() {
        System.out.println("Desactivar usuario");
        System.out.print("ID del usuario: ");
        int id = leerEntero();

        System.out.println("Usuario desactivado.");
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
