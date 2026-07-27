package strategy;

public class PagoEfectivo implements ProcesadorPago {

    @Override
    public boolean procesarPago(double monto) {

        System.out.println("El pago en efectivo se realizará al momento de la entrega.");

        return true;
    }
}
