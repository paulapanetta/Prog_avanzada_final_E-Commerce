package exceptions;

public class PermisoDenegadoException extends RuntimeException{

    public PermisoDenegadoException(String mensaje){
        super(mensaje);
    }
}
