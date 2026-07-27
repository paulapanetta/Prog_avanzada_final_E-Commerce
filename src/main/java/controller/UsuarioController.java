package controller;

import dao.UsuarioDAO;
import model.usuario.EstadoUsuario;
import model.usuario.Rol;
import model.usuario.Usuario;

import exceptions.DatosInvalidosException;
import exceptions.PermisoDenegadoException;
import exceptions.UsuarioNoEncontradoException;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;


    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }


    public void registrar(Usuario usuario) {

        if (usuario == null) {
            throw new DatosInvalidosException("El usuario no puede estar vacio");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new DatosInvalidosException("El email no puede estar vacio");
        }

        if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
            throw new DatosInvalidosException(
                    "Ya existe un usuario registrado con el email '" + usuario.getEmail() + "'"
            );
        }

        usuarioDAO.guardar(usuario);
    }

    public Usuario buscarPorId(int id) {

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioNoEncontradoException(
                    "No existe un usuario con id " + id
            );
        }

        return usuario;
    }

    public Usuario buscarPorEmail(String email) {

        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsuarioNoEncontradoException(
                    "No existe un usuario con email '" + email + "'"
            );
        }

        return usuario;
    }

    public List<Usuario> listar() {
        return usuarioDAO.obtenerTodos();
    }

    public void modificar(int id, String nombre, String apellido, String email) {

        if (email == null || email.isBlank()) {
            throw new DatosInvalidosException("El email no puede estar vacío");
        }

        Usuario usuario = buscarPorId(id);

        Usuario existente = usuarioDAO.buscarPorEmail(email);

        if (existente != null && existente.getId() != id) {
            throw new DatosInvalidosException(
                    "Ya existe otro usuario con el email '" + email + "'"
            );
        }

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);

        usuarioDAO.actualizar(usuario);
    }

    public void eliminar(int id) {

        buscarPorId(id);
        usuarioDAO.eliminar(id);
    }

    public void activar(int id) {

        Usuario usuario = buscarPorId(id);
        usuario.activar();
        usuarioDAO.actualizar(usuario);
    }

    public void desactivar(int id) {

        Usuario usuario = buscarPorId(id);
        usuario.desactivar();
        usuarioDAO.actualizar(usuario);
    }

    public void bloquear(int id) {

        Usuario usuario = buscarPorId(id);
        usuario.setEstado(EstadoUsuario.BLOQUEADO);
        usuarioDAO.actualizar(usuario);
    }

    public void validarPermiso(Usuario usuario, Rol... rolesPermitidos) {

        for (Rol rol : rolesPermitidos) {
            if (usuario.getRol() == rol) {
                return;
            }
        }

        throw new PermisoDenegadoException(
                "El usuario '" + usuario.getEmail() + "' no tiene permisos para realizar esta accion"
        );
    }
}
