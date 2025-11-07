package edu.austral.prog2.model.exceptions;

import edu.austral.prog2.model.entities.Order;

public class ServedOrderException extends RuntimeException {
    private final Order order;
    
    public ServedOrderException(String mensaje, Order order) {
        super(mensaje);
        this.order = order;
    }
    
    public ServedOrderException(String mensaje, Order order, Throwable causa) {
        super(mensaje, causa);
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }
}

