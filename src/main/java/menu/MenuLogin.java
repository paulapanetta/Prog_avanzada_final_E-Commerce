package menu;

import model.usuario.Usuario;
import model.usuario.EstadoUsuario;
import model.usuario.Cliente;
import model.usuario.Administrador;
import model.usuario.OperadorVentas;
import model.usuario.ResponsableLogistica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuLogin {

    private final Scanner scanner;
    private final Map<String, Usuario> usuariosPrueba = new HashMap<>();

    public MenuLogin(Scanner scanner) {
        this.scanner = scanner;
        cargarUsuariosPrueba();
    }

    private void cargarUsuariosPrueba() {
        usuariosPrueba.put("cliente@mail.com", new Cliente(
                "Juan", "Pérez", "cliente@mail.com", "1234", LocalDate.now(), EstadoUsuario.ACTIVO));
        usuariosPrueba.put("admin@mail.com", new Administrador(
                "Ana", "Gómez", "admin@mail.com", "1234", LocalDate.now(), EstadoUsuario.ACTIVO));
        usuariosPrueba.put("ventas@mail.com", new OperadorVentas(
                "Luis", "Díaz", "ventas@mail.com", "1234", LocalDate.now(), EstadoUsuario.ACTIVO));
        usuariosPrueba.put("logistica@mail.com", new ResponsableLogistica(
                "Sol", "Ruiz", "logistica@mail.com", "1234", LocalDate.now(), EstadoUsuario.ACTIVO));
    }

    private Usuario autenticarModoPrueba(String email, String password) {
        Usuario u = usuariosPrueba.get(email);
        if (u != null && u.getPassword().equals(password) && u.getEstado() == EstadoUsuario.ACTIVO) {
            return u;
        }
        return null;
    }
    public Usuario iniciarSesion() {
        while (true) {
            System.out.println();
            System.out.println("INICIAR SESION");
            System.out.print("Email (vacio para salir): ");
            String email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                return null;
            }

            System.out.print("Contraseña: ");
            String password = scanner.nextLine().trim();

            Usuario usuario = autenticarModoPrueba(email, password);

            if (usuario == null) {
                System.out.println("Email o contraseña incorrectos, o usuario inactivo. Intente nuevamente.");
                continue;
            }

            System.out.println("Bienvenido/a " + usuario.getNombre() + " (" + usuario.getRol() + ")");
            return usuario;
        }
    }
}