package model.postventa;

import model.interfaces.Mostrable;
import model.orden.Orden;
import model.usuario.Cliente;

import java.time.LocalDate;

public class Reclamo implements Mostrable {

    private int id;
    private Cliente cliente;
    private Orden orden;
    private String motivo;
    private LocalDate fecha;
    private EstadoReclamo estado;

    // Reclamo nuevo
    public Reclamo(Cliente cliente, Orden orden,
                   String motivo) {

        this.cliente = cliente;
        this.orden = orden;
        this.motivo = motivo;
        this.fecha = LocalDate.now();
        this.estado = EstadoReclamo.ABIERTO;
    }

    // Reclamo recuperado de la BD
    public Reclamo(int id, Cliente cliente,
                   Orden orden, String motivo,
                   LocalDate fecha,
                   EstadoReclamo estado) {

        this.id = id;
        this.cliente = cliente;
        this.orden = orden;
        this.motivo = motivo;
        this.fecha = fecha;
        this.estado = estado;
    }


    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Cliente getCliente() { return cliente; }

    public Orden getOrden() { return orden; }

    public String getMotivo() { return motivo; }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha() { return fecha; }

    public EstadoReclamo getEstado() { return estado; }


    public void ponerEnRevision() {
        estado = EstadoReclamo.EN_REVISION;
    }

    public void resolver() {
        estado = EstadoReclamo.RESUELTO;
    }

    public void rechazar() {
        estado = EstadoReclamo.RECHAZADO;
    }


    @Override
    public void mostrarInformacion() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Reclamo{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", orden=" + orden +
                ", motivo='" + motivo + '\'' +
                ", fecha=" + fecha +
                ", estado=" + estado +
                '}';
    }

}
