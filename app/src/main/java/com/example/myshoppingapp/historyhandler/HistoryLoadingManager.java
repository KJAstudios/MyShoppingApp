package com.example.myshoppingapp.historyhandler;

import java.util.ArrayList;

public class HistoryLoadingManager {
    private static LoaderListener listener;

    /**
     * classes that want to be notified implement this
     */
    public interface LoaderListener {
        void onLoaded();
    }

    /**
     * set the listener
     *
     * @param historyListener the LoadingScreen waiting for an update
     */
    public static void addListener(LoaderListener historyListener) {
        listener = historyListener;
    }

    /**
     * notify the listener that the load has completed
     */
    public static void notifyListener() {
        listener.onLoaded();
    }
}

