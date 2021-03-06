package com.example.myshoppingapp.databasehandler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ShopItemDAO {
    @Query("SELECT * FROM ShopItem")
    List<ShopItem> getAll();

    @Query("SELECT * from ShopItem where itemID=:itemID")
    ShopItem getItem(int itemID);

    @Query("Select * from ShopItem where itemID in (:idList) ")
    List<ShopItem> getItemsFromIdList(List<Integer> idList);

    @Query("SELECT itemID from ShopItem where itemName like '%' || :searchParam || '%' or description like '%' || :searchParam || '%' or keywords like '%' || :searchParam || '%'")
    int[] search(String searchParam);

    @Query("Delete from ShopItem")
    void clearDatabase();

    @Insert
    void insertAll(ArrayList<ShopItem> shopItems);
}
