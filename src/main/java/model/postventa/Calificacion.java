package model.postventa;

import model.interfaces.Mostrable;
import model.producto.Producto;
import model.usuario.Cliente;

import exceptions.DatosInvalidosException;

import java.time.LocalDate;

public class Calificacion implements Mostrable {

    private int id;
    private Cliente cliente;
    private Producto producto;
    private int puntuacion;
    private String comentario;
    private LocalDate fecha;


    // Calificación nueva
    public Calificacion(Cliente cliente,
                        Producto producto,
                        int puntuacion,
                        String comentario) {

        this.cliente = cliente;
        this.producto = producto;
        setPuntuacion(puntuacion);
        this.comentario = comentario;
        this.fecha = LocalDate.now();
    }

    // Calificación recuperada de la BD
    public Calificacion(int id,
                        Cliente cliente,
                        Producto producto,
                        int puntuacion,
                        String comentario,
                        LocalDate fecha) {

        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        setPuntuacion(puntuacion);
        this.comentario = comentario;
        this.fecha = fecha;
    }


    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
    }

    public Cliente getCliente() { return cliente; }

    public Producto getProducto() { return producto; }

    public int getPuntuacion() { return puntuacion; }

    public void setPuntuacion(int puntuacion) throws DatosInvalidosException {
        if (puntuacion < 1 || puntuacion > 5) {
            throw new DatosInvalidosException("La puntuación debe estar entre 1 y 5.");
        }

        this.puntuacion = puntuacion;
    }

    public String getComentario() { return comentario; }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() { return fecha; }


    @Override
    public void mostrarInformacion() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", producto=" + producto +
                ", puntuacion=" + puntuacion +
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}