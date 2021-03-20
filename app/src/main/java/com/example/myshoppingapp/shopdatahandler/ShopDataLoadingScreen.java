package com.example.myshoppingapp.shopdatahandler;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;

public class ShopDataLoadingScreen implements LoadManager.loadListener {

    public ShopDataLoadingScreen(ShopItemDAO itemDAO, LinearLayout layout, LayoutInflater inflater){
        LoadManager.addListener(this);
        layout.removeAllViews();
        layout.addView(inflater.inflate(R.layout.loading_screen, null));
        Thread loadShopThread = new Thread(new LoadShopThread(itemDAO));
        loadShopThread.start();

    }

    @Override
    public void onLoaded(){
        ShopDataManager.InitShopItemsDisplay();
    }
}
