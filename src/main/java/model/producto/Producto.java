package model.producto;

import model.interfaces.Calculable;
import model.interfaces.Descontable;
import model.interfaces.Mostrable;

import exceptions.StockNegativoException;
import exceptions.StockInsuficienteException;

public abstract class Producto implements Mostrable, Calculable, Descontable {

    private int codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private int stock;
    private double peso;
    private EstadoProducto estado;


    // Constructor para producto nuevo
    public Producto(String nombre, String descripcion,
                    double precio, Categoria categoria,
                    int stock, double peso,
                    EstadoProducto estado) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;

        if (stock < 0) {
            throw new StockNegativoException(
                    "El stock inicial no puede ser negativo"
            );
        }

        this.stock = stock;
        this.peso = peso;
        this.estado = estado;

        actualizarEstado();

    }

    // Constructor para recuperar producto de la BD
    public Producto(int codigo, String nombre,
                    String descripcion, double precio,
                    Categoria categoria, int stock,
                    double peso, EstadoProducto estado) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;

        if (stock < 0) {
            throw new StockNegativoException(
                    "El stock recuperado no puede ser negativo"
            );
        }

        this.stock = stock;
        this.peso = peso;

        actualizarEstado();

        this.estado = estado;
    }


    // sin setter pq dsp lo asigna SQLite
    public int getCodigo() { return codigo; }

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

    // sin setter pq dsp se modifica en metodos
    public int getStock() { return stock; }

    public double getPeso() { return peso; }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public EstadoProducto getEstado() { return estado; }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }


    public boolean validarDisponibilidad() {
        return stock > 0 && estado == EstadoProducto.ACTIVO;
    }


    private void actualizarEstado(){

        if(stock == 0){

            estado = EstadoProducto.SIN_STOCK;

        }else if(estado != EstadoProducto.SUSPENDIDO && estado != EstadoProducto.INACTIVO){

            estado = EstadoProducto.ACTIVO;
        }
    }

    public void ingresarStock(int cantidad) {

        if(cantidad <= 0){
            throw new StockNegativoException(
                    "La cantidad ingresada debe ser positiva"
            );
        }

        stock += cantidad;
        actualizarEstado();
    }

    public void egresarStock(int cantidad) {

        if(cantidad <= 0){
            throw new StockNegativoException(
                    "La cantidad debe ser mayor a cero"
            );
        }

        if(cantidad > stock){

            throw new StockInsuficienteException(
                    "No hay stock suficiente para realizar la operación"
            );
        }

        stock -= cantidad;

        actualizarEstado();
    }

    public void ajustarStock(int cantidad) {

        if(cantidad < 0){

            throw new StockNegativoException(
                    "El stock no puede quedar negativo"
            );
        }

        stock = cantidad;

        actualizarEstado();
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
                ", stock=" + stock +
                ", peso=" + peso +
                ", estado=" + estado +
                '}';
    }
}
