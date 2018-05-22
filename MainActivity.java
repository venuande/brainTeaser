package com.andythings.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView resultTextView;
    TextView pointsTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;
    RelativeLayout resultRelativeLayout;
    TextView finalResultTextView;
    TextView highScoreTextView;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    int gameCount = 0;
    int highScore = 0;
    int highNumberOfQuestions = 0;

    public void highScore() {

        highScoreTextView.setText("High score:" + Integer.toString(highScore) + "/" + Integer.toString(highNumberOfQuestions));

    }

    public void playagain (final View view) {

        score = 0;
        numberOfQuestions = 0;

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        gameRelativeLayout.setVisibility(view.VISIBLE);
        resultRelativeLayout.setVisibility(view.INVISIBLE);

        generateQuestion();

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");


            }

            @Override
            public void onFinish() {

                playAgainButton.setVisibility(view.VISIBLE);

                timerTextView.setText("0s");

                finalResultTextView.setText("Final score:" + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

                gameRelativeLayout.setVisibility(view.INVISIBLE);

                resultRelativeLayout.setVisibility(view.VISIBLE);

                gameCount++;

                if(gameCount == 0) {

                    highScore = score;
                    highNumberOfQuestions = numberOfQuestions;
                    highScore();

                } else {

                    if(score > highScore) {

                        highScore = score;

                        highNumberOfQuestions = numberOfQuestions;

                        highScore();

                    } else {

                        highScore();

                    }

                }





            }
        }.start();

    }

    public void generateQuestion() {

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) + "+" + Integer.toString(b));

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        int incorrectAnswer;

        for (int i = 0; i < 4; i++) {

            if (i == locationOfCorrectAnswer) {

                answers.add(a + b);

            } else {

                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == a + b) {

                    incorrectAnswer = rand.nextInt(41);

                }

                answers.add(incorrectAnswer);

            }

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));


    }

    public void chooseAnswer (View view) {

       if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {

           score++;
           resultTextView.setText("Correct!");

           generateQuestion();

       } else {

           resultTextView.setText("wrong!");

           MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
           mplayer.start();


       }

        numberOfQuestions++;

        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));




    }

    public void start(View view) {

        startButton.setVisibility(view.INVISIBLE);
        
        gameRelativeLayout.setVisibility(view.VISIBLE);

        playagain(findViewById(R.id.playAgainButton));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);
        resultRelativeLayout = (RelativeLayout) findViewById(R.id.resultRelativeLayout);
        finalResultTextView = (TextView) findViewById(R.id.finalResultTextView);
        highScoreTextView = (TextView) findViewById(R.id.highScoreTextView);

    }
}
