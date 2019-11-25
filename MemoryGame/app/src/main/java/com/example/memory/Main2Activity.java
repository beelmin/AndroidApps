package com.example.memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private static final int[] BUTTONS_ID= {R.id.field0, R.id.field1, R.id.field2, R.id.field3,
            R.id.field4, R.id.field5, R.id.field6, R.id.field7,
            R.id.field8, R.id.field9, R.id.field10, R.id.field11,
            R.id.field12, R.id.field13, R.id.field14, R.id.field15};

    private static final String KEY1 = "number of attempts";
    private static final String KEY2 = "number of matched pairs";
    private static final String KEY3 = "number of clicks";
    private static final String KEY4 = "index of first clicked button";
    private static final String KEY5 = "array of numbers";
    private static final String KEY6 = "array of available buttons";
    private static final String KEY7 = "start time of game";

    private TextView time;
    private Button newGame;
    private FrameLayout frameLayout;
    private LinearLayout linearLayout;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.game = new Game();
        this.setAllButtons();
        this.game.setAllListeners();

        this.game.attemptsTV = (TextView) findViewById(R.id.attempts);
        this.game.matchedTV = (TextView) findViewById(R.id.matched);

        this.time = (TextView) findViewById(R.id.total_time);
        this.time.setText(getIntent().getStringExtra("totalTime"));

        this.frameLayout = (FrameLayout)findViewById(R.id.table);
        this.linearLayout = (LinearLayout) findViewById(R.id.details);


        if(savedInstanceState != null) {
            this.frameLayout.setVisibility(View.VISIBLE);
            this.linearLayout.setVisibility(View.VISIBLE);

            this.game.setNumOfAttempts(savedInstanceState.getInt(KEY1));
            this.game.setNumOfMatchedPairs(savedInstanceState.getInt(KEY2));
            this.game.setNumOfClicks(savedInstanceState.getInt(KEY3));
            this.game.setFirstClicked(savedInstanceState.getInt(KEY4));
            this.game.setNumbers(savedInstanceState.getIntArray(KEY5));
            this.game.setAvailableButtons(savedInstanceState.getBooleanArray(KEY6));
            this.game.setStartTime(savedInstanceState.getString(KEY7));

            this.game.setFirstButtonClicked(this.game.buttons[this.game.getFirstClicked()]);
            this.game.attemptsTV.setText(String.valueOf(this.game.getNumOfAttempts()));
            this.game.matchedTV.setText(String.valueOf(this.game.getNumOfMatchedPairs()));

            this.game.enableOtherButtons();
            this.game.openFields();

        }else{
            this.game.setAvailableButtons();
        }




        this.newGame = (Button) findViewById(R.id.new_game);
        this.newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main2Activity.this.frameLayout.setVisibility(View.VISIBLE);
                Main2Activity.this.linearLayout.setVisibility(View.VISIBLE);

                Main2Activity.this.game = new Game();
                Main2Activity.this.setAllButtons();
                Main2Activity.this.game.setAllListeners();

                Main2Activity.this.game.attemptsTV = (TextView) findViewById(R.id.attempts);
                Main2Activity.this.game.matchedTV = (TextView) findViewById(R.id.matched);
                Main2Activity.this.game.attemptsTV.setText(String.valueOf(0));
                Main2Activity.this.game.matchedTV.setText(String.valueOf(0));

                Main2Activity.this.game.setAvailableButtons();
                Main2Activity.this.setAllButtonsEmpty();



            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY1,this.game.getNumOfAttempts());
        outState.putInt(KEY2,this.game.getNumOfMatchedPairs());
        outState.putInt(KEY3,this.game.getNumOfClicks());
        outState.putInt(KEY4,this.game.getFirstClicked());
        outState.putIntArray(KEY5,this.game.getNumbers());
        outState.putBooleanArray(KEY6,this.game.getAvailableButtons());
        outState.putString(KEY7,this.game.getStartTime());
    }

    private void setAllButtons() {
        for(int i = 0; i < this.game.buttons.length; i++) {
            this.game.buttons[i] = (Button) findViewById(BUTTONS_ID[i]);
        }
    }

    private void setAllButtonsEmpty() {
        for(int i = 0; i < this.game.buttons.length; i++) {
            this.game.buttons[i].setText("");
        }
    }

}
