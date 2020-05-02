package com.mingkang.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    //This is branch1 on github
    private enum OPERATOR{
        PLUS, MINUS, MULTIPLY, DIVIDE, EQUAL
    }

    TextView txtCalculation;
    TextView txtResult;
    ImageView btnEqual;

    private float calculationResult;
    private String currentNumber;
    private String stringNumberAtLeft;
    private String stringNumberAtRight;
    private OPERATOR currentOperator;
    private String calculationsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentNumber = "";
        calculationResult = 0;
        calculationsString ="";

        txtCalculation = findViewById(R.id.txtCalculation);
        txtResult = findViewById(R.id.txtResult);

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
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEqual:
                operatorIsTapped(OPERATOR.EQUAL);
                break;
            case R.id.btn0:
                numberIsTapped(0);
                break;
            case R.id.btn1:
                numberIsTapped(1);
                break;
            case R.id.btn2:
                numberIsTapped(2);
                break;
            case R.id.btn3:
                numberIsTapped(3);
                break;
            case R.id.btn4:
                numberIsTapped(4);
                break;
            case R.id.btn5:
                numberIsTapped(5);
                break;
            case R.id.btn6:
                numberIsTapped(6);
                break;
            case R.id.btn7:
                numberIsTapped(7);
                break;
            case R.id.btn8:
                numberIsTapped(8);
                break;
            case R.id.btn9:
                numberIsTapped(9);
                break;
            case R.id.btnDivide:
                calculationsString += "/";
                operatorIsTapped(OPERATOR.DIVIDE );
                break;
            case R.id.btnMultiply:
                calculationsString += "*";
                operatorIsTapped(OPERATOR.MULTIPLY);
                break;
            case R.id.btnMinus:
                calculationsString += "-";
                operatorIsTapped(OPERATOR.MINUS);
                break;
            case R.id.btnPlus:
                calculationsString += "+";
                operatorIsTapped(OPERATOR.PLUS);
                break;
            case R.id.btnC:
                clearTapped();
                break;
        }

        txtCalculation.setText(calculationsString);
    }

    private void numberIsTapped(int tappedNumber){
        currentNumber = currentNumber + tappedNumber;
        txtResult.setText(currentNumber);
        calculationsString = currentNumber;
        txtCalculation.setText(calculationsString);
    }

    private void operatorIsTapped(OPERATOR tappedOperator){
        if(currentOperator!=null){
            if(currentNumber!="") {
                stringNumberAtRight = currentNumber;
                currentNumber = "";

                switch (currentOperator) {
                    case PLUS:
                        calculationResult = Float.parseFloat(stringNumberAtLeft) +
                                Float.parseFloat(stringNumberAtRight);
                        break;
                    case MINUS:
                        calculationResult = Float.parseFloat(stringNumberAtLeft) -
                                Float.parseFloat(stringNumberAtRight);
                        break;
                    case MULTIPLY:
                        calculationResult = Float.parseFloat(stringNumberAtLeft) *
                                Float.parseFloat(stringNumberAtRight);
                        break;
                    case DIVIDE:
                        calculationResult = Float.parseFloat(stringNumberAtLeft) /
                                Float.parseFloat(stringNumberAtRight);
                        break;
                    case EQUAL:
                        break;
                }

                stringNumberAtLeft = String.valueOf(calculationResult);
                txtResult.setText(stringNumberAtLeft);
                calculationsString = stringNumberAtLeft;
            }
        } else {
            stringNumberAtLeft = currentNumber;
            currentNumber="";
        }
        currentOperator = tappedOperator;
    }

    private void clearTapped(){
        stringNumberAtLeft="";
        stringNumberAtRight="";
        calculationResult=0;
        currentNumber="";
        currentOperator=null;
        txtResult.setText("");
        calculationsString="";
        txtCalculation.setText("");

    }
}
