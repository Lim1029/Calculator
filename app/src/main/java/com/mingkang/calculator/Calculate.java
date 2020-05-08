package com.mingkang.calculator;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Calculate {
    public static double answer = 0;
    public static boolean done = false;
    public static HashMap<String, String> operatorHashMap = new HashMap<>();
    public static HashMap<String, String> buttonShiftHashMap = new HashMap<>();
    public static String[] addAnswerBeforeIt = {"×", "×₁₀", "^(", "²", "+", "÷", "-","%","⁻¹","!","³","³√(","%","⌟"};
    public static String[] addAnswerAfterIt = new String[]{"ln(","log(","Abs(","√(","sin(","cos(","tan(","sin⁻¹(","cos⁻¹(","tan⁻¹("};
    public static ArrayList<String> displayStringArray = new ArrayList<>();
    public static String validExpression = "";

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
    }


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

    public static String arrayListToString() {
        String displayString = "";
        for (String value : displayStringArray)
            displayString += value;
        return displayString;
    }

    public static void populateCalculationArray(String temp) {
        if (temp.equals("Ans")) {
            displayStringArray.add(temp);
            done = false;
        } else if (done) {
            if (Arrays.asList(addAnswerBeforeIt).contains(temp)) {
                displayStringArray.add("Ans");
                displayStringArray.add(temp);
            } else if (Arrays.asList(addAnswerAfterIt).contains(temp)) {
                displayStringArray.add(temp);
                displayStringArray.add("Ans");
            } else
                displayStringArray.add(temp);
            done = false;
        } else displayStringArray.add(temp);
        //return displayStringArray;
    }

    public static double solveUsingLibrary() {
        FunctionAndOperator fo = new FunctionAndOperator();
        Expression e = new ExpressionBuilder(validExpression)
                .operator(fo.factorial, fo.combination, fo.permutation, fo.fraction)
                .functions(fo.logb,fo.quadratic)
                .variables("x", "y")
                .build();

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
        displayStringArray.clear();
    }
}
