package strategy;

public class PagoTransferencia implements ProcesadorPago {

    @Override
    public boolean procesarPago(double monto) {

        System.out.println("Procesando transferencia bancaria por $" + monto);

        return true;

    }
}
