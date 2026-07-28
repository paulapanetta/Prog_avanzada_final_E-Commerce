package menu;

import java.util.Scanner;

public class MenuCategorias {

    private final Scanner scanner;

    public MenuCategorias(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;

        while (!volver) {
            System.out.println();
            System.out.println("CATEGORIAS");
            System.out.println("1. Alta de categoria");
            System.out.println("2. Baja de categoria");
            System.out.println("3. Modificar categoria");
            System.out.println("4. Consultar categoria");
            System.out.println("5. Asociar productos a categoria");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> altaCategoria();
                    case 2 -> bajaCategoria();
                    case 3 -> modificarCategoria();
                    case 4 -> consultarCategoria();
                    case 5 -> asociarProductos();
                    case 0 -> volver = true;
                    default -> System.out.println("Opcion incorrecta. Intenta nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void altaCategoria() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine().trim();
        System.out.println("Categoria creada correctamente.");
    }

    private void bajaCategoria() {
        System.out.print("ID de la categoria: ");
        int id = leerEntero();
        System.out.println("Categoria eliminada correctamente.");
    }

    private void modificarCategoria() {
        System.out.print("ID de la categoria: ");
        int id = leerEntero();
        System.out.println("Categoria modificada correctamente.");
    }

    private void consultarCategoria() {
        System.out.print("ID de la categoria: ");
        int id = leerEntero();
    }

    private void asociarProductos() {
        System.out.print("ID de la categoria: ");
        int categoriaId = leerEntero();
        System.out.print("Codigo del producto: ");
        String codigoProducto = scanner.nextLine().trim();
        System.out.println("Producto asociado a la categoria.");
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
