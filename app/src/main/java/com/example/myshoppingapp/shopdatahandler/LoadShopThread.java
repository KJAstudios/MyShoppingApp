package com.example.myshoppingapp.shopdatahandler;

import android.os.Handler;
import android.os.Looper;

import com.example.myshoppingapp.ApiCaller;
import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItem;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadShopThread implements Runnable {
    private ShopData data[] = {new ShopData("Bone", R.drawable.bone, "Good chew toy", 1, 0, "dog:food:pet"),
            new ShopData("Carrot", R.drawable.carrot, "Good chew", 1, 1, "food:vegetable:cold"),
            new ShopData("Dog", R.drawable.dog, "Chews toy", 2, 2, "pet:animal"),
            new ShopData("Flame", R.drawable.flame, "it burns", 1, 3, "tool:dangerous"),
            new ShopData("Grapes", R.drawable.grapes, "You eat them", 1, 4, "food:fruit:cold"),
            new ShopData("House", R.drawable.house, "As opposed to home", 100, 5, "shelter:living:household"),
            new ShopData("Lamp", R.drawable.lamp, "It lights", 2, 6, "furniture:household:living"),
            new ShopData("Mouse", R.drawable.mouse, "not a rat", 1, 7, "animal:pet:rodent"),
            new ShopData("Nail", R.drawable.nail, "Hammer Required", 1, 8, "tool:household"),
            new ShopData("Penguin", R.drawable.penguin, "Find Batman", 10, 9, "animal:bird"),
            new ShopData("Rocks", R.drawable.rocks, "Rolls", 1, 10, "outdoors:decoration:yard"),
            new ShopData("Star", R.drawable.star, "Like the sun but farther away", 25, 11, "space:danger:bright"),
            new ShopData("Toad", R.drawable.toad, "like a frog", 1, 12, "animal:reptile"),
            new ShopData("Van", R.drawable.van, "Has four wheels", 10, 13, "transport:car"),
            new ShopData("Wheat", R.drawable.wheat, "Some breads have it", 1, 14, "food:grain:unprocessed"),
            new ShopData("Yak", R.drawable.yak, "Yakity Yak Yak", 15, 15, "animal:mammal:farm")};
    private List<ShopData> dataArrayList = new ArrayList<>(Arrays.asList(data));
    private ShopItemDAO databaseController;
    private Boolean dataLoaded = false;

    public LoadShopThread(ShopItemDAO databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public void run() {
        //check the database to make sure the image ids are correct, and load it from the array
        ApiCaller.getRequest(this);
        while (!dataLoaded) {

        }
        boolean isBadDatabase = false;
        List<ShopItem> itemList = databaseController.getAll();
        if (itemList != null && itemList.size() == dataArrayList.size()) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).image != dataArrayList.get(i).getImageResource()) {
                    isBadDatabase = true;
                    break;
                }
            }
        }
        // if the database is empty, or if the ids are wrong, populate it f
        if (itemList == null || itemList.size() == 0) {
            ArrayList<ShopItem> shopItems = new ArrayList<>();
            for (ShopData item : dataArrayList) {
                shopItems.add(new ShopItem(item.getItemId(), item.getName(), item.getImageResource(), item.getDescription(), item.getKeywords(), item.getCost()));
            }
            databaseController.insertAll(shopItems);
        } else if (isBadDatabase || itemList.size() != dataArrayList.size()) {
            databaseController.clearDatabase();
            ArrayList<ShopItem> shopItems = new ArrayList<>();
            for (ShopData item : dataArrayList) {
                shopItems.add(new ShopItem(item.getItemId(), item.getName(), item.getImageResource(), item.getDescription(), item.getKeywords(), item.getCost()));


            }
            databaseController.insertAll(shopItems);

        }
        final List<ShopItem> returnList = databaseController.getAll();

        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                ShopDataManager.setItemList(returnList);
                ShopDataManager.setLoadedSortType(SaveSortManager.loadPreference());
                LoadManager.notifyListener();
            }
        });
    }

    public void putDataFromServer(ArrayList<ShopData> inList) {
        dataArrayList.addAll(inList);
        dataLoaded = true;
    }
}
