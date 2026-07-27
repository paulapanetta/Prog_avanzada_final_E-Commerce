package strategy;

public class PagoBilleteraVirtual implements ProcesadorPago{

    @Override
    public boolean procesarPago(double monto) {

        System.out.println("Procesando pago con billetera virtual por $" + monto);

        return true;
    }

}
