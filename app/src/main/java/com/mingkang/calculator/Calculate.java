package com.mingkang.calculator;

import android.util.Log;

import android.widget.EditText;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Calculate {
    public static double answer = 0;
    public static boolean done = false;
    public static HashMap<String, String> operatorHashMap = new HashMap<>();
    public static HashMap<String, String> buttonShiftHashMap = new HashMap<>();
    public static String[] addAnswerBeforeIt = {"×", "×₁₀","²", "+", "÷", "-","%","⁻¹","!","³","%","⌟","^("};
    public static String[] addAnswerAfterIt = new String[]{"ln(","log(","Abs(","√(","sin(","cos(","tan(","sin⁻¹(","cos⁻¹(","tan⁻¹(","³√("};
    public static ArrayList<String> displayStringArray = new ArrayList<>();
    public static String validExpression = "";
    public static ArrayList<Integer> textCountArray = new ArrayList<>(), focusArray = new ArrayList<>();

    public static void initializeOperator() {
        operatorHashMap.put("√(", "sqrt(");
        operatorHashMap.put("²", "^(2)");
        operatorHashMap.put("%", "/(100)");
        operatorHashMap.put("×", "*");
        operatorHashMap.put("Ans", answer+"");
        operatorHashMap.put("⁻¹", "^(-1)");
        operatorHashMap.put("³√(", "cbrt(");
        operatorHashMap.put("³", "^(3)");
        operatorHashMap.put("ˣ√(", "");
        operatorHashMap.put("sin⁻¹(", "asin(");
        operatorHashMap.put("cos⁻¹(", "acos(");
        operatorHashMap.put("tan⁻¹(", "atan(");
        operatorHashMap.put("ln(", "log(");
        operatorHashMap.put("log(", "log10(");
        operatorHashMap.put("×₁₀","*10^");
        operatorHashMap.put("÷","/");
        operatorHashMap.put("C","#|");
        operatorHashMap.put("P","#&");
        operatorHashMap.put("⌟","|");
        operatorHashMap.put("≡", "$");
        operatorHashMap.put("Abs(","abs(");
        refreshFocusArray();


    }
    public static void initializeShiftSymbol(){
        buttonShiftHashMap.put(".","Ran#");
        buttonShiftHashMap.put("×₁₀","π");
        buttonShiftHashMap.put("0","Rnd");
        buttonShiftHashMap.put("1","STAT");
        buttonShiftHashMap.put("Ans","DRG>");
        buttonShiftHashMap.put("2","CMPLX");
        buttonShiftHashMap.put("3","BASE");
        buttonShiftHashMap.put("+","Pol");
        buttonShiftHashMap.put("-","Rec");
        buttonShiftHashMap.put("4","MATRIX");
        buttonShiftHashMap.put("5","VECTOR");
        buttonShiftHashMap.put("×","P");
        buttonShiftHashMap.put("÷","C");
        buttonShiftHashMap.put("7","CONST");
        buttonShiftHashMap.put("8","CONV");
        buttonShiftHashMap.put("9","CLR");
        buttonShiftHashMap.put("DEL","INS");
        buttonShiftHashMap.put("AC","OFF");
        buttonShiftHashMap.put("RCL","STO");
        buttonShiftHashMap.put("ENG","<-");
        buttonShiftHashMap.put("(","%");
        buttonShiftHashMap.put(")",",");
        buttonShiftHashMap.put("S<=>D","abc<=>d/c");
        buttonShiftHashMap.put("M+","M-");
        buttonShiftHashMap.put("(-)","<");
        buttonShiftHashMap.put("°","<-");
        buttonShiftHashMap.put("hyp","Abs(");
        buttonShiftHashMap.put("sin(","sin⁻¹(");
        buttonShiftHashMap.put("cos(","cos⁻¹(");
        buttonShiftHashMap.put("tan(","tan⁻¹(");
        buttonShiftHashMap.put("⌟","⌟⌟");
        buttonShiftHashMap.put("√(","³√(");
        buttonShiftHashMap.put("²","³");
        buttonShiftHashMap.put("²","³");
        buttonShiftHashMap.put("^(","³√(");
        buttonShiftHashMap.put("log(","₁₀^(");
        buttonShiftHashMap.put("ln(","e^(");
        buttonShiftHashMap.put("calc","SOLVE");
        buttonShiftHashMap.put("∫(","d/dx(");
        buttonShiftHashMap.put("⁻¹","!");
        buttonShiftHashMap.put("log(","Σ(");
        buttonShiftHashMap.put("MODE","SETUP");
    }    //∫
    public static void convertVisualToExpression() {
        validExpression = "";
        String convert2 = "";
        for (String value : displayStringArray) {
            if (operatorHashMap.containsKey(value))
                convert2 = operatorHashMap.get(value);
            else convert2 = value;
            validExpression += convert2;
        }
    }
    public static void populateCalculationArray(String temp) {
        if(done)
            resetPartially();
        if (temp.equals("Ans")) {
            populateString(temp);
            done = false;
        } else if (done) {
            if (Arrays.asList(addAnswerBeforeIt).contains(temp)) {
                populateString("Ans");
                populateString(temp);
            } else if (Arrays.asList(addAnswerAfterIt).contains(temp)) {
                populateString(temp);
                populateString("Ans");
            } else
                populateString(temp);
            done = false;
        } else populateString(temp);
    }
    public static double solveUsingLibrary() {
        FunctionAndOperator fo = new FunctionAndOperator();
        Expression e;
        if(SetupFragment.unitOfAngle.equals("degree")) {
                e = new ExpressionBuilder(validExpression)
                    .operator(fo.factorial, fo.combination, fo.permutation, fo.fraction)
                    .functions(fo.myDegreeFunctions)
                    .variables("x", "y")
                    .build();
        } else if (SetupFragment.unitOfAngle.equals("radian")) {
                e = new ExpressionBuilder(validExpression)
                    .operator(fo.factorial, fo.combination, fo.permutation, fo.fraction)
                    .functions(fo.myRadianFunctions)
                    .variables("x", "y")
                    .build();
        } else {
            e = new ExpressionBuilder(validExpression)
                    .operator(fo.factorial, fo.combination, fo.permutation, fo.fraction)
                    .functions(fo.myFunctions)
                    .variables("x", "y")
                    .build();
        }

        if(MainActivity.substitute == true){
            ValidationResult res = e.validate();
            e.setVariable("x", Double.parseDouble(MainActivity.txtResult.getText().toString()));
            res = e.validate();
        }

        answer = e.evaluate();
        operatorHashMap.put("Ans",answer+"");
        return answer;
    }
    public static void resetEverything() {
        done = false;
        focusArray = new ArrayList<>();
        textCountArray = new ArrayList<>();
        validExpression = "";

        refreshFocusArray();
        MainActivity.txtResult.getText().clear();
    }
    public static void resetPartially(){
        focusArray = new ArrayList<>();
        textCountArray = new ArrayList<>();
        validExpression = "";

        refreshFocusArray();
        MainActivity.txtResult.getText().clear();
    }
    public static void refreshFocusArray() {
        focusArray = new ArrayList<>();
        focusArray.add(0);
        for (int number : textCountArray) {
            focusArray.add(focusArray.get(focusArray.size() - 1) + number);
        }
    }
    public static void delete() {
        int num = 0;
        int i;
        for (i = 0; i < textCountArray.size(); i++) {
            if (num != MainActivity.txtResult.getSelectionStart())
                num += textCountArray.get(i);
            else break;
        }
        MainActivity.txtResult.getText().delete(MainActivity.txtResult.getSelectionStart() - textCountArray.get(i - 1), MainActivity.txtResult.getSelectionStart());
        textCountArray.remove(i - 1);
        refreshFocusArray();
    }
    public static void stringToDisplayStringArray() {
        displayStringArray = new ArrayList<>();
        String string = MainActivity.txtResult.getText().toString();
        int position = 0;
        for (int i = 1; i < focusArray.size(); i++) {
            displayStringArray.add(string.substring(position, focusArray.get(i)));
            position = focusArray.get(i);
        }
    }
    public static void setSelectionPosition(int position) {
        if (focusArray.contains(position)) {
            MainActivity.txtResult.setSelection(position);
        } else {
            boolean repeat = true;
            int focus=0;
            while (repeat) {
                int diff = Integer.MAX_VALUE;
                for (int i=1;i<focusArray.size();i++) {
                    if(Math.abs(focusArray.get(i)-position)<=diff){
                        focus=focusArray.get(i);
                        diff=Math.abs(focusArray.get(i)-position);
                    }

                }
                if (focus <= position) {
                    position++;
                    repeat = true;
                } else {
                    repeat = false;
                }

            }
            MainActivity.txtResult.setSelection(focus);

        }
//        convertToDisplayStringArray();
//        textView.setText(Calculate.arrayListToString(stringArray));
    }
    public static void setSelectionPosition2(int position) {
        if (focusArray.contains(position)) {
            MainActivity.txtResult.setSelection(position);
        } else {
            boolean repeat = true;
            int focus=0;
            while (repeat) {
                int diff = Integer.MAX_VALUE;
                for (int i=1;i<focusArray.size();i++) {
                    if(Math.abs(focusArray.get(i)-position)<=diff){
                        focus=focusArray.get(i);
                        diff=Math.abs(focusArray.get(i)-position);
                    }

                }
                if (focus >= position) {
                    position--;
                    repeat = true;
                } else {
                    repeat = false;
                }

            }
            MainActivity.txtResult.setSelection(focus);

        }
//        convertToDisplayStringArray();
//        textView.setText(Calculate.arrayListToString(stringArray));
    }
    public static void populateString(String value){
        MainActivity.txtResult.getText().insert(MainActivity.txtResult.getSelectionStart(), value);
        textCountArray.add(focusArray.indexOf(MainActivity.txtResult.getSelectionStart() - value.length()), value.length());
        refreshFocusArray();
    }
    public static void rightButtonPressed() {
        MainActivity.txtResult.setSelection(MainActivity.txtResult.getSelectionStart() + 1);
        setSelectionPosition(MainActivity.txtResult.getSelectionStart());
    }
    public static void leftButtonPressed(){
        MainActivity.txtResult.setSelection(MainActivity.txtResult.getSelectionStart() - 1);
        setSelectionPosition2(MainActivity.txtResult.getSelectionStart());
    }
}
