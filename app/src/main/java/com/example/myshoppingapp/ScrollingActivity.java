package com.example.myshoppingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myshoppingapp.databasehandler.ItemDaoThreadWrapper;
import com.example.myshoppingapp.databasehandler.ItemDatabase;
import com.example.myshoppingapp.databasehandler.ShopItemDAO;
import com.example.myshoppingapp.databasehandler.UserPreferenceDAO;
import com.example.myshoppingapp.databasehandler.databaseManager;
import com.example.myshoppingapp.historyhandler.HistoryLoadingScreen;
import com.example.myshoppingapp.historyhandler.HistoryManager;
import com.example.myshoppingapp.shopdatahandler.SaveSortManager;
import com.example.myshoppingapp.shopdatahandler.ShopDataLoadingScreen;
import com.example.myshoppingapp.shopdatahandler.ShopDataManager;
import com.example.myshoppingapp.utils.MoneyUpdateListener;
import com.example.myshoppingapp.utils.SortSpinnerController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScrollingActivity extends AppCompatActivity {
    public static ConfirmPurchaseDialog confirmPurchaseDialog = null;

    private String curUser;
    private ItemDatabase itemDatabase;
    private LinearLayout mainLayout;
    private ShopItemDAO shopItemDAO;
    private ItemDaoThreadWrapper itemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // android startup stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Context context = this;

        // class init stuff
        curUser = getIntent().getStringExtra("User");


        // make sure the saved money database for each user exists
        File moneyFile = new File(getBaseContext().getFilesDir(), "money.txt");
        try {
            if (moneyFile.createNewFile()) {
                FileWriter fileWriter = new FileWriter(moneyFile);
                fileWriter.close();
            }
        } catch (IOException e) {

        }

        //make sure the history file for the user exists
        File historyFile = new File(getBaseContext().getFilesDir(), curUser + ".txt");
        try {
            if (historyFile.createNewFile()) {
                FileWriter fileWriter = new FileWriter(historyFile);
                fileWriter.close();
            }
        } catch (IOException e) {

        }

        // init the database of items
        itemDatabase = databaseManager.getDatabase(context);
        shopItemDAO = itemDatabase.shopItemDAO();
        itemDao = ItemDaoThreadWrapper.getInstance(shopItemDAO);
        UserPreferenceDAO userPreferenceDAO = itemDatabase.userPreferenceDAO();
        SaveSortManager.Init(userPreferenceDAO, curUser);

        // init the apiCaller
        ApiCaller.init(this);

        // create and find various views needed
        confirmPurchaseDialog = new ConfirmPurchaseDialog(context);
        mainLayout = findViewById(R.id.mainVerticalLayout);
        Button resetButton = findViewById(R.id.search_reset);

        // create the sort menu
        Spinner sortSpinner = findViewById(R.id.sort_spinner);
        // creates a textView for each string in the sort_array string resource
        ArrayAdapter<CharSequence> stringAdapter = ArrayAdapter.createFromResource(this, R.array.sort_array, R.layout.spinner_text_layout);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(stringAdapter);
        sortSpinner.setOnItemSelectedListener(new SortSpinnerController());

        // init the main data manager
        ShopDataManager.initShop(mainLayout, context, (EditText) findViewById(R.id.search_bar), resetButton, curUser, sortSpinner);

        //init the moneyUpdateListener by giving it the textView for displaying the balance
        TextView curMoney = findViewById(R.id.cur_money);
        MoneyUpdateListener.initInstance(curMoney);


        // init the cart and set the starting money for the user
        CashHandler.InitCash(curUser, moneyFile);
        curMoney.setText("$" + CashHandler.getMoney());

        //init the history manager to keep track of transactions
        HistoryManager.Init(historyFile, context, mainLayout);

        // create the searchbar
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ShopDataManager.searchShopItems(context, shopItemDAO);
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


        //now that everything is created, now send it over to the ShopDataLoadingScreen to display the data
        ShopDataLoadingScreen shopDataLoadingScreen = new ShopDataLoadingScreen(shopItemDAO, mainLayout, LayoutInflater.from(this));

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
            CashHandler.AddMoney(10);
            ShopDataManager.displayStore();
            return true;
        }

        if (id == R.id.view_history) {
            HistoryLoadingScreen loadingScreen = new HistoryLoadingScreen(HistoryManager.getHistoryFile(), shopItemDAO, mainLayout, LayoutInflater.from(this));

            return true;
        }
        if (id == R.id.view_store) {
            ShopDataManager.displayStore();

            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            HistoryManager.saveHistory();
            CashHandler.shutdownCash(new File(getBaseContext().getFilesDir(), "money.txt"));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateMoney() {
        TextView curMoney = findViewById(R.id.cur_money);
        curMoney.setText("$" + CashHandler.getMoney());
    }

    @Override
    protected void onPause() {
        super.onPause();
        CashHandler.shutdownCash(new File(getBaseContext().getFilesDir(), "money.txt"));
        HistoryManager.saveHistory();
    }
}