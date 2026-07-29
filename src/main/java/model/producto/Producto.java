package model.producto;

import model.interfaces.Calculable;
import model.interfaces.Descontable;
import model.interfaces.Mostrable;

public abstract class Producto implements Mostrable, Calculable, Descontable {

    private int codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private double peso;
    private EstadoProducto estado;


    // Constructor para producto nuevo
    public Producto(String nombre, String descripcion,
                    double precio, Categoria categoria,
                    double peso, EstadoProducto estado) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.peso = peso;
        this.estado = estado;
    }

    // Constructor para recuperar producto de la BD
    public Producto(int codigo, String nombre,
                    String descripcion, double precio,
                    Categoria categoria,
                    double peso, EstadoProducto estado) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.peso = peso;
        this.estado = estado;
    }


    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() { return precio; }

    protected void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() { return categoria; }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getPeso() { return peso; }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public EstadoProducto getEstado() { return estado; }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(this);
    }

    @Override
    public abstract double calcularPrecioFinal();

    @Override
    public abstract void aplicarDescuento(double porcentaje);


    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", categoria=" + categoria +
                ", peso=" + peso +
                ", estado=" + estado +
                '}';
    }
}
