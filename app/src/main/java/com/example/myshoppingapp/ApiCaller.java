package com.example.myshoppingapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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

public class ApiCaller {
    private static RequestQueue requestQueue;
    private static String url = "http://10.0.2.2:5005";

    public static void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static void getRequest(final LoadShopThread loadThread) {
        // 10.0.2.2

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
            }
        });
        requestQueue.add(stringRequest);
    }


    public static void buyRequest(final ShopItem item) {
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

    /**
     * sends a request to login or register
     *
     * @param user     username of request
     * @param password password of request
     * @param isLogin  true if logging in, false if registering
     */
    public static void loginRequest(String user, String password, final Boolean isLogin) {
        JSONObject sendJson;
        String endpoint = (isLogin ? "/login" : "/register");
        try {
            sendJson = new JSONObject();
            sendJson.put("username", user);
            sendJson.put("password", password);
            JsonObjectRequest postRequest = new JsonObjectRequest(url + endpoint, sendJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("login response", response.toString());
                    try {
                        int successResponse = (int) response.get("success");
                        final Boolean isSuccess = (successResponse == 1);
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (isLogin) {
                                    LoginHandler.returnLoginRequest(isSuccess);
                                } else {
                                    LoginHandler.returnRegisterRequest(isSuccess);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        if (isLogin) {
                            LoginHandler.returnLoginRequest(false);
                        } else {
                            LoginHandler.returnRegisterRequest(false);
                        }
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());
                        }
                    });
            requestQueue.add(postRequest);
        } catch (JSONException e) {

        }
    }

    public static void featuredItemRequest(final int shopSize, final LoadShopThread loadThread) {
        JSONObject jsonBody;
        try {
            jsonBody = new JSONObject();
            jsonBody.put("size", shopSize);

            JsonObjectRequest postRequest = new JsonObjectRequest(url + "/featured", jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int itemId = (int) response.get("item");
                        loadThread.putFeaturedItemFromServer(itemId);
                    } catch (JSONException e) {

                    }
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


