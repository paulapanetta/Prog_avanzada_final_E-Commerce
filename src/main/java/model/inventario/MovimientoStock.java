package model.inventario;

import java.time.LocalDateTime;

public class MovimientoStock {

    private int id;
    private int codigoProducto;
    private TipoMovimiento tipo;
    private int cantidad;
    private LocalDateTime fecha;
    private String descripcion;


    public MovimientoStock(int codigoProducto, TipoMovimiento tipo,
                           int cantidad, String descripcion) {

        this.codigoProducto = codigoProducto;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
    }

    public MovimientoStock(int id,
                           int codigoProducto,
                           TipoMovimiento tipo,
                           int cantidad,
                           LocalDateTime fecha,
                           String descripcion) {

        this.id = id;
        this.codigoProducto = codigoProducto;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoProducto() { return codigoProducto; }

    public TipoMovimiento getTipo() { return tipo; }

    public int getCantidad() { return cantidad; }

    public LocalDateTime getFecha() { return fecha; }

    public String getDescripcion() { return descripcion; }


    @Override
    public String toString(){

        return "MovimientoStock{" +
                "id=" + id +
                ", codigoProducto=" + codigoProducto +
                ", tipo=" + tipo +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

}
