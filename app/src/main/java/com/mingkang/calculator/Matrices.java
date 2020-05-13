package com.mingkang.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrices extends AppCompatActivity implements View.OnClickListener {

    private GridLayout layout2, layout3;
    private TextView matric;
    private EditText[] matric_value1;
    private EditText[] matric_value2;
    private double[] matricA, matricB, matricAns;
    private boolean matrices_currentState = true; // true for 2d, false for 3d
    private boolean matrices_currentMatric = true; // true for A, false for B
    private ArrayList<String> matrices_equation;
    private String matric_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrices);

        matrices_initiliase();

    }

    private void matrices_initiliase(){
        layout2 = findViewById(R.id.matric_layoutValue2);
        layout3 = findViewById(R.id.matric_layoutValue);
        matric = findViewById(R.id.matric_matric);
        matrices_equation = new ArrayList<>();

        matrices_initialiseMatric();
        matrices_initialiseBtn();
    }

    private void matrices_initialiseMatric(){
        matricA = new double[9];
        matricB = new double[9];
        matricAns = new double[9];
        matric_value1 = new EditText[9];
        matric_value2 = new EditText[4];
        Arrays.fill(matricA, 0);
        Arrays.fill(matricB, 0);
        Arrays.fill(matricAns, 0);
    }

    private void matrices_initialiseValue(){

        matric_value1[0] = findViewById(R.id.matric_value1);
        matric_value1[1] = findViewById(R.id.matric_value2);
        matric_value1[2] = findViewById(R.id.matric_value3);
        matric_value1[3] = findViewById(R.id.matric_value4);
        matric_value1[4] = findViewById(R.id.matric_value5);
        matric_value1[5] = findViewById(R.id.matric_value6);
        matric_value1[6] = findViewById(R.id.matric_value7);
        matric_value1[7] = findViewById(R.id.matric_value8);
        matric_value1[8] = findViewById(R.id.matric_value9);

        matric_value2[0] = findViewById(R.id.matric_value2_1);
        matric_value2[1] = findViewById(R.id.matric_value2_2);
        matric_value2[2] = findViewById(R.id.matric_value2_3);
        matric_value2[3] = findViewById(R.id.matric_value2_4);

    }

    private void matrices_initialiseBtn(){
        findViewById(R.id.matric_btnA).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnAnswer).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnB).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnCalculate).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnClear).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnDet).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnDelete).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnMinus).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnMatricSwitcher).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnMultiply).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnPlus).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnTrn).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnInverse).setOnClickListener(Matrices.this);
        findViewById(R.id.matric_btnModeSwitcher).setOnClickListener(Matrices.this);
    }

    @Override
    public void onClick(View v){
        switch(v.getTag().toString()){
            case "switch_mode" :
                Button matric_layoutValue = findViewById(R.id.matric_btnModeSwitcher);
                String current_mode = matric_layoutValue.getText().toString();
                if(current_mode.equals("2 x 2")){
                    matric_layoutValue.setText("3 x 3");
                    matrices_currentState = false;
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                }
                else if(current_mode.equals("3 x 3")){
                    matric_layoutValue.setText("2 x 2");
                    matrices_currentState = true;
                    layout3.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                }
            break;

            case "switch_matric" :
                if(matrices_currentMatric){
                    if(!matric_storeValue(true)){
                        Toast.makeText(Matrices.this, "Fill in all the spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        matric.setText("B   =");
                        matrices_currentMatric = false;
                    }
                }
                else{
                    if(!matric_storeValue(false)){
                        Toast.makeText(Matrices.this, "Fill in all the spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        matric.setText("A   =");
                        matrices_currentMatric = true;
                    }
                }
            break;

            case "calculate" :
//                if(matric_validate()){
//
//                }

            break;

            case "del" :
                if(matrices_equation.size()>0){
                    matrices_equation.remove(matrices_equation.size()-1);
                    matric_showEquation();
                }
            break;

            case "clear" :
                matrices_initiliase();
                matrices_equation.clear();
                matric_showEquation();
            break;

            default :
                String matric_input = v.getTag().toString();
                matrices_equation.add(matric_input);

                matric_showEquation();

            break;
        }
    }

//    private boolean matric_validate(){ // Remember to add error message here <--
//        if(){
//
//        }
//    }

    private void matric_showEquation(){
        TextView matric_equation = findViewById(R.id.matric_equationview);
        String matric_tempFinal = "";
        for(int i=0;i<matrices_equation.size();i++){
            matric_tempFinal += matrices_equation.get(i);
        }
        matric_final = matric_tempFinal;
        matric_equation.setText(matric_tempFinal);
    }

    private boolean matric_storeValue(boolean stored){
        matrices_initialiseValue();
        if(matrices_currentState){
            if(stored){
                for(int i=0;i<matric_value2.length;i++){
                    String matric_checkNull = matric_value2[i].getText().toString();
//                    if(TextUtils.isEmpty(matric_checkNull)) return false;
//                    else matricA[i] = Double.parseDouble(matric_checkNull);
                }
            }
            else{
                for(int i=0;i<matric_value2.length;i++){
                    String matric_checkNull = matric_value2[i].getText().toString();
//                    if(TextUtils.isEmpty(matric_checkNull)) return false;
//                    else matricB[i] = Double.parseDouble(matric_checkNull);
                }
            }
        }
        else{
            if(stored){
                for(int i=0;i<matric_value1.length;i++){
                    String matric_checkNull = matric_value1[i].getText().toString();
//                    if(TextUtils.isEmpty(matric_checkNull)) return false;
//                    else matricA[i] = Double.parseDouble(matric_checkNull);
                }
            }
            else{
                for(int i=0;i<matric_value1.length;i++){
                    String matric_checkNull = matric_value1[i].getText().toString();
//                    if(TextUtils.isEmpty(matric_checkNull)) return false;
//                    else matricB[i] = Double.parseDouble(matric_checkNull);
                }
            }
        }
        return true;
    }

    public void matric_backButton (View v){
        Intent intent = new Intent(Matrices.this, MainActivity.class);
        startActivity(intent);
    }
}
