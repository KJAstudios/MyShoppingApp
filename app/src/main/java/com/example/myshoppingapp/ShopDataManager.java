package com.example.myshoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myshoppingapp.databasehandler.ShopItem;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;
import com.example.myshoppingapp.utils.SearchQueryProcessor;
import com.example.myshoppingapp.utils.ShopItemSorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopDataManager {

    private static ShopData data[] = {new ShopData("Bone", R.drawable.bone, "Good chew toy", 1, 0, "dog:food:pet"),
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

    private static List<View> dataViewList;
    private static EditText searchBar;
    private static ShopItemDAO databaseController;
    private static LinearLayout itemLayout;
    private static Button resetButton;
    private static ArrayList<Integer> returnedResults = new ArrayList<>();
    private static Context managerContext;

    public static void ShopClicked(int index) {

    }

    public static void InitShopItems(LinearLayout layout, Context context, EditText searchText, ShopItemDAO itemDAO, Button button) {
        managerContext = context;
        databaseController = itemDAO;
        resetButton = button;
        boolean isBadDatabase = false;
        List<ShopItem> itemList = databaseController.getAll();
        if (itemList != null) {
            for (ShopItem item : itemList) {
                if (item.image != data[item.itemID].getImageResource()) {
                    isBadDatabase = true;
                    break;
                }
            }
        }
        if (itemList == null || itemList.size() == 0) {
            ArrayList<ShopItem> shopItems = new ArrayList<>();
            for (ShopData item : data) {
                shopItems.add(new ShopItem(item.getItemId(), item.getName(), item.getImageResource(), item.getDescription(), item.getKeywords(), item.getCost()));
            }
            databaseController.insertAll(shopItems);
        } else if (isBadDatabase) {
            databaseController.clearDatabase();
            ArrayList<ShopItem> shopItems = new ArrayList<>();
            for (ShopData item : data) {
                shopItems.add(new ShopItem(item.getItemId(), item.getName(), item.getImageResource(), item.getDescription(), item.getKeywords(), item.getCost()));
            }
            databaseController.insertAll(shopItems);

        }


        searchBar = searchText;
        // inflate our custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        itemLayout = layout;
        dataViewList = new ArrayList<>();

        // app will crash on first startup on new device without this, it refreshes the itemList so the view gets populated with correct image resource id
        itemList = databaseController.getAll();
        for (ShopItem item : itemList) {
            View myShopItem = createShopView(inflater, item, itemLayout);
            dataViewList.add(myShopItem);
        }
        for (View item : dataViewList) {
            // then add the view to the main layout
            layout.addView(item);
        }
    }

    public static void searchShopItems(Context context) {
        String query = searchBar.getText().toString();
        List<String> searchTerms = SearchQueryProcessor.processQuery(query);
        if (searchTerms == null) {
            returnedResults = null;
            Toast toast = Toast.makeText(context, "Invalid Search, Try again", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            returnedResults.clear();
            for (String term : searchTerms) {
                int[] foundItems = databaseController.search(term);
                for (int item : foundItems) {
                    if (!returnedResults.contains(item)) {
                        returnedResults.add(item);
                    }
                }
            }
            itemLayout.removeAllViews();
            for (int itemID : returnedResults) {
                View tempView = dataViewList.get(itemID);
                itemLayout.addView(tempView);
            }
            resetButton.setVisibility(View.VISIBLE);
        }
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
        List<ShopItem> shopItems;
        if(itemLayout.getChildCount() == 0){
            Toast toast = Toast.makeText(managerContext, "Nothing to sort", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        else if (!returnedResults.isEmpty()) {
            shopItems = databaseController.getItemsFromIdList(returnedResults);
        } else {
            shopItems = databaseController.getAll();
        }

        switch (spinnerResult) {
            case "default":
                break;
            case "A-Z":
                shopItems = ShopItemSorter.sortByName(shopItems);
                break;
            case "Z-A":
                shopItems = ShopItemSorter.sortByName(shopItems);
                Collections.reverse(shopItems);
                break;
            case "Highest Price":
                shopItems = ShopItemSorter.sortByCost(shopItems);
                Collections.reverse(shopItems);
                break;
            case "Lowest Price":
                shopItems = ShopItemSorter.sortByCost(shopItems);

                break;
        }

        itemLayout.removeAllViews();
        for (ShopItem item : shopItems) {
            View tempView = dataViewList.get(item.itemID);
            itemLayout.addView(tempView);
        }

    }

    public static void removeItem(int id) {
        for (View item : dataViewList) {
            if (Integer.parseInt(item.findViewById(R.id.nameButton).getTag().toString()) == id) {
                dataViewList.remove(item);
                return;
            }
        }
    }

    private static View createShopView(LayoutInflater inflater, final ShopItem item, final LinearLayout curLayout) {
        View myShopItem = inflater.inflate(R.layout.shop_item, null);
        View.OnClickListener clickListener = new View.OnClickListener() {
            private LinearLayout layout = curLayout;

            @Override
            public void onClick(View v) {
                ScrollingActivity.confirmPurchaseDialog.show();
                ScrollingActivity.confirmPurchaseDialog.SetDetails(item.itemID, layout, databaseController);
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
}
