package com.example.myshoppingapp.historyhandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.databasehandler.ShopItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class HistoryManager {
    private static File historyFile;
    private static Queue<HistoryObject> userHistory;
    private static SimpleDateFormat dateFormat;
    private static Context context;
    private static LinearLayout mainLayout;
    private static ArrayList<HistoryObject> historyObjects;

    // initializes the history manager with the necessary data to do its work
    public static void Init(File historyF, Context mainContext, LinearLayout layout) {
        historyFile = historyF;
        userHistory = new LinkedList<>();
        dateFormat = new SimpleDateFormat("HH:mm;MM/dd/YYYY");
        context = mainContext;
        mainLayout = layout;
    }

    public static void displayHistory(){
        saveHistory();
        mainLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create
        for (HistoryObject historyObject: historyObjects){
            ShopItem item = historyObject.item;
            View historyItemView = inflater.inflate(R.layout.history_item, null);
            ImageButton imageButton = historyItemView.findViewById(R.id.cartItemImage);
            imageButton.setImageResource(item.image);
            Button nameButton = historyItemView.findViewById(R.id.nameButton);
            nameButton.setText(item.itemName);
            Button costButton = historyItemView.findViewById(R.id.cartCostButton);
            costButton.setText("-$"+item.price);
            Button balButton = historyItemView.findViewById(R.id.remainingBalanceButton);
            balButton.setText("$" + historyObject.balance);
            TextView timeStamp = historyItemView.findViewById(R.id.timeDateText);
            timeStamp.setText("Purchased on " + historyObject.date + " at " + historyObject.time);
            mainLayout.addView(historyItemView);
        }
    }

    // adds a transaction to the current history
    public static void addTransaction(int itemId, int balance) {
        userHistory.add(new HistoryObject(itemId, dateFormat.format(new Date()), balance));
    }

    // loads the history from the file to display
    public static ArrayList<HistoryObject> loadHistoryData(File historyFile) {
        ArrayList<HistoryObject> historyList = new ArrayList<>();

        try {
            BufferedReader fReader = new BufferedReader(new FileReader(historyFile));
            for (String user = fReader.readLine(); user != null; user = fReader.readLine()) {
                String[] historyData = user.split(";");
                historyList.add(new HistoryObject(Integer.parseInt(historyData[0]), historyData[1], historyData[2], Integer.parseInt(historyData[3])));
            }
            fReader.close();
        } catch (IOException e) {
        }
        return historyList;
    }

    // saves the current history to file for later access
    public static void saveHistory() {
        try {
            PrintWriter fWriter = new PrintWriter(new BufferedWriter(new FileWriter(historyFile, true)));
            for (HistoryObject historyObject : userHistory) {
                fWriter.println(historyObject.toString());
            }
            fWriter.close();
            userHistory.clear();

        } catch (IOException e) {

        }
    }

    public static void setHistoryObjects(ArrayList<HistoryObject> inHistoryObjects){
        historyObjects = inHistoryObjects;
        HistoryLoadingManager.notifyListener();
    }

    public static File getHistoryFile(){
        return historyFile;
    }
}
