package com.example.myshoppingapp.shopdatahandler;

public class LoadManager {
    private static loadListener listener;

    /**
     * listener interface
     */
    public interface loadListener{
        void onLoaded();
    }

    public static void notifyListener(){
        listener.onLoaded();
    }

    public static void addListener(loadListener inListener){
        listener = inListener;
    }
}
