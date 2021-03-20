package com.example.myshoppingapp.historyhandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.example.myshoppingapp.databasehandler.ShopItemDAO;

import java.io.File;
import java.util.ArrayList;

public class LoadHistoryThread implements Runnable {
    private File historyFile;
    private ShopItemDAO itemDAO;

    public LoadHistoryThread(File historyFile, ShopItemDAO itemDAO) {
        this.historyFile = historyFile;
        this.itemDAO = itemDAO;
    }

    @Override
    public void run() {
        HistoryManager.saveHistory();
        final ArrayList<HistoryObject> historyObjects = HistoryManager.loadHistoryData(historyFile);
        for (HistoryObject object : historyObjects) {
            object.item = itemDAO.getItem(object.itemId);
        }
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                HistoryManager.setHistoryObjects(historyObjects);
            }
        });
    }
}
