package com.mingkang.calculator;

import android.app.Activity;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView txtCalculation;
    TextView txtResult;
    ImageView btnEqual;

    private DecimalFormat df = new DecimalFormat("#.#");
    private float calculationResult;
    private String strHold;
    private double answer;
    private boolean done;
    private String calculationsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculationResult = 0;
        calculationsString = "";

        done=false;
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
        findViewById(R.id.btnAC).setOnClickListener(this);
        findViewById(R.id.btnDel).setOnClickListener(this);
        findViewById(R.id.btnAns).setOnClickListener(this);
    }

    @Override
    public void onClick(View btnView) {
        switch (btnView.getTag().toString()){
            case "ac":
                calculationsString = "";
                txtResult.setText(calculationsString);
            break;
            
            case "=":
                done=true;
                calculationsString = String.valueOf(df.format(solve()));
                txtResult.setText(calculationsString);
                answer = solve();//show on screen
                calculationsString = "";
            break;

            case "del":
                if(calculationsString.equals("") == false){
                    if(calculationsString.charAt(calculationsString.length()-1)=='s'){
                        calculationsString = calculationsString.substring(0, calculationsString.length() - 3);
                    }
                    else {
                        calculationsString = calculationsString.substring(0, calculationsString.length() - 1);
                    }
                    txtResult.setText(calculationsString);
                }

            break;

            default:
                if(done){
                    String temp = btnView.getTag().toString();
                    if(temp.equals("+") || temp.equals("-") || temp.equals("*") || temp.equals("/")){
                        calculationsString = "Ans";
                        calculationsString += btnView.getTag().toString();
                    }
                    else{
                        calculationsString += btnView.getTag().toString();
                    }
                    done=false;
                }
                else{
                    calculationsString += btnView.getTag().toString();
                }
                txtResult.setText(calculationsString);

            break;
        }

    }

    private double solve(){
        ArrayList<Double> number = new ArrayList<>();
        ArrayList<Character> operator = new ArrayList<>();
        int[] op = {0, 0, 0, 0}; //operator
        boolean tapped = false;
        double temp=0;
        for(int i=0;i<calculationsString.length();i++){
            if(calculationsString.charAt(i) == 'A'){
                if(i==0) number.add(answer);
                else{
                    if(calculationsString.charAt(i) == '+' || calculationsString.charAt(i)== '-' || calculationsString.charAt(i) =='*' || calculationsString.charAt(i)=='/') {
                        number.add(answer);
                    }
                    else{
                        double num1 = number.get(number.size()-1);
                        number.remove((number.size()-1));
                        number.add(num1*answer);
                    }
                }
                i+=2;
            }
            else if(calculationsString.charAt(i) == '+' || calculationsString.charAt(i)== '-' || calculationsString.charAt(i) =='*' || calculationsString.charAt(i)=='/'){
                if(tapped) {
                    if(calculationsString.charAt(i) == '+') op[0]++;
                    else if(calculationsString.charAt(i) == '-') op[1]++;
                    else if(calculationsString.charAt(i) == '*') op[2]++;
                    else if(calculationsString.charAt(i) == '/') op[3]++;
                    number.add(temp);
                    temp=0;
                    operator.add(calculationsString.charAt(i));
                }
            }
            else {
                if (!tapped) tapped = true;
                double num = Double.parseDouble(calculationsString.substring(i,i+1)); //
                temp = temp*10 +num;
            }
        }
        if(calculationsString.charAt(calculationsString.length()-1) != 's') number.add(temp);
        double ans=0;
        if(op[2]>0 || op[3]>0){
            for(int i=0;i<operator.size();i++) {
                if(operator.get(i) == '*'){
                    double num1 = number.get(i), num2 = number.get(i+1);
                    number.set(i , num1*num2);
                    number.remove((i+1));
                    operator.remove(i);
                }
                else if (operator.get(i) == '/'){
                    double num1 = number.get(i), num2 = number.get(i+1);
                    number.set(i , num1/num2);
                    number.remove((i+1));
                    operator.remove(i);
                }
            }
        }
        ans = number.get(0);
        for(int i=0;i<operator.size();i++){
            if(operator.get(i) == '+') ans += number.get(i+1);
            else if(operator.get(i) == '-') ans -= number.get(i+1);
        }
        return ans;
    };

}
