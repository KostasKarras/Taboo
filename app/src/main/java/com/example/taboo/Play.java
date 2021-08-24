package com.example.taboo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Play extends AppCompatActivity {

    private TextView similarWord1;
    private TextView similarWord2;
    private TextView similarWord3;
    private TextView similarWord4;
    private TextView similarWord5;
    private TextView word;
    private TextView forbiddenWords;
    private TextView whoIsPlaying;

    private String timer;
    private String teams;
    private String team;
    private String rounds;

    private static int lines;

    private Button found;
    private Button notFound;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private boolean timerRunning;
    private TextView timerText;
    private Button timerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.play);

        Store.decreaseRound();

        Bundle bundle = this.getIntent().getExtras();
        timer = bundle.getString("timer");
        teams = bundle.getString("teams");
        rounds = bundle.getString("rounds");
        team = bundle.getString("team");

        found = (Button) findViewById(R.id.Found);
        notFound = (Button) findViewById(R.id.notFound);

        timeLeftInMillis = Integer.parseInt(timer) * 1000;
        timerText = (TextView) findViewById(R.id.timer);
        timerButton = (Button) findViewById(R.id.timerButton);

        timerButton.setOnClickListener(v -> startStop());

        whoIsPlaying = (TextView) findViewById(R.id.team);
        whoIsPlaying.setText("Team " + team + "\n");

        forbiddenWords = findViewById(R.id.forbiddenWords);
        forbiddenWords.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        String [] words = read("Data.txt");
        String [] similarWords = read("SimilarWords.txt");
        /*IMPORTANT
         *This must be 2x2 array. With the first read i will have the five words in a row. So, i have to make another one
         *reader, to be able to read the five words into a table.
        **/

        Random random = new Random();
        int rand = random.nextInt(lines);
        System.out.println("Random number: " + rand);

        String title = "";
        title = words[rand];

        String similarWord1title = similarWords[0];
        String similarWord2title = similarWords[1];
        String similarWord3title = similarWords[2];
        String similarWord4title = similarWords[3];
        String similarWord5title = similarWords[4];

        System.out.println("Title: " + title);

        word = (TextView) findViewById(R.id.word);
        similarWord1 = (TextView) findViewById(R.id.SimilarWord1);
        similarWord2 = (TextView) findViewById(R.id.SimilarWord2);
        similarWord3 = (TextView) findViewById(R.id.SimilarWord3);
        similarWord4 = (TextView) findViewById(R.id.SimilarWord4);
        similarWord5 = (TextView) findViewById(R.id.SimilarWord5);

        word.setText(title);
        similarWord1.setText(similarWord1title);
        similarWord2.setText(similarWord2title);
        similarWord3.setText(similarWord3title);
        similarWord4.setText(similarWord4title);
        similarWord5.setText(similarWord5title);

        found.setOnClickListener(v -> {

            if (Store.getSound()) {
                MediaPlayer player = MediaPlayer.create(this, R.raw.correct_buzzer);
                player.start();


                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (timerRunning)
                startStop();

            if (Store.anotherRound()) {
                Store.State(team, true);

                String nextTeam = "";
                int teamToPlay = Integer.parseInt(team) + 1;
                if (teamToPlay > Integer.parseInt(teams))
                    nextTeam = "1";
                else
                    nextTeam = String.valueOf(teamToPlay);

                Intent intent = new Intent(this, BeforeEachRound.class);

                Bundle bundle2 = new Bundle();
                bundle2.putString("timer", timer);
                bundle2.putString("teams", teams);
                bundle2.putString("team", nextTeam);
                intent.putExtras(bundle2);

                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, Result.class);
                startActivity(intent);
                finish();
            }
        });

        notFound.setOnClickListener(v -> {

            if (Store.getSound()) {
                MediaPlayer player = MediaPlayer.create(this, R.raw.wrong_buzzer);
                player.start();

                try {
                    Thread.sleep(1100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (timerRunning)
                startStop();

            if (Store.anotherRound()) {
                Store.State(team, false);

                String nextTeam = "";
                int teamToPlay = Integer.parseInt(team) + 1;
                if (teamToPlay > Integer.parseInt(teams))
                    nextTeam = "1";
                else
                    nextTeam = String.valueOf(teamToPlay);

                Intent intent = new Intent(this, BeforeEachRound.class);

                Bundle bundle2 = new Bundle();
                bundle2.putString("timer", timer);
                bundle2.putString("teams", teams);
                bundle2.putString("team", nextTeam);
                intent.putExtras(bundle2);

                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, Result.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public String[] read(String category) {

        String[] array;
        try {
            lines = 0;
            BufferedReader s = new BufferedReader(new InputStreamReader(getAssets().open(category), "UTF-8"));
            while (s.readLine() != null) {
                lines++;
                s.readLine();
            }
            s.close();

            BufferedReader s1 = new BufferedReader(new InputStreamReader(getAssets().open(category), "UTF-8"));
            array = new String[lines];
            for (int i = 0; i < array.length; i++) {
                array[i] = s1.readLine();
            }
            s1.close();

        } catch (IOException e) {
            System.err.println("File Not Found");
            array = new String[0];
        }
        return array;
    }

    public void startStop() {
        if (timerRunning)
            stop();
        else
            start();
    }

    public void start() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerButton.setText("PAUSE");
        timerRunning = true;
    }

    public void stop(){
        countDownTimer.cancel();
        timerButton.setText("START");
        timerRunning = false;
    }

    public void updateTimer(){
        int seconds = (int) timeLeftInMillis % (Integer.parseInt(timer) * 1000) / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;

        String timeLeft;
        timeLeft = "0" + minutes;
        timeLeft += ":";
        if (seconds < 10)
            timeLeft += "0";
        timeLeft += seconds;

        timerText.setText(timeLeft);
        if (timeLeft.equals("00:00")){
            if (Store.getVibration()) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }
            }
        }
    }

    public void exit(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}