package com.example.hanashops.model;

public class ProductUI {
    private String name;
    private int layoutResource;

    public ProductUI(String name, int layoutResource) {
        this.name = name;
        this.layoutResource = layoutResource;
    }

    public String getName() {
        return name;
    }

    public int getLayoutResource() {
        return layoutResource;
    }
}
