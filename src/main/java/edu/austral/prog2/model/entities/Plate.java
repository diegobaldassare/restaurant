package edu.austral.prog2.model.entities;

import edu.austral.prog2.model.Identifiable;

public class Plate implements Identifiable {

    private static int idCounter = 1;
    private final int id;
    private String title;
    private double price;

    public Plate(String title, double price) {
        this.id = ++idCounter;
        this.title = title;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plate plate = (Plate) o;
        return id == plate.id;
    }

    /**
     * Example that compares plates by their price.
     */
    public double compareTo(Plate other) {
        return this.getPrice() - other.getPrice();
    }

    @Override
    public String toString() {
        return "Plate{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}

