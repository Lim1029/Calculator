package com.mingkang.calculator;

import java.util.Collections;

public class Calculate_Matric {

    private double[] matric_answer;
    private double matric_ans;

    private void matric_initialiseAnswer(int n){
        matric_answer = new double[n];
    }

    public double[] matric_add(double[] matric1, double[] matric2){ // true for 2d, false for 3d
        matric_initialiseAnswer(matric1.length);
        for(int i=0;i<matric1.length;i++){
            matric_answer[i] = matric1[i] + matric2[i];
        }
        return matric_answer;
    }

    public double[] matric_subtract(double[] matric1, double[] matric2){
        matric_initialiseAnswer(matric1.length);
        for(int i=0;i<matric1.length;i++){
            matric_answer[i] = matric1[i] - matric2[i];
        }
        return matric_answer;
    }

    public double[] matric_multiply(double[] matric1, double[] matric2){
        matric_initialiseAnswer(matric1.length);
        if(matric1.length == 4){
            matric_answer[0] = matric1[0] * matric2[0];
            matric_answer[1] = matric1[1] * matric2[2];
            matric_answer[2] = matric1[2] * matric2[1];
            matric_answer[3] = matric1[3] * matric2[3];
        }
        else if(matric2.length == 9){
            matric_answer[0] = matric1[0] * matric2[0];
            matric_answer[1] = matric1[1] * matric2[3];
            matric_answer[2] = matric1[2] * matric2[6];
            matric_answer[3] = matric1[3] * matric2[1];
            matric_answer[4] = matric1[4] * matric2[4];
            matric_answer[5] = matric1[5] * matric2[7];
            matric_answer[6] = matric1[6] * matric2[2];
            matric_answer[7] = matric1[7] * matric2[5];
            matric_answer[8] = matric1[8] * matric2[8];
        }
        else{
            double[] temp = {0,0,0,0,0,0,0,0,0};
            return temp;
        }
        return matric_answer;
    }

    private double matric_findDet(double a,double b, double c, double d){
        return a*d-b*c;
    }

    public double matric_det(double[] matric1){
        matric_initialiseAnswer(matric1.length);
        if(matric1.length == 4){
            matric_ans = matric_findDet(matric1[0], matric1[3], matric1[1], matric1[2]);
        }
        else if(matric1.length == 9){
            matric_ans = matric1[0]*(matric_findDet(matric1[4], matric1[8], matric1[5], matric1[7]))
                    - matric1[1]*(matric_findDet(matric1[3], matric1[8], matric1[5], matric1[6]))
                    + matric1[2]*(matric_findDet(matric1[3], matric1[7], matric1[4], matric1[6]));
        }
        else return 0;
        return matric_ans;
    }

    public double[] matric_trn(double[] matric1){
        matric_initialiseAnswer(matric1.length);
        if(matric1.length == 4){
            Collections.swap(Collections.singletonList(matric1), 1, 2);
        }
        else if(matric1.length == 9){
            Collections.swap(Collections.singletonList(matric1), 1, 3);
            Collections.swap(Collections.singletonList(matric1), 2, 6);
            Collections.swap(Collections.singletonList(matric1), 5, 7);
        }
        else{
            double[] temp = {0,0,0,0,0,0,0,0,0};
            return temp;
        }
        return matric1;
    }

    public double[] matric_inv(double[] matric1){
        matric_initialiseAnswer(matric1.length);
        double det = matric_det(matric1);
        if(det == 0){
            double[] temp = {-1};
            return temp;
        }
        else if(matric1.length == 4){
            matric_answer[0] = 1 / det * matric1[3];
            matric_answer[1] = -1 / det * matric1[1];
            matric_answer[2] = -1 / det * matric1[2];
            matric_answer[3] = 1 / det * matric1[0];
        }
        else if(matric1.length == 9){
            matric_answer[0] = 1 / det * matric_findDet(matric1[4], matric1[5], matric1[7], matric1[8]);
            matric_answer[1] = -1 / det * matric_findDet(matric1[3], matric1[5], matric1[6], matric1[8]);
            matric_answer[2] = 1 / det * matric_findDet(matric1[3], matric1[4], matric1[6], matric1[7]);
            matric_answer[3] = -1 / det * matric_findDet(matric1[1], matric1[2], matric1[7], matric1[8]);
            matric_answer[4] = 1 / det * matric_findDet(matric1[0], matric1[2], matric1[6], matric1[8]);
            matric_answer[5] = -1 / det * matric_findDet(matric1[0], matric1[1], matric1[6], matric1[7]);
            matric_answer[6] = 1 / det * matric_findDet(matric1[1], matric1[2], matric1[4], matric1[5]);
            matric_answer[7] = -1 / det * matric_findDet(matric1[0], matric1[2], matric1[3], matric1[5]);
            matric_answer[8] = 1 / det * matric_findDet(matric1[0], matric1[1], matric1[3], matric1[4]);
        }
        else{
            double[] temp = {0,0,0,0,0,0,0,0,0};
            return temp;
        }
        return matric_answer;
    }
}
