package com.mingkang.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
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
    private int matrices_ansSize;
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
        findViewById(R.id.matric_btnRightBracket).setOnClickListener(Matrices.this);
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
                if(matric_validate()){
                    Toast.makeText(Matrices.this, matric_simpilfy(), Toast.LENGTH_LONG).show();
                    //matric_calculate();
                }

            break;

            case "del" :
                if(matrices_equation.size()>0){
//                    if(matrices_equation.get(matrices_equation.size()-1).equals(")")){
//                        matrices_bracket++;
//                    }
//                    if(matrices_bracket>0 && !matrices_equation.get(matrices_equation.size()-1).equals(")")){
//                        matrices_equation.add(")");
//                        matrices_bracket--;
//                    }
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
//                if(matric_input.equals("DET(") || matric_input.equals("TRN(")){
//                    matrices_bracket++;
//                }
//                else if(matrices_bracket>0){
//                    matrices_equation.add(")");
//                    matrices_bracket--;
//                }
                matric_showEquation();

            break;
        }
    }

    private boolean matric_validate(){ // Remember to add error message here <--
        if(matrices_equation.size() == 0){
            Toast.makeText(Matrices.this, "Equation cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!matric_checkBracket()){
            Toast.makeText(Matrices.this, "Wrong bracket location", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!matric_checkOperator()){
            Toast.makeText(Matrices.this, "Cannot have simultaneous operators", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!matric_checkInverse()){
            Toast.makeText(Matrices.this, "Inverse must be in front of a matrix", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!matric_checkLastOp()){
            Toast.makeText(Matrices.this, "Invalid equation", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String matric_simpilfy(){
        String fa = "";
        ArrayList<String> temp = new ArrayList<>();
        for(int i=0;i<matrices_equation.size();i++){
            String temp1 = matrices_equation.get(i);
            if(i==0){
                temp.add(temp1);
            }
            else if(temp.get(temp.size()-1) == "DET(" && temp1 == "DET("){
                temp.remove(temp.size()-1);
            }
            else if(temp.get(temp.size()-1) == "TRN(" && temp1 == "TRN("){
                temp.remove(temp.size()-1);
            }
            else if(temp.get(temp.size()-1) == "⁻¹" && temp1 == "⁻¹"){
                temp.remove(temp.size()-1);
            }
            else{
                temp.add(temp1);
            }
        }
        for(int i=0;i<temp.size();i++) fa += temp.get(i);
        return fa;
    }

    private void matric_calculate(){
        ArrayList<String> temp = new ArrayList<>();
        String temp1 = "";
        boolean check = false;
        for(int i=matrices_equation.size()-1;i>=0;i--){
            if(check){

            }
            if(matrices_equation.get(i).equals(")")){
                check = true;
            }
        }
    }

    private boolean matric_checkLastOp(){
        boolean check = true, check2 = false;
        for(int i=0;i<matrices_equation.size();i++){
            if(matrices_equation.get(i).equals("A") || matrices_equation.get(i).equals("B")
                    || matrices_equation.get(i).equals("⁻¹"))
                check=false;
            if((matrices_equation.get(i).equals("+") || matrices_equation.get(i).equals("-")
                    || matrices_equation.get(i).equals("*"))
                    && check){
                return false;
            }
            else if((matrices_equation.get(i).equals("+") || matrices_equation.get(i).equals("-")
                    || matrices_equation.get(i).equals("*"))
                    && !check){
                check2 = true;
                check = true;
            }
            if((matrices_equation.get(i).equals(")") || matrices_equation.get(i).equals("DET(")) && check2)
                return false;
            else if((matrices_equation.get(i).equals(")") || matrices_equation.get(i).equals("DET(")) && !check2){
                check2 = false;
            }
        }
        if(matrices_equation.get(matrices_equation.size()-1).equals("+")
                || matrices_equation.get(matrices_equation.size() - 1).equals("-")
                || matrices_equation.get(matrices_equation.size() - 1).equals("*")
                || matrices_equation.get(matrices_equation.size() - 1).equals("DET(")
                || matrices_equation.get(matrices_equation.size() - 1).equals("TRN(")) return false;
        return true;
    }

    private boolean matric_checkInverse(){
        for(int i=1;i<matrices_equation.size();i++){
            if(matrices_equation.get(i) == "⁻¹"){
                if(matrices_equation.get(i-1) != "A" && matrices_equation.get(i-1) != "B"){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean matric_checkOperator(){
        boolean op = false;
        for(int i=0;i<matrices_equation.size();i++){
            if(matrices_equation.get(i).equals("+") || matrices_equation.get(i).equals("-")
                    || matrices_equation.get(i).equals("*")){
                if(op) return false;
                else op = true;
            }
            else op = false;
        }
        return true;
    }

    private boolean matric_checkBracket(){
        int bs=0, be=0;
        for(int i=0;i<matrices_equation.size();i++){
            if(matrices_equation.get(i).equals("DET(") || matrices_equation.get(i).equals("TRN(")) bs++;
            if(matrices_equation.get(i).equals(")")) be++;
        }
        boolean check = false;
        for(int i=0;i<matrices_equation.size();i++){
            if(matrices_equation.get(i).equals("DET(") || matrices_equation.get(i).equals("TRN(")) check = true;
            if(matrices_equation.get(i).equals(")") && check) check = false;
            else if(matrices_equation.get(i).equals(")") && !check) return false;
        }
        return bs == be;
    }

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