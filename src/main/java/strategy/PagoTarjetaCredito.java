package strategy;

public class PagoTarjetaCredito implements ProcesadorPago{

    @Override
    public boolean procesarPago(double monto) {

        System.out.println("Procesando pago con tarjeta de crédito por $" + monto);

        return true;
    }
}
