package com.example.myshoppingapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginHandler {
    private static EditText nameField;
    private static EditText passwordField;
    private static HashMap<String, String> userCredentials;

    private static Context context;
    private static int toastLength;
    private static Intent intent;

    //sets up all the stuff needed for the handler to work
    public static void startLoginHandler(EditText nameInput, EditText passwordInput,
                                         Context inContext, Intent inIntent) {
        nameField = nameInput;
        passwordField = passwordInput;
        context = inContext;
        toastLength = Toast.LENGTH_SHORT;
        intent = inIntent;
    }

    public static void login(View view) {
        String username = nameField.getText().toString();
        String password = passwordField.getText().toString();
        ApiCaller.loginRequest(username, password, true);
    }

    public static void createUser(View view) {
        String username = nameField.getText().toString();
        String password = passwordField.getText().toString();
        ApiCaller.loginRequest(username, password, false);
    }

    public static void returnLoginRequest(Boolean isSuccess) {
        if (isSuccess) {
            Toast toast = Toast.makeText(context, "Login Successful!", toastLength);
            toast.show();
            intent.putExtra("User", nameField.getText().toString());
            context.startActivity(intent);
        } else {
            Toast toast = Toast.makeText(context, "Login failed, check username and password", toastLength);
            toast.show();
        }
    }

    public static void returnRegisterRequest(Boolean isSuccess) {
        if (isSuccess) {
            Toast toast = Toast.makeText(context, "Account Created! You may now log in", toastLength);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "Account already exists, please log in", toastLength);
            toast.show();
        }
    }
}
