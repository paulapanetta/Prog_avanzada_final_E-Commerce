package model.usuario;

import java.time.LocalDate;

public class ResponsableLogistica extends Usuario {

    public ResponsableLogistica(String nombre, String apellido, String email,
                          String password, LocalDate fechaAlta,
                          EstadoUsuario estado) {

        super(nombre, apellido, email, password,
                estado, Rol.RESPONSABLE_LOGISTICA);
    }

    public ResponsableLogistica(int id, String nombre, String apellido,
                          String email, String password,
                          LocalDate fechaAlta,
                          EstadoUsuario estado) {

        super(id, nombre, apellido, email, password,
                fechaAlta, estado, Rol.RESPONSABLE_LOGISTICA);
    }
}
