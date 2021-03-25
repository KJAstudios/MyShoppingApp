package com.example.myshoppingapp.databasehandler;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ShopItem.class, UserPreference.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {
    public abstract ShopItemDAO shopItemDAO();
    public abstract UserPreferenceDAO userPreferenceDAO();
}
