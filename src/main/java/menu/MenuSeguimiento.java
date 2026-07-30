package menu;

import controller.EnvioController;
import controller.OrdenController;
import model.envio.Envio;
import model.orden.HistorialEstado;
import model.orden.Orden;

import java.util.Scanner;

public class MenuSeguimiento {

    private final Scanner scanner;
    private final OrdenController ordenController;
    private final EnvioController envioController;

    public MenuSeguimiento(Scanner scanner, OrdenController ordenController, EnvioController envioController) {
        this.scanner = scanner;
        this.ordenController = ordenController;
        this.envioController = envioController;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("SEGUIMIENTO DE PEDIDOS");
            System.out.println("1. Consultar pedido por numero");
            System.out.println("2. Consultar envio por codigo de seguimiento");
            System.out.println("3. Consultar historial de estados de una orden");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> consultarPedido();
                    case 2 -> consultarEnvio();
                    case 3 -> consultarHistorial();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void consultarPedido() {
        System.out.print("Numero de orden: ");
        String numero = scanner.nextLine().trim();

        Orden orden = ordenController.buscarPorNumero(numero);

        System.out.println("Estado actual: " + orden.getEstado());
        if (orden.getEnvio() != null) {
            System.out.println("Envio asociado: " + orden.getEnvio());
        }
    }

    private void consultarEnvio() {
        System.out.print("Codigo de seguimiento: ");
        String codigo = scanner.nextLine().trim();

        Envio envio = envioController.buscarPorCodigoSeguimiento(codigo);

        if (envio == null) {
            System.out.println("No existe un envio con ese codigo de seguimiento.");
            return;
        }

        System.out.println("Estado actual: " + envio.getEstado());
        envio.mostrarInformacion();
    }

    private void consultarHistorial() {
        System.out.print("Numero de orden: ");
        String numero = scanner.nextLine().trim();

        Orden orden = ordenController.buscarPorNumero(numero);

        if (orden.getHistorial().isEmpty()) {
            System.out.println("No hay historial de estados registrado para esta orden.");
            return;
        }

        System.out.println("Historial de estados");
        for (HistorialEstado historial : orden.getHistorial()) {
            System.out.println(historial);
        }
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}