package com.example.myshoppingapp.utils;

import com.example.myshoppingapp.databasehandler.ShopItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShopItemSorter {

    public static List<ShopItem> sortByCost(List<ShopItem> itemList){
        Collections.sort(itemList, new SortByCost());
        return itemList;
    }

    public static List<ShopItem> sortByName(List<ShopItem> itemList){
        Collections.sort(itemList, new SortByName());
        return itemList;
    }
}

class SortByName implements Comparator<ShopItem> {

    @Override
    public int compare(ShopItem o1, ShopItem o2) {
        return o1.itemName.compareTo(o2.itemName);
    }
}

class SortByCost implements Comparator<ShopItem>{
    @Override
    public int compare(ShopItem o1, ShopItem o2){
        return o1.price - o2.price;
    }
}