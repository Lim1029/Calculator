package com.mingkang.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;

public class Summation extends AppCompatActivity implements View.OnClickListener {

    String equation="";
    double start=0, end=0, increment=0;
    private DecimalFormat df = new DecimalFormat("#.################");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summation);
        findViewById(R.id.summation_btnCalculate).setOnClickListener(Summation.this);
    }

    @Override
    public void onClick(View v){

        TextView answer = findViewById(R.id.summation_answer);
        EditText etequation = findViewById(R.id.summation_varaibleEquation);
        EditText etstart = findViewById(R.id.summation_variableStart);
        EditText etend = findViewById(R.id.summation_variableEnd);
        EditText etincrement = findViewById(R.id.summation_variableIncrement);

        equation = etequation.getText().toString();
        start = Double.parseDouble(etstart.getText().toString());
        end = Double.parseDouble(etend.getText().toString());
        increment = Double.parseDouble(etincrement.getText().toString());

        int check=1;
        if(equation.equals("") || start == 0 || end == 0 || increment== 0) check=0;
        if(end<start && increment>0) check=-1;
        if(start>end && increment<0) check=-2;
        if(!checkString()) check=-3;

        if(check==1){
            answer.setText(df.format(solve()));
        }
        else if (check==0){
            Toast.makeText(Summation.this, "Values cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if(check==-1){
            Toast.makeText(Summation.this, "End must be larger than start for positive increment", Toast.LENGTH_LONG).show();
        }
        else if(check==-2){
            Toast.makeText(Summation.this, "Start must be larger than end for negative increment", Toast.LENGTH_LONG).show();
        }
        else if(check==-3){
            Toast.makeText(Summation.this, "Equation must only include x as variable", Toast.LENGTH_LONG).show();
        }
    }

    private double solve(){
        double ans=0;
        for(double i=start;i<=end;i+=increment){
            double tempans = new ExpressionBuilder(equation)
                    .variables("x")
                    .build()
                    .setVariable("x",i)
                    .evaluate();
            ans += tempans;
        }
        return ans;
    }

    private boolean checkString(){
        char[] valid = {'+', '-', '*', '/', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'x', '^', '(' , ')'};
        for(int i=0;i<equation.length();i++){
            boolean temp=false;
            for(int j=0;j<valid.length;j++){
                if(equation.charAt(i) == valid[j]) temp=true;
            }
            if(!temp) return false;
        }
        return true;
    }

    public void backButtonPressed(View v){
        Intent intent = new Intent(Summation.this, MainActivity.class);
        startActivity(intent);
    }

}
