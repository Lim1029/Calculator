//package com.mingkang.calculator;
//
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.NoSuchElementException;
//
//public class InputExperiment extends AppCompatActivity {
//
//    private ArrayList<Integer> textCountArray, focusArray;
//    private EditText editText2, editText;
//    private ArrayList<String> stringArray;
//    private TextView textView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_input_experiment);
//
//        editText = findViewById(R.id.editText);
//        editText.setShowSoftInputOnFocus(false);
//        editText2 = findViewById(R.id.editText2);
//        editText2.setShowSoftInputOnFocus(false);
//
//        textView = findViewById(R.id.textView);
//
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText.getText().insert(editText.getSelectionStart(), "ˣ√(");
//                textCountArray.add(focusArray.indexOf(editText.getSelectionStart() - 3), 3);
//                refreshFocusArray();
//            }
//        });
//
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText.getText().insert(editText.getSelectionStart(), "×");
//                textCountArray.add(focusArray.indexOf(editText.getSelectionStart() - 1), 1);
//                refreshFocusArray();
//            }
//        });
//
//        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                delete();
//            }
//        });
//
//        findViewById(R.id.btnLeftTest).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText.setSelection(editText.getSelectionStart() - 1);
//                setSelectionPosition2(editText.getSelectionStart());
//            }
//        });
//        findViewById(R.id.btnRightTest).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText.setSelection(editText.getSelectionStart() + 1);
//                setSelectionPosition(editText.getSelectionStart());
//            }
//        });
//        editText.setText("");
//        textCountArray = new ArrayList<>();
//
//        refreshFocusArray();
//
//        editText.setOnClickListener(v -> {
//            setSelectionPosition(editText.getSelectionStart());
//        });
//    }
//
//    public void delete() {
//        int num = 0;
//        int i;
//        for (i = 0; i < textCountArray.size(); i++) {
//            if (num != editText.getSelectionStart())
//                num += textCountArray.get(i);
//            else break;
//        }
//        editText.getText().delete(editText.getSelectionStart() - textCountArray.get(i - 1), editText.getSelectionStart());
//        textCountArray.remove(i - 1);
//        refreshFocusArray();
//    }
//    public void refreshFocusArray() {
//        focusArray = new ArrayList<>();
//        focusArray.add(0);
//        for (int number : textCountArray) {
//            focusArray.add(focusArray.get(focusArray.size() - 1) + number);
//        }
//    }
//    public void convertToDisplayStringArray() {
//        stringArray = new ArrayList<>();
//        String string = editText.getText().toString();
//        int position = 0;
//        for (int i = 1; i < focusArray.size(); i++) {
//            stringArray.add(string.substring(position, focusArray.get(i)));
//            position = focusArray.get(i);
//        }
//    }
//    public void setSelectionPosition(int position) {
//        if (focusArray.contains(position)) {
//            editText.setSelection(position);
//        } else {
//            boolean repeat = true;
//            int focus=0;
//            while (repeat) {
//                int diff = Integer.MAX_VALUE;
//                for (int i=1;i<focusArray.size();i++) {
//                    if(Math.abs(focusArray.get(i)-position)<=diff){
//                        focus=focusArray.get(i);
//                        diff=Math.abs(focusArray.get(i)-position);
//                    }
//
//                }
//                if (focus <= position) {
//                    position++;
//                    repeat = true;
//                } else {
//                    repeat = false;
//                }
//
//            }
//            editText.setSelection(focus);
//
//        }
//        convertToDisplayStringArray();
//        textView.setText(Calculate.arrayListToString(stringArray));
//    }
//    public void setSelectionPosition2(int position) {
//        if (focusArray.contains(position)) {
//            editText.setSelection(position);
//        } else {
//            boolean repeat = true;
//            int focus=0;
//            while (repeat) {
//                int diff = Integer.MAX_VALUE;
//                for (int i=1;i<focusArray.size();i++) {
//                    if(Math.abs(focusArray.get(i)-position)<=diff){
//                        focus=focusArray.get(i);
//                        diff=Math.abs(focusArray.get(i)-position);
//                    }
//
//                }
//                if (focus >= position) {
//                    position--;
//                    repeat = true;
//                } else {
//                    repeat = false;
//                }
//
//            }
//            editText.setSelection(focus);
//
//        }
//        convertToDisplayStringArray();
//        textView.setText(Calculate.arrayListToString(stringArray));
//    }
//}
