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
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

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
    private boolean done, shift;
    private String calculationsString;
    private String displayString;
    private ArrayList<String> displayStringArray;
    private String[] addAnswerBeforeIt;
    private String[] addAnswerAfterIt;

    private Button btnCos, btnSin;
    private Button btnTan;
    private Button btnInverse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shift = false;
        answer = "";
        calculationsString = "";
        displayString="";
        addAnswerBeforeIt = new String[]{"*", "*10^", "^", "^2", "+", "/","-"};
        addAnswerAfterIt = new String[]{"log(","log10(","1/(","abs(","sqrt(","sin(","cos(","tan("};
        done=false;
        txtResult = findViewById(R.id.txtResult);
        txtResult.setShowSoftInputOnFocus(false);
        displayStringArray = new ArrayList<>();

        btnCos = findViewById(R.id.btnCos);
        btnSin = findViewById(R.id.btnSin);
        btnTan = findViewById(R.id.btnTan);
        btnInverse = findViewById(R.id.btnInverse);

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
        findViewById(R.id.btnLogBase).setOnClickListener(this);
        findViewById(R.id.btnLn).setOnClickListener(this);
        findViewById(R.id.btnAbs).setOnClickListener(this);
        findViewById(R.id.btnExponent).setOnClickListener(this);
        findViewById(R.id.btnShift).setOnClickListener(this);
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
                    answer = df.format((solveUsingLibrary()));
                    txtResult.setText(answer);
                    displayString = "";
                    calculationsString = "";
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                break;

            case "del":
                if(!calculationsString.equals("")) {
                    calculationsString = calculationsString.substring(0, calculationsString.length() - 1);
                    txtResult.setText(calculationsString);
                }
            break;

            case "ANS":
                calculationsString += answer;
                displayString += "Ans";
            break;

            case "SHIFT":
                btnShiftTapped(0);
                break;

            default:
                String temp = btnView.getTag().toString();
                populateCalculationString(temp);

                if(calculationsString.contains("logb(") && calculationsString.length() >= 6 && !calculationsString.contains(","))
                    calculationsString += ",";
                btnShiftTapped(1);

            break;
        }
        txtResult.setSelection(txtResult.length());
    }

    public double solveUsingLibrary() {
        Expression e;
        FunctionAndOperator fo = new FunctionAndOperator();
        e = new ExpressionBuilder(calculationsString).operator(fo.factorial).function(fo.logb).build();
        return e.evaluate();
    }

    public void resetEverything() {
        done=false;
        displayString="";
        calculationsString = "";
        txtResult.setText(calculationsString);
    }

    public void populateCalculationString(String temp) {
        if (temp.equals("Ans")) {
            calculationsString += answer;
            done = false;
        }
        else if(done){
            if(Arrays.asList(addAnswerBeforeIt).contains(temp))
                calculationsString += answer + temp;
            else if (Arrays.asList(addAnswerAfterIt).contains(temp))
                calculationsString += temp + answer;
            else
                calculationsString += temp;
            done=false;
        }
        else calculationsString += temp;
        txtResult.setText(calculationsString);
    }

    private void btnShiftTapped(int state){
        if(shift == false) shift = true;
        else shift = false;

        if(state == 0){
            if(shift == true){
                btnCos.setText("COS⁻¹");
                btnCos.setTag("acos(");
                btnSin.setText("SIN⁻¹");
                btnSin.setTag("asin(");
                btnTan.setText("TAN⁻¹");
                btnTan.setTag("atan(");
                btnInverse.setText("X !");
                btnInverse.setTag("!");
            }
            else{
                btnCos.setText("COS");
                btnCos.setTag("cos(");
                btnSin.setText("SIN");
                btnSin.setTag("sin(");
                btnTan.setText("TAN");
                btnTan.setTag("tan(");
                btnInverse.setText("X⁻¹");
                btnInverse.setTag("1/(");
            }
        }else{
            shift = false;
            btnCos.setText("COS");
            btnCos.setTag("cos(");
            btnSin.setText("SIN");
            btnSin.setTag("sin(");
            btnTan.setText("TAN");
            btnTan.setTag("tan(");
            btnInverse.setText("X⁻¹");
            btnInverse.setTag("1/(");
        }


    }
}
