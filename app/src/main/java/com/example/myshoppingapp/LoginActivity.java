package com.example.myshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ApiCaller.init(this);
        final EditText userInput = (EditText) findViewById(R.id.username_input);
        final Intent intent = new Intent(this, ScrollingActivity.class);
        LoginHandler.startLoginHandler(userInput, (EditText) findViewById(R.id.password_input), this, intent);
        Button guestButton = findViewById(R.id.guest_login);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("User", "guest");
                startActivity(intent);
            }
        });
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginHandler.login(v);
                }
        });
        Button newAccountButton = findViewById(R.id.create_account);
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginHandler.createUser(v);
            }
        });
    }
}