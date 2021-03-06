package com.example.myshoppingapp.databasehandler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

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

    public ShopItem(int itemID, String itemName, int image, String description, String keywords, int price){
        this.itemID = itemID;
        this.itemName = itemName;
        this.image = image;
        this.description = description;
        this.keywords = keywords;
        this.price = price;
    }
}


