package com.example.myshoppingapp;

import java.time.LocalDateTime;

public class HistoryObject {
    private int itemId;
    private LocalDateTime timeStamp;
    private int balance;

    public HistoryObject(int itemId, LocalDateTime timeStamp, int balance) {
        this.itemId = itemId;
        this.timeStamp = timeStamp;
        this.balance = balance;
    }
}