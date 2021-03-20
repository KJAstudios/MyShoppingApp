package com.example.myshoppingapp.historyhandler;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;

import java.io.File;

public class HistoryLoadingScreen implements HistoryLoadingManager.LoaderListener {
    LinearLayout layout;

    public HistoryLoadingScreen(File historyFile, ShopItemDAO itemDAO, LinearLayout layout, LayoutInflater inflater){
        this.layout = layout;
        HistoryLoadingManager.addListener(this);
        layout.removeAllViews();
        layout.addView(inflater.inflate(R.layout.loading_screen, null));
        Thread loadHistoryThread = new Thread(new LoadHistoryThread(historyFile, itemDAO));
        loadHistoryThread.start();
    }

    @Override
    public void onLoaded() {
        HistoryManager.displayHistory();
    }
}
