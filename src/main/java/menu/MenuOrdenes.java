package menu;

import controller.EnvioController;
import controller.OrdenController;
import model.envio.Envio;
import model.envio.TipoEnvio;
import model.orden.EstadoOrden;
import model.orden.Orden;
import model.usuario.Cliente;
import model.usuario.Usuario;

import exceptions.PermisoDenegadoException;

import java.util.List;
import java.util.Scanner;

public class MenuOrdenes {

    private final Scanner scanner;
    private final OrdenController ordenController;
    private final EnvioController envioController;
    private final Usuario usuarioLogueado;

    public MenuOrdenes(Scanner scanner, OrdenController ordenController,
                       EnvioController envioController, Usuario usuarioLogueado) {
        this.scanner = scanner;
        this.ordenController = ordenController;
        this.envioController = envioController;
        this.usuarioLogueado = usuarioLogueado;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("ORDENES DE COMPRA");
            System.out.println("1. Generar orden desde el carrito");
            System.out.println("2. Buscar orden por ID");
            System.out.println("3. Buscar orden por numero");
            System.out.println("4. Listar ordenes");
            System.out.println("5. Listar mis ordenes (cliente)");
            System.out.println("6. Confirmar pago de una orden");
            System.out.println("7. Cambiar estado de la orden");
            System.out.println("8. Cancelar orden");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> generarOrden();
                    case 2 -> buscarPorId();
                    case 3 -> buscarPorNumero();
                    case 4 -> listarOrdenes();
                    case 5 -> listarMisOrdenes();
                    case 6 -> confirmarPago();
                    case 7 -> cambiarEstado();
                    case 8 -> cancelarOrden();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void generarOrden() {
        if (!(usuarioLogueado instanceof Cliente cliente)) {
            throw new PermisoDenegadoException("Solo un cliente puede generar una orden desde su carrito.");
        }

        System.out.println("Datos de envio para la orden:");
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine().trim();
        System.out.print("Provincia: ");
        String provincia = scanner.nextLine().trim();
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine().trim();
        System.out.print("Codigo postal: ");
        String codigoPostal = scanner.nextLine().trim();

        System.out.println("Tipo de envio: 1. Retiro en sucursal  2. Estandar  3. Expres  4. Internacional");
        System.out.print("Opcion: ");
        int tipoOpcion = leerEntero();

        TipoEnvio tipoEnvio = switch (tipoOpcion) {
            case 1 -> TipoEnvio.RETIRO_SUCURSAL;
            case 2 -> TipoEnvio.ESTANDAR;
            case 3 -> TipoEnvio.EXPRES;
            case 4 -> TipoEnvio.INTERNACIONAL;
            default -> throw new IllegalArgumentException("Tipo de envio invalido.");
        };

        Envio envioTemporal = new Envio(0, direccion, provincia, ciudad, codigoPostal, tipoEnvio);

        Orden orden = ordenController.generarOrden(cliente, envioTemporal);

        envioController.crear(orden.getId(), direccion, provincia, ciudad, codigoPostal, tipoEnvio);

        System.out.println("Orden generada correctamente. Numero: " + orden.getNumero() + " | Total: $" + orden.getTotal());
    }

    private void buscarPorId() {
        System.out.print("ID de la orden: ");
        int id = leerEntero();

        Orden orden = ordenController.buscarPorId(id);
        System.out.println(orden);
    }

    private void buscarPorNumero() {
        System.out.print("Numero de la orden: ");
        String numero = scanner.nextLine().trim();

        Orden orden = ordenController.buscarPorNumero(numero);
        System.out.println(orden);
    }

    private void listarOrdenes() {
        List<Orden> ordenes = ordenController.listar();

        if (ordenes.isEmpty()) {
            System.out.println("No hay ordenes cargadas.");
            return;
        }

        System.out.println("Listado de ordenes");
        ordenes.forEach(Orden::mostrarInformacion);
    }

    private void listarMisOrdenes() {
        if (!(usuarioLogueado instanceof Cliente cliente)) {
            throw new PermisoDenegadoException("Solo un cliente puede consultar sus propias ordenes.");
        }

        List<Orden> ordenes = ordenController.listarPorCliente(cliente);

        if (ordenes.isEmpty()) {
            System.out.println("No tenes ordenes registradas.");
            return;
        }

        ordenes.forEach(Orden::mostrarInformacion);
    }

    private void confirmarPago() {
        System.out.print("ID de la orden: ");
        int id = leerEntero();

        ordenController.confirmarPago(id);

        System.out.println("Pago confirmado y orden actualizada a PAGADA.");
    }

    private void cambiarEstado() {
        System.out.print("ID de la orden: ");
        int id = leerEntero();

        System.out.println("Nuevo estado (CREADA, PENDIENTE_PAGO, PAGADA, PREPARACION,");
        System.out.println("DESPACHADA, EN_TRANSITO, ENTREGADA, CANCELADA, DEVUELTA): ");
        String estadoTexto = scanner.nextLine().trim().toUpperCase();

        EstadoOrden nuevoEstado;
        try {
            nuevoEstado = EstadoOrden.valueOf(estadoTexto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado de orden invalido.");
        }

        ordenController.cambiarEstado(id, nuevoEstado);

        System.out.println("Estado actualizado correctamente.");
    }

    private void cancelarOrden() {
        System.out.print("ID de la orden: ");
        int id = leerEntero();

        ordenController.cancelar(id);

        System.out.println("Orden cancelada.");
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