package menu;

import java.util.Scanner;

public class MenuProductos {

    private final Scanner scanner;

    public MenuProductos(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("GESTION DE PRODUCTOS");
            System.out.println("1. Alta de producto");
            System.out.println("2. Modificar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Buscar producto");
            System.out.println("5. Listar productos");
            System.out.println("6. Validar disponibilidad");
            System.out.println("7. Aplicar descuento");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> altaProducto();
                    case 2 -> modificarProducto();
                    case 3 -> eliminarProducto();
                    case 4 -> buscarProducto();
                    case 5 -> listarProductos();
                    case 6 -> validarDisponibilidad();
                    case 7 -> aplicarDescuento();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void altaProducto() {
        System.out.println("Alta de producto");
        System.out.println("Tipo de producto:");
        System.out.println("1. Fisico   2. Digital   3. Importado");
        System.out.print("Opcion: ");
        int tipo = leerEntero();

        System.out.print("Codigo unico: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();
        System.out.print("Precio: ");
        double precio = leerDouble();
        System.out.print("Categoria (ID): ");
        int categoriaId = leerEntero();
        System.out.print("Stock inicial: ");
        int stock = leerEntero();
        System.out.print("Peso: ");
        double peso = leerDouble();

        System.out.println("Producto creado correctamente.");
    }

    private void modificarProducto() {
        System.out.print("Codigo del producto a modificar: ");
        String codigo = scanner.nextLine().trim();
        System.out.println("Producto modificado correctamente.");
    }

    private void eliminarProducto() {
        System.out.print("Codigo del producto a eliminar: ");
        String codigo = scanner.nextLine().trim();
        System.out.println("Producto eliminado correctamente.");
    }

    private void buscarProducto() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        // System.out.println(p.mostrarInformacion());
    }

    private void listarProductos() {
        System.out.println("Listado de productos");
    }

    private void validarDisponibilidad() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Cantidad deseada: ");
        int cantidad = leerEntero();
        System.out.println("Disponibilidad validada.");
    }

    private void aplicarDescuento() {
        System.out.print("Codigo del producto: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Porcentaje de descuento: ");
        double porcentaje = leerDouble();
        System.out.println("Descuento aplicado correctamente.");
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

    private double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido, se usara 0.");
            return 0;
        }
    }
}
