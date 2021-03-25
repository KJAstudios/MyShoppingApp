package com.example.myshoppingapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshoppingapp.databasehandler.ShopItem;
import com.example.myshoppingapp.shopdatahandler.LoadShopThread;
import com.example.myshoppingapp.shopdatahandler.ShopData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiCaller {
    private static RequestQueue requestQueue;
    private static String url = "http://10.0.2.2:5005";

    public static void init(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static void getRequest(final LoadShopThread loadThread) {
        // 10.0.2.2

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // load.start();
                ArrayList<ShopData> returnData = new ArrayList<>();
                try {
                    JSONObject reader = new JSONObject((response));
                    JSONArray lst = reader.getJSONArray("list");
                    for (int i = 0; i < lst.length(); i++) {
                        JSONObject obj = lst.getJSONObject(i);
                        String name = obj.getString("name");
                        String image = obj.getString("image");
                        String description = obj.getString("description");
                        int cost = obj.getInt("cost");
                        int itemId = obj.getInt("itemId");
                        String keywords = obj.getString("keywords");
                        int ir = 0;
                        try {
                            Field idField = R.drawable.class.getDeclaredField(image);
                            ir = idField.getInt(idField);
                        } catch (Exception e) {

                        }
                        ShopData shopData = new ShopData(name, ir, description, cost, itemId, keywords);
                        returnData.add(shopData);
                    }
                    loadThread.putDataFromServer(returnData);
                } catch (JSONException e) {
                    e.getMessage();
                }
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


    public static void postRequest(final ShopItem item) {
        JSONObject jsonBody;
        try {
            jsonBody = new JSONObject();
            jsonBody.put("name", item.itemName);
            jsonBody.put("price", item.price);

            JsonObjectRequest postRequest = new JsonObjectRequest(url + "/buy", jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            requestQueue.add(postRequest);
        } catch (JSONException e) {

        }
    }
}


