package com.mingkang.calculator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Calculate {
    public String answer = "";
    public boolean done = false;
    public HashMap<String, String> operatorHashMap = new HashMap<>();
    public String[] addAnswerBeforeIt = new String[]{"*", "*10^", "^", "^2", "+", "/", "-",};
    public String[] addAnswerAfterIt = new String[]{"log(", "log10(", "1/(", "abs(", "sqrt("};

    public Calculate(String answer) {
        operatorHashMap.put("√(", "sqrt(");
        operatorHashMap.put("²", "^(2)");
        operatorHashMap.put("%", "/(100)");
        operatorHashMap.put("×", "*");
        operatorHashMap.put("Ans", (answer));
        operatorHashMap.put("⁻¹", "^(-1)");
        operatorHashMap.put("³√(", "");
        operatorHashMap.put("³", "^(3)");
        operatorHashMap.put("ˣ√(", "");
        operatorHashMap.put("sin⁻¹(", "asin(");
        operatorHashMap.put("cos⁻¹(", "acos(");
        operatorHashMap.put("tan⁻¹(", "atan(");
        operatorHashMap.put("ln(", "log(");
        operatorHashMap.put("log{", "log10(");
    }

    public String convertVisualToExpression(ArrayList<String> Visual) {
        String convert2 = "";
        String validExpression = "";
        for (String value : Visual) {
            if (operatorHashMap.containsKey(value))
                convert2 = operatorHashMap.get(value);
            else convert2 = value;
            validExpression += convert2;
        }
        return validExpression;
    }

    public String arrayListToString(ArrayList<String> displayStringArray) {
        String displayString = "";
        for (String value : displayStringArray)
            displayString += value;
        return displayString;
    }

    public ArrayList<String> populateCalculationArray(String temp, ArrayList<String> displayStringArray) {
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
        return displayStringArray;
    }
}
