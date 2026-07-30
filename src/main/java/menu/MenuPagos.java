package menu;

import controller.PagoController;
import model.pago.Pago;
import strategy.PagoBilleteraVirtual;
import strategy.PagoEfectivo;
import strategy.PagoTarjetaCredito;
import strategy.PagoTarjetaDebito;
import strategy.PagoTransferencia;
import strategy.ProcesadorPago;

import java.util.List;
import java.util.Scanner;

public class MenuPagos {

    private final Scanner scanner;
    private final PagoController pagoController;

    public MenuPagos(Scanner scanner, PagoController pagoController) {
        this.scanner = scanner;
        this.pagoController = pagoController;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("PROCESAMIENTO DE PAGOS");
            System.out.println("1. Procesar pago de una orden");
            System.out.println("2. Consultar pago por ID");
            System.out.println("3. Listar pagos de una orden");
            System.out.println("4. Listar todos los pagos");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> procesarPago();
                    case 2 -> consultarPago();
                    case 3 -> listarPagosPorOrden();
                    case 4 -> listarTodosLosPagos();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void procesarPago() {
        System.out.print("ID de la orden a pagar: ");
        int ordenId = leerEntero();

        System.out.println("Metodo de pago:");
        System.out.println("1. Tarjeta de credito");
        System.out.println("2. Tarjeta de debito");
        System.out.println("3. Transferencia bancaria");
        System.out.println("4. Billetera virtual");
        System.out.println("5. Pago contra entrega (efectivo)");
        System.out.print("Opcion: ");
        int opcionMetodo = leerEntero();

        ProcesadorPago metodo = switch (opcionMetodo) {
            case 1 -> new PagoTarjetaCredito();
            case 2 -> new PagoTarjetaDebito();
            case 3 -> new PagoTransferencia();
            case 4 -> new PagoBilleteraVirtual();
            case 5 -> new PagoEfectivo();
            default -> throw new IllegalArgumentException("Metodo de pago invalido.");
        };

        Pago pago = pagoController.procesarPago(ordenId, metodo);

        System.out.println("Pago procesado correctamente. Estado: " + pago.getEstado());
    }

    private void consultarPago() {
        System.out.print("ID del pago: ");
        int id = leerEntero();

        Pago pago = pagoController.buscarPorId(id);
        System.out.println(pago);
    }

    private void listarPagosPorOrden() {
        System.out.print("ID de la orden: ");
        int ordenId = leerEntero();

        List<Pago> pagos = pagoController.listarPorOrden(ordenId);

        if (pagos.isEmpty()) {
            System.out.println("Esa orden no tiene pagos registrados.");
            return;
        }

        pagos.forEach(Pago::mostrarInformacion);
    }

    private void listarTodosLosPagos() {
        List<Pago> pagos = pagoController.listar();

        if (pagos.isEmpty()) {
            System.out.println("No hay pagos cargados.");
            return;
        }

        System.out.println("Listado de pagos");
        pagos.forEach(Pago::mostrarInformacion);
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