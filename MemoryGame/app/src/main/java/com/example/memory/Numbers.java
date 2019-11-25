package com.example.memory;

import java.util.Random;

public class Numbers {
    private int[] numbers;

    Numbers() {
        this.numbers = new int[16];
        this.fillNumbers();
        this.shuffleNumbers();
    }

    private void fillNumbers() {
        int[] tempNumbers = new int[8];
        Random rand = new Random();
        int i = 0;

        while(i < 8){
            int number = rand.nextInt(100);
            boolean exist = false;
            for(int j = 0; j < tempNumbers.length; j++){
                if(tempNumbers[j] == number) {
                    exist = true;
                    break;
                }
            }
            if(exist){
                continue;
            }else{
                tempNumbers[i] = number;
                i++;
            }
        }

        for(int j = 0; j < tempNumbers.length; j++) {
            this.numbers[j] = tempNumbers[j];
            this.numbers[j+8] = tempNumbers[j];
        }
    }

    private void shuffleNumbers() {
        Random rand = new Random();
        for(int i = 0; i < this.numbers.length; i++) {
            int randomPosition = rand.nextInt(numbers.length);
            int temp = this.numbers[i];
            this.numbers[i] = this.numbers[randomPosition];
            this.numbers[randomPosition] = temp;
        }
    }

    public int[] getNumbers() {
        return this.numbers;
    }
}

