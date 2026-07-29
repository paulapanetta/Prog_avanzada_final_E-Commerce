package exceptions;

public class PagoNoEncontradoException extends RuntimeException {
    public PagoNoEncontradoException(String message) {
        super(message);
    }
}
