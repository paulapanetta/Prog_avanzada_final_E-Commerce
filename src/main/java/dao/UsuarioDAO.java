package dao;

import model.usuario.Rol;
import model.usuario.Usuario;

import java.util.List;

public interface UsuarioDAO {

    void guardar(Usuario usuario);
    Usuario buscarPorId(int id);
    Usuario buscarPorEmail(String email);
    List<Usuario> obtenerTodos();
    void actualizar(Usuario usuario);
    void eliminar(int id);
    void cambiarRol(int id, Rol nuevoRol);

}