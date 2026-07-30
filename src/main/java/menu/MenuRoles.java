package menu;

import controller.UsuarioController;
import model.usuario.Rol;
import model.usuario.Usuario;

import java.util.Scanner;
import java.util.Set;

public class MenuRoles {

    private final Scanner scanner;
    private final UsuarioController usuarioController;

    public MenuRoles(Scanner scanner, UsuarioController usuarioController) {
        this.scanner = scanner;
        this.usuarioController = usuarioController;
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
        for (Rol rol : Rol.values()) {
            System.out.println("- " + rol);
        }
    }

    private void verPermisos() {
        System.out.print("Rol (CLIENTE, ADMINISTRADOR, OPERADOR_VENTAS, RESPONSABLE_LOGISTICA): ");
        String texto = scanner.nextLine().trim().toUpperCase();

        Rol rol;
        try {
            rol = Rol.valueOf(texto);
        } catch (IllegalArgumentException e) {
            System.out.println("Rol invalido.");
            return;
        }

        Set<Integer> opcionesPermitidas = MenuPrincipal.PERMISOS.getOrDefault(rol, Set.of());

        if (opcionesPermitidas.isEmpty()) {
            System.out.println("El rol " + rol + " no tiene permisos asignados.");
            return;
        }

        System.out.println("Permisos de " + rol);
        opcionesPermitidas.stream()
                .sorted()
                .forEach(opcion -> System.out.println(
                        opcion + ". " + MenuPrincipal.NOMBRES_OPCION.get(opcion)));
    }

    private void asignarRol() {
        System.out.print("ID del usuario: ");
        int id = leerEntero();

        Usuario usuario = usuarioController.buscarPorId(id);
        System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido()
                + " | Rol actual: " + usuario.getRol());

        System.out.print("Nuevo rol (CLIENTE, ADMINISTRADOR, OPERADOR_VENTAS, RESPONSABLE_LOGISTICA): ");
        String texto = scanner.nextLine().trim().toUpperCase();

        Rol nuevoRol;
        try {
            nuevoRol = Rol.valueOf(texto);
        } catch (IllegalArgumentException e) {
            System.out.println("Rol invalido.");
            return;
        }

        usuarioController.cambiarRol(id, nuevoRol);

        System.out.println("Rol asignado correctamente. " + usuario.getEmail() + " ahora es " + nuevoRol);
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