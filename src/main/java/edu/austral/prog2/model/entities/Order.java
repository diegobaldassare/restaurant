package edu.austral.prog2.model.entities;

import edu.austral.prog2.model.Identifiable;
import edu.austral.prog2.model.exceptions.ServedOrderException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Order implements Identifiable {

    private final int id;
    private static int idCounter = 1;
    private Status status = Status.PENDING;
    private final List<Plate> plates = new ArrayList<>();
    private final int table;

    public Order(int table) {
        this.id = ++idCounter;
        this.table = table;
    }

    public double getTotalCost() {
        double total = 0;
        for (Plate p : this.plates) {
            total += p.getPrice();
        }
        return total;
    }
    public void addPlate(Plate plate) throws ServedOrderException {
        if (this.status != Status.PENDING) {
            throw new ServedOrderException("Error: No se pueden agregar platos a un pedido que no esté PENDING.", this);
        }
        if (plate != null) {
            this.plates.add(plate);
        }
    }
    /**
     * Recursively counts the total number of plates in this order.
     * Uses a clean recursive approach with index parameter.
     * 
     * @return the total number of plates
     */
    public int getTotalPlatesRecursive() {
        return countPlatesRecursive(0);
    }
    
    /**
     * Helper method that recursively counts plates starting from the given index.
     * Base case: index is beyond the list size, return 0
     * Recursive case: 1 + count of plates from the next index
     * 
     * @param index the starting index to count from
     * @return the number of plates from this index to the end
     */
    private int countPlatesRecursive(int index) {
        if (index >= this.plates.size()) {
            return 0;
        }
        return 1 + countPlatesRecursive(index + 1);
    }
    /**
     * Avanza al siguiente estado del pedido.
     * De esta manera garantizamos el encapsulamiento
     * porque no permito transicionar a cualquier estado.
     * PENDING -> SERVED
     * SERVED -> SERVED (permanece en el mismo estado)
     */
    public void nextStatus() {
        if (this.status == Status.PENDING) {
            this.status = Status.SERVED;
        }
        // Si ya está SERVED, permanece en SERVED
    }

    public int getId() {
        return this.id;
    }
    public Status getStatus() {
        return this.status;
    }
    public int getTable() {
        return this.table;
    }
    public List<Plate> getPlates() {
        return new ArrayList<>(plates);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", table=" + table +
                ", status=" + status +
                '}';
    }
    
    /**
     * Comparator para ordenar pedidos por Status en orden natural del enum.
     * Ordena según el orden de declaración del enum (SERVED, luego PENDING).
     */
    public static Comparator<Order> byStatus() {
        return Comparator.comparing(Order::getStatus);
    }
}

