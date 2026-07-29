package dao.impl;

import dao.PagoDAO;
import database.DatabaseManager;
import exceptions.PagoNoEncontradoException;
import model.pago.*;
import strategy.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLitePagoDAO implements PagoDAO {


    @Override
    public void guardar(Pago pago) {

        String sql = """
            INSERT INTO pagos(
                orden_id,
                monto,
                fecha,
                estado,
                metodo
            )
            VALUES (?, ?, ?, ?, ?)
            """;


        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setInt(1, pago.getOrdenId());
            ps.setDouble(2, pago.getMonto());
            ps.setString(3, pago.getFecha().toString());
            ps.setString(4, pago.getEstado().name());
            ps.setString(5, obtenerTipoMetodo(pago.getMetodo()));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                pago.setId(keys.getInt(1));
            }

        }catch(SQLException e){

            throw new RuntimeException(
                    "Error al guardar pago.",
                    e
            );
        }
    }

    @Override
    public Pago buscarPorId(int id) {

        String sql = """
            SELECT *
            FROM pagos
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return mapearPago(rs);
            }

        }catch(SQLException e){

            throw new RuntimeException(
                    "Error al buscar pago.",
                    e
            );
        }

        throw new PagoNoEncontradoException(
                "No existe un pago con id: " + id
        );
    }

    @Override
    public List<Pago> obtenerTodos() {

        List<Pago> pagos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM pagos
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){

                pagos.add(
                        mapearPago(rs)
                );
            }

        }catch(SQLException e){

            throw new RuntimeException(
                    "Error al obtener pagos.",
                    e
            );
        }

        return pagos;
    }

    @Override
    public List<Pago> obtenerPorOrden(int ordenId) {

        List<Pago> pagos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM pagos
            WHERE orden_id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, ordenId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                pagos.add(
                        mapearPago(rs)
                );
            }

        }catch(SQLException e){

            throw new RuntimeException("Error al obtener pagos de la orden.", e);
        }

        return pagos;
    }

    @Override
    public void actualizar(Pago pago) {

        String sql = """
            UPDATE pagos
            SET estado = ?,
                metodo = ?
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(1, pago.getEstado().name());
            ps.setString(2, obtenerTipoMetodo(pago.getMetodo()));
            ps.setInt(3, pago.getId());

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al actualizar pago.", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = """
            DELETE FROM pagos
            WHERE id = ?
            """;

        try(
                Connection conn = DatabaseManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1,id);

            ps.executeUpdate();

        }catch(SQLException e){

            throw new RuntimeException("Error al eliminar pago.", e);
        }
    }

    private Pago mapearPago(ResultSet rs)
            throws SQLException {

        return new Pago(
                rs.getInt("id"),
                rs.getInt("orden_id"),
                rs.getDouble("monto"),
                LocalDate.parse(
                        rs.getString("fecha")
                ),
                EstadoPago.valueOf(
                        rs.getString("estado")
                ),
                crearMetodo(
                        rs.getString("metodo")
                )
        );
    }

    private String obtenerTipoMetodo(ProcesadorPago metodo) {

        if(metodo instanceof PagoEfectivo)
            return "EFECTIVO";

        if(metodo instanceof PagoBilleteraVirtual)
            return "BILLETERA_VIRTUAL";

        if(metodo instanceof PagoTarjetaCredito)
            return "TARJETA_CREDITO";

        if(metodo instanceof PagoTarjetaDebito)
            return "TARJETA_DEBITO";

        if(metodo instanceof PagoTransferencia)
            return "TRANSFERENCIA";

        throw new RuntimeException("Método de pago no soportado");
    }

    private ProcesadorPago crearMetodo(String metodo) {

        return switch(metodo) {

            case "EFECTIVO" ->
                    new PagoEfectivo();

            case "BILLETERA_VIRTUAL" ->
                    new PagoBilleteraVirtual();

            case "TARJETA_CREDITO" ->
                    new PagoTarjetaCredito();

            case "TARJETA_DEBITO" ->
                    new PagoTarjetaDebito();

            case "TRANSFERENCIA" ->
                    new PagoTransferencia();

            default ->
                    throw new RuntimeException(
                            "Método de pago desconocido: " + metodo
                    );
        };
    }
}
