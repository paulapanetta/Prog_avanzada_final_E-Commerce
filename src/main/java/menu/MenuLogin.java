package menu;

import dao.UsuarioDAO;
import factory.DAOFactory;
import factory.SQLiteDAOFactory;
import model.usuario.EstadoUsuario;
import model.usuario.Usuario;

import java.util.Scanner;

public class MenuLogin {

    private final Scanner scanner;
    private final UsuarioDAO usuarioDAO;


    public MenuLogin(Scanner scanner) {
        this.scanner = scanner;

        DAOFactory factory = new SQLiteDAOFactory();
        this.usuarioDAO = factory.crearUsuarioDAO();

    }


    public Usuario iniciarSesion() {

        while (true) {

            System.out.println();
            System.out.println("INICIAR SESION");

            System.out.print("Email (vacio para salir): ");
            String email = scanner.nextLine().trim();

            if(email.isEmpty()){
                return null;
            }

            System.out.print("Contraseña: ");
            String password = scanner.nextLine().trim();

            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            if(usuario != null
                    && usuario.getPassword().equals(password)
                    && usuario.getEstado() == EstadoUsuario.ACTIVO){

                System.out.println(
                        "Bienvenido/a "
                                + usuario.getNombre()
                                + " ("
                                + usuario.getRol()
                                + ")"
                );

                return usuario;
            }

            System.out.println("Email o contraseña incorrectos, o usuario inactivo. Intente nuevamente.");
        }
    }
}