package com.example.myshoppingapp.databasehandler;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserPreference {

    @PrimaryKey
    @NonNull
    public String userID;

    @ColumnInfo
    public String sortType;

    public UserPreference(String userID, String sortType){
        this.userID = userID;
        this.sortType = sortType;
    }
}
