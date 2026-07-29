package factory;

import dao.*;

public interface DAOFactory {

    UsuarioDAO crearUsuarioDAO();

    ProductoDAO crearProductoDAO();

    CategoriaDAO crearCategoriaDAO();

    OrdenDAO crearOrdenDAO();

    CarritoDAO crearCarritoDAO();

    PagoDAO crearPagoDAO();

    EnvioDAO crearEnvioDAO();

    ReclamoDAO crearReclamoDAO();

    DevolucionDAO crearDevolucionDAO();

    CalificacionDAO crearCalificacionDAO();

    InventarioDAO crearInventarioDAO();

}
