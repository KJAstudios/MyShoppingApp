package com.example.myshoppingapp;

public class HistoryObject {
    private int itemId;
    private String time;
    private String date;
    private int balance;

    public HistoryObject(int itemId, String timeStamp, int balance) {
        this.itemId = itemId;
        this.balance = balance;
        String[] dateTimeArray = timeStamp.split(";");
        this.time = dateTimeArray[0];
        this.date = dateTimeArray[1];
    }

    public HistoryObject(int itemId, String time, String date, int balance) {
        this.itemId = itemId;
        this.balance = balance;
        this.time = time;
        this.date = date;
    }

    @Override
    public String toString() {
        return itemId + ";" + time + ";" + date + ";" + balance;
    }
}