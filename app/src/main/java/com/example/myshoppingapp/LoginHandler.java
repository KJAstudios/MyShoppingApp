package com.example.myshoppingapp;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myshoppingapp.utils.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LoginHandler {
    private static EditText nameField;
    private static EditText passwordField;
    private static HashMap<String, String> userCredentials;
    private static File userDB;
    private static Context context;
    private static int toastLength;

    private static void loadUsers() {
        // create the database of user logins
        userCredentials = new HashMap<String, String>();
        try {
            userCredentials = FileHandler.loadDatabase(userDB);
        } catch (IOException e) {

        }
    }

    private static void saveUsers() {
        try {
            FileHandler.saveDatabase(userCredentials, userDB);
        } catch (IOException e) {

        }
    }

    //Keeping this for now, just in case I need it
    public static void startLoginHandler(EditText nameInput, EditText passwordInput, File
            credentialFile, Context inContext) {
        nameField = nameInput;
        passwordField = passwordInput;
        userDB = credentialFile;
        context = inContext;
        toastLength = Toast.LENGTH_SHORT;
        loadUsers();
    }

    public static Boolean login(View view) {
        String username = nameField.getText().toString();
        String password = passwordField.getText().toString();
        if (userCredentials.get(username) != null) {
            String realPass = userCredentials.get(username);
            if (password.equals(realPass)) {
                saveUsers();
                return true;
            } else {
                Toast toast = Toast.makeText(context, "Invalid Password", toastLength);
                toast.show();
                return false;
            }
        } else {
            Toast toast = Toast.makeText(context, "Invalid Username", toastLength);
            toast.show();
            return false;

        }
    }

    public static void createUser(View view) {
        String username = nameField.getText().toString();
        String password = passwordField.getText().toString();
        if (userCredentials.get(username) == null) {
            if (!password.equals("")) {
                userCredentials.put(username, password);
                saveUsers();
                Toast toast = Toast.makeText(context, "Account Created! please log in", toastLength);
                toast.show();
            } else {
                Toast toast = Toast.makeText(context, "Please enter a valid password", toastLength);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, "Username already exists", toastLength);
            toast.show();
        }

    }
}
