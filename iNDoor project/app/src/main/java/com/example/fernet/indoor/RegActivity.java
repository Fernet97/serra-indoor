package com.example.fernet.indoor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fernet.indoor.ui.login.LoginActivity;

public class RegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);



    }



    public void  accedi(View v){

        Intent i = new Intent(RegActivity.this, MainActivity.class);
        startActivity(i);
        System.out.println("AAAAAAAAAAAAAAAAAA");

    }

    public void  effettualogin(View v){

        Intent i = new Intent(RegActivity.this, LoginActivity.class);
        startActivity(i);


    }
}
