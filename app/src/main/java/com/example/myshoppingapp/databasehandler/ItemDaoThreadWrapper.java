package com.example.myshoppingapp.databasehandler;

import java.util.ArrayList;
import java.util.List;

/**
 * this class serves a wrapper for ShopItemDAO to allow for the calls to be handled on another thread
 */
public class ItemDaoThreadWrapper {
    private ShopItemDAO shopItemDAO;
    private static ItemDaoThreadWrapper itemDaoThreadWrapper;

    private ItemDaoThreadWrapper(ShopItemDAO itemDAO){
        this.shopItemDAO = itemDAO;
    }
    public static ItemDaoThreadWrapper getInstance(ShopItemDAO itemDAO) {
        if (itemDaoThreadWrapper == null){
            itemDaoThreadWrapper = new ItemDaoThreadWrapper(itemDAO);
        }
        return itemDaoThreadWrapper;
    }

    // wrapper functions to start itemDao threads
    public void getAll(){
        Thread getAllThread = new Thread(new GetAll(), "GetAllThread");
        getAllThread.start();
    }
    public  void getItem(int itemId){
        Thread getItemThread = new Thread(new GetItem(itemId), "GetItemThread");
        getItemThread.start();
    }
    public  void getItemsFromIDList(List<Integer> idList){
        Thread getItemListThread = new Thread(new GetItemsFromIdList(idList));
        getItemListThread.start();
    }
    public  void search(String searchParam){
        Thread searchThread = new Thread(new Search(searchParam));
        searchThread.start();
    }
    public  void clearDatabase(){
        Thread clearThread = new Thread(new ClearDatabase());
        clearThread.start();
    }
    public  void insertAll(ArrayList<ShopItem> shopItems){
        Thread insertAllThread = new Thread(new InsertAll(shopItems));
        insertAllThread.start();
    }
    // supporting runnables for ShopItemDao
    class GetAll implements Runnable {

        @Override
        public void run() {
            List<ShopItem> items = shopItemDAO.getAll();
        }
    }

    class GetItem implements Runnable {
        int itemId;

        public GetItem(int itemId){
            this.itemId = itemId;
        }
        @Override
        public void run() {
            ShopItem item = shopItemDAO.getItem(itemId);
        }
    }

    class GetItemsFromIdList implements Runnable {
        List<Integer> idList;

        public GetItemsFromIdList(List<Integer> idList){
            this.idList = idList;
        }

        @Override
        public void run() {
            List<ShopItem> itemList = shopItemDAO.getItemsFromIdList(idList);
        }
    }

    class Search implements Runnable {
        String searchParam;

        public Search(String searchParam){
            this.searchParam = searchParam;
        }
        @Override
        public void run() {
            int[] searchResults = shopItemDAO.search(searchParam);
        }
    }

    class ClearDatabase implements Runnable {

        @Override
        public void run() {
            shopItemDAO.clearDatabase();
        }
    }

    class InsertAll implements Runnable {
        ArrayList<ShopItem> shopItems;

        public InsertAll(ArrayList<ShopItem> shopItems){
            this.shopItems = shopItems;
        }
        @Override
        public void run() {
            shopItemDAO.insertAll(shopItems);
        }
    }
}
