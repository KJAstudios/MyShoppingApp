package com.example.myshoppingapp.databasehandler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShopItem {

    @PrimaryKey
    @NonNull
    public int itemID;

    @ColumnInfo
    public String itemName;

    @ColumnInfo
    public int image;

    @ColumnInfo
    public String description;

    @ColumnInfo
    public String keywords;

    @ColumnInfo
    public int price;

    public ShopItem(int itemID, String itemName, int image, String description, String keywords, int price) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.image = image;
        this.description = description;
        this.keywords = keywords;
        this.price = price;
    }

    public String toJson() {
        String returnString = "{name: Kitten, image: kitten, description: It purrs, cost: 1, itemId: 17, keywords: animal:mammal:pe' }";
            return returnString;
}
}


