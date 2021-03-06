package com.example.myshoppingapp;

import com.example.myshoppingapp.utils.FileHandler;
import com.example.myshoppingapp.utils.MoneyUpdateListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Cart {
    private static int money = 0;
    private static String curUser;
    private static MoneyUpdateListener moneyUpdateListener;
    private static HashMap<String, String> moneyDatabase;
    private static File dbFile;

    public static void InitCart(String user, File file) {
        curUser = user;
        dbFile = file;
        moneyUpdateListener = MoneyUpdateListener.getInstance();
        try {
            moneyDatabase = FileHandler.loadDatabase(file);
        } catch (IOException e) {

        }
        loadUserMoney();

    }

    public static void AddMoney(int cents) {
        money += cents;
        saveUserMoney();
        moneyUpdateListener.updateMoney();
    }

    public static void buySomething(int price) {
        money -= price;
        saveUserMoney();
        moneyUpdateListener.updateMoney();
    }

    public static int getMoney() {
        return money;
    }

    public static void shutdownCart(File file) {
        try {
            FileHandler.saveDatabase(moneyDatabase, file);
        } catch (IOException e) {

        }
    }

    private static void loadUserMoney() {
        if (!curUser.equals("guest")) {
            if (moneyDatabase.containsKey(curUser)) {
                String userMoney = moneyDatabase.get(curUser);
                money = Integer.parseInt(userMoney);
            } else {
                moneyDatabase.put(curUser, "0");
                money = 0;
            }
        } else {
            money = 0;
        }
    }

    private static void saveUserMoney() {
        if (!curUser.equals("guest")) {
            moneyDatabase.put(curUser, Integer.toString(money));
            try {
                FileHandler.saveDatabase(moneyDatabase, dbFile);
            } catch (IOException e) {

            }
        }
    }

}
