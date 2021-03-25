package com.example.myshoppingapp.databasehandler;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserPreferenceDAO {
    @Query("Select * From userPreference where userID=:userId")
    UserPreference getPreference(String userId);

    @Query("Update userpreference Set sortType=:sortSetting Where userID=:userId")
    void updateUserData (String userId, String sortSetting);

    @Insert
    void insertPreference(UserPreference userPreference);
}
