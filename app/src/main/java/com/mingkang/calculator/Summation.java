package com.mingkang.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

public class Summation extends AppCompatActivity implements View.OnClickListener {

    private TextView answer;
    private EditText etequation, etstart, etend, etincrement;
    String equation="";
    double start=0, end=0, increment=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summation);
        findViewById(R.id.btnCalculate).setOnClickListener(Summation.this);
    }

    @Override
    public void onClick(View v){

        answer = findViewById(R.id.answer);
        etequation = findViewById(R.id.varaibleEquation);
        etstart = findViewById(R.id.variableStart);
        etend = findViewById(R.id.variableEnd);
        etincrement = findViewById(R.id.variableIncrement);

        equation = etequation.getText().toString();
        start = Double.parseDouble(etstart.getText().toString());
        end = Double.parseDouble(etend.getText().toString());
        increment = Double.parseDouble(etincrement.getText().toString());

        int check=1;
        Expression e = new ExpressionBuilder(equation)
                .variables("x","X")
                .build();

        ValidationResult validate = e.validate();
        if(equation == "" || start == 0 || end == 0 || increment== 0) check=0;
//        if(!validate.isValid()) check=;
        if(end<start && increment>0) check=-1;
        if(start>end && increment<0) check=-2;

        if(check==1){
            answer.setText(solve()+"");
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
    }

    private double solve(){
        double ans=0;
        for(double i=start;i<=end;i+=increment){
            Expression e = new ExpressionBuilder(equation)
                    .variables("x")
                    .build()
                    .setVariable("x",i);
            double tempans = e.evaluate();
            ans += tempans;
        }
        return ans;
    }

}
