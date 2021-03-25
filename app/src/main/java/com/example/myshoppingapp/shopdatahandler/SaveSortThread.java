package com.example.myshoppingapp.shopdatahandler;

import com.example.myshoppingapp.databasehandler.UserPreference;
import com.example.myshoppingapp.databasehandler.UserPreferenceDAO;

public class SaveSortThread implements Runnable{

    UserPreferenceDAO userPreferenceDAO;
    String userID;
    String sortSetting;

    public SaveSortThread(UserPreferenceDAO userPreferenceDAO, String userID, String sortSetting){
        this.userPreferenceDAO = userPreferenceDAO;
        this.userID = userID;
        this.sortSetting = sortSetting;
    }

    @Override
    public void run(){
        UserPreference userPreference = userPreferenceDAO.getPreference(userID);
        if (userPreference == null){
            userPreferenceDAO.insertPreference(new UserPreference(userID, sortSetting));
        }
        else{
            userPreferenceDAO.updateUserData(userID, sortSetting);
        }

    }
}
