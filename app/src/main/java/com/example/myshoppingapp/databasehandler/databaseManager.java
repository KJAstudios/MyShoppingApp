package com.example.myshoppingapp.databasehandler;

import android.content.Context;

import androidx.room.Room;

public class databaseManager {
    private static ItemDatabase itemDatabase;

    public static ItemDatabase getDatabase(Context context) {
        if (itemDatabase == null) {
            itemDatabase = Room.databaseBuilder(context, ItemDatabase.class, "item-database").createFromAsset("mainDatabase.db").allowMainThreadQueries().build();
        }
        return itemDatabase;
    }
}
