package com.mingkang.calculator;

import java.util.ArrayList;

public class Calculate {
    private ArrayList<Integer> number;
    private ArrayList<String> operator;
    private ArrayList<String> multiply;
    private ArrayList<String> divide;
    private double answer;

    public ArrayList<Integer> getNumber() {
        return number;
    }
    public ArrayList<String> getOperator() {
        return operator;
    }
    public ArrayList<String> getMultiply() {
        return multiply;
    }
    public ArrayList<String> getDivide() {
        return divide;
    }
    public double getAnswer() {
        return answer;
    }
    public void setNumber(ArrayList<Integer> number) {
        this.number = number;
    }
    public void setOperator(ArrayList<String> operator) {
        this.operator = operator;
    }
    public void setAnswer(double answer) {
        this.answer = answer;
    }
    public void initialise(String string){


    }
    public void simplify(String string){

    }


}


/*
    1) public void initialise(string) (MK)
    int number[] , String operator[]
    
    2) public boolean validate(operator[]) (MK)
    -> Check for all strings in operator
        -> If got 'x' or '/' after first character -> False
        -> else -> True

    3) public void simplify() (ZH)
       -> Simplify all '+' and '-' into number[]
       -> Set index for multiply and divide

    4) public void calculate() (LK)
    -> Multiply and Divide all values
    
    5) private double findAnswer() (LK)
    -> Find sum of number[];
     */