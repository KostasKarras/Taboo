package com.example.taboo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.about);
    }

    public void exit(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void settings(View v){
        Intent intent = new Intent(this, Settings.class);

        Bundle bundle = new Bundle();
        bundle.putString("context", this.getClass().getSimpleName());
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}