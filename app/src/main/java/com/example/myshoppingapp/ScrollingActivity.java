package com.example.myshoppingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.myshoppingapp.databasehandler.ItemDatabase;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;
import com.example.myshoppingapp.databasehandler.databaseManager;
import com.example.myshoppingapp.utils.MoneyUpdateListener;
import com.example.myshoppingapp.utils.SortSpinnerController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScrollingActivity extends AppCompatActivity {

    public static ConfirmPurchaseDialog confirmPurchaseDialog = null;
    private static SharedPreferences preferences = null;
    private String curUser;
    private ItemDatabase itemDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Context context = this;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        // make sure the saved money database for each user exists
        File moneyFile = new File(getBaseContext().getFilesDir(), "money.txt");
        try {
            if (moneyFile.createNewFile()) {
                FileWriter fileWriter = new FileWriter(moneyFile);
                fileWriter.close();
            }
        } catch (IOException e) {

        }

        // init the database of items
        itemDatabase = databaseManager.getDatabase(context);
        ShopItemDAO shopItemDAO = itemDatabase.shopItemDAO();

        // create and find various views needed
        confirmPurchaseDialog = new ConfirmPurchaseDialog(context);
        LinearLayout mainLayout = findViewById(R.id.mainVerticalLayout);
        Button resetButton = findViewById(R.id.search_reset);

        // init the main data manager
        ShopDataManager.InitShopItems(mainLayout, context, (EditText) findViewById(R.id.search_bar), shopItemDAO, resetButton);

        //init the moneyUpdateListener by giving it the textView for displaying the balance
        TextView curMoney = findViewById(R.id.cur_money);
        MoneyUpdateListener.initInstance(curMoney);
        curUser = getIntent().getStringExtra("User");

        // init the cart and set the starting money for the user
        Cart.InitCart(curUser, moneyFile);
        curMoney.setText("$" + Cart.getMoney());

        // create the searchbar
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    ShopDataManager.searchShopItems(context);
                }
                return false;
            }
        });
        // create search reset button
        resetButton.setVisibility(View.GONE);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDataManager.resetShopItems();
            }
        });

        // create the sort menu
        Spinner sortSpinner = findViewById(R.id.sort_spinner);
        // creates a textView for each string in the sort_array string resource
        ArrayAdapter<CharSequence> stringAdapter = ArrayAdapter.createFromResource(this, R.array.sort_array, R.layout.spinner_text_layout);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(stringAdapter);
        sortSpinner.setOnItemSelectedListener(new SortSpinnerController());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_money) {
            Cart.AddMoney(10);

            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Cart.shutdownCart(new File(getBaseContext().getFilesDir(), "money.txt"));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    private void updateMoney() {
        TextView curMoney = findViewById(R.id.cur_money);
        curMoney.setText("$" + Cart.getMoney());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Cart.shutdownCart(new File(getBaseContext().getFilesDir(), "money.txt"));
    }
}