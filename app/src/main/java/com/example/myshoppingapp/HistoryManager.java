package com.example.myshoppingapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class HistoryManager {
    private static String curUser;
    private static File historyFile;
    private static Queue<HistoryObject> userHistory;

    // initializes the history manager with the necessary data to do its work
    public static void Init(String user, File historyF) {
        curUser = user;
        historyFile = historyF;
        userHistory = new LinkedList<>();
    }

    // adds a transaction to the current history
    public static void addTransaction(int itemId, int balance) {
        userHistory.add(new HistoryObject(itemId, LocalDateTime.now(), balance));

    }

    // loads the history from the file to display
    public static void loadHistory() {
        try {
            Stack<HistoryObject>
            BufferedReader fReader = new BufferedReader(new FileReader(file));
            for (String user = fReader.readLine(); user != null; user = fReader.readLine()) {
                String[] userLogin = user.split(":");
                database.put(userLogin[0], userLogin[1]);
            }
            fReader.close();
        } catch (IOException e) {

        }
    }

    // saves the current history to file for later access
    public static void saveHistory() {

    }
}
