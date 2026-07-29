package model.pago;

import strategy.ProcesadorPago;

import exceptions.DatosInvalidosException;
import exceptions.PagoRechazadoException;

import java.time.LocalDate;

public class Pago {

    private int id;
    private int ordenId;
    private double monto;
    private LocalDate fecha;
    private EstadoPago estado;
    private ProcesadorPago metodo;


    public Pago(int ordenId, double monto, ProcesadorPago metodo) {

        if (monto <= 0) {
            throw new DatosInvalidosException(
                    "El monto del pago tiene que ser mayor a cero"
            );
        }

        if (metodo == null) {
            throw new DatosInvalidosException(
                    "" +
                            "Indica un metodo de pago"
            );
        }

        this.ordenId = ordenId;
        this.monto = monto;
        this.metodo = metodo;
        this.fecha = LocalDate.now();
        this.estado = EstadoPago.PENDIENTE;
    }

    public Pago(int id, int ordenId, double monto, LocalDate fecha,
                EstadoPago estado, ProcesadorPago metodo) {

        this.id = id;
        this.ordenId = ordenId;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.metodo = metodo;
    }


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getOrdenId() {return ordenId;}

    public double getMonto() { return monto; }

    public LocalDate getFecha() { return fecha; }

    public EstadoPago getEstado() { return estado; }

    public ProcesadorPago getMetodo() { return metodo; }


    public boolean procesar() {

        boolean aprobado = metodo.procesarPago(monto);

        if (aprobado) {
            estado = EstadoPago.APROBADO;
        } else {
            estado = EstadoPago.RECHAZADO;

            throw new PagoRechazadoException(
                    "El pago fue rechazado por el medio de pago seleccionado"
            );
        }

        return aprobado;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", ordenId=" + ordenId +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", estado=" + estado +
                '}';
    }
}
