package com.mingkang.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class BaseConversion extends AppCompatActivity implements View.OnClickListener {

    private EditText edtDecimal, edtBinary, edtOctal, edtHexadecimal, txtResult;
    private String lastDec = "", lastBin = "", lastOct = "", lastHex = "";
    private String edtBin, edtDec, edtOct, edtHex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_conversion);

        FunctionAndOperator fo = new FunctionAndOperator();

        edtBinary = findViewById(R.id.edtBinary_Base);
        edtDecimal = findViewById(R.id.edtDecimal_Base);
        edtOctal = findViewById(R.id.edtOctal_Base);
        edtHexadecimal = findViewById(R.id.edtHexadecimal_Base);

        InitialiseOnClickButton();
    }

    private void InitialiseOnClickButton() {
        findViewById(R.id.btnConvertBase).setOnClickListener(this);
        findViewById(R.id.btnClear_Base).setOnClickListener(this);
        findViewById(R.id.btnBack_Base).setOnClickListener(this);
        findViewById(R.id.btnAddition_Base).setOnClickListener(this);
        findViewById(R.id.btnSubstration_Base).setOnClickListener(this);
        findViewById(R.id.btnMultiplication_Base).setOnClickListener(this);
        findViewById(R.id.btnDivision_Base).setOnClickListener(this);
        findViewById(R.id.btnDelete_Base).setOnClickListener(this);

        edtBinary.setOnClickListener(this);
        edtDecimal.setOnClickListener(this);
        edtOctal.setOnClickListener(this);
        edtHexadecimal.setOnClickListener(this);
        //txtResult = findViewById(R.id.edtDecimal_Base);


    }

    @Override
    public void onClick(View btnView) {

        //Expression e = new ExpressionBuilder(edtDec).build();
//        if(edtBin.isEmpty() == false) {
//
//
//            if(edtDec.isEmpty() == false) txtResult = findViewById(R.id.edtDecimal_Base);

//
//        } else if(edtOct.isEmpty() == false) {
//            txtResult = findViewById(R.id.edtOctal_Base);
//
//        } else if(edtHex.isEmpty() == false) {
//            //txtResult = findViewById(R.id.edtHexadecimal_Base);
//
//        }

        try {
            switch (btnView.getId()) {
                case R.id.btnConvertBase:

                    String decimal = "", binary = "", octal = "", hexadecimal = "";
                    edtBin = edtBinary.getText().toString();
                    edtDec = edtDecimal.getText().toString();
                    edtOct = edtOctal.getText().toString();
                    edtHex = edtHexadecimal.getText().toString();

//                    Calculate.done = true;
//                    Calculate.convertVisualToExpression();
//                    txtResult.setText(Integer.toString((int) Calculate.solveUsingLibrary()));
//                    Calculate.validExpression = "";
//                    Calculate.displayStringArray.clear();
//                    double answer = e.evaluate();
//                    txtResult.setText(Integer.toString((int) answer));

                    if (edtBin.equals(lastBin) && edtOct.equals(lastOct) && edtHex.equals(lastHex)) {
                        decimal = edtDec;
                        binary = Integer.toBinaryString(Integer.parseInt(edtDec));
                        octal = Integer.toOctalString(Integer.parseInt(edtDec));
                        hexadecimal = Integer.toHexString(Integer.parseInt(edtDec));

                    } else if (edtDec.equals(lastDec) && edtOct.equals(lastOct) && edtHex.equals(lastHex)) {
                        decimal = toDecimal(edtBin, 2);
                        binary = edtBin;
                        octal = Integer.toOctalString(Integer.parseInt(decimal));
                        hexadecimal = Integer.toHexString(Integer.parseInt(decimal));

                    } else if (edtDec.equals(lastDec) && edtBin.equals(lastBin) && edtHex.equals(lastHex)) {
                        decimal = toDecimal(edtOct, 8);
                        binary = Integer.toBinaryString(Integer.parseInt(decimal));
                        octal = edtOctal.getText().toString();
                        hexadecimal = Integer.toHexString(Integer.parseInt(decimal));

                    } else if (edtDec.equals(lastDec) && edtBin.equals(lastBin) && edtOct.equals(lastOct)) {
                        decimal = toDecimal(edtHex, 16);
                        binary = Integer.toBinaryString(Integer.parseInt(decimal));
                        octal = Integer.toOctalString(Integer.parseInt(decimal));
                        hexadecimal = edtHex;
                    }

                    edtBinary.setText(binary);
                    edtDecimal.setText(decimal);
                    edtOctal.setText(octal);
                    edtHexadecimal.setText(hexadecimal);

                    lastDec = decimal;
                    lastBin = binary;
                    lastOct = octal;
                    lastHex = hexadecimal;
                    break;

                case R.id.btnClear_Base:
                    edtBinary.setText("");
                    edtDecimal.setText("");
                    edtOctal.setText("");
                    edtHexadecimal.setText("");
                    lastBin = lastDec = lastOct = lastHex = "";
                    break;

//                case R.id.btnDelete_Base:
//                    if (Calculate.displayStringArray.size() > 0)
//                        Calculate.displayStringArray.remove(Calculate.displayStringArray.size() - 1);
//                    txtResult.setText(Calculate.arrayListToString());
//                    break;

                case R.id.btnBack_Base:
                    Intent intent = new Intent(BaseConversion.this, MainActivity.class);
                    startActivity(intent);
                    break;

                case R.id.edtBinary_Base:
                    edtDecimal.setText("");
                    edtOctal.setText("");
                    edtHexadecimal.setText("");
                    break;

                case R.id.edtDecimal_Base:
                    edtBinary.setText("");
                    edtOctal.setText("");
                    edtHexadecimal.setText("");
                    break;

                case R.id.edtOctal_Base:
                    edtBinary.setText("");
                    edtDecimal.setText("");
                    edtHexadecimal.setText("");
                    break;

                case R.id.edtHexadecimal_Base:
                    edtBinary.setText("");
                    edtDecimal.setText("");
                    edtOctal.setText("");
                    break;

                default:
                    String temp = btnView.getTag().toString();
                    Log.i("TAG", temp);
//                    Calculate.populateCalculationArray(temp);
//                    txtResult.setText(Calculate.arrayListToString());
                    txtResult.setText(txtResult.getText().toString() + temp);
//                    edtDecimal.setText(edtDecimal.getText().toString() + temp);

                    break;


            }
        } catch (Exception E) {
            Toast.makeText(this, "No number to be converted", Toast.LENGTH_LONG).show();
        }
    }


    static int val(char c) {
        if (c >= '0' && c <= '9')
            return (int)c - '0';
        else
            return (int)c - 'A' + 10;
    }

    public String toDecimal(String str,int base) {
        int len = str.length();
        int power = 1, num = 0, i;
        for (i = len - 1; i >= 0; i--) {
            // A digit in input number must be less than number's base
            if (val(str.charAt(i)) >= base) {
                Toast.makeText(this, "Invalid Number" ,Toast.LENGTH_SHORT).show();
                return "";
            }
            num += val(str.charAt(i)) * power;
            power = power * base;
        }
        return Integer.toString(num);
    }
}
