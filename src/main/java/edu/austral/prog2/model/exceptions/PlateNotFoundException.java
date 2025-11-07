package edu.austral.prog2.model.exceptions;

public class PlateNotFoundException extends RuntimeException {
    public PlateNotFoundException(String mensaje) {
        super(mensaje);
    }
    
    public PlateNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

