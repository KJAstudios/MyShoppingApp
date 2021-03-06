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
        File credentialFile = new File(getBaseContext().getFilesDir(), "credentials.txt");
        try {
            if(credentialFile.createNewFile()){
                FileWriter fileWriter = new FileWriter(credentialFile);
                fileWriter.write("admin:adminPass");
                fileWriter.close();
            }
        } catch (IOException e) {

        }
        final EditText userInput = (EditText) findViewById(R.id.username_input);
        LoginHandler.startLoginHandler(userInput, (EditText) findViewById(R.id.password_input), credentialFile, this);
        final Intent intent = new Intent(this, ScrollingActivity.class);
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
                if(LoginHandler.login(v)){
                    intent.putExtra("User", userInput.getText().toString());
                    startActivity(intent);
                }
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