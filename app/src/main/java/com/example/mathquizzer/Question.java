package com.example.mathquizzer;

import java.util.Random;

public class Question {

private int firstNumber;
private int secondNumber;
private int answer;

// there are 4 possible choices for the user to pick from
private int [] answerArray;

//which of the four positions is correct 0 or 1 or 2 or 3
private int answerPosition;

//the maximum value of first number and second number
private int upperLimit;

//string output of the problem e.g. "4 + 9 = "
    private String questionPhrase;

    //generate a new random question
    public Question(int upperLimit) {
        this.upperLimit = upperLimit;
        Random randomNumberMaker = new Random();
        this.firstNumber = randomNumberMaker.nextInt(upperLimit);
        if(this.firstNumber==0)
            this.firstNumber+=1;
        this.secondNumber = randomNumberMaker.nextInt(upperLimit);
        if(this.secondNumber==0)
            this.secondNumber+=1;
        if( (this.firstNumber % this.secondNumber) == 0 )
        {
            this.answer = this.firstNumber / this.secondNumber;
            this.questionPhrase = firstNumber + "/" + secondNumber + "=";
        }
        else {
            Random random = new Random();
            int operation = random.nextInt(3);
            if (operation == 0) {
                this.answer = this.firstNumber + this.secondNumber;
                this.questionPhrase = firstNumber + "+" + secondNumber + "=";
            } else if (operation == 1) {
                if (this.firstNumber < this.secondNumber) {
                    int temp = this.firstNumber;
                    this.firstNumber = this.secondNumber;
                    this.secondNumber = temp;
                }
                this.answer = this.firstNumber - this.secondNumber;
                this.questionPhrase = firstNumber + "-" + secondNumber + "=";
            } else if (operation == 2) {
                this.answer = this.firstNumber * this.secondNumber;
                this.questionPhrase = firstNumber + "*" + secondNumber + "=";
            }
        }

        this.answerPosition = randomNumberMaker.nextInt(4);

        this.answerArray = new int[] {0, 1, 2, 3};

        this.answerArray[0] = answer + 1;
        this.answerArray[1] = answer + 10;
        this.answerArray[2] = answer - 5;
        this.answerArray[3] = answer - 2;

        this.answerArray = shuffleArray(this.answerArray);
        answerArray[answerPosition]=answer;
    }
    private int[] shuffleArray(int[] array){
        int index,temp;
        Random randomNumberGenerator=new Random();
        for(int i=array.length-1;i>0;i--){
            index=randomNumberGenerator.nextInt(i+1);
            temp=array[index];
            array[index]=array[i];
            array[i]=temp;


        }
        return array;
    }


    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int[] getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(int[] answerArray) {
        this.answerArray = answerArray;
    }

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        this.answerPosition = answerPosition;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
    }
}
