package menu;

import java.util.Scanner;

public class MenuCarrito {

    private final Scanner scanner;

    public MenuCarrito(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("CARRITO");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Modificar cantidad");
            System.out.println("4. Vaciar carrito");
            System.out.println("5. Visualizar carrito");
            System.out.println("6. Calcular subtotal");
            System.out.println("7. Calcular total");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> eliminarProducto();
                    case 3 -> modificarCantidad();
                    case 4 -> vaciarCarrito();
                    case 5 -> visualizarCarrito();
                    case 6 -> calcularSubtotal();
                    case 7 -> calcularTotal();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void agregarProducto() {
        System.out.print("Código del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Cantidad: ");
        int cantidad = leerEntero();
        System.out.println("Producto agregado al carrito.");
    }

    private void eliminarProducto() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.println("Producto eliminado del carrito.");
    }

    private void modificarCantidad() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Nueva cantidad: ");
        int cantidad = leerEntero();
        System.out.println("Cantidad modificada.");
    }

    private void vaciarCarrito() {
        System.out.println("Carrito vaciado.");
    }

    private void visualizarCarrito() {
        System.out.println("Contenido del carrito");
    }

    private void calcularSubtotal() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
    }

    private void calcularTotal() {
        System.out.println("Total calculado.");
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
