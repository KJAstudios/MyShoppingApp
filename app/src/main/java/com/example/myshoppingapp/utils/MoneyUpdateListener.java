package com.example.myshoppingapp.utils;

import android.widget.TextView;

import com.example.myshoppingapp.Cart;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        moneyText.setText("$" + Cart.getMoney());
    }

    private void loadMoney(String user){

    }
}
