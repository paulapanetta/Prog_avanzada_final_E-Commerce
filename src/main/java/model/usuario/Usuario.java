package model.usuario;

import model.interfaces.Mostrable;

import java.time.LocalDate;

public abstract class Usuario implements Mostrable{

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private LocalDate fechaAlta;
    private EstadoUsuario estado;
    private final Rol rol;


    // Constructor para usuario nuevo
    public Usuario(String nombre, String apellido, String email,
                   String password, EstadoUsuario estado, Rol rol) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.fechaAlta = LocalDate.now();
        this.estado = estado;
        this.rol = rol;
    }

    // Constructor para recuperar usuario de la BD
    public Usuario(int id, String nombre, String apellido,
                   String email, String password,
                   LocalDate fechaAlta,
                   EstadoUsuario estado, Rol rol) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.rol = rol;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public Rol getRol() { return rol; }



    public void activar() {
        this.estado = EstadoUsuario.ACTIVO;
    }


    public void desactivar() {
        this.estado = EstadoUsuario.INACTIVO;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", estado=" + estado +
                ", rol=" + rol +
                '}';
    }

}
