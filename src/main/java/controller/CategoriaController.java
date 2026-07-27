package controller;

import dao.CategoriaDAO;
import model.producto.Categoria;
import model.producto.EstadoCategoria;

import exceptions.CategoriaNoEncontradaException;
import exceptions.DatosInvalidosException;

import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;


    public CategoriaController(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }


    public Categoria crear(String nombre, String descripcion) {

        validarNombre(nombre);

        if (categoriaDAO.buscarPorNombre(nombre) != null) {
            throw new DatosInvalidosException(
                    "Ya existe una categoria con el nombre '" + nombre + "'"
            );
        }

        Categoria categoria = new Categoria(nombre, descripcion, EstadoCategoria.ACTIVA);
        categoriaDAO.guardar(categoria);

        return categoria;
    }

    public Categoria buscarPorId(int id) {

        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            throw new CategoriaNoEncontradaException(
                    "No existe una categoria con id " + id
            );
        }

        return categoria;
    }

    public List<Categoria> listar() {
        return categoriaDAO.obtenerTodos();
    }

    public void modificar(int id, String nombre, String descripcion) {

        validarNombre(nombre);

        Categoria categoria = buscarPorId(id);

        Categoria existente = categoriaDAO.buscarPorNombre(nombre);

        if (existente != null && existente.getId() != id) {
            throw new DatosInvalidosException(
                    "Ya existe otra categoria con el nombre '" + nombre + "'"
            );
        }

        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        categoriaDAO.actualizar(categoria);
    }

    public void activar(int id) {

        Categoria categoria = buscarPorId(id);
        categoria.setEstado(EstadoCategoria.ACTIVA);
        categoriaDAO.actualizar(categoria);
    }

    public void desactivar(int id) {

        Categoria categoria = buscarPorId(id);
        categoria.setEstado(EstadoCategoria.INACTIVA);
        categoriaDAO.actualizar(categoria);
    }

    public void eliminar(int id) {

        buscarPorId(id);
        categoriaDAO.eliminar(id);
    }


    private void validarNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new DatosInvalidosException(
                    "El nombre de la categoria no puede estar vacio"
            );
        }
    }
}
