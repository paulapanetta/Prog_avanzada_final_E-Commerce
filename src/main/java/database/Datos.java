package database;

import factory.*;

import dao.*;
import model.carrito.*;
import model.envio.*;
import model.inventario.*;
import model.orden.*;
import model.pago.*;
import model.producto.*;
import model.usuario.*;
import strategy.*;
import model.postventa.*;


import java.time.LocalDate;

public class Datos {

    public static void cargar() {

        DAOFactory factory = new SQLiteDAOFactory();

        UsuarioDAO usuarioDAO = factory.crearUsuarioDAO();
        CategoriaDAO categoriaDAO = factory.crearCategoriaDAO();
        ProductoDAO productoDAO = factory.crearProductoDAO();
        InventarioDAO inventarioDAO = factory.crearInventarioDAO();
        CarritoDAO carritoDAO = factory.crearCarritoDAO();
        OrdenDAO ordenDAO = factory.crearOrdenDAO();
        PagoDAO pagoDAO = factory.crearPagoDAO();
        EnvioDAO envioDAO = factory.crearEnvioDAO();
        ReclamoDAO reclamoDAO = factory.crearReclamoDAO();
        DevolucionDAO devolucionDAO = factory.crearDevolucionDAO();
        CalificacionDAO calificacionDAO = factory.crearCalificacionDAO();

        if (!usuarioDAO.obtenerTodos().isEmpty()) {
            return;
        }


        Administrador admin = new Administrador(
                        "Admin",
                        "Sistema",
                        "admin@cafeteria.com",
                        "admin123",
                        LocalDate.now(),
                        EstadoUsuario.ACTIVO
                );


        OperadorVentas operador = new OperadorVentas(
                        "Mica",
                        "Suarez",
                        "ventas@cafeteria.com",
                        "ventas123",
                        LocalDate.now(),
                        EstadoUsuario.ACTIVO
                );


        ResponsableLogistica logistica = new ResponsableLogistica(
                        "Nico",
                        "Fernandez",
                        "logistica@cafeteria.com",
                        "logi123",
                        LocalDate.now(),
                        EstadoUsuario.ACTIVO
                );


        Cliente sofia = new Cliente(
                        "Sofia",
                        "Gomez",
                        "sofia@mail.com",
                        "sofia123",
                        LocalDate.now(),
                        EstadoUsuario.ACTIVO
                );


        Cliente tomas = new Cliente(
                        "Tomas",
                        "Diaz",
                        "tomas@mail.com",
                        "tomas123",
                        LocalDate.now(),
                        EstadoUsuario.ACTIVO
                );


        usuarioDAO.guardar(admin);
        usuarioDAO.guardar(operador);
        usuarioDAO.guardar(logistica);
        usuarioDAO.guardar(sofia);
        usuarioDAO.guardar(tomas);


        Categoria cafeGrano = new Categoria(
                        "Café en grano",
                        "Cafés en grano y molidos para preparar en casa",
                        EstadoCategoria.ACTIVA
                );


        Categoria bebidasCalientes = new Categoria(
                        "Bebidas calientes",
                        "Cafés, tés e infusiones listas para tomar",
                        EstadoCategoria.ACTIVA
                );


        Categoria bebidasFrias = new Categoria(
                        "Bebidas frías",
                        "Frappés, cold brew y bebidas heladas",
                        EstadoCategoria.ACTIVA
                );


        Categoria pasteleria = new Categoria(
                        "Pastelería",
                        "Medialunas, tortas, cookies y productos de panadería",
                        EstadoCategoria.ACTIVA
                );


        Categoria sandwiches = new Categoria(
                        "Sandwiches",
                        "Sandwiches y tostados salados",
                        EstadoCategoria.ACTIVA
                );


        Categoria merchandising = new Categoria(
                        "Merchandising",
                        "Accesorios de cafetería y productos digitales",
                        EstadoCategoria.ACTIVA
                );

        categoriaDAO.guardar(cafeGrano);
        categoriaDAO.guardar(bebidasCalientes);
        categoriaDAO.guardar(bebidasFrias);
        categoriaDAO.guardar(pasteleria);
        categoriaDAO.guardar(sandwiches);
        categoriaDAO.guardar(merchandising);


        ProductoFisico cafeMolido = new ProductoFisico(
                        "Café molido Blend Casa",
                        "Blend propio de la casa, tueste medio, 500g",
                        4200.00,
                        cafeGrano,
                        0.500,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico cafeBourbon = new ProductoFisico(
                        "Café en grano Bourbon Rojo",
                        "Grano 100% arábica, tueste claro, 250g",
                        5100.00,
                        cafeGrano,
                        0.250,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico espresso = new ProductoFisico(
                        "Espresso doble",
                        "Doble shot de espresso",
                        1800.00,
                        bebidasCalientes,
                        0.050,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico latte = new ProductoFisico(
                        "Latte",
                        "Espresso con leche vaporizada",
                        2200.00,
                        bebidasCalientes,
                        0.300,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico teVerde = new ProductoFisico(
                        "Té verde en hebras",
                        "Té verde importado, 100g",
                        2600.00,
                        bebidasCalientes,
                        0.100,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico coldBrew = new ProductoFisico(
                        "Cold brew",
                        "Café de extracción en frío, 400ml",
                        2800.00,
                        bebidasFrias,
                        0.400,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico frappe = new ProductoFisico(
                        "Frappé de caramelo",
                        "Café frappé con caramelo y crema",
                        3200.00,
                        bebidasFrias,
                        0.350,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico medialuna = new ProductoFisico(
                        "Medialuna de manteca",
                        "Medialuna artesanal, unidad",
                        900.00,
                        pasteleria,
                        0.060,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico cheesecake = new ProductoFisico(
                        "Cheesecake individual",
                        "Porción individual de cheesecake de frutos rojos",
                        3400.00,
                        pasteleria,
                        0.180,
                        EstadoProducto.ACTIVO
                );


        ProductoFisico sandwich = new ProductoFisico(
                        "Sandwich de jamón y queso",
                        "Tostado de jamón y queso en pan de campo",
                        2900.00,
                        sandwiches,
                        0.220,
                        EstadoProducto.SIN_STOCK
                );


        ProductoDigital tarjeta = new ProductoDigital(
                        "Tarjeta de regalo digital $10.000",
                        "Gift card digital para canjear en cualquier sucursal",
                        10000.00,
                        merchandising,
                        0,
                        EstadoProducto.ACTIVO
                );


        ProductoDigital curso = new ProductoDigital(
                        "Curso online de método V60",
                        "Curso en video para preparar café método V60",
                        6500.00,
                        merchandising,
                        0,
                        EstadoProducto.ACTIVO
                );

        ProductoImportado yirgacheffe = new ProductoImportado(
                        "Café Yirgacheffe (Etiopía)",
                        "Café de origen único, notas florales y cítricas, 250g",
                        8900.00,
                        cafeGrano,
                        0.250,
                        EstadoProducto.ACTIVO
                );


        ProductoImportado earlGrey = new ProductoImportado(
                        "Té Earl Grey inglés",
                        "Té negro importado con bergamota, lata 100g",
                        4700.00,
                        bebidasCalientes,
                        0.150,
                        EstadoProducto.ACTIVO
                );


        ProductoImportado chocolate = new ProductoImportado(
                        "Chocolate belga en trozos",
                        "Chocolate importado para acompañar el café, 200g",
                        5600.00,
                        pasteleria,
                        0.200,
                        EstadoProducto.ACTIVO
                );



        productoDAO.guardar(cafeMolido);
        productoDAO.guardar(cafeBourbon);
        productoDAO.guardar(espresso);
        productoDAO.guardar(latte);
        productoDAO.guardar(teVerde);
        productoDAO.guardar(coldBrew);
        productoDAO.guardar(frappe);
        productoDAO.guardar(medialuna);
        productoDAO.guardar(cheesecake);
        productoDAO.guardar(sandwich);
        productoDAO.guardar(tarjeta);
        productoDAO.guardar(curso);
        productoDAO.guardar(yirgacheffe);
        productoDAO.guardar(earlGrey);
        productoDAO.guardar(chocolate);


        Inventario inventario = new Inventario(1);


        inventario.agregarProducto(new StockProducto(cafeMolido, 40));

        inventario.agregarProducto(new StockProducto(cafeBourbon, 25));

        inventario.agregarProducto(new StockProducto(espresso, 100));

        inventario.agregarProducto(new StockProducto(latte, 80));

        inventario.agregarProducto(new StockProducto(teVerde, 30));

        inventario.agregarProducto(new StockProducto(coldBrew, 50));

        inventario.agregarProducto(new StockProducto(frappe, 35));

        inventario.agregarProducto(new StockProducto(medialuna, 60));

        inventario.agregarProducto(new StockProducto(cheesecake, 20));

        inventario.agregarProducto(new StockProducto(sandwich, 0));

        inventario.agregarProducto(new StockProducto(tarjeta, 999));

        inventario.agregarProducto(new StockProducto(curso, 999));

        inventario.agregarProducto(new StockProducto(yirgacheffe, 15));

        inventario.agregarProducto(new StockProducto(earlGrey, 22));

        inventario.agregarProducto(new StockProducto(chocolate, 18) );



        inventarioDAO.guardar(inventario);

        Carrito carritoSofia = new Carrito(sofia);

        carritoSofia.agregarProducto(new StockProducto(cafeMolido, 40), 2);
        carritoSofia.agregarProducto(new StockProducto(medialuna, 60), 3);

        carritoDAO.guardar(carritoSofia);


        Carrito carritoTomas = new Carrito(tomas);

        carritoTomas.agregarProducto(new StockProducto(coldBrew, 50), 1);

        carritoDAO.guardar(carritoTomas);

        Orden ordenSofia = new Orden(
                        "ORD-001",
                        carritoSofia,
                        null
                );

        ordenDAO.guardar(ordenSofia);


        Orden ordenTomas = new Orden(
                        "ORD-002",
                        carritoTomas,
                        null
                );

        ordenDAO.guardar(ordenTomas);


        Envio envioSofia = new Envio(
                        ordenSofia.getId(),
                        "Av. Siempre Viva 123",
                        "Buenos Aires",
                        "CABA",
                        "1000",
                        TipoEnvio.ESTANDAR
                );

        envioDAO.guardar(envioSofia);



        Envio envioTomas = new Envio(
                        ordenTomas.getId(),
                        "Calle Terra 456",
                        "Buenos Aires",
                        "La Plata",
                        "1900",
                        TipoEnvio.EXPRES
                );

        envioDAO.guardar(envioTomas);


        Pago pagoSofia = new Pago(
                        ordenSofia.getId(),
                        ordenSofia.getTotal(),
                        new PagoTarjetaCredito()
                );


        pagoDAO.guardar(pagoSofia);
        ordenSofia.asignarPago(pagoSofia);


        Pago pagoTomas = new Pago(
                        ordenTomas.getId(),
                        ordenTomas.getTotal(),
                        new PagoBilleteraVirtual()
                );


        pagoDAO.guardar(pagoTomas);
        ordenTomas.asignarPago(pagoTomas);


        Reclamo reclamoSofia = new Reclamo(
                        sofia,
                        ordenSofia,
                        "El pedido llegó incompleto."
                );

        reclamoDAO.guardar(reclamoSofia);


        Devolucion devolucionTomas = new Devolucion(
                        tomas,
                        cafeMolido,
                        "El producto llegó dañado."
                );

        devolucionDAO.guardar(devolucionTomas);


        Calificacion calificacionSofia = new Calificacion(
                        sofia,
                        cafeMolido,
                        5,
                        "Excelente calidad y sabor."
                );

        calificacionDAO.guardar(calificacionSofia);

    }
}
