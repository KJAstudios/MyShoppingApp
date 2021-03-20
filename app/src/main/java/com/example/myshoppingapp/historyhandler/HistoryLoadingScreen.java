package com.example.myshoppingapp.historyhandler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.core.os.HandlerCompat;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class HistoryLoadingScreen implements HistoryLoadingManager.LoaderListener {
    LinearLayout layout;

    public HistoryLoadingScreen(File historyFile, ShopItemDAO itemDAO, LinearLayout layout, LayoutInflater inflater){
        this.layout = layout;
        HistoryLoadingManager.addListener(this);
        layout.removeAllViews();
        layout.addView(inflater.inflate(R.layout.loading_screen, null));
        Thread loadHistoryThread = new Thread(new LoadHistoryThread(historyFile, itemDAO));
        HistoryLoadingManager.addListener(this);
        loadHistoryThread.start();
    }

    @Override
    public void onLoaded() {
        HistoryManager.displayHistory();
    }
}
