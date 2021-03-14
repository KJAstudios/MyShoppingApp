package com.example.myshoppingapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class HistoryManager {
    private static File historyFile;
    private static Queue<HistoryObject> userHistory;
    private static SimpleDateFormat dateFormat;

    // initializes the history manager with the necessary data to do its work
    public static void Init(File historyF) {
        historyFile = historyF;
        userHistory = new LinkedList<>();
        dateFormat = new SimpleDateFormat("HH:mm;MM/dd/YYYY");
    }

    // adds a transaction to the current history
    public static void addTransaction(int itemId, int balance) {
        userHistory.add(new HistoryObject(itemId, dateFormat.format(new Date()), balance));
    }

    // loads the history from the file to display
    public static void loadHistoryData() {
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
    }

    // saves the current history to file for later access
    public static void saveHistory() {
        try {
            BufferedWriter fWriter = new BufferedWriter(new FileWriter(historyFile));
            for (HistoryObject historyObject : userHistory) {
                fWriter.write(historyObject.toString());
            }

        } catch (IOException e) {

        }
    }
}
