package com.example.myshoppingapp.shopdatahandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.ScrollingActivity;
import com.example.myshoppingapp.databasehandler.ShopItem;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;
import com.example.myshoppingapp.searchhandler.SearchLoadingScreen;
import com.example.myshoppingapp.utils.SearchQueryProcessor;
import com.example.myshoppingapp.utils.ShopItemSorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ShopDataManager {


    private static List<View> dataViewList;
    private static EditText searchBar;
    private static LinearLayout itemLayout;
    private static Button resetButton;
    private static ArrayList<Integer> returnedResults = new ArrayList<>();
    private static Context managerContext;
    private static HashMap<Integer, Integer> inventory;
    private static List<ShopItem> itemList;
    private static String user;
    private static String loadedSortType;
    private static Boolean isFirstSort = true;
    private static Spinner spinner;
    private static ShopItem featuredItem;

    public static void initShop(LinearLayout layout, Context context, EditText searchText, Button button, String inUser, Spinner inSpinner) {
        // parameter input/setup
        managerContext = context;
        resetButton = button;
        inventory = new HashMap<>();
        searchBar = searchText;
        itemLayout = layout;
        user = inUser;
        spinner = inSpinner;
    }

    public static void InitShopItemsDisplay() {


        // inflate our custom layout and set up layout resources
        LayoutInflater inflater = LayoutInflater.from(managerContext);
        dataViewList = new ArrayList<>();

        // app will crash on first startup on new device without this, it refreshes the itemList so the view gets populated with correct image resource id
        for (ShopItem item : itemList) {
            inventory.put(item.itemID, 3);
            View myShopItem = createShopView(inflater, item, itemLayout);
            dataViewList.add(myShopItem);
        }
        itemLayout.removeAllViews();
        for (View item : dataViewList) {
            // then add the view to the main layout
            itemLayout.addView(item);
        }
        switch (loadedSortType){
            case "default sort":
                spinner.setSelection(0);
                break;
            case "A-Z":
                spinner.setSelection(1);
                break;
            case "Z-A":
                spinner.setSelection(2);
                break;
            case "Highest Price":
                spinner.setSelection(3);
                break;
            case "Lowest Price":
                spinner.setSelection(4);
                break;
        }

    }

    public static void displayStore() {
        itemLayout.removeAllViews();
        for (View item : dataViewList) {
            // then add the view to the main layout
            itemLayout.addView(item);
        }
    }

    public static void searchShopItems(Context context, ShopItemDAO itemDAO) {
        String query = searchBar.getText().toString();
        List<String> searchTerms = SearchQueryProcessor.processQuery(query);
        if (searchTerms == null) {
            returnedResults = null;
            Toast toast = Toast.makeText(context, "Invalid Search, Try again", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            returnedResults.clear();
            SearchLoadingScreen loadingScreen = new SearchLoadingScreen(itemDAO, itemLayout, LayoutInflater.from(managerContext), searchTerms);
        }
    }

    public static void finishSearching() {
        itemLayout.removeAllViews();
        for (int itemID : returnedResults) {
            View tempView = dataViewList.get(itemID);
            itemLayout.addView(tempView);
        }
        resetButton.setVisibility(View.VISIBLE);
    }

    public static void resetShopItems() {
        itemLayout.removeAllViews();
        returnedResults.clear();
        for (View view : dataViewList) {
            itemLayout.addView(view);
        }
        searchBar.setText("");
        resetButton.setVisibility(View.GONE);
    }

    /**
     * sorts the shop items
     *
     * @param spinnerResult which sort option is selected from the spinner
     */
    public static void sortShopItems(String spinnerResult) {
        List<View> shopItems;
        if (itemLayout.getChildCount() == 0) {
            Toast toast = Toast.makeText(managerContext, "Nothing to sort", Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else if (!returnedResults.isEmpty()) {
            shopItems = new ArrayList<>();
            for (int result : returnedResults) {
                shopItems.add(dataViewList.get(result));
            }
        } else {
            shopItems = dataViewList;
        }

        switch (spinnerResult) {
            case "default sort":
                if (!isFirstSort) {
                    SaveSortManager.updatePreferences("default sort");
                }
                break;
            case "A-Z":
                shopItems = ShopItemSorter.sortByName(shopItems);
                if (!isFirstSort) {
                    SaveSortManager.updatePreferences("A-Z");
                }
                break;
            case "Z-A":
                shopItems = ShopItemSorter.sortByName(shopItems);
                Collections.reverse(shopItems);
                if (!isFirstSort) {
                    SaveSortManager.updatePreferences("Z-A");
                }
                break;
            case "Highest Price":
                shopItems = ShopItemSorter.sortByCost(shopItems);
                Collections.reverse(shopItems);
                if (!isFirstSort) {
                    SaveSortManager.updatePreferences("Highest Price");
                }
                break;
            case "Lowest Price":
                shopItems = ShopItemSorter.sortByCost(shopItems);
                if (!isFirstSort) {
                    SaveSortManager.updatePreferences("Lowest Price");
                }

                break;
        }

        if (shopItems != null) {
            itemLayout.removeAllViews();
            for (View view : shopItems) {
                LinearLayout parent = (LinearLayout) view.getParent();
                if (parent != null){
                    parent.removeView(view);
                }
                itemLayout.addView(view);
            }
        }
        if (isFirstSort) {
            isFirstSort = false;
        }
    }

    public static Boolean takeItemFromInventory(int id) {
        for (View item : dataViewList) {
            if (Integer.parseInt(item.findViewById(R.id.nameButton).getTag().toString()) == id) {
                int numberRemaining = inventory.get(id);
                numberRemaining--;
                if (numberRemaining == 0) {
                    dataViewList.remove(item);
                    inventory.remove(id);
                    return true;
                } else {
                    inventory.put(id, numberRemaining);
                }

            }
        }
        return false;
    }

    private static View createShopView(LayoutInflater inflater, final ShopItem item, final LinearLayout curLayout) {
        View myShopItem = inflater.inflate(R.layout.shop_item, null);
        View.OnClickListener clickListener = new View.OnClickListener() {
            private LinearLayout layout = curLayout;

            @Override
            public void onClick(View v) {
                ScrollingActivity.confirmPurchaseDialog.show();
                ScrollingActivity.confirmPurchaseDialog.SetDetails(item, layout);
            }
        };
        // set info for the item
        Button nameButton = myShopItem.findViewById(R.id.nameButton);
        nameButton.setText(item.itemName);
        nameButton.setTag(item.itemID);
        nameButton.setOnClickListener(clickListener);
        Button costButton = myShopItem.findViewById(R.id.costButton);
        costButton.setText("$" + item.price);
        costButton.setOnClickListener(clickListener);
        ImageButton imageButton = myShopItem.findViewById(R.id.imageButton2);
        imageButton.setImageResource(item.image);
        imageButton.setOnClickListener(clickListener);
        return myShopItem;
    }

    public static void setItemList(List<ShopItem> inList) {
        itemList = inList;
    }

    public static void setReturnedResults(ArrayList<Integer> results) {
        returnedResults = results;
    }

    public static void setLoadedSortType(String sortType){
        loadedSortType = sortType;
    }

    public static void setDataViewList(List<View> viewList) {
        dataViewList = viewList;
    }

    public static void setFeaturedItem(int itemId){
        featuredItem = itemList.get(itemId);
    }
}
