package com.example.myshoppingapp.carthandler;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.myshoppingapp.ApiCaller;
import com.example.myshoppingapp.CashHandler;
import com.example.myshoppingapp.databasehandler.ShopItem;
import com.example.myshoppingapp.historyhandler.HistoryManager;
import com.example.myshoppingapp.shopdatahandler.ShopDataManager;

import java.util.HashMap;

public class CartHandler {
    private static HashMap<Integer, Integer> cart;
    private static CartDisplayDialog cartDisplayDialog;

    public static void initCart(Context context) {
        cart = new HashMap<>();
        cartDisplayDialog = new CartDisplayDialog(context);
    }

    public static void addItemToCart(int itemId) {
        if (cart.containsKey(itemId)) {
            cart.replace(itemId, cart.get(itemId) + 1);
        } else {
            cart.put(itemId, 1);
        }
    }

    public static boolean decrementItemFromCart(int itemId) {
        if (cart.containsKey(itemId)) {
            int itemAmount = cart.get(itemId);
            itemAmount--;
            if (itemAmount == 0) {
                cart.remove(itemId);
                return true;
            } else {
                cart.put(itemId, itemAmount);

            }
        }
        return false;
    }

    public static void removeItemFromCart(int itemId) {
        if (cart.containsKey(itemId))
            cart.remove(itemId);
    }

    public static void displayCart(LayoutInflater inflater) {
        if (!cart.isEmpty()) {
            cartDisplayDialog.show();
            cartDisplayDialog.generateCart(cart, inflater);
        }

    }

    public static void buyCart() {
        for (int key : cart.keySet()) {
            ShopItem item = ShopDataManager.getShopItem(key);
            // buy the item and send it to the server
            for (int i = 0; i < cart.get(key); i++) {
                CashHandler.buySomething(item.price);
                ApiCaller.buyRequest(item);
                HistoryManager.addTransaction(key, CashHandler.getMoney());
                ShopDataManager.takeItemFromInventory(item.itemID);
            }
        }
        cart.clear();
        cartDisplayDialog.clearCartView();
        cartDisplayDialog.dismiss();
        ShopDataManager.displayStore();
    }
}
