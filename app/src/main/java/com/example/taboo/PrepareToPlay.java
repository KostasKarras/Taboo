package com.example.taboo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class PrepareToPlay extends AppCompatActivity {

    private CheckBox checkbox60;
    private CheckBox checkbox90;
    private CheckBox checkbox120;

    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private CheckBox checkbox4;

    private CheckBox checkbox5;
    private CheckBox checkbox10;
    private CheckBox checkbox15;

    private Button letsGo;

    private String timer;
    private String teams;
    private String rounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.prepare_to_play);

        //timer
        checkbox60 = (CheckBox) findViewById(R.id.checkbox60);
        checkbox90 = (CheckBox) findViewById(R.id.checkbox90);
        checkbox120 = (CheckBox) findViewById(R.id.checkbox120);
        timer = "90";

        //teams
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkbox4 = (CheckBox) findViewById(R.id.checkbox4);
        teams = "2";

        //rounds
        checkbox5 = (CheckBox) findViewById(R.id.checkbox5);
        checkbox10 = (CheckBox) findViewById(R.id.checkbox10);
        checkbox15 = (CheckBox) findViewById(R.id.checkbox15);
        rounds = "10";

        //button
        letsGo = (Button) findViewById(R.id.letsGo);

        //TIMER-START
        checkbox60.setChecked(false);
        checkbox90.setChecked(true);
        checkbox120.setChecked(false);

        checkbox60.setOnClickListener(v -> {
            timer = "60";
            checkbox60.setChecked(true);
            checkbox90.setChecked(false);
            checkbox120.setChecked(false);
        });

        checkbox90.setOnClickListener(v -> {
            timer = "90";
            checkbox60.setChecked(false);
            checkbox90.setChecked(true);
            checkbox120.setChecked(false);
        });

        checkbox120.setOnClickListener(v -> {
            timer = "120";
            checkbox60.setChecked(false);
            checkbox90.setChecked(false);
            checkbox120.setChecked(true);
        });
        //TIMER-END

        //TEAMS-START
        checkbox2.setChecked(true);
        checkbox3.setChecked(false);
        checkbox4.setChecked(false);

        checkbox2.setOnClickListener(v -> {
            teams = "2";
            checkbox2.setChecked(true);
            checkbox3.setChecked(false);
            checkbox4.setChecked(false);
        });

        checkbox3.setOnClickListener(v -> {
            teams = "3";
            checkbox2.setChecked(false);
            checkbox3.setChecked(true);
            checkbox4.setChecked(false);
        });

        checkbox4.setOnClickListener(v -> {
            teams = "4";
            checkbox2.setChecked(false);
            checkbox3.setChecked(false);
            checkbox4.setChecked(true);
        });
        //TEAMS-END

        //ROUNDS-START
        checkbox5.setChecked(false);
        checkbox10.setChecked(true);
        checkbox15.setChecked(false);

        checkbox5.setOnClickListener(v -> {
            rounds = "5";
            checkbox5.setChecked(true);
            checkbox10.setChecked(false);
            checkbox15.setChecked(false);
        });

        checkbox10.setOnClickListener(v -> {
            rounds = "10";
            checkbox5.setChecked(false);
            checkbox10.setChecked(true);
            checkbox15.setChecked(false);
        });

        checkbox15.setOnClickListener(v -> {
            rounds = "15";
            checkbox5.setChecked(false);
            checkbox10.setChecked(false);
            checkbox15.setChecked(true);
        });
        //ROUNDS-END

        //LETS_GO-START
        letsGo.setOnClickListener(v -> {
            Intent intent = new Intent(this, BeforeEachRound.class);

            Store.Rounds(Integer.parseInt(teams), Integer.parseInt(rounds));

            Bundle bundle = new Bundle();
            bundle.putString("timer", timer);
            bundle.putString("teams", teams);
            bundle.putString("rounds", rounds);
            bundle.putString("team", "1");
            intent.putExtras(bundle);

            startActivity(intent);
            finish();
        });
        //LETS_GO-END
    }

    public void exit(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}