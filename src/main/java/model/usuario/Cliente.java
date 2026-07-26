package model.usuario;

import java.time.LocalDate;

public class Cliente extends Usuario {

    public Cliente(String nombre, String apellido, String email,
                   String password, LocalDate fechaAlta,
                   EstadoUsuario estado) {

        super(nombre, apellido, email, password,
                estado, Rol.CLIENTE);
    }

    public Cliente(int id, String nombre, String apellido,
                   String email, String password,
                   LocalDate fechaAlta,
                   EstadoUsuario estado) {

        super(id, nombre, apellido, email, password,
                fechaAlta, estado, Rol.CLIENTE);
    }

}
