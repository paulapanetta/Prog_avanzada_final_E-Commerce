import menu.MenuLogin;
import menu.MenuPrincipal;
import model.producto.EstadoCategoria;
import model.usuario.Usuario;
import database.DatabaseManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println(EstadoCategoria.ACTIVA.name());
        System.out.println(EstadoCategoria.INACTIVA.name());

        DatabaseManager.inicializarBase();

        Scanner scanner = new Scanner(System.in);

        MenuLogin menuLogin = new MenuLogin(scanner);
        Usuario usuario = menuLogin.iniciarSesion();

        if (usuario == null) {
            System.out.println("No se inicio sesion. Cerrando el sistema.");
            scanner.close();
            return;
        }

        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner, usuario);
        menuPrincipal.iniciar();

        DatabaseManager.cerrarConexion();
        scanner.close();
    }
}