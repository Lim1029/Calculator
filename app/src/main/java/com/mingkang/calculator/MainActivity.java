package com.mingkang.calculator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
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
    private TextView txtResult2;
    private DecimalFormat df = new DecimalFormat("#.################");
    private boolean shift;
    private String calculationsString;
    private Button btnCos, btnSin, btnTan, btnInverse, btn10, btnSquare, btnSqrt, btnDot, btnMultiply, btnDivide, btnPercent;
    private boolean state = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shift = false;
        txtResult = findViewById(R.id.txtResult);
        txtResult2 = findViewById(R.id.txtResult2);
        txtResult.setShowSoftInputOnFocus(false);

        InitialiseButton();
        InitialiseOnClickButton();
        Calculate.initializeOperator();

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
        btnPercent = findViewById(R.id.btnPercent);
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
        findViewById(R.id.btnPercent).setOnClickListener(this);
    }

    @Override
    public void onClick(View btnView) {
        switch (btnView.getTag().toString()) {
            case "ac":
                Calculate.resetEverything();
                txtResult.setText(Calculate.arrayListToString());
                txtResult2.setText("0");
                state = false;
                break;
            case "=":
                Calculate.done = true;
                try {
                    Calculate.convertVisualToExpression();
                    txtResult2.setText(df.format(Calculate.solveUsingLibrary()));
                    Calculate.validExpression = "";
                    Calculate.displayStringArray.clear();
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                state = false;
                break;

            case "del":
                if (Calculate.displayStringArray.size() > 0)
                    Calculate.displayStringArray.remove(Calculate.displayStringArray.size() - 1);
                txtResult.setText(Calculate.arrayListToString());
                state = false;
                break;

            case "ANS":
                Calculate.populateCalculationArray("Ans");
                txtResult.setText(Calculate.arrayListToString());
                state = false;
                break;

            case "SHIFT":
                btnShiftTapped();
                state = true;
                break;

            default:
                String temp = btnView.getTag().toString();
                Calculate.populateCalculationArray(temp);
                txtResult.setText(Calculate.arrayListToString());
//                populateCalculationString(temp);

                //if(Calculate.displayStringArray.contains("logb(") && calculationsString.length() >= 6 && !calculationsString.contains(","))

                state = false;
                break;
        }
        txtResult.setSelection(txtResult.length());
        if(shift && !state) btnShiftTapped();
    }

    private void btnShiftTapped() {
        if (shift) shift = false;
        else shift = true;

        if (shift == true) {
            btnCos.setText("COS⁻¹");
            btnCos.setTag("cos⁻¹(");
            btnSin.setText("SIN⁻¹");
            btnSin.setTag("sin⁻¹(");
            btnTan.setText("TAN⁻¹");
            btnTan.setTag("tan⁻¹(");
            btnInverse.setText("X !");
            btnInverse.setTag("!");
            btn10.setText("π");
            btn10.setTag("π");
            btnSquare.setText("x³");
            btnSquare.setTag("³");
            btnSqrt.setText("∛□");
            btnSqrt.setTag("³√(");
            btnDot.setText(",");
            btnDot.setTag(",");
            btnMultiply.setText("nPr");
            btnMultiply.setTag("P");
            btnDivide.setText("nCr");
            btnDivide.setTag("C");
            btnPercent.setText("≡");
            btnPercent.setTag("≡");
        }
        else {
            btnCos.setText("COS");
            btnCos.setTag("cos(");
            btnSin.setText("SIN");
            btnSin.setTag("sin(");
            btnTan.setText("TAN");
            btnTan.setTag("tan(");
            btnInverse.setText("X⁻¹");
            btnInverse.setTag("⁻¹");
            btn10.setText("×10ᵡ");
            btn10.setTag("×₁₀");
            btnSquare.setText("x²");
            btnSquare.setTag("²");
            btnSqrt.setText("√□");
            btnSqrt.setTag("√(");
            btnDot.setText(".");
            btnDot.setTag(".");
            btnMultiply.setText("×");
            btnMultiply.setTag("×");
            btnDivide.setText("÷");
            btnDivide.setTag("÷");
            btnPercent.setText("%");
            btnPercent.setTag("%");
        }
    }
}
