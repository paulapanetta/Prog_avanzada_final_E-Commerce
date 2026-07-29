package factory;

import dao.*;
import dao.impl.*;
import factory.DAOFactory;

public class SQLiteDAOFactory implements DAOFactory {


    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private CategoriaDAO categoriaDAO;
    private OrdenDAO ordenDAO;
    private PagoDAO pagoDAO;
    private EnvioDAO envioDAO;
    private ReclamoDAO reclamoDAO;
    private DevolucionDAO devolucionDAO;
    private CalificacionDAO calificacionDAO;


    @Override
    public UsuarioDAO crearUsuarioDAO() {

        if(usuarioDAO == null){
            usuarioDAO = new SQLiteUsuarioDAO();
        }

        return usuarioDAO;
    }


    @Override
    public CategoriaDAO crearCategoriaDAO() {

        if(categoriaDAO == null){
            categoriaDAO = new SQLiteCategoriaDAO();
        }

        return categoriaDAO;
    }


    @Override
    public ProductoDAO crearProductoDAO() {

        if(productoDAO == null){

            productoDAO =
                    new SQLiteProductoDAO(
                            crearCategoriaDAO()
                    );
        }

        return productoDAO;
    }


    @Override
    public PagoDAO crearPagoDAO() {

        if(pagoDAO == null){
            pagoDAO = new SQLitePagoDAO();
        }

        return pagoDAO;
    }


    @Override
    public EnvioDAO crearEnvioDAO() {

        if(envioDAO == null){
            envioDAO = new SQLiteEnvioDAO();
        }

        return envioDAO;
    }


    @Override
    public OrdenDAO crearOrdenDAO() {

        if(ordenDAO == null){

            ordenDAO =
                    new SQLiteOrdenDAO(
                            crearUsuarioDAO(),
                            crearProductoDAO(),
                            crearPagoDAO(),
                            crearEnvioDAO()
                    );
        }

        return ordenDAO;
    }


    @Override
    public ReclamoDAO crearReclamoDAO() {

        if(reclamoDAO == null){

            reclamoDAO =
                    new SQLiteReclamoDAO(
                            crearUsuarioDAO(),
                            crearOrdenDAO()
                    );
        }

        return reclamoDAO;
    }


    @Override
    public DevolucionDAO crearDevolucionDAO() {

        if(devolucionDAO == null){

            devolucionDAO =
                    new SQLiteDevolucionDAO(
                            crearUsuarioDAO(),
                            crearProductoDAO()
                    );
        }

        return devolucionDAO;
    }


    @Override
    public CalificacionDAO crearCalificacionDAO() {

        if(calificacionDAO == null){

            calificacionDAO =
                    new SQLiteCalificacionDAO(
                            crearUsuarioDAO(),
                            crearProductoDAO()
                    );
        }

        return calificacionDAO;
    }
}