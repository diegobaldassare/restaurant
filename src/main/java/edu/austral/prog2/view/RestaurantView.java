package edu.austral.prog2.view;

import edu.austral.prog2.controller.RestaurantController;
import edu.austral.prog2.model.entities.Order;
import edu.austral.prog2.model.entities.Plate;
import edu.austral.prog2.model.exceptions.PlateNotFoundException;
import edu.austral.prog2.model.exceptions.ServedOrderException;
import java.util.List;
import java.util.Scanner;

/**
 * View class that handles all user interface interactions.
 * Displays menus, prompts for input, and shows results.
 */
public class RestaurantView {
    private final RestaurantController controller;
    private final Scanner scanner;

    public RestaurantView(RestaurantController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("  Bienvenido a GourmetFlow: Sistema de Gestión de Restaurantes");
        System.out.println("========================================");
        System.out.println();
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");
            System.out.println();
            
            try {
                switch (opcion) {
                    case 1 -> registerPlate();
                    case 2 -> createOrder();
                    case 3 -> addPlateToOrder();
                    case 4 -> markOrderAsServed();
                    case 5 -> calculateOrderCost();
                    case 6 -> calculateNumberOfPlates();
                    case 7 -> listPendingOrders();
                    case 8 -> listMenu();
                    case 9 -> listAllOrders();
                    case 0 -> {
                        continuar = false;
                        System.out.println("¡Gracias por usar GourmetFlow!");
                    }
                    default -> System.out.println("Opción inválida. Por favor, seleccione una opción del menú.");
                }
            } catch (PlateNotFoundException e) {
                System.out.println("❌ Error: " + e.getMessage());
                if (leerSiNo("Querés registrar el plato en el menú? (si/no): ")) {
                    registerPlate();
                }
            } catch (ServedOrderException e) {
                System.out.println("❌ Error: " + e.getMessage());
                Order order = e.getOrder();
                if (order != null) {
                    System.out.println();
                    System.out.println("📋 Detalles del pedido:");
                    System.out.println("   ID: " + order.toString());
                }
            } catch (Exception e) {
                System.out.println("❌ Error inesperado, intente nuevamente...");
            } finally {
                if (continuar) {
                    System.out.println();
                    System.out.println("Presione Enter para continuar...");
                    scanner.nextLine();
                }
            }
        }
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("════════════════════════════════════════");
        System.out.println("            MENÚ PRINCIPAL");
        System.out.println("════════════════════════════════════════");
        System.out.println("1.  Registrar plato en el menú");
        System.out.println("2.  Crear nuevo pedido");
        System.out.println("3.  Agregar plato a un pedido");
        System.out.println("4.  Marcar pedido como servido");
        System.out.println("5.  Calcular costo total de un pedido");
        System.out.println("6.  Calcular número de platos en un pedido");
        System.out.println("7.  Listar pedidos pendientes");
        System.out.println("8.  Listar menú del restaurante");
        System.out.println("9.  Listar todos los pedidos");
        System.out.println("0.  Salir");
        System.out.println("════════════════════════════════════════");
    }

    private void registerPlate() {
        System.out.println("--- Registrar Plato en el Menú ---");
        String title = leerTexto("Ingrese el título del plato: ");
        double price = leerDecimal("Ingrese el precio del plato: ");
        
        controller.registerPlate(title, price);
        System.out.println("✅ Plato registrado exitosamente en el menú.");
    }

    private void createOrder() {
        System.out.println("--- Crear Nuevo Pedido ---");
        int table = leerEntero("Ingrese el número de mesa: ");
        
        Order newOrder = controller.createOrder(table);
        System.out.println("✅ Pedido creado exitosamente.");
        System.out.println("   ID del pedido: " + newOrder.getId());
        System.out.println("   Mesa: " + table);
        System.out.println("   Estado: PENDING");
    }

    private void addPlateToOrder() throws ServedOrderException, PlateNotFoundException {
        System.out.println("--- Agregar Plato a un Pedido ---");
        listPendingOrders();
        
        int orderId = leerEntero("Ingrese el ID del pedido: ");
        Order order = controller.findOrderById(orderId);
        
        if (order == null) {
            System.out.println("❌ Error: No se encontró un pedido con el ID " + orderId);
            return;
        }
        
        listMenu();
        int plateId = leerEntero("Ingrese el ID del plato a agregar: ");
        Plate plate = controller.findPlateById(plateId);
        
        if (plate == null) {
            System.out.println("❌ Error: No se encontró un plato con el ID " + plateId);
            return;
        }
        
        controller.addPlateToOrder(orderId, plate);
        System.out.println("✅ Plato '" + plate.getTitle() + "' agregado al pedido #" + orderId);
    }

    private void markOrderAsServed() {
        System.out.println("--- Marcar Pedido como Servido ---");
        listPendingOrders();
        
        int orderId = leerEntero("Ingrese el ID del pedido a marcar como servido: ");
        Order order = controller.findOrderById(orderId);
        
        if (order == null) {
            System.out.println("❌ Error: No se encontró un pedido con el ID " + orderId);
            return;
        }
        
        controller.markOrderAsServed(order);
        System.out.println("✅ Pedido #" + orderId + " marcado como SERVED.");
    }

    private void calculateOrderCost() {
        System.out.println("--- Calcular Costo Total de un Pedido ---");
        listAllOrders();
        
        int orderId = leerEntero("Ingrese el ID del pedido: ");
        Order order = controller.findOrderById(orderId);
        
        if (order == null) {
            System.out.println("❌ Error: No se encontró un pedido con el ID " + orderId);
            return;
        }
        
        double cost = controller.getOrderCost(order);
        System.out.println("💰 Costo total del pedido #" + orderId + ": $" + String.format("%.2f", cost));
    }

    private void calculateNumberOfPlates() {
        System.out.println("--- Calcular Número de Platos en un Pedido ---");
        listAllOrders();
        
        int orderId = leerEntero("Ingrese el ID del pedido: ");
        Order order = controller.findOrderById(orderId);
        
        if (order == null) {
            System.out.println("❌ Error: No se encontró un pedido con el ID " + orderId);
            return;
        }
        
        int numberOfPlates = controller.getTotalPlates(order);
        System.out.println("🍽️  Número total de platos en el pedido #" + orderId + ": " + numberOfPlates);
    }

    private void listPendingOrders() {
        System.out.println("--- Pedidos Pendientes ---");
        List<Order> pendingOrders = controller.getPendingOrders();
        
        if (pendingOrders.isEmpty()) {
            System.out.println("No hay pedidos pendientes.");
            return;
        }
        
        System.out.println("Total de pedidos pendientes: " + pendingOrders.size());
        System.out.println();
        for (Order o : pendingOrders) {
            System.out.println("  📋 Pedido #" + o.getId() + " - Mesa: " + o.getTable() + 
                             " - Estado: " + o.getStatus() + 
                             " - Platos: " + controller.getTotalPlates(o) +
                             " - Costo: $" + String.format("%.2f", controller.getOrderCost(o)));
        }
    }

    private void listMenu() {
        System.out.println("--- Menú del Restaurante ---");
        List<Plate> menu = controller.getMenu();
        
        if (menu.isEmpty()) {
            System.out.println("El menú está vacío.");
            return;
        }
        
        System.out.println("Total de platos en el menú: " + menu.size());
        System.out.println();
        for (Plate p : menu) {
            System.out.println("  🍴 ID: " + p.getId() + " - " + p.getTitle() + 
                             " - $" + String.format("%.2f", p.getPrice()));
        }
    }

    private void listAllOrders() {
        System.out.println("--- Todos los Pedidos ---");
        List<Order> allOrders = controller.getAllOrders();
        
        if (allOrders.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        
        System.out.println("Total de pedidos: " + allOrders.size());
        System.out.println();
        for (Order o : allOrders) {
            System.out.println("  📋 Pedido #" + o.getId() + " - Mesa: " + o.getTable() + 
                             " - Estado: " + o.getStatus() + 
                             " - Platos: " + controller.getTotalPlates(o) +
                             " - Costo: $" + String.format("%.2f", controller.getOrderCost(o)));
        }
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, ingrese un número entero válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor, ingrese un número decimal válido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private boolean leerSiNo(String mensaje) {
        System.out.print(mensaje);
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si");
    }
}

