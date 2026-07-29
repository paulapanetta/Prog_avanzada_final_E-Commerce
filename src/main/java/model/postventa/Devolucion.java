package model.postventa;

import model.interfaces.Mostrable;
import model.producto.Producto;
import model.usuario.Cliente;

import java.time.LocalDate;

public class Devolucion implements Mostrable {

    private int id;
    private Cliente cliente;
    private Producto producto;
    private String motivo;
    private LocalDate fecha;
    private EstadoDevolucion estado;


    // Devolución nueva
    public Devolucion(Cliente cliente,
                      Producto producto,
                      String motivo) {

        this.cliente = cliente;
        this.producto = producto;
        this.motivo = motivo;
        this.fecha = LocalDate.now();
        this.estado = EstadoDevolucion.PENDIENTE;
    }

    // Devolución recuperada de la BD
    public Devolucion(int id, Cliente cliente,
                      Producto producto,
                      String motivo,
                      LocalDate fecha,
                      EstadoDevolucion estado) {

        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.motivo = motivo;
        this.fecha = fecha;
        this.estado = estado;
    }


    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
    }

    public Cliente getCliente() { return cliente; }

    public Producto getProducto() { return producto; }

    public String getMotivo() { return motivo; }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha() { return fecha; }

    public EstadoDevolucion getEstado() { return estado; }


    public void aprobar() {
        estado = EstadoDevolucion.APROBADA;
    }

    public void rechazar() {
        estado = EstadoDevolucion.RECHAZADA;
    }


    @Override
    public void mostrarInformacion() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Devolucion{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", producto=" + producto +
                ", motivo='" + motivo + '\'' +
                ", fecha=" + fecha +
                ", estado=" + estado +
                '}';
    }
}