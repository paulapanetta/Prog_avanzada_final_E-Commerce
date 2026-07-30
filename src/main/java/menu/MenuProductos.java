package menu;

import controller.CategoriaController;
import controller.InventarioController;
import controller.ProductoController;
import model.producto.Categoria;
import model.producto.EstadoProducto;
import model.producto.Producto;
import model.producto.ProductoDigital;
import model.producto.ProductoFisico;
import model.producto.ProductoImportado;

import java.util.List;
import java.util.Scanner;

public class MenuProductos {

    private final Scanner scanner;
    private final ProductoController productoController;
    private final CategoriaController categoriaController;
    private final InventarioController inventarioController;

    public MenuProductos(
            Scanner scanner,
            ProductoController productoController,
            CategoriaController categoriaController,
            InventarioController inventarioController
    ) {
        this.scanner = scanner;
        this.productoController = productoController;
        this.categoriaController = categoriaController;
        this.inventarioController = inventarioController;
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

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();
        System.out.print("Precio: ");
        double precio = leerDouble();

        System.out.print("Categoria (ID): ");
        int categoriaId = leerEntero();
        Categoria categoria = categoriaController.buscarPorId(categoriaId);

        System.out.print("Peso: ");
        double peso = leerDouble();
        System.out.print("Stock inicial: ");
        int stock = leerEntero();

        Producto producto = switch (tipo) {
            case 1 -> new ProductoFisico(nombre, descripcion, precio, categoria, peso, EstadoProducto.ACTIVO);
            case 2 -> new ProductoDigital(nombre, descripcion, precio, categoria, peso, EstadoProducto.ACTIVO);
            case 3 -> new ProductoImportado(nombre, descripcion, precio, categoria, peso, EstadoProducto.ACTIVO);
            default -> throw new IllegalArgumentException("Tipo de producto invalido.");
        };

        productoController.guardar(producto);

        inventarioController.agregarProducto(producto, stock);

        System.out.println("Producto creado correctamente. Codigo asignado: " + producto.getCodigo());
    }

    private void modificarProducto() {
        System.out.print("Codigo del producto a modificar: ");
        int codigo = leerEntero();

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine().trim();
        System.out.print("Nuevo peso: ");
        double peso = leerDouble();

        productoController.modificar(codigo, nombre, descripcion, peso);

        System.out.println("Producto modificado correctamente.");
    }

    private void eliminarProducto() {
        System.out.print("Codigo del producto a eliminar: ");
        int codigo = leerEntero();

        productoController.eliminar(codigo);

        System.out.println("Producto eliminado correctamente.");
    }

    private void buscarProducto() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();

        Producto p = productoController.buscarPorId(codigo);
        p.mostrarInformacion();
    }

    private void listarProductos() {
        List<Producto> productos = productoController.listar();

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println("Listado de productos");
        productos.forEach(Producto::mostrarInformacion);
    }

    private void validarDisponibilidad() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Cantidad deseada: ");
        int cantidad = leerEntero();

        int stockActual = inventarioController.buscarStock(codigo).getCantidad();

        if (stockActual >= cantidad) {
            System.out.println("Disponible. Stock actual: " + stockActual);
        } else {
            System.out.println("No hay disponibilidad suficiente. Stock actual: " + stockActual);
        }
    }

    private void aplicarDescuento() {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Porcentaje de descuento: ");
        double porcentaje = leerDouble();

        productoController.aplicarDescuento(codigo, porcentaje);

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