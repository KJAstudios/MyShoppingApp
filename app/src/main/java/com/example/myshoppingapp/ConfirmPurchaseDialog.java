package com.example.myshoppingapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myshoppingapp.carthandler.CartHandler;
import com.example.myshoppingapp.databasehandler.ShopItem;

public class ConfirmPurchaseDialog extends Dialog {
    public ConfirmPurchaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout);
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void SetDetails(final ShopItem item, LinearLayout layout) {
        ShopItem shopItem = item;
        TextView title = findViewById(R.id.nameText);
        title.setText(shopItem.itemName);

        TextView desc = findViewById(R.id.descText);
        desc.setText(shopItem.description);

        TextView price = findViewById(R.id.costText);
        price.setText("$" + shopItem.price);

        ImageView image = findViewById(R.id.itemImage);
        image.setImageResource(shopItem.image);

        final int curItemId = shopItem.itemID;
        Button purchaseButton = findViewById(R.id.purchase_button);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartHandler.addItemToCart(curItemId);
                dismiss();
            }
        });
    }

}
