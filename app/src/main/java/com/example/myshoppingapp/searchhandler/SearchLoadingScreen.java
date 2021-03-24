package com.example.myshoppingapp.searchhandler;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;
import com.example.myshoppingapp.shopdatahandler.LoadManager;
import com.example.myshoppingapp.shopdatahandler.ShopDataManager;

import java.util.List;

public class SearchLoadingScreen implements LoadManager.loadListener {
    public SearchLoadingScreen(ShopItemDAO itemDAO, LinearLayout layout, LayoutInflater inflater, List<String> searchTerms){
        LoadManager.addListener(this);
        layout.removeAllViews();
        layout.addView(inflater.inflate(R.layout.loading_screen, null));
        Thread loadSearchThread = new Thread(new SearchResultThread(itemDAO, searchTerms));
        loadSearchThread.start();

    }
    @Override
    public void onLoaded(){
        ShopDataManager.finishSearching();
    }
}
