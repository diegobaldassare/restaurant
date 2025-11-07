package edu.austral.prog2.controller;

import edu.austral.prog2.model.entities.Order;
import edu.austral.prog2.model.entities.Plate;
import edu.austral.prog2.model.entities.Restaurant;
import edu.austral.prog2.model.exceptions.PlateNotFoundException;
import edu.austral.prog2.model.exceptions.ServedOrderException;
import java.util.List;

/**
 * Controller that coordinates between the model (Restaurante) and the view.
 * Handles business logic and user interactions.
 */
public class RestaurantController {
    private final Restaurant restaurant;

    public RestaurantController() {
        this.restaurant = new Restaurant();
    }

    public void registerPlate(String title, double price) {
        restaurant.registerPlate(title, price);
    }

    public Order createOrder(int table) {
        return restaurant.createOrder(table);
    }

    public void addPlateToOrder(int orderId, Plate plate) throws ServedOrderException, PlateNotFoundException {
        restaurant.addPlateToOrder(orderId, plate);
    }

    public void markOrderAsServed(Order order) {
        restaurant.markOrderAsServed(order);
    }

    public double getOrderCost(Order order) {
        return restaurant.getOrderCost(order);
    }

    public int getTotalPlates(Order order) {
        return restaurant.getTotalPlates(order);
    }

    public List<Order> getPendingOrders() {
        return restaurant.getPendingOrders();
    }

    public List<Plate> getMenu() {
        return restaurant.getMenu();
    }

    public List<Order> getAllOrders() {
        return restaurant.getOrders();
    }

    public Order findOrderById(int orderId) {
        return restaurant.findOrderById(orderId);
    }

    public Plate findPlateById(int plateId) {
        return restaurant.findPlateById(plateId);
    }
}

