package menu;

import controller.CategoriaController;
import model.producto.Categoria;

import java.util.List;
import java.util.Scanner;

public class MenuCategorias {

    private final Scanner scanner;
    private final CategoriaController categoriaController;

    public MenuCategorias(Scanner scanner, CategoriaController categoriaController) {
        this.scanner = scanner;
        this.categoriaController = categoriaController;
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
            System.out.println("5. Listar categorias");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> altaCategoria();
                    case 2 -> bajaCategoria();
                    case 3 -> modificarCategoria();
                    case 4 -> consultarCategoria();
                    case 5 -> listarCategorias();
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

        Categoria categoria = categoriaController.crear(nombre, descripcion);

        System.out.println("Categoria creada correctamente. ID asignado: " + categoria.getId());
    }

    private void bajaCategoria() {
        System.out.print("ID de la categoria a eliminar: ");
        int id = leerEntero();

        categoriaController.eliminar(id);

        System.out.println("Categoria eliminada correctamente.");
    }

    private void modificarCategoria() {
        System.out.print("ID de la categoria a modificar: ");
        int id = leerEntero();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine().trim();

        categoriaController.modificar(id, nombre, descripcion);

        System.out.println("Categoria modificada correctamente.");
    }

    private void consultarCategoria() {
        System.out.print("ID de la categoria: ");
        int id = leerEntero();

        Categoria categoria = categoriaController.buscarPorId(id);
        System.out.println(categoria);
    }

    private void listarCategorias() {
        List<Categoria> categorias = categoriaController.listar();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
            return;
        }

        System.out.println("Listado de categorias");
        categorias.forEach(Categoria::mostrarInformacion);
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