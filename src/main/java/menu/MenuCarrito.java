package menu;

import controller.CarritoController;
import controller.ProductoController;
import model.carrito.Carrito;
import model.carrito.ItemCarrito;
import model.producto.Producto;
import model.usuario.Cliente;
import model.usuario.Usuario;

import exceptions.PermisoDenegadoException;

import java.util.Scanner;

public class MenuCarrito {

    private final Scanner scanner;
    private final CarritoController carritoController;
    private final ProductoController productoController;
    private final Usuario usuarioLogueado;

    public MenuCarrito(Scanner scanner, CarritoController carritoController,
                       ProductoController productoController, Usuario usuarioLogueado) {
        this.scanner = scanner;
        this.carritoController = carritoController;
        this.productoController = productoController;
        this.usuarioLogueado = usuarioLogueado;
    }

    public void mostrar() {
        boolean volver = false;

        Cliente cliente = obtenerClienteLogueado();

        while (!volver) {
            System.out.println();
            System.out.println("CARRITO");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Modificar cantidad");
            System.out.println("4. Vaciar carrito");
            System.out.println("5. Visualizar carrito");
            System.out.println("6. Calcular total");
            System.out.println("0. Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> agregarProducto(cliente);
                    case 2 -> eliminarProducto(cliente);
                    case 3 -> modificarCantidad(cliente);
                    case 4 -> vaciarCarrito(cliente);
                    case 5 -> visualizarCarrito(cliente);
                    case 6 -> calcularTotal(cliente);
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
            throw new PermisoDenegadoException("Solo un cliente puede operar el carrito de compras.");
        }
        return cliente;
    }

    private void agregarProducto(Cliente cliente) {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Cantidad: ");
        int cantidad = leerEntero();

        carritoController.agregarProducto(cliente, codigo, cantidad);

        System.out.println("Producto agregado al carrito.");
    }

    private void eliminarProducto(Cliente cliente) {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();

        Producto producto = productoController.buscarPorId(codigo);
        carritoController.eliminarProducto(cliente, producto);

        System.out.println("Producto eliminado del carrito.");
    }

    private void modificarCantidad(Cliente cliente) {
        System.out.print("Codigo del producto: ");
        int codigo = leerEntero();
        System.out.print("Nueva cantidad: ");
        int cantidad = leerEntero();

        carritoController.modificarCantidad(cliente, codigo, cantidad);

        System.out.println("Cantidad modificada.");
    }

    private void vaciarCarrito(Cliente cliente) {
        carritoController.vaciar(cliente);
        System.out.println("Carrito vaciado.");
    }

    private void visualizarCarrito(Cliente cliente) {
        Carrito carrito = carritoController.obtenerCarritoDeCliente(cliente);

        if (carrito.getItems().isEmpty()) {
            System.out.println("El carrito esta vacio.");
            return;
        }

        System.out.println("Contenido del carrito");
        for (ItemCarrito item : carrito.getItems()) {
            System.out.println(item);
        }
    }

    private void calcularTotal(Cliente cliente) {
        Carrito carrito = carritoController.obtenerCarritoDeCliente(cliente);
        double total = carrito.calcularPrecioFinal();

        System.out.println("Total del carrito: $" + total);
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