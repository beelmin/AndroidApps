package com.example.memory;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Game {

    private int numOfAttempts;
    private int numOfMatchedPairs;
    private int numOfClicks;
    private int firstClicked;
    private Button firstButtonClicked;

    private Numbers obj;
    private int[] numbers;

    public TextView attemptsTV;
    public TextView matchedTV;

    public Button[] buttons;
    private boolean[] availableButtons;

    private String startTime;
    private String endTime;

    Game() {

        this.numOfAttempts = 0;
        this.numOfMatchedPairs = 0;
        this.numOfClicks = 0;
        this.startTime = "";
        this.endTime = "";
        this.buttons = new Button[16];
        this.availableButtons = new boolean[16];

        this.obj = new Numbers();
        this.numbers = obj.getNumbers();


    }


    public void setAllListeners() {

        for(int i = 0; i < this.buttons.length; i++) {

            this.buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.field0:
                            Game.this.doAction(0);
                            break;
                        case R.id.field1:
                            Game.this.doAction(1);
                            break;
                        case R.id.field2:
                            Game.this.doAction(2);
                            break;
                        case R.id.field3:
                            Game.this.doAction(3);
                            break;
                        case R.id.field4:
                            Game.this.doAction(4);
                            break;
                        case R.id.field5:
                            Game.this.doAction(5);
                            break;
                        case R.id.field6:
                            Game.this.doAction(6);
                            break;
                        case R.id.field7:
                            Game.this.doAction(7);
                            break;
                        case R.id.field8:
                            Game.this.doAction(8);
                            break;
                        case R.id.field9:
                            Game.this.doAction(9);
                            break;
                        case R.id.field10:
                            Game.this.doAction(10);
                            break;
                        case R.id.field11:
                            Game.this.doAction(11);
                            break;
                        case R.id.field12:
                            Game.this.doAction(12);
                            break;
                        case R.id.field13:
                            Game.this.doAction(13);
                            break;
                        case R.id.field14:
                            Game.this.doAction(14);
                            break;
                        case R.id.field15:
                            Game.this.doAction(15);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

    }




    public void setAvailableButtons() {
        for(int i = 0; i < this.availableButtons.length; i++) {
            this.availableButtons[i] = true;
        }
    }



    private void isEndGame() {
        if(this.numOfMatchedPairs == 8) {
            //Toast.makeText(MainActivity.this,R.string.toast,Toast.LENGTH_SHORT).show();
            this.endTime =  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        }

    }

    private void setAllButtonsUnavailable() {
        for(int i = 0; i < this.buttons.length; i++) {
            this.buttons[i].setEnabled(false);
        }
    }


    public void enableOtherButtons() {
        for(int i = 0; i < this.availableButtons.length; i++) {
            this.buttons[i].setEnabled(this.availableButtons[i]);
        }
    }

    public void openFields() {
        for(int i = 0; i < this.availableButtons.length; i++) {
            if(this.availableButtons[i] == false) {
                this.buttons[i].setText(String.valueOf(this.numbers[i]));
            }
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
                Game.this.firstButtonClicked.setText("");
                Game.this.buttons[x].setText("");
                Game.this.enableOtherButtons();
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

    public int getNumOfAttempts() {
        return this.numOfAttempts;
    }

    public void setNumOfAttempts(int x){
        this.numOfAttempts = x;
    }

    public int getNumOfMatchedPairs(){
        return this.numOfMatchedPairs;
    }

    public void setNumOfMatchedPairs(int x){
        this.numOfMatchedPairs = x;
    }

    public int getNumOfClicks() {
        return this.numOfClicks;
    }

    public void setNumOfClicks(int x){
        this.numOfClicks = x;
    }

    public int getFirstClicked() {
        return this.firstClicked;
    }

    public void setFirstClicked(int x){
        this.firstClicked = x;
    }

    public int[] getNumbers(){
        return this.numbers;
    }

    public void setNumbers(int[] x){
        this.numbers = new int[16];
        for(int i = 0; i < 16; i++){
            this.numbers[i] = x[i];
        }
    }

    public boolean[] getAvailableButtons(){
        return this.availableButtons;
    }

    public void setAvailableButtons(boolean[] x){
        this.availableButtons = new boolean[16];
        for(int i = 0; i < 16; i++){
            this.availableButtons[i] = x[i];
        }
    }

    public String getStartTime(){
        return this.startTime;
    }

    public void setStartTime(String x){
        this.startTime = x;
    }

    public void setFirstButtonClicked(Button x){
        this.firstButtonClicked = x;
    }




}
