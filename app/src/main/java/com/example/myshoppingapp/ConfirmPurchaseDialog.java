package com.example.myshoppingapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myshoppingapp.databasehandler.ShopItem;
import com.example.myshoppingapp.historyhandler.HistoryManager;
import com.example.myshoppingapp.shopdatahandler.ShopDataManager;

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

        final int curIndex = item.itemID;
        final LinearLayout curLayout = layout;
        final int curItemId = shopItem.itemID;
        Button purchaseButton = findViewById(R.id.purchase_button);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            private int index = curIndex;
            private LinearLayout layout = curLayout;
            private int itemId = curItemId;

            @Override
            public void onClick(View v) {
                // TODO purchase item
                for (int i = 0; i < layout.getChildCount(); i++) {
                    View view = layout.getChildAt(i);
                    Log.d("id", view.findViewById(R.id.nameButton).getTag().toString());
                    if (Integer.parseInt(view.findViewById(R.id.nameButton).getTag().toString()) == itemId) {
                        int itemPrice = item.price;
                        // buy the item and send it to the server
                        CashHandler.buySomething(itemPrice);
                        ApiCaller.buyRequest(item);
                        HistoryManager.addTransaction(itemId, CashHandler.getMoney());
                        Boolean isAllSold = ShopDataManager.takeItemFromInventory(itemId);
                        if (isAllSold) {
                            layout.removeViewAt(i);
                        }
                        break;
                    }
                }
                dismiss();
                ShopDataManager.displayStore();
            }
        });
    }
}
