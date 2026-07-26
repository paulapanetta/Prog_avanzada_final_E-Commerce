package exceptions;

public class CarritoVacioException extends RuntimeException{

    public CarritoVacioException(String mensaje) {
        super(mensaje);
    }
}
