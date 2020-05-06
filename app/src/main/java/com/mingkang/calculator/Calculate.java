package com.mingkang.calculator;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Calculate {
    public static double answer = 0;
    public static boolean done = false;
    public static HashMap<String, String> operatorHashMap = new HashMap<>();
    public static String[] addAnswerBeforeIt = {"×", "×₁₀", "^", "²", "+", "÷", "-","%","⁻¹"};
    public static String[] addAnswerAfterIt = new String[]{"ln(","log(","abs(","√(","sin(","cos(","tan(","sin⁻¹(","cos⁻¹(","tan⁻¹("};
    public static ArrayList<String> displayStringArray = new ArrayList<>();
    public static String validExpression = "";

    public static void initializeOperator() {
        operatorHashMap.put("√(", "sqrt(");
        operatorHashMap.put("²", "^(2)");
        operatorHashMap.put("%", "/(100)");
        operatorHashMap.put("×", "*");
        operatorHashMap.put("Ans", answer+"");
        operatorHashMap.put("⁻¹", "^(-1)");
        operatorHashMap.put("³√(", "");
        operatorHashMap.put("³", "^(3)");
        operatorHashMap.put("ˣ√(", "");
        operatorHashMap.put("sin⁻¹(", "asin(");
        operatorHashMap.put("cos⁻¹(", "acos(");
        operatorHashMap.put("tan⁻¹(", "atan(");
        operatorHashMap.put("ln(", "log(");
        operatorHashMap.put("log(", "log10(");
        operatorHashMap.put("×₁₀","*10^");
        operatorHashMap.put("÷","/");
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
        Expression e;
        FunctionAndOperator fo = new FunctionAndOperator();
        e = new ExpressionBuilder(validExpression).operator(fo.factorial).function(fo.logb).build();
        answer = e.evaluate();
        operatorHashMap.put("Ans",answer+"");
        return answer;
    }

    public static void resetEverything() {
        done = false;
        displayStringArray.clear();
    }
}
