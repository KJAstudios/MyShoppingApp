package com.example.myshoppingapp;

public class ShopData {
    private String name;
    private int imageResource;
    private String description;
    private int cost;
    private int itemId;
    private String keywords;
    private boolean purchased;

    ShopData(String name, int imageResource, String description, int cost, int itemId, String keywords) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.cost = cost;
        this.itemId = itemId;
        this.keywords = keywords;

    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public int getItemId() {
        return itemId;
    }

    public String getKeywords() {
        return keywords;
    }

    public boolean isPurchased() {
        return purchased;
    }
}
