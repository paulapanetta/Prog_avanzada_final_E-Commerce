package menu;

import controller.EnvioController;
import model.envio.EstadoEnvio;
import model.envio.Envio;

import java.util.List;
import java.util.Scanner;

public class MenuEnvios {

    private final Scanner scanner;
    private final EnvioController envioController;

    public MenuEnvios(Scanner scanner, EnvioController envioController) {
        this.scanner = scanner;
        this.envioController = envioController;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("ENVIOS");
            System.out.println("1. Buscar envio por ID");
            System.out.println("2. Buscar envio por codigo de seguimiento");
            System.out.println("3. Listar envios");
            System.out.println("4. Despachar envio");
            System.out.println("5. Actualizar estado de envio");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> buscarPorId();
                    case 2 -> buscarPorCodigo();
                    case 3 -> listarEnvios();
                    case 4 -> despacharEnvio();
                    case 5 -> actualizarEstado();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void buscarPorId() {
        System.out.print("ID del envio: ");
        int id = leerEntero();

        Envio envio = envioController.buscarPorId(id);

        if (envio == null) {
            System.out.println("No existe un envio con ese ID.");
            return;
        }

        envio.mostrarInformacion();
    }

    private void buscarPorCodigo() {
        System.out.print("Codigo de seguimiento: ");
        String codigo = scanner.nextLine().trim();

        Envio envio = envioController.buscarPorCodigoSeguimiento(codigo);

        if (envio == null) {
            System.out.println("No existe un envio con ese codigo de seguimiento.");
            return;
        }

        envio.mostrarInformacion();
    }

    private void listarEnvios() {
        List<Envio> envios = envioController.listar();

        if (envios.isEmpty()) {
            System.out.println("No hay envios cargados.");
            return;
        }

        System.out.println("Listado de envios");
        envios.forEach(Envio::mostrarInformacion);
    }

    private void despacharEnvio() {
        System.out.print("ID del envio a despachar: ");
        int id = leerEntero();

        envioController.despachar(id);

        System.out.println("Envio despachado correctamente.");
    }

    private void actualizarEstado() {
        System.out.print("ID del envio: ");
        int id = leerEntero();

        System.out.println("Nuevo estado (PENDIENTE, PREPARACION, DESPACHADO,");
        System.out.println("EN_TRANSITO, ENTREGADO, DEMORADO, CANCELADO): ");
        String estadoTexto = scanner.nextLine().trim().toUpperCase();

        EstadoEnvio nuevoEstado;
        try {
            nuevoEstado = EstadoEnvio.valueOf(estadoTexto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de envio invalido.");
        }

        envioController.actualizarEstado(id, nuevoEstado);

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