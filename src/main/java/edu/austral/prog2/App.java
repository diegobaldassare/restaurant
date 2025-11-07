package edu.austral.prog2;

import edu.austral.prog2.controller.RestaurantController;
import edu.austral.prog2.view.RestaurantView;

/**
 * Main application entry point.
 * Initializes the MVC components and starts the application.
 */
public class App {
    public static void main(String[] args) {
        RestaurantController controller = new RestaurantController();
        RestaurantView view = new RestaurantView(controller);
        view.start();
    }
}
