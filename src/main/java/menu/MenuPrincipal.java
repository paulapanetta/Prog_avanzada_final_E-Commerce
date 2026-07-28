package menu;

import model.usuario.Rol;
import model.usuario.Usuario;
import exceptions.PermisoDenegadoException;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MenuPrincipal {

    private static final Map<Rol, Set<Integer>> PERMISOS = new EnumMap<>(Rol.class);

    static {
        PERMISOS.put(Rol.CLIENTE, opciones(3, 6, 7, 10, 11));
        PERMISOS.put(Rol.ADMINISTRADOR, opciones(1, 2, 3, 4, 5, 12));
        PERMISOS.put(Rol.OPERADOR_VENTAS, opciones(7, 8, 10));
        PERMISOS.put(Rol.RESPONSABLE_LOGISTICA, opciones(9, 10));
    }

    private static Set<Integer> opciones(Integer... valores) {
        return Set.of(valores);
    }

    private static final Map<Integer, String> NOMBRES_OPCION = Map.ofEntries(
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

        this.menuUsuarios = new MenuUsuarios(scanner);
        this.menuRoles = new MenuRoles(scanner);
        this.menuProductos = new MenuProductos(scanner);
        this.menuCategorias = new MenuCategorias(scanner);
        this.menuInventario = new MenuInventario(scanner);
        this.menuCarrito = new MenuCarrito(scanner);
        this.menuOrdenes = new MenuOrdenes(scanner);
        this.menuPagos = new MenuPagos(scanner);
        this.menuEnvios = new MenuEnvios(scanner);
        this.menuSeguimiento = new MenuSeguimiento(scanner);
        this.menuReclamosDevoluciones = new MenuReclamosDevoluciones(scanner);
        this.menuReportes = new MenuReportes(scanner);
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
        System.out.println("CAFETERIA (" + usuarioLogueado.getRol() + ")");

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
