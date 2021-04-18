package com.example.myshoppingapp.carthandler;

import android.content.Context;

import java.util.HashMap;

public class CartHandler {
    private static HashMap<Integer, Integer> cart;

    public static void initCart() {
        cart = new HashMap<>();
    }

    public static void addItemToCart(int itemId) {
        if (cart.containsKey(itemId)) {
            cart.replace(itemId, cart.get(itemId) + 1);
        } else {
            cart.put(itemId, 1);
        }
    }

    public static void displayCart(Context context) {

    }

    public static void buyCart() {

    }
}
