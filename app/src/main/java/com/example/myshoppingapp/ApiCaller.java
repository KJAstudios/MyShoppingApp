package com.example.myshoppingapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApiCaller {
    private static RequestQueue requestQueue;
    private static String url = "http://10.0.2.2:5005";

    public static void init(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static void getRequest() {
        // 10.0.2.2

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // load.start();
                Log.d("servr test", "response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // load.start();
            }
        });
        requestQueue.add(stringRequest);
    }

    public static void postRequest(final String message){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "/buy", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("item", message);
                return dataMap;
            }
        };
        requestQueue.add(postRequest);
    }
}


