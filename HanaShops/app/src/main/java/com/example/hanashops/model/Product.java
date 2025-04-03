package com.example.hanashops.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private String price;

    // Constructor vacío requerido por Retrofit
    public Product() {
    }

    // Constructor con parámetros
    public Product(int id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
