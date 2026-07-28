package controller;

import dao.*;
import model.envio.Envio;
import model.envio.EstadoEnvio;
import model.inventario.Inventario;
import model.orden.EstadoOrden;
import model.orden.Orden;
import model.postventa.EstadoReclamo;
import model.postventa.Reclamo;
import model.producto.Producto;
import model.usuario.Rol;
import model.usuario.Usuario;
import model.pago.Pago;
import model.pago.EstadoPago;

import java.util.Map;
import java.util.HashMap;

import java.util.List;

public class ReporteController {

    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private InventarioDAO inventarioDAO;
    private OrdenDAO ordenDAO;
    private ReclamoDAO reclamoDAO;
    private EnvioDAO envioDAO;
    private PagoDAO pagoDAO;

    public ReporteController(
            UsuarioDAO usuarioDAO,
            ProductoDAO productoDAO,
            InventarioDAO inventarioDAO,
            OrdenDAO ordenDAO,
            ReclamoDAO reclamoDAO,
            EnvioDAO envioDAO,
            PagoDAO pagoDAO) {

        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        this.inventarioDAO = inventarioDAO;
        this.ordenDAO = ordenDAO;
        this.reclamoDAO = reclamoDAO;
        this.envioDAO = envioDAO;
        this.pagoDAO = pagoDAO;
    }

    // cant total usuarios
    public int cantidadUsuarios() {

        return usuarioDAO.obtenerTodos().size();
    }


    // cant clientes
    public int cantidadClientes() {

        int cantidad = 0;

        for (Usuario usuario : usuarioDAO.obtenerTodos()) {

            if (usuario.getRol() == Rol.CLIENTE) {
                cantidad++;
            }
        }

        return cantidad;
    }


    //cant total productos
    public int cantidadProductos() {

        return productoDAO.obtenerTodos().size();
    }


    // productos x categoria
    public List<Producto> productosPorCategoria(int categoriaId) {

        return productoDAO.obtenerTodos()
                .stream()
                .filter(producto ->
                        producto.getCategoria().getId() == categoriaId)
                .toList();
    }


    // productos sin stock
    public List<Inventario> productosSinStock() {

        return inventarioDAO.listar()
                .stream()
                .filter(inventario ->
                        inventario.getStockActual() == 0)
                .toList();
    }


    // productos + vendidos
    public Map<String, Integer> productosMasVendidos() {

        Map<String, Integer> ventas = new HashMap<>();

        for (Orden orden : ordenDAO.obtenerTodos()) {

            orden.getItems()
                    .forEach(item -> {

                        String producto = item.getProducto().getNombre();

                        if (ventas.containsKey(producto)) {

                            ventas.put(producto, ventas.get(producto) + item.getCantidad());

                        } else {
                            ventas.put(producto, item.getCantidad());
                        }
                    });
        }

        return ventas;
    }

    // cant ordenes generadas
    public int cantidadOrdenes() {

        return ordenDAO.obtenerTodos().size();
    }

    // ordenes x estado
    public Map<EstadoOrden, Integer> ordenesPorEstado() {

        Map<EstadoOrden, Integer> reporte = new HashMap<>();

        for (Orden orden : ordenDAO.obtenerTodos()) {

            EstadoOrden estado = orden.getEstado();

            if (reporte.containsKey(estado)) {

                reporte.put(estado, reporte.get(estado) + 1
                );

            } else {

                reporte.put(estado, 1);
            }
        }

        return reporte;
    }


    // rec total
    public double recaudacionTotal() {

        double total = 0;

        for (Orden orden : ordenDAO.obtenerTodos()) {

            total += orden.getTotal();
        }

        return total;
    }


    // clientes con + compras
    public Map<String, Integer> clientesConMasCompras() {

        Map<String, Integer> compras = new HashMap<>();

        for (Orden orden : ordenDAO.obtenerTodos()) {

            String email = orden.getCliente().getEmail();

            if (compras.containsKey(email)) {

                compras.put(email, compras.get(email) + 1
                );

            } else {

                compras.put(email, 1);
            }
        }

        return compras;
    }


    public List<Reclamo> reclamosAbiertos() {

        return reclamoDAO.obtenerTodos()
                .stream()
                .filter(reclamo ->
                        reclamo.getEstado()
                                == EstadoReclamo.ABIERTO)
                .toList();
    }


    public List<Reclamo> reclamosResueltos() {

        return reclamoDAO.obtenerTodos()
                .stream()
                .filter(reclamo ->
                        reclamo.getEstado()
                                == EstadoReclamo.RESUELTO)
                .toList();
    }


    public List<Envio> enviosPendientes() {

        return envioDAO.obtenerTodos()
                .stream()
                .filter(envio ->
                        envio.getEstado()
                                == EstadoEnvio.PENDIENTE)
                .toList();
    }


    public List<Envio> enviosEntregados() {

        return envioDAO.obtenerTodos()
                .stream()
                .filter(envio ->
                        envio.getEstado()
                                == EstadoEnvio.ENTREGADO)
                .toList();
    }

    // recaudacion x metodo de pago
    public Map<String, Double> recaudacionPorMetodo() {

        Map<String, Double> recaudacion = new HashMap<>();

        for (Pago pago : pagoDAO.obtenerTodos()) {

            if (pago.getEstado() == EstadoPago.APROBADO) {

                String metodo =
                        pago.getMetodo().getClass().getSimpleName();

                if (recaudacion.containsKey(metodo)) {

                    recaudacion.put(metodo, recaudacion.get(metodo) + pago.getMonto()
                    );

                } else {

                    recaudacion.put(metodo, pago.getMonto());
                }
            }
        }

        return recaudacion;
    }

}
