package com.example.myshoppingapp.shopdatahandler;

import com.example.myshoppingapp.databasehandler.UserPreference;
import com.example.myshoppingapp.databasehandler.UserPreferenceDAO;

public class SaveSortManager {

    private static UserPreferenceDAO userPreferenceDAO;
    private static String user;

    public static void Init(UserPreferenceDAO inDao, String userId){
        userPreferenceDAO = inDao;
        user = userId;
    }

    public static void updatePreferences(String sortType){
        Thread updateThread = new Thread(new SaveSortThread(userPreferenceDAO, user, sortType));
        updateThread.start();
    }

    public static String loadPreference(){
        UserPreference userPreference = userPreferenceDAO.getPreference(user);
        if (userPreference != null){
            return userPreference.sortType;
        }
        return "default sort";
    }
}
