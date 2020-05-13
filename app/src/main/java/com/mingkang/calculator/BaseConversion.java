package com.mingkang.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;

public class BaseConversion extends AppCompatActivity implements View.OnClickListener {

    private EditText edtDecimal, edtBinary, edtOctal, edtHexadecimal, txtResult;
    private String lastDec = "", lastBin = "", lastOct = "", lastHex = "";
    private String edtBin, edtDec, edtOct, edtHex, result;
    private boolean fromBin = false, fromDec = false, fromOct = false, fromHex = false, clear = false;


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
    }

    @Override
    public void onClick(View btnView) {
        if(clear) {
            edtBin = "";
            edtDec = "";
            edtOct = "";
            edtHex = "";
            clear = false;
        } else {
            edtBin = edtBinary.getText().toString();
            edtDec = edtDecimal.getText().toString();
            edtOct = edtOctal.getText().toString();
            edtHex = edtHexadecimal.getText().toString();
        }

        if (!edtBin.isEmpty() && !edtBin.equals(lastBin)) {
            txtResult = findViewById(R.id.edtBinary_Base);
            fromBin = true;
        } else if (!edtDec.isEmpty() && !edtDec.equals(lastDec)) {
            txtResult = findViewById(R.id.edtDecimal_Base);
            fromDec = true;
        } else if (!edtOct.isEmpty() && !edtOct.equals(lastOct)) {
            txtResult = findViewById(R.id.edtOctal_Base);
            fromOct = true;
        } else if (!edtHex.isEmpty() && !edtHex.equals(lastHex)) {
            txtResult = findViewById(R.id.edtHexadecimal_Base);
            fromHex = true;
        }
        result = txtResult.getText().toString();

        switch (btnView.getTag().toString()) {
            case "convert":
                try {
                    String decimal = "", binary = "", octal = "", hexadecimal = "";
                    if(fromBin) result = convertExpressionToDecimal(result, 2);
                    else if(fromDec) result = convertExpressionToDecimal(result, 10);
                    else if(fromOct) result = convertExpressionToDecimal(result, 8);
                    else if(fromHex) result = convertExpressionToDecimal(result, 16);

                    Expression e = new ExpressionBuilder(result).build();
                    double answer = e.evaluate();
                    txtResult.setText(Integer.toString((int) answer));
                    result = txtResult.getText().toString();

                    decimal = result;
                    binary = Integer.toBinaryString(Integer.parseInt(result));
                    octal = Integer.toOctalString(Integer.parseInt(result));
                    hexadecimal = Integer.toHexString(Integer.parseInt(result));

                    edtBinary.setText(binary);
                    edtDecimal.setText(decimal);
                    edtOctal.setText(octal);
                    edtHexadecimal.setText(hexadecimal);

                    lastDec = decimal; lastBin = binary; lastOct = octal; lastHex = hexadecimal;
                    fromBin = false; fromDec = false; fromOct = false; fromHex = false;
                }
                catch (Exception E) {
                    Toast.makeText(this, "No number to be converted", Toast.LENGTH_LONG).show();
                }
                break;

            case "clear":
                clear = true;
                edtBinary.getText().clear();
                edtDecimal.getText().clear();
                edtOctal.getText().clear();
                edtHexadecimal.getText().clear();
                lastBin = lastDec = lastOct = lastHex = "";
                break;

            case "del":
                if (result.length() > 0)
                    result = result.substring(0,result.length()-1);
                txtResult.setText(result);
                break;

            case "back":
                Intent intent = new Intent(BaseConversion.this, MainActivity.class);
                startActivity(intent);
                break;

            case "bin":
                edtDecimal.setText("");
                edtOctal.setText("");
                edtHexadecimal.setText("");
                break;

            case "dec":
                edtBinary.setText("");
                edtOctal.setText("");
                edtHexadecimal.setText("");
                break;

            case "oct":
                edtBinary.setText("");
                edtDecimal.setText("");
                edtHexadecimal.setText("");
                break;

            case "hex":
                edtBinary.setText("");
                edtDecimal.setText("");
                edtOctal.setText("");
                break;

            default:
                if(result.charAt(result.length()-1) == '+' || result.charAt(result.length()-1) == '-' ||
                    result.charAt(result.length()-1) == '×' || result.charAt(result.length()-1) == '/') {
                    Toast.makeText(this, "Operator cannot be tapped multiple times repeatedly",Toast.LENGTH_SHORT).show();
                } else {
                    String temp = btnView.getTag().toString();
                    txtResult.setText(txtResult.getText().toString() + temp);
                }
                break;
        }
        txtResult.setSelection(txtResult.length());
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

    public String convertExpressionToDecimal(String expression, int base) {
        int startIndex = 0, stopIndex = 0;
        ArrayList<String> number = new ArrayList<>();
        ArrayList<Character> operator = new ArrayList<>();
        for(int i=0; i<expression.length(); i++) {
            if(expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '×' || expression.charAt(i) == '/') {
                stopIndex = i;
                number.add(expression.substring(startIndex == 0? 0: startIndex+1,stopIndex));
                operator.add(expression.charAt(i));
                startIndex = stopIndex;
            }
            if(i == expression.length()-1) {
                number.add(expression.substring(startIndex == 0? 0:startIndex+1));
                operator.add(' ');
            }
        }
        for(int i=0; i<number.size(); i++) {
            number.set(i, toDecimal(number.get(i), base));
        }
        String exp = "";
        for(int i=0; i<number.size(); i++) {
            exp += number.get(i) + operator.get(i);
        }
        return exp;
    }
}
