package com.example.myshoppingapp.utils;

import android.widget.TextView;

import com.example.myshoppingapp.CashHandler;

public class MoneyUpdateListener {
    private static MoneyUpdateListener moneyUpdateListener;
    private TextView moneyText;

    private MoneyUpdateListener(TextView moneyText){
        this.moneyText = moneyText;

    }

    public static MoneyUpdateListener getInstance(){
        return moneyUpdateListener;
    }

    public static void initInstance(TextView moneyText){
            moneyUpdateListener = new MoneyUpdateListener(moneyText);

    }

    public void updateMoney(){
        moneyText.setText("$" + CashHandler.getMoney());
    }

    private void loadMoney(String user){

    }
}
