package menu;

import controller.UsuarioController;
import model.usuario.Administrador;
import model.usuario.Cliente;
import model.usuario.EstadoUsuario;
import model.usuario.OperadorVentas;
import model.usuario.ResponsableLogistica;
import model.usuario.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuUsuarios {

    private final Scanner scanner;
    private final UsuarioController usuarioController;

    public MenuUsuarios(Scanner scanner, UsuarioController usuarioController) {
        this.scanner = scanner;
        this.usuarioController = usuarioController;
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

        System.out.println("Rol:");
        System.out.println("1. Cliente   2. Administrador   3. Operador de ventas   4. Responsable de logistica");
        System.out.print("Opcion: ");
        int rol = leerEntero();

        Usuario usuario = switch (rol) {
            case 1 -> new Cliente(nombre, apellido, email, password, LocalDate.now(), EstadoUsuario.ACTIVO);
            case 2 -> new Administrador(nombre, apellido, email, password, LocalDate.now(), EstadoUsuario.ACTIVO);
            case 3 -> new OperadorVentas(nombre, apellido, email, password, LocalDate.now(), EstadoUsuario.ACTIVO);
            case 4 -> new ResponsableLogistica(nombre, apellido, email, password, LocalDate.now(), EstadoUsuario.ACTIVO);
            default -> throw new IllegalArgumentException("Rol invalido.");
        };

        usuarioController.registrar(usuario);

        System.out.println("Usuario registrado correctamente. ID asignado: " + usuario.getId());
    }

    private void modificarUsuario() {
        System.out.println("Modificar usuario");
        System.out.print("ID del usuario a modificar: ");
        int id = leerEntero();

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nuevo apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Nuevo email: ");
        String email = scanner.nextLine().trim();

        usuarioController.modificar(id, nombre, apellido, email);

        System.out.println("Usuario modificado correctamente.");
    }

    private void eliminarUsuario() {
        System.out.println("Eliminar usuario");
        System.out.print("ID del usuario a eliminar: ");
        int id = leerEntero();

        usuarioController.eliminar(id);

        System.out.println("Usuario eliminado correctamente.");
    }

    private void buscarUsuario() {
        System.out.println("Buscar usuario");
        System.out.print("ID del usuario: ");
        int id = leerEntero();

        Usuario usuario = usuarioController.buscarPorId(id);
        usuario.mostrarInformacion();
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioController.listar();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        System.out.println("Listado de usuarios");
        usuarios.forEach(Usuario::mostrarInformacion);
    }

    private void activarUsuario() {
        System.out.println("Activar usuario");
        System.out.print("ID del usuario: ");
        int id = leerEntero();

        usuarioController.activar(id);

        System.out.println("Usuario activado.");
    }

    private void desactivarUsuario() {
        System.out.println("Desactivar usuario");
        System.out.print("ID del usuario: ");
        int id = leerEntero();

        usuarioController.desactivar(id);

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