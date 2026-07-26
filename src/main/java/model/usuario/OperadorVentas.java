package model.usuario;

import java.time.LocalDate;

public class OperadorVentas extends Usuario {

    public OperadorVentas(String nombre, String apellido, String email,
                         String password, LocalDate fechaAlta,
                         EstadoUsuario estado) {

        super(nombre, apellido, email, password,
                fechaAlta, estado, Rol.OPERADOR_VENTAS);
    }

    public OperadorVentas(int id, String nombre, String apellido,
                         String email, String password,
                         LocalDate fechaAlta,
                         EstadoUsuario estado) {

        super(id, nombre, apellido, email, password,
                fechaAlta, estado, Rol.OPERADOR_VENTAS);
    }
}
