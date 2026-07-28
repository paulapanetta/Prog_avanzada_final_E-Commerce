package menu;

import java.util.Scanner;

public class MenuReportes {

    private final Scanner scanner;

    public MenuReportes(Scanner scanner) {
        this.scanner = scanner;
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
            System.out.println("11. Clientes con más compras");
            System.out.println("12. Reclamos abiertos");
            System.out.println("13. Reclamos resueltos");
            System.out.println("14. Envios pendientes");
            System.out.println("15. Envios entregados");
            System.out.println("0.  Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> System.out.println("Total de usuarios: " + reporteCantidadUsuarios());
                    case 2 -> System.out.println("Total de clientes: " + reporteCantidadClientes());
                    case 3 -> System.out.println("Total de productos: " + reporteCantidadProductos());
                    case 4 -> reporteProductosPorCategoria();
                    case 5 -> reporteProductosSinStock();
                    case 6 -> reporteProductosMasVendidos();
                    case 7 -> System.out.println("Órdenes generadas: " + reporteOrdenesGeneradas());
                    case 8 -> reporteOrdenesPorEstado();
                    case 9 -> System.out.println("Recaudación total: " + reporteRecaudacionTotal());
                    case 10 -> reporteRecaudacionPorMetodoPago();
                    case 11 -> reporteClientesConMasCompras();
                    case 12 -> System.out.println("Reclamos abiertos: " + reporteReclamosAbiertos());
                    case 13 -> System.out.println("Reclamos resueltos: " + reporteReclamosResueltos());
                    case 14 -> System.out.println("Envios pendientes: " + reporteEnviosPendientes());
                    case 15 -> System.out.println("Envios entregados: " + reporteEnviosEntregados());
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private int reporteCantidadUsuarios() {
        return 0;
    }

    private int reporteCantidadClientes() {
        return 0;
    }

    private int reporteCantidadProductos() {
        return 0;
    }

    private void reporteProductosPorCategoria() {
    }

    private void reporteProductosSinStock() {
    }

    private void reporteProductosMasVendidos() {
    }

    private int reporteOrdenesGeneradas() {
        return 0;
    }

    private void reporteOrdenesPorEstado() {
    }

    private double reporteRecaudacionTotal() {
        return 0.0;
    }

    private void reporteRecaudacionPorMetodoPago() {
    }

    private void reporteClientesConMasCompras() {
    }

    private int reporteReclamosAbiertos() {
        return 0;
    }

    private int reporteReclamosResueltos() {
        return 0;
    }

    private int reporteEnviosPendientes() {
        return 0;
    }

    private int reporteEnviosEntregados() {
        return 0;
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}