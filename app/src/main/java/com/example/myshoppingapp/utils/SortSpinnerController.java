package com.example.myshoppingapp.utils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.example.myshoppingapp.ShopDataManager;

public class SortSpinnerController implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        ShopDataManager.sortShopItems((String) parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent){

    }
}
