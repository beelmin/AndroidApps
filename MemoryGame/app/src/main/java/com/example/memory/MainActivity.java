package com.example.memory;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

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

    private int numOfAttempts = 0;
    private int numOfMatchedPairs = 0;
    private int numOfClicks = 0;
    private int firstClicked;
    private Button firstButtonClicked;

    private Numbers obj;
    private int[] numbers;

    private TextView attemptsTV;
    private TextView matchedTV;
    private Button newGame;

    private Button[] buttons = new Button[16];
    private boolean[] availableButtons = new boolean[16];

    private String startTime = "";
    private String endTime = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.setAllButtons();
        this.attemptsTV = (TextView) findViewById(R.id.attempts);
        this.matchedTV = (TextView) findViewById(R.id.matched);

        if(savedInstanceState != null) {
            this.numOfAttempts = savedInstanceState.getInt(KEY1);
            this.numOfMatchedPairs = savedInstanceState.getInt(KEY2);
            this.numOfClicks = savedInstanceState.getInt(KEY3);
            this.firstClicked = savedInstanceState.getInt(KEY4);
            this.numbers = savedInstanceState.getIntArray(KEY5);
            this.availableButtons = savedInstanceState.getBooleanArray(KEY6);
            this.startTime = savedInstanceState.getString(KEY7);

            this.firstButtonClicked = this.buttons[this.firstClicked];
            this.attemptsTV.setText(String.valueOf(this.numOfAttempts));
            this.matchedTV.setText(String.valueOf(this.numOfMatchedPairs));

            this.enableOtherButtons();
            this.openFields();


        }else{
            this.obj = new Numbers();
            this.numbers = obj.getNumbers();
            this.setAvailableButtons();
        }



        for(int i = 0; i < this.buttons.length; i++) {

            this.buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(v.getId()) {
                        case R.id.field0:
                            MainActivity.this.doAction(0);
                            break;
                        case R.id.field1:
                            MainActivity.this.doAction(1);
                            break;
                        case R.id.field2:
                            MainActivity.this.doAction(2);
                            break;
                        case R.id.field3:
                            MainActivity.this.doAction(3);
                            break;
                        case R.id.field4:
                            MainActivity.this.doAction(4);
                            break;
                        case R.id.field5:
                            MainActivity.this.doAction(5);
                            break;
                        case R.id.field6:
                            MainActivity.this.doAction(6);
                            break;
                        case R.id.field7:
                            MainActivity.this.doAction(7);
                            break;
                        case R.id.field8:
                            MainActivity.this.doAction(8);
                            break;
                        case R.id.field9:
                            MainActivity.this.doAction(9);
                            break;
                        case R.id.field10:
                            MainActivity.this.doAction(10);
                            break;
                        case R.id.field11:
                            MainActivity.this.doAction(11);
                            break;
                        case R.id.field12:
                            MainActivity.this.doAction(12);
                            break;
                        case R.id.field13:
                            MainActivity.this.doAction(13);
                            break;
                        case R.id.field14:
                            MainActivity.this.doAction(14);
                            break;
                        case R.id.field15:
                            MainActivity.this.doAction(15);
                            break;
                        default:
                            break;
                    }
                }
            });
        }


        this.newGame = (Button) findViewById(R.id.newGame);
        this.newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity.this.numOfClicks = 0;
                MainActivity.this.numOfAttempts = 0;
                MainActivity.this.numOfMatchedPairs = 0;
                MainActivity.this.startTime = "";
                MainActivity.this.endTime = "";

                MainActivity.this.obj = new Numbers();
                MainActivity.this.numbers = obj.getNumbers();

                MainActivity.this.matchedTV.setText(String.valueOf(0));
                MainActivity.this.attemptsTV.setText(String.valueOf(0));

                MainActivity.this.setAllButtonsEmpty();
                MainActivity.this.enableAllButtons();
                MainActivity.this.setAvailableButtons();


            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY1,this.numOfAttempts);
        outState.putInt(KEY2,this.numOfMatchedPairs);
        outState.putInt(KEY3,this.numOfClicks);
        outState.putInt(KEY4,this.firstClicked);
        outState.putIntArray(KEY5,this.numbers);
        outState.putBooleanArray(KEY6,this.availableButtons);
        outState.putString(KEY7,this.startTime);
    }

    private void enableAllButtons() {
        for(int i = 0; i < this.buttons.length; i++) {
            this.buttons[i].setEnabled(true);
        }
    }

    private void setAllButtonsEmpty() {
       for(int i = 0; i < this.buttons.length; i++) {
           this.buttons[i].setText("");
       }
    }

    private void setAllButtons() {
        for(int i = 0; i < this.buttons.length; i++) {
            this.buttons[i] = (Button) findViewById(this.BUTTONS_ID[i]);
        }
    }

    private void setAvailableButtons() {
        for(int i = 0; i < this.availableButtons.length; i++) {
            this.availableButtons[i] = true;
        }
    }

    private void setAllButtonsUnavailable() {
        for(int i = 0; i < this.buttons.length; i++) {
            this.buttons[i].setEnabled(false);
        }
    }

    private void enableOtherButtons() {
        for(int i = 0; i < this.availableButtons.length; i++) {
            this.buttons[i].setEnabled(this.availableButtons[i]);
        }
    }

    private void openFields() {
        for(int i = 0; i < this.availableButtons.length; i++) {
            if(this.availableButtons[i] == false) {
                this.buttons[i].setText(String.valueOf(this.numbers[i]));
            }
        }
    }

    private void isEndGame() {
        if(this.numOfMatchedPairs == 8) {
            Toast.makeText(this,R.string.toast,Toast.LENGTH_SHORT).show();
            this.endTime =  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

            Intent intent = new Intent(this,Main2Activity.class);
            intent.putExtra("totalTime",this.timeOfGame());
            startActivity(intent);

        }

    }

    private void action1(int index) {
        this.firstClicked = index;
        this.firstButtonClicked = buttons[index];
        this.buttons[index].setEnabled(false);
        this.availableButtons[index] = false;
    }

    private void action2(int index) {
        this.numOfMatchedPairs++;
        this.matchedTV.setText(String.valueOf(this.numOfMatchedPairs));
        this.availableButtons[this.firstClicked] = false;
        this.availableButtons[index] = false;
        this.enableOtherButtons();
        this.isEndGame();
    }


    private void action3(int index) {
        final int x = index;
        this.availableButtons[this.firstClicked] = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.firstButtonClicked.setText("");
                MainActivity.this.buttons[x].setText("");
                MainActivity.this.enableOtherButtons();
            }
        }, 500);

    }

    private void action4() {
        this.numOfClicks = 0;
        this.numOfAttempts++;
        this.attemptsTV.setText(String.valueOf(this.numOfAttempts));
    }


    private void doAction(int index) {
        if(this.startTime.equals("")){
            this.startTime =  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        }

        this.buttons[index].setText(String.valueOf(this.numbers[index]));
        this.numOfClicks++;

        if(this.numOfClicks == 1) {
            this.action1(index);
        }else if(this.numOfClicks == 2) {
            this.setAllButtonsUnavailable();
            if(this.numbers[firstClicked] == this.numbers[index]){
                this.action2(index);
            }else{
                this.action3(index);
            }
            this.action4();
        }
    }

    private String timeOfGame(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        String totalTime = "";

        try{
            date1 = format.parse(this.startTime);
            date2 = format.parse(this.endTime);
            long diff = date2.getTime() - date1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            totalTime ="Total time: " + diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds.";

        }catch(Exception e){
            e.printStackTrace();
        }

        return totalTime;
    }

}
