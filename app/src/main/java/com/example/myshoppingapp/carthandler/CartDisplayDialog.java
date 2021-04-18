package com.example.myshoppingapp.carthandler;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItem;
import com.example.myshoppingapp.shopdatahandler.ShopDataManager;

import java.util.HashMap;
import java.util.zip.Inflater;

public class CartDisplayDialog extends Dialog {
    CartDisplayDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cart_layout);
        Button backButton = findViewById(R.id.cartBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button buyButton = findViewById(R.id.buyCartButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartHandler.buyCart();
            }
        });
    }

    public void generateCart(HashMap<Integer, Integer> cart, LayoutInflater inflater){
        for (int key : cart.keySet()){
            addItemToCartView(ShopDataManager.getShopItem(key), cart.get(key), inflater);
        }

    }

    private void addItemToCartView(final ShopItem item, int amount, LayoutInflater inflater){
        final LinearLayout mainLayout = this.findViewById(R.id.cart_linear_layout);
        final View cartItemView = inflater.inflate(R.layout.cart_item, null);

        //set text values for all the fields for the cartItem View
        Button nameButton = cartItemView.findViewById(R.id.nameButton);
        nameButton.setText(item.itemName);
        nameButton.setTag(item.itemID);
        ImageButton imageButton = cartItemView.findViewById(R.id.cartItemImage);
        imageButton.setImageResource(item.image);
        Button costButton = cartItemView.findViewById(R.id.cartCostButton);
        costButton.setText("$"+item.price);
        final TextView amountText = cartItemView.findViewById(R.id.amountText);
        amountText.setText(Integer.toString(amount));

        // set click listeners for the trickier buttons
        Button removeItemButton = cartItemView.findViewById(R.id.removeItemButton);
        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curAmount = Integer.parseInt(amountText.getText().toString());
                amountText.setText(Integer.toString(--curAmount));

                if (CartHandler.decrementItemFromCart(item.itemID)){
                    mainLayout.removeView(cartItemView);
                }
            }
        });
        Button addItemButton = cartItemView.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curAmount = Integer.parseInt(amountText.getText().toString());
                amountText.setText(Integer.toString(++curAmount));
                CartHandler.addItemToCart(item.itemID);
            }
        });
        Button deleteItemButton =  cartItemView.findViewById(R.id.delete_button);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartHandler.removeItemFromCart(item.itemID);
                mainLayout.removeView(cartItemView);
            }
        });

        // finally, add it to the main cart view
        mainLayout.addView(cartItemView);
    }

    public void clearCartView(){
        LinearLayout mainLayout = findViewById(R.id.cart_linear_layout);
        mainLayout.removeAllViews();
    }
}
