package menu;

import controller.ReporteController;
import model.envio.Envio;
import model.inventario.StockProducto;
import model.orden.EstadoOrden;
import model.postventa.Reclamo;
import model.producto.Producto;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuReportes {

    private final Scanner scanner;
    private final ReporteController reporteController;

    public MenuReportes(Scanner scanner, ReporteController reporteController) {
        this.scanner = scanner;
        this.reporteController = reporteController;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("REPORTES");
            System.out.println("1.  Cantidad total de usuarios");
            System.out.println("2.  Cantidad de clientes");
            System.out.println("3.  Cantidad de productos");
            System.out.println("4.  Productos por categoria");
            System.out.println("5.  Productos sin stock");
            System.out.println("6.  Productos mas vendidos");
            System.out.println("7.  Ordenes generadas");
            System.out.println("8.  Ordenes por estado");
            System.out.println("9.  Recaudacion total");
            System.out.println("10. Recaudacion por metodo de pago");
            System.out.println("11. Clientes con mas compras");
            System.out.println("12. Reclamos abiertos");
            System.out.println("13. Reclamos resueltos");
            System.out.println("14. Envios pendientes");
            System.out.println("15. Envios entregados");
            System.out.println("0.  Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> System.out.println("Total de usuarios: " + reporteController.cantidadUsuarios());
                    case 2 -> System.out.println("Total de clientes: " + reporteController.cantidadClientes());
                    case 3 -> System.out.println("Total de productos: " + reporteController.cantidadProductos());
                    case 4 -> reporteProductosPorCategoria();
                    case 5 -> reporteProductosSinStock();
                    case 6 -> reporteProductosMasVendidos();
                    case 7 -> System.out.println("Ordenes generadas: " + reporteController.cantidadOrdenes());
                    case 8 -> reporteOrdenesPorEstado();
                    case 9 -> System.out.println("Recaudacion total: $" + reporteController.recaudacionTotal());
                    case 10 -> reporteRecaudacionPorMetodo();
                    case 11 -> reporteClientesConMasCompras();
                    case 12 -> reporteReclamosAbiertos();
                    case 13 -> reporteReclamosResueltos();
                    case 14 -> reporteEnviosPendientes();
                    case 15 -> reporteEnviosEntregados();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void reporteProductosPorCategoria() {
        System.out.print("ID de la categoria: ");
        int categoriaId = leerEntero();

        List<Producto> productos = reporteController.productosPorCategoria(categoriaId);

        if (productos.isEmpty()) {
            System.out.println("No hay productos en esa categoria.");
            return;
        }

        productos.forEach(Producto::mostrarInformacion);
    }

    private void reporteProductosSinStock() {
        List<StockProducto> sinStock = reporteController.productosSinStock();

        if (sinStock.isEmpty()) {
            System.out.println("No hay productos sin stock.");
            return;
        }

        sinStock.forEach(StockProducto::mostrarInformacion);
    }

    private void reporteProductosMasVendidos() {
        Map<String, Integer> ventas = reporteController.productosMasVendidos();

        if (ventas.isEmpty()) {
            System.out.println("Todavia no hay ventas registradas.");
            return;
        }

        ventas.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " unidades"));
    }

    private void reporteOrdenesPorEstado() {
        Map<EstadoOrden, Integer> reporte = reporteController.ordenesPorEstado();

        if (reporte.isEmpty()) {
            System.out.println("No hay ordenes cargadas.");
            return;
        }

        reporte.forEach((estado, cantidad) -> System.out.println(estado + ": " + cantidad));
    }

    private void reporteRecaudacionPorMetodo() {
        Map<String, Double> recaudacion = reporteController.recaudacionPorMetodo();

        if (recaudacion.isEmpty()) {
            System.out.println("Todavia no hay pagos aprobados.");
            return;
        }

        recaudacion.forEach((metodo, monto) -> System.out.println(metodo + ": $" + monto));
    }

    private void reporteClientesConMasCompras() {
        Map<String, Integer> compras = reporteController.clientesConMasCompras();

        if (compras.isEmpty()) {
            System.out.println("Todavia no hay compras registradas.");
            return;
        }

        compras.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " compras"));
    }

    private void reporteReclamosAbiertos() {
        List<Reclamo> reclamos = reporteController.reclamosAbiertos();
        System.out.println("Reclamos abiertos: " + reclamos.size());
        reclamos.forEach(Reclamo::mostrarInformacion);
    }

    private void reporteReclamosResueltos() {
        List<Reclamo> reclamos = reporteController.reclamosResueltos();
        System.out.println("Reclamos resueltos: " + reclamos.size());
        reclamos.forEach(Reclamo::mostrarInformacion);
    }

    private void reporteEnviosPendientes() {
        List<Envio> envios = reporteController.enviosPendientes();
        System.out.println("Envios pendientes: " + envios.size());
        envios.forEach(Envio::mostrarInformacion);
    }

    private void reporteEnviosEntregados() {
        List<Envio> envios = reporteController.enviosEntregados();
        System.out.println("Envios entregados: " + envios.size());
        envios.forEach(Envio::mostrarInformacion);
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