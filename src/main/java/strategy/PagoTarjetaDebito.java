package strategy;

public class PagoTarjetaDebito implements ProcesadorPago {

    @Override
    public boolean procesarPago(double monto) {

        System.out.println("Procesando pago con tarjeta de débito por $" + monto);

        return true;
    }
}