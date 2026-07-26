package model.usuario;

import java.time.LocalDate;

public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String email,
                   String password, LocalDate fechaAlta,
                   EstadoUsuario estado) {

        super(nombre, apellido, email, password,
                estado, Rol.ADMINISTRADOR);
    }

    public Administrador(int id, String nombre, String apellido,
                   String email, String password,
                   LocalDate fechaAlta,
                   EstadoUsuario estado) {

        super(id, nombre, apellido, email, password,
                fechaAlta, estado, Rol.ADMINISTRADOR);
    }
}
