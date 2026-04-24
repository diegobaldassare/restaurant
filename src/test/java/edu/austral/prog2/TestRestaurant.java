package edu.austral.prog2;

import edu.austral.prog2.model.entities.Order;
import edu.austral.prog2.model.entities.Plate;
import edu.austral.prog2.model.entities.Restaurant;
import edu.austral.prog2.model.exceptions.PlateNotFoundException;
import edu.austral.prog2.model.exceptions.ServedOrderException;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestRestaurant {
    private Restaurant initializeRestaurant() {
        Restaurant r = new Restaurant();
        r.registerPlate("Pizza", 15.00);
        r.registerPlate("Refresco", 3.00);
        r.registerPlate("Postre", 7.50);
        return r;
    }

    @Test
    void testTotalCostAndRecursiveCount() throws ServedOrderException, PlateNotFoundException {
        Restaurant r = initializeRestaurant();

        // Get plates directly from menu to ensure they're the same objects
        List<Plate> menu = r.getMenu();
        Plate pizza = menu.get(0); // First registered plate
        Plate beverage = menu.get(1); // Second registered plate

        Order order1 = r.createOrder(1);

        r.addPlateToOrder(order1.getId(), pizza);
        r.addPlateToOrder(order1.getId(), pizza);
        r.addPlateToOrder(order1.getId(), beverage);
        r.addPlateToOrder(order1.getId(), beverage);
        r.addPlateToOrder(order1.getId(), beverage);

        double expectedCost = 39.00;
        double calculatedCost = r.getOrderCost(order1);

        int expectedPlates = 5;
        int calculatedPlates = r.getTotalPlates(order1);

        assertEquals(expectedCost, calculatedCost, 0.001, "El costo total del pedido (39.00) es incorrecto.");
        assertEquals(expectedPlates, calculatedPlates, "El conteo recursivo de platos debe devolver 5.");
        assertFalse(r.getPendingOrders().isEmpty(), "El pedido debe estar en la lista de pedidos pendientes.");
    }

    @Test
    void testServedStatusAndFilterOrders() throws ServedOrderException, PlateNotFoundException {
        Restaurant r = initializeRestaurant();

        // Get plate directly from menu to ensure it's the same object
        List<Plate> menu = r.getMenu();
        Plate desert = menu.get(2); // Third registered plate

        Order orderA = r.createOrder(10);
        Order orderB = r.createOrder(11);
        Order orderC = r.createOrder(12);

        r.addPlateToOrder(orderA.getId(), desert);

        assertEquals(3, r.getPendingOrders().size(), "Al inicio, deben haber 3 pedidos pendientes.");

        r.markOrderAsServed(orderA);
        r.markOrderAsServed(orderC);
        
        // Intentar agregar plato a un pedido servido debe lanzar excepción
        assertThrows(ServedOrderException.class, () -> {
            r.addPlateToOrder(orderA.getId(), desert);
        });

        List<Order> pendientes = r.getPendingOrders();
        int platosEnServido = r.getTotalPlates(orderA);

        assertEquals(1, pendientes.size(), "Después de servir 2, solo debe quedar 1 pedido pendiente (Pedido B).");
        assertEquals(orderB.getId(), pendientes.get(0).getId(), "El ID del pedido pendiente debe ser el del pedido B.");

        assertEquals(1, platosEnServido, "No se debe modificar un pedido SERVED. El conteo debe ser 1.");
    }
}
