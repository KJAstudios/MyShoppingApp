package com.example.myshoppingapp.searchhandler;

import com.example.myshoppingapp.databasehandler.ShopItemDAO;
import com.example.myshoppingapp.shopdatahandler.LoadManager;
import com.example.myshoppingapp.shopdatahandler.ShopData;
import com.example.myshoppingapp.shopdatahandler.ShopDataManager;

import java.util.ArrayList;
import java.util.List;

public class SearchResultThread implements Runnable {
    private ShopItemDAO itemDAO;
    private List<String> searchTerms;
    public SearchResultThread(ShopItemDAO itemDAO, List<String> searchTerms){
        this.itemDAO = itemDAO;
        this.searchTerms = searchTerms;
    }
    @Override
    public void run() {
        ArrayList<Integer> returnedResults = new ArrayList<>();
        for (String term : searchTerms) {
            int[] foundItems = itemDAO.search(term);
            for (int item : foundItems) {
                if (!returnedResults.contains(item)) {
                    returnedResults.add(item);
                }
            }
        }
        ShopDataManager.setReturnedResults(returnedResults);
        LoadManager.notifyListener();
    }
}
