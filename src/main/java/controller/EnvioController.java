package controller;

import dao.EnvioDAO;
import model.envio.EstadoEnvio;
import model.envio.Envio;
import model.envio.TipoEnvio;

import exceptions.DatosInvalidosException;
import exceptions.EnvioNoEncontradoException;

import java.util.List;
import java.util.UUID;

public class EnvioController {

    private EnvioDAO envioDAO;


    public EnvioController(EnvioDAO envioDAO) {
        this.envioDAO = envioDAO;
    }


    public Envio crear(String direccion, String provincia, String ciudad,
                       String codigoPostal, TipoEnvio tipoEnvio) {

        Envio envio = new Envio(direccion, provincia, ciudad, codigoPostal, tipoEnvio);
        envioDAO.guardar(envio);

        return envio;
    }

    public Envio buscarPorId(int id) {

        Envio envio = envioDAO.buscarPorId(id);

        if (envio == null) {
            throw new EnvioNoEncontradoException("No existe un envio con id " + id);
        }

        return envio;
    }

    public Envio buscarPorCodigoSeguimiento(String codigo) {

        Envio envio = envioDAO.buscarPorCodigoSeguimiento(codigo);

        if (envio == null) {
            throw new EnvioNoEncontradoException(
                    "No existe un envio con codigo de seguimiento '" + codigo + "'"
            );
        }

        return envio;
    }

    public List<Envio> listar() {
        return envioDAO.obtenerTodos();
    }

    public void despachar(int id) {

        Envio envio = buscarPorId(id);

        if (envio.getCodigoSeguimiento() == null) {
            envio.asignarCodigoSeguimiento(generarCodigoSeguimiento());
        }

        envio.setEstado(EstadoEnvio.DESPACHADO);
        envioDAO.actualizar(envio);
    }

    public void actualizarEstado(int id, EstadoEnvio nuevoEstado) {

        if (nuevoEstado == null) {
            throw new DatosInvalidosException("Debe indicarse un estado de envio valido");
        }

        Envio envio = buscarPorId(id);
        envio.setEstado(nuevoEstado);
        envioDAO.actualizar(envio);
    }

    private String generarCodigoSeguimiento() {
        return "ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
