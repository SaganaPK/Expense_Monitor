package com.example.expensemonitor.Model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private double quantity;
    private String unit;
    private double price;
    private String purchaseDate;
    private String storeName;
    private List<Double> priceHistory;

    public Product(String name, Double quantity, double price, String purchaseDate, String storeName) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.storeName = storeName;
        this.priceHistory = new ArrayList<>();
        this.priceHistory.add(price); // Add the initial price to the price history
    }
    public Product(){}
    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public List<Double> getPriceHistory() {
        return priceHistory;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
      //  this.priceHistory.add(price); // Add the new price to the price history
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", storeName='" + storeName + '\'' +
                ", priceHistory=" + priceHistory +
                '}';
    }
}
