package com.example.myshoppingapp.utils;

import android.view.View;
import android.widget.Button;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShopItemSorter {

    public static List<View> sortByCost(List<View> itemList) {
        Collections.sort(itemList, new SortByCost());
        return itemList;
    }

    public static List<View> sortByName(List<View> itemList) {
        Collections.sort(itemList, new SortByName());
        return itemList;
    }
}

class SortByName implements Comparator<View> {

    @Override
    public int compare(View o1, View o2) {
        Button o1NameText = o1.findViewById(R.id.nameButton);
        Button o2NameText = o2.findViewById(R.id.nameButton);
        String o1Name = (String) o1NameText.getText();
        String o2Name = (String) o2NameText.getText();
        return o1Name.compareTo(o2Name);
    }
}

class SortByCost implements Comparator<View> {
    @Override
    public int compare(View o1, View o2) {
        Button o1CostText = o1.findViewById(R.id.costButton);
        Button o2CostText = o2.findViewById(R.id.costButton);
        int o1Price = Integer.parseInt(o1CostText.getText().toString().substring(1));
        int o2Price = Integer.parseInt(o2CostText.getText().toString().substring(1));
        return o1Price - o2Price;
    }
}