package menu;

import controller.CalificacionController;
import controller.CarritoController;
import controller.CategoriaController;
import controller.DevolucionController;
import controller.EnvioController;
import controller.InventarioController;
import controller.OrdenController;
import controller.PagoController;
import controller.ProductoController;
import controller.ReclamoController;
import controller.ReporteController;
import controller.UsuarioController;

import dao.CalificacionDAO;
import dao.CarritoDAO;
import dao.CategoriaDAO;
import dao.DevolucionDAO;
import dao.EnvioDAO;
import dao.InventarioDAO;
import dao.OrdenDAO;
import dao.PagoDAO;
import dao.ProductoDAO;
import dao.ReclamoDAO;
import dao.UsuarioDAO;

import factory.DAOFactory;
import factory.SQLiteDAOFactory;

import model.usuario.Rol;
import model.usuario.Usuario;
import exceptions.PermisoDenegadoException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MenuPrincipal {

    static final Map<Rol, Set<Integer>> PERMISOS = new EnumMap<>(Rol.class);

    static {
        PERMISOS.put(Rol.CLIENTE, opciones(3, 6, 7, 10, 11));
        PERMISOS.put(Rol.ADMINISTRADOR, opciones(1, 2, 3, 4, 5, 12));
        PERMISOS.put(Rol.OPERADOR_VENTAS, opciones(7, 8, 10));
        PERMISOS.put(Rol.RESPONSABLE_LOGISTICA, opciones(9, 10));
    }

    private static Set<Integer> opciones(Integer... valores) {
        return Set.of(valores);
    }

    static final Map<Integer, String> NOMBRES_OPCION = Map.ofEntries(
            Map.entry(1, "Gestión de Usuarios"),
            Map.entry(2, "Gestión de Roles"),
            Map.entry(3, "Gestión de Productos"),
            Map.entry(4, "Gestión de Categorias"),
            Map.entry(5, "Gestión de Inventario"),
            Map.entry(6, "Carrito de Compras"),
            Map.entry(7, "Ordenes de Compra"),
            Map.entry(8, "Procesamiento de Pagos"),
            Map.entry(9, "Gestion de Envios"),
            Map.entry(10, "Seguimiento de Pedidos"),
            Map.entry(11, "Reclamos y Devoluciones"),
            Map.entry(12, "Reportes")
    );

    private final Scanner scanner;
    private final Usuario usuarioLogueado;

    private final MenuUsuarios menuUsuarios;
    private final MenuRoles menuRoles;
    private final MenuProductos menuProductos;
    private final MenuCategorias menuCategorias;
    private final MenuInventario menuInventario;
    private final MenuCarrito menuCarrito;
    private final MenuOrdenes menuOrdenes;
    private final MenuPagos menuPagos;
    private final MenuEnvios menuEnvios;
    private final MenuSeguimiento menuSeguimiento;
    private final MenuReclamosDevoluciones menuReclamosDevoluciones;
    private final MenuReportes menuReportes;

    public MenuPrincipal(Scanner scanner, Usuario usuarioLogueado) {
        this.scanner = scanner;
        this.usuarioLogueado = usuarioLogueado;

        DAOFactory factory = new SQLiteDAOFactory();

        UsuarioDAO usuarioDAO = factory.crearUsuarioDAO();
        CategoriaDAO categoriaDAO = factory.crearCategoriaDAO();
        ProductoDAO productoDAO = factory.crearProductoDAO();
        InventarioDAO inventarioDAO = factory.crearInventarioDAO();
        CarritoDAO carritoDAO = factory.crearCarritoDAO();
        OrdenDAO ordenDAO = factory.crearOrdenDAO();
        PagoDAO pagoDAO = factory.crearPagoDAO();
        EnvioDAO envioDAO = factory.crearEnvioDAO();
        ReclamoDAO reclamoDAO = factory.crearReclamoDAO();
        DevolucionDAO devolucionDAO = factory.crearDevolucionDAO();
        CalificacionDAO calificacionDAO = factory.crearCalificacionDAO();

        UsuarioController usuarioController = new UsuarioController(usuarioDAO);
        ProductoController productoController = new ProductoController(productoDAO);
        CategoriaController categoriaController = new CategoriaController(categoriaDAO, productoDAO);
        InventarioController inventarioController = new InventarioController(inventarioDAO);
        CarritoController carritoController = new CarritoController(carritoDAO, inventarioDAO);
        OrdenController ordenController = new OrdenController(ordenDAO, carritoController, inventarioController);
        PagoController pagoController = new PagoController(pagoDAO, ordenController);
        EnvioController envioController = new EnvioController(envioDAO);
        ReclamoController reclamoController = new ReclamoController(reclamoDAO);
        DevolucionController devolucionController = new DevolucionController(devolucionDAO);
        CalificacionController calificacionController = new CalificacionController(calificacionDAO);
        ReporteController reporteController = new ReporteController(
                usuarioDAO, productoDAO, inventarioDAO, ordenDAO, reclamoDAO, envioDAO, pagoDAO
        );

        this.menuUsuarios = new MenuUsuarios(scanner, usuarioController);
        this.menuRoles = new MenuRoles(scanner, usuarioController);
        this.menuProductos = new MenuProductos(scanner, productoController, categoriaController, inventarioController);
        this.menuCategorias = new MenuCategorias(scanner, categoriaController);
        this.menuInventario = new MenuInventario(scanner, inventarioController);
        this.menuCarrito = new MenuCarrito(scanner, carritoController, productoController, usuarioLogueado);
        this.menuOrdenes = new MenuOrdenes(scanner, ordenController, envioController, usuarioLogueado);
        this.menuPagos = new MenuPagos(scanner, pagoController);
        this.menuEnvios = new MenuEnvios(scanner, envioController);
        this.menuSeguimiento = new MenuSeguimiento(scanner, ordenController, envioController);
        this.menuReclamosDevoluciones = new MenuReclamosDevoluciones(
                scanner, reclamoController, devolucionController, calificacionController,
                ordenController, productoController, usuarioLogueado
        );
        this.menuReportes = new MenuReportes(scanner, reporteController);
    }

    public void iniciar() {
        boolean salir = false;
        Set<Integer> opcionesPermitidas = PERMISOS.getOrDefault(usuarioLogueado.getRol(), Set.of());

        while (!salir) {
            mostrarOpciones(opcionesPermitidas);
            int opcion = leerOpcion();

            if (opcion == 13) {
                salir = true;
                System.out.println("Sesion cerrada. Hasta luego, " + usuarioLogueado.getNombre() + "!");
                continue;
            }

            try {
                ejecutarOpcion(opcion, opcionesPermitidas);
            } catch (PermisoDenegadoException e) {
                System.out.println("Acceso denegado: " + e.getMessage());
            }
        }
    }

    private void ejecutarOpcion(int opcion, Set<Integer> opcionesPermitidas) throws PermisoDenegadoException {
        if (!opcionesPermitidas.contains(opcion)) {
            throw new PermisoDenegadoException(
                    "El rol " + usuarioLogueado.getRol() + " no tiene acceso a esta opcion.");
        }

        switch (opcion) {
            case 1 -> menuUsuarios.mostrar();
            case 2 -> menuRoles.mostrar();
            case 3 -> menuProductos.mostrar();
            case 4 -> menuCategorias.mostrar();
            case 5 -> menuInventario.mostrar();
            case 6 -> menuCarrito.mostrar();
            case 7 -> menuOrdenes.mostrar();
            case 8 -> menuPagos.mostrar();
            case 9 -> menuEnvios.mostrar();
            case 10 -> menuSeguimiento.mostrar();
            case 11 -> menuReclamosDevoluciones.mostrar();
            case 12 -> menuReportes.mostrar();
            default -> System.out.println("Opcion incorrecta. Intente nuevamente.");
        }
    }

    private void mostrarOpciones(Set<Integer> opcionesPermitidas) {
        System.out.println();
        System.out.println("=== SISTEMA E-COMMERCE - CAFETERIA === (" + usuarioLogueado.getRol() + ")");

        for (int i = 1; i <= 12; i++) {
            if (opcionesPermitidas.contains(i)) {
                System.out.println(i + ". " + NOMBRES_OPCION.get(i));
            }
        }
        System.out.println("13. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}