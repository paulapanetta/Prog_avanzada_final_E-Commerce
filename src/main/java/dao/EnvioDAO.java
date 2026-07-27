package dao;

import model.envio.Envio;

import java.util.List;

public interface EnvioDAO {

    void guardar(Envio envio);
    Envio buscarPorId(int id);
    Envio buscarPorCodigoSeguimiento(String codigoSeguimiento);
    List<Envio> obtenerTodos();
    void actualizar(Envio envio);
    void eliminar(int id);

}
