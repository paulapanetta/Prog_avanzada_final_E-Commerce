package exceptions;

public class PagoRechazadoException extends RuntimeException{

    public PagoRechazadoException(String mensaje) {
        super(mensaje);
    }
}
