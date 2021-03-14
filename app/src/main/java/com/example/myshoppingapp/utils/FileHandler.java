package com.example.myshoppingapp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    public static HashMap loadDatabase(File file) throws IOException {
        HashMap database = new HashMap<String, String>();
        BufferedReader fReader = new BufferedReader(new FileReader(file));
        for (String user = fReader.readLine(); user != null; user = fReader.readLine()) {
            String[] userLogin = user.split(":");
            database.put(userLogin[0], userLogin[1]);
        }
        fReader.close();
        return database;
    }

    public static void saveDatabase(HashMap<String, String> database, File file) throws IOException{
        BufferedWriter fWriter = new BufferedWriter(new FileWriter(file));
        for (Map.Entry<String, String> user:database.entrySet()) {
            fWriter.write(user.getKey() + ":" + user.getValue());
            fWriter.newLine();
        }
        fWriter.close();
    }
}
