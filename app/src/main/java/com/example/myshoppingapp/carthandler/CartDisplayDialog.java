package com.example.myshoppingapp.carthandler;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.myshoppingapp.R;

public class CartDisplayDialog extends Dialog {
    CartDisplayDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout);
    }
}
