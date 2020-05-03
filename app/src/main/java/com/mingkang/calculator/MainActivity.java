package com.mingkang.calculator;

import android.app.Activity;
import android.content.Context;
import android.icu.lang.UCharacterEnums;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;

import android.text.InputType;
import android.text.Selection;

import android.text.Selection;import android.text.Editable;import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText txtResult;
    private DecimalFormat df = new DecimalFormat("#.###############");
    private String answer;
    private boolean done;
    private String calculationsString;
    private String displayString;
    private String[] addAnswerBeforeIt;
    private String[] addAnswerAfterIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answer = "";
        calculationsString = "";
        displayString="";
        addAnswerBeforeIt = new String[]{"*", "*10^", "^", "^2", "+", "/","-",};
        addAnswerAfterIt = new String[]{"log(","log10(","1/(","abs(","sqrt("};
        done=false;
        txtResult = findViewById(R.id.txtResult);
        txtResult.setShowSoftInputOnFocus(false);

        InitialiseButton();

    }


    private void InitialiseButton(){
        findViewById(R.id.btnEqual).setOnClickListener(this);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnAC).setOnClickListener(this);
        findViewById(R.id.btnDel).setOnClickListener(this);
        findViewById(R.id.btnAns).setOnClickListener(this);
        findViewById(R.id.btnCos).setOnClickListener(this);
        findViewById(R.id.btnSin).setOnClickListener(this);
        findViewById(R.id.btnTan).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnLeftBracket).setOnClickListener(this);
        findViewById(R.id.btnRightBracket).setOnClickListener(this);
        findViewById(R.id.btnFraction).setOnClickListener(this);
        findViewById(R.id.btnSqrt).setOnClickListener(this);
        findViewById(R.id.btnInverse).setOnClickListener(this);
        findViewById(R.id.btnSquare).setOnClickListener(this);
        findViewById(R.id.btnLog10).setOnClickListener(this);
        findViewById(R.id.btnLn).setOnClickListener(this);
        findViewById(R.id.btnAbs).setOnClickListener(this);
        findViewById(R.id.btnExponent).setOnClickListener(this);
    }

    @Override
    public void onClick(View btnView) {
        switch (btnView.getTag().toString()){
            case "ac":
                resetEverything();
                break;
            case "=":
                done=true;
                try {
//                    answer = solveUsingLibrary();
                    answer = df.format((solveUsingLibrary()));
                    txtResult.setText(answer);
//                    txtResult.setText(df.format(answer));
                    displayString = "";
                    calculationsString = "";
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                break;

            case "del":
//                if(!calculationsString.equals("")) {
//                    calculationsString = calculationsString.substring(0, calculationsString.length() - 1);
//                    if (displayString.charAt(displayString.length() - 1) == 's' || displayString.charAt(displayString.length() - 1) == 'n' || displayString.charAt(displayString.length() - 1) == 'g') {
//                        displayString = displayString.substring(0, displayString.length() - 3);
//                    } else {
//                        displayString = displayString.substring(0, displayString.length() - 1);
//                    }
//                    txtResult.setText(calculationsString);
//                }
                if(!calculationsString.equals("")) {
                    calculationsString = calculationsString.substring(0, calculationsString.length() - 1);
                    txtResult.setText(calculationsString);
                }
            break;
            case "ANS":
                calculationsString += answer;
                displayString += "Ans";
            break;
            default:
                String temp = btnView.getTag().toString();
                populateCalculationString(temp);
            break;
        }
        txtResult.setSelection(txtResult.length());
    }

    public double solveUsingLibrary() {
            Expression e = new ExpressionBuilder(calculationsString).build();
            return e.evaluate();
    }

    public void resetEverything() {
        done=false;
        displayString="";
        calculationsString = "";
        txtResult.setText(calculationsString);
    }

    public void populateCalculationString(String temp)
    {
        if (temp.equals("Ans")) {
            calculationsString += answer;
            done = false;
        }
        else if(done){
            if(Arrays.asList(addAnswerBeforeIt).contains(temp))
                calculationsString += answer + temp;
            else if (Arrays.asList(addAnswerAfterIt).contains(temp))
                calculationsString += temp + answer;
//                    if(temp.equals("*")){
//                        displayString += "Ans";
//                        calculationsString += String.valueOf(answer) + '*';
//                    }
//                    else if(temp.equals("/")){
//                        displayString += "Ans" + "\u00f7";
//                        calculationsString += String.valueOf(answer) + '/';
//                    }
//                    else if(temp.equals("+") || temp.equals("-")){
//                        displayString += "Ans" + temp;
//                        calculationsString += String.valueOf(answer) + temp;
//                    }
//                    else if(temp.equals("/fraction")){
//                        displayString += "Ans/";
//                        calculationsString += String.valueOf(answer) + "/";
//                    }
//                    else if(temp.equals("^2")){
//                        displayString = "Ans^2";
//                        calculationsString = "" + String.valueOf(answer) + "^2";
//                    }
//                    else if(temp.equals("^")){
//                        displayString = "Ans^";
//                        calculationsString = "" + String.valueOf(answer)  + "^";
//                    }
//                    else if(temp.equals("log(")){
//                        displayString = "ln(Ans";
//                        calculationsString = "log(" + String.valueOf(answer);
//                    }
//                    else if(temp.equals("log10(")){
//                        displayString = "log(Ans";
//                        calculationsString = "log10(" + String.valueOf(answer);
//                    }
//                    else if(temp.equals("1/(")){
//                        displayString = "(Ans)^-1";
//                        calculationsString = "1/(" + String.valueOf(answer) + ")";
//                    }
//                    else if(temp.equals("abs(")){
//                        displayString = "abs(Ans";
//                        calculationsString = "abs(" + String.valueOf(answer);
//                    }
//                    else if(temp.equals("Exp")){
//                        displayString = "Ansx10^";
//                        calculationsString = String.valueOf(answer) + "*10^";
//                    }
            else{
                displayString += temp;
                calculationsString += temp;
            }
            done=false;
        }
        else calculationsString += temp;
//                    if(temp.equals("*")){
//                        displayString += 'x';
//                        calculationsString += "*";
//                    }
//                    else if(temp.equals("/")){
//                        displayString += "\u00f7";
//                        calculationsString += "/";
//                    }
//                    else if(temp.equals("/fraction")){
//                        displayString = "" + displayString + "/";
//                        calculationsString = "" + calculationsString + "/";
//                    }
//                    else if(temp.equals("^2")){
//                        displayString = "" + displayString + "^2";
//                        calculationsString = "" + calculationsString + "^2";
//                    }
//                    else if(temp.equals("^")){
//                        displayString = "" + displayString + "^";
//                        calculationsString = "" + calculationsString  + "^";
//                    }
//                    else if(temp.equals("Exp")){
//                        displayString = "*10^";
//                        calculationsString += "*10^";
//                    }
//                    else if(temp.equals("log10(")){
//                        displayString = "log(";
//                        calculationsString += temp;
//                    }
//                    else if(temp.equals("log(")){
//                        displayString = "ln(";
//                        calculationsString +=temp;
//                    }
//                    else {
//                        displayString += temp;
//                        calculationsString += temp;
//                    }
        txtResult.setText(calculationsString);
    }
}
