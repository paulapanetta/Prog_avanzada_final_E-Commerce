package menu;

import controller.CalificacionController;
import controller.DevolucionController;
import controller.OrdenController;
import controller.ProductoController;
import controller.ReclamoController;
import model.orden.Orden;
import model.postventa.Calificacion;
import model.postventa.Devolucion;
import model.postventa.Reclamo;
import model.producto.Producto;
import model.usuario.Cliente;
import model.usuario.Usuario;

import exceptions.PermisoDenegadoException;

import java.util.List;
import java.util.Scanner;

public class MenuReclamosDevoluciones {

    private final Scanner scanner;
    private final ReclamoController reclamoController;
    private final DevolucionController devolucionController;
    private final CalificacionController calificacionController;
    private final OrdenController ordenController;
    private final ProductoController productoController;
    private final Usuario usuarioLogueado;

    public MenuReclamosDevoluciones(Scanner scanner, ReclamoController reclamoController,
                                    DevolucionController devolucionController,
                                    CalificacionController calificacionController,
                                    OrdenController ordenController,
                                    ProductoController productoController,
                                    Usuario usuarioLogueado) {
        this.scanner = scanner;
        this.reclamoController = reclamoController;
        this.devolucionController = devolucionController;
        this.calificacionController = calificacionController;
        this.ordenController = ordenController;
        this.productoController = productoController;
        this.usuarioLogueado = usuarioLogueado;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("RECLAMOS Y DEVOLUCIONES");
            System.out.println("1. Generar reclamo");
            System.out.println("2. Consultar reclamo");
            System.out.println("3. Poner reclamo en revision");
            System.out.println("4. Resolver reclamo");
            System.out.println("5. Rechazar reclamo");
            System.out.println("6. Registrar devolucion");
            System.out.println("7. Aprobar devolucion");
            System.out.println("8. Rechazar devolucion");
            System.out.println("9. Calificar producto");
            System.out.println("10. Consultar valoraciones de un producto");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> generarReclamo();
                    case 2 -> consultarReclamo();
                    case 3 -> ponerEnRevision();
                    case 4 -> resolverReclamo();
                    case 5 -> rechazarReclamo();
                    case 6 -> registrarDevolucion();
                    case 7 -> aprobarDevolucion();
                    case 8 -> rechazarDevolucion();
                    case 9 -> calificarProducto();
                    case 10 -> consultarValoraciones();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private Cliente obtenerClienteLogueado() {
        if (!(usuarioLogueado instanceof Cliente cliente)) {
            throw new PermisoDenegadoException("Solo un cliente puede realizar esta accion.");
        }
        return cliente;
    }

    private void generarReclamo() {
        Cliente cliente = obtenerClienteLogueado();

        System.out.print("ID de la orden asociada: ");
        int ordenId = leerEntero();
        System.out.print("Motivo: ");
        String motivo = scanner.nextLine().trim();

        Orden orden = ordenController.buscarPorId(ordenId);
        Reclamo reclamo = reclamoController.generar(cliente, orden, motivo);

        System.out.println("Reclamo generado correctamente. ID: " + reclamo.getId());
    }

    private void consultarReclamo() {
        System.out.print("ID del reclamo: ");
        int id = leerEntero();

        Reclamo reclamo = reclamoController.buscarPorId(id);
        reclamo.mostrarInformacion();
    }

    private void ponerEnRevision() {
        System.out.print("ID del reclamo: ");
        int id = leerEntero();

        reclamoController.ponerEnRevision(id);
        System.out.println("Reclamo puesto en revision.");
    }

    private void resolverReclamo() {
        System.out.print("ID del reclamo: ");
        int id = leerEntero();

        reclamoController.resolver(id);
        System.out.println("Reclamo resuelto.");
    }

    private void rechazarReclamo() {
        System.out.print("ID del reclamo: ");
        int id = leerEntero();

        reclamoController.rechazar(id);
        System.out.println("Reclamo rechazado.");
    }

    private void registrarDevolucion() {
        Cliente cliente = obtenerClienteLogueado();

        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Motivo: ");
        String motivo = scanner.nextLine().trim();

        Producto producto = productoController.buscarPorId(codigo);
        Devolucion devolucion = devolucionController.solicitar(cliente, producto, motivo);

        System.out.println("Devolucion registrada correctamente. ID: " + devolucion.getId());
    }

    private void aprobarDevolucion() {
        System.out.print("ID de la devolucion: ");
        int id = leerEntero();

        devolucionController.aprobar(id);
        System.out.println("Devolucion aprobada.");
    }

    private void rechazarDevolucion() {
        System.out.print("ID de la devolucion: ");
        int id = leerEntero();

        devolucionController.rechazar(id);
        System.out.println("Devolucion rechazada.");
    }

    private void calificarProducto() {
        Cliente cliente = obtenerClienteLogueado();

        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Puntuacion (1 a 5): ");
        int puntuacion = leerEntero();
        System.out.print("Comentario: ");
        String comentario = scanner.nextLine().trim();

        Producto producto = productoController.buscarPorId(codigo);
        Calificacion calificacion = calificacionController.calificar(cliente, producto, puntuacion, comentario);

        System.out.println("Calificacion registrada correctamente. ID: " + calificacion.getId());
    }

    private void consultarValoraciones() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();

        Producto producto = productoController.buscarPorId(codigo);
        List<Calificacion> calificaciones = calificacionController.listarPorProducto(producto);

        if (calificaciones.isEmpty()) {
            System.out.println("Este producto todavia no tiene calificaciones.");
            return;
        }

        calificaciones.forEach(Calificacion::mostrarInformacion);
        System.out.println("Promedio: " + calificacionController.promedioPorProducto(producto));
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