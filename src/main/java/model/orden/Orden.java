package model.orden;

import model.carrito.Carrito;
import model.carrito.ItemCarrito;
import model.envio.Envio;
import model.pago.Pago;
import model.usuario.Cliente;

import exceptions.CarritoVacioException;
import exceptions.DatosInvalidosException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Orden {

    private int id;
    private String numero;
    private Cliente cliente;
    private LocalDate fecha;
    private List<ItemCarrito> items;
    private double total;
    private EstadoOrden estado;
    private Pago pago;
    private Envio envio;
    private List<HistorialEstado> historial;


    public Orden(String numero, Carrito carrito, Envio envio) {

        if (numero == null || numero.isBlank()) {
            throw new DatosInvalidosException("La orden debe tener un numero");
        }

        if (carrito == null || carrito.getItems().isEmpty()) {
            throw new CarritoVacioException("No se puede generar una orden a partir de un carrito vacio");
        }

        this.numero = numero;
        this.cliente = carrito.getCliente();
        this.fecha = LocalDate.now();
        this.items = new ArrayList<>(carrito.getItems());
        this.total = carrito.calcularPrecioFinal();
        this.envio = envio;
        this.estado = EstadoOrden.CREADA;
        this.historial = new ArrayList<>();
        this.historial.add(new HistorialEstado(EstadoOrden.CREADA));
    }

    public Orden(int id, String numero, Cliente cliente,
                 LocalDate fecha, List<ItemCarrito> items,
                 double total, EstadoOrden estado,
                 Pago pago, Envio envio,
                 List<HistorialEstado> historial) {

        this.id = id;
        this.numero = numero;
        this.cliente = cliente;
        this.fecha = fecha;
        this.items = items != null ? items : new ArrayList<>();
        this.total = total;
        this.estado = estado;
        this.pago = pago;
        this.envio = envio;
        this.historial = historial != null ? historial : new ArrayList<>();
    }


    public int getId() { return id; }

    public String getNumero() { return numero; }

    public Cliente getCliente() { return cliente; }

    public LocalDate getFecha() { return fecha; }

    public List<ItemCarrito> getItems() { return items; }

    public double getTotal() { return total; }

    public EstadoOrden getEstado() { return estado; }

    public Pago getPago() { return pago; }

    public Envio getEnvio() { return envio; }

    public List<HistorialEstado> getHistorial() { return historial; }


    public void asignarPago(Pago pago) {
        this.pago = pago;
    }

    public void cambiarEstado(EstadoOrden nuevoEstado) {
        this.estado = nuevoEstado;
        this.historial.add(new HistorialEstado(nuevoEstado));
    }

    public void confirmarPago() {

        if (pago == null) {
            throw new DatosInvalidosException(
                    "La orden no tiene un pago asociado"
            );
        }

        cambiarEstado(EstadoOrden.PENDIENTE_PAGO);
        pago.procesar();
        cambiarEstado(EstadoOrden.PAGADA);
    }

    public void cancelar() {
        cambiarEstado(EstadoOrden.CANCELADA);
    }

    @Override
    public String toString() {
        return "Orden{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", cliente=" + cliente.getEmail() +
                ", fecha=" + fecha +
                ", total=" + total +
                ", estado=" + estado +
                '}';
    }
}
