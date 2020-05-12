package com.mingkang.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.lang.UCharacterEnums;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;

import android.text.InputType;
import android.text.Selection;

import android.text.Selection;import android.text.Editable;import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    public static EditText txtResult;
    private TextView txtResult2;
    private DecimalFormat df = new DecimalFormat("#.################");
    private boolean shift;
    private String calculationsString, storedValue = "";
    private Button btnCos, btnSin, btnTan, btnInverse, btn10, btnSquare, btnSqrt, btnDot, btnMultiply, btnDivide, btnPercent, btnRightBracket;
    private boolean state = false;
    public static boolean quadraticOperation, substitute, imaginaryRoot;
    private View root;
    private PopupMenu popup;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(root);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        shift = false;
        txtResult = findViewById(R.id.txtResult);
        txtResult2 = findViewById(R.id.txtResult2);
        txtResult.setShowSoftInputOnFocus(false);

        InitialiseButton();
        InitialiseOnClickButton();
        Calculate.initializeOperator();
        Calculate.initializeShiftSymbol();

        findViewById(R.id.txtM).setVisibility(View.VISIBLE);
    }
    private void InitialiseButton(){
        btnCos = findViewById(R.id.btnCos);
        btnSin = findViewById(R.id.btnSin);
        btnTan = findViewById(R.id.btnTan);
        btnInverse = findViewById(R.id.btnInverse);
        btn10 = findViewById(R.id.btn10);
        btnSquare = findViewById(R.id.btnSquare);
        btnSqrt = findViewById(R.id.btnSqrt);
        btnDot = findViewById(R.id.btnDot);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        //btnPercent = findViewById(R.id.btnPercent);
        btnRightBracket = findViewById(R.id.btnRightBracket);
    }
    private void InitialiseOnClickButton() {
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
        //findViewById(R.id.btnPercent).setOnClickListener(this);
        findViewById(R.id.btnQuadratic).setOnClickListener(this);
        findViewById(R.id.btnCalc).setOnClickListener(this);
        findViewById(R.id.btnMode).setOnClickListener(this);
        findViewById(R.id.btnLeft).setOnClickListener(this);
        findViewById(R.id.btnRight).setOnClickListener(this);

        txtResult2.setMovementMethod(new ScrollingMovementMethod());
    }
    @Override
    public void onClick(View btnView) {
        switch (btnView.getTag().toString()) {
            case "ac":
                Calculate.resetEverything();
                txtResult2.setText("0");
                state = false;
                break;
            case "=":
                Calculate.done = true;
                try {
                    Calculate.stringToDisplayStringArray();
                    Calculate.convertVisualToExpression();
                    txtResult2.setText(df.format(Calculate.solveUsingLibrary()));
                    if (quadraticOperation) {
                        String solution = df.format(Calculate.solveUsingLibrary());
                        if (imaginaryRoot == true) {
                            txtResult2.setText("X1: " + txtResult2.getText().toString() + "+" + solution + "i" + "\nX2: " + txtResult2.getText().toString() + "-" + solution + "i");
                            imaginaryRoot = false;
                        } else {
                            txtResult2.setText("X1: " + txtResult2.getText().toString() + "\nX2: " + solution);
                        }
                        quadraticOperation = false;
                    }

                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                state = false;
                break;

            case "del":
                if(!txtResult.getText().toString().isEmpty()&&txtResult.getSelectionStart()!=0
                && !Calculate.done)
                    Calculate.delete();
                state = false;
                break;

            case "ANS":
                Calculate.populateCalculationArray("Ans");
                state = false;
                break;

            case "SHIFT":
                btnShiftTapped();
                state = true;
                break;

            case "CALC":
                if(txtResult.getText().toString().contains("x")){
                    txtResult2.setText("X?");
                }
                else Toast.makeText(this,"No variable available",Toast.LENGTH_SHORT).show();
                break;

            case "MODE":
                popup = new PopupMenu(MainActivity.this, btnView);
                popup.setOnMenuItemClickListener(this);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_popup, popup.getMenu());
                popup.show();
                break;
            case "RIGHT":
                if(txtResult.getSelectionStart()!=txtResult.length()) {
                    Calculate.rightButtonPressed();
                    Calculate.done = false;
                }
                break;
            case "LEFT":
                if(txtResult.getSelectionStart()!=0) {
                    Calculate.leftButtonPressed();
                    Calculate.done = false;
                }
                break;

            default:
                String temp = btnView.getTag().toString();

                if(txtResult2.getText().toString().contains("X?")){
                    storedValue += temp;
                    txtResult.setText(storedValue);
                    substitute = true;
             } else{
                    Calculate.populateCalculationArray(temp);
                }

                state = false;
                break;
        }

        if(shift && !state) btnShiftTapped();
    }
    private void btnShiftTapped() {
        if (shift) shift = false;
        else shift = true;

        if (shift == true) {
            findViewById(R.id.txtS).setVisibility(View.VISIBLE);
            for(Map.Entry<String,String> entry : Calculate.buttonShiftHashMap.entrySet()){
                Button button = root.findViewWithTag(entry.getKey());
                if(button!=null) {
                    button.setTag(entry.getValue());
                }
            }
        }
        else {
            findViewById(R.id.txtS).setVisibility(View.INVISIBLE);
            for(Map.Entry<String,String> entry : Calculate.buttonShiftHashMap.entrySet()){
                Button button = root.findViewWithTag(entry.getValue());
                if(button!=null) {
                    button.setTag(entry.getKey());
                }
            }
        }
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.solveQuadraticEqn:
                onClick(findViewById(R.id.btnQuadratic));
                return true;
            case R.id.calcSequenceSummation:
                Intent intent = new Intent(MainActivity.this, Summation.class);
                startActivity(intent);
                return true;
            case R.id.chgBase:
                intent = new Intent(MainActivity.this, BaseConversion.class);
                startActivity(intent);
                return true;
            case R.id.calcSimultaneousEqn:
                //Intent intent = new Intent(MainActivity.this, SimultaneousEqn.class);
                //startActivity(intent);
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

}
