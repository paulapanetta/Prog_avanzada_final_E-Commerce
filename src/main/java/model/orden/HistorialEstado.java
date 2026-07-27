package model.orden;

import java.time.LocalDateTime;

public class HistorialEstado {

    private EstadoOrden estado;
    private LocalDateTime fecha;


    public HistorialEstado(EstadoOrden estado) {
        this.estado = estado;
        this.fecha = LocalDateTime.now();
    }

    public HistorialEstado(EstadoOrden estado, LocalDateTime fecha) {
        this.estado = estado;
        this.fecha = fecha;
    }


    public EstadoOrden getEstado() { return estado; }

    public LocalDateTime getFecha() { return fecha; }

    @Override
    public String toString() {
        return "HistorialEstado{" +
                "estado=" + estado +
                ", fecha=" + fecha +
                '}';
    }
}
