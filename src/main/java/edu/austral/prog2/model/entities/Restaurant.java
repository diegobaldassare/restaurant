package edu.austral.prog2.model.entities;

import edu.austral.prog2.model.exceptions.ServedOrderException;
import edu.austral.prog2.model.exceptions.PlateNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static edu.austral.prog2.model.RepositoryUtil.findById;

public class Restaurant {
    private final List<Plate> menu = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

    public Restaurant() {
    }

    public void registerPlate(String title, double price) {
        Plate plate = new Plate(title, price);
        menu.add(plate);
    }

    public void addPlateToOrder(int orderId, Plate newPlate) throws ServedOrderException, PlateNotFoundException {
        if (!menu.contains(newPlate)) {
            throw new PlateNotFoundException("El plato ingresado no esta en el menu");
        }
        for (Order o : orders) {
            if (o.getId() == orderId) {
                o.addPlate(newPlate);
                return;
            }
        }
    }
    public void markOrderAsServed(Order order) {
        if (order != null && order.getStatus() == Status.PENDING) {
            for (Order o : orders) {
                if (o.getId() == order.getId()) {
                    o.nextStatus();
                    return;
                }
            }
        }
    }
    public List<Order> getActiveOrders() {
        return getPendingOrders();
    }
    public double getOrderCost(Order order) {
        if (order == null) return 0.0;
        return order.getTotalCost();
    }
    public int getTotalPlates(Order order) {
        if (order == null) return 0;
        return order.getTotalPlatesRecursive();
    }
    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order o : this.orders) {
            if (o.getStatus() == Status.PENDING) {
                pendingOrders.add(o);
            }
        }
        return pendingOrders;
    }
    
    public List<Plate> getMenu() {
        return new ArrayList<>(menu);
    }
    
    public Order findOrderById(int orderId) {
        return findById(orders, orderId);
    }
    
    public Plate findPlateById(int plateId) {
        return findById(menu, plateId);
    }
    
    public Order createOrder(int table) {
        Order newOrder = new Order(table);
        orders.add(newOrder);
        return newOrder;
    }
    
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
    
    /**
     * Retorna los pedidos ordenados por Status en orden natural del enum.
     * PENDING primero (más urgente), luego SERVED.
     */
    public List<Order> getOrdersSortedByStatus() {
        List<Order> sortedOrders = new ArrayList<>(orders);
        sortedOrders.sort(Order.byStatus());
        return sortedOrders;
    }
}

