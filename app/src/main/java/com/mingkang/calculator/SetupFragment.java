package com.mingkang.calculator;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetupFragment extends DialogFragment {

    public static String unitOfAngle = "degree";
    public static int norm = 1;
    private Button btnSave, btnCancel;


    public SetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_setup, container, false);
        final RadioGroup unitOfAngleRadioGroup = view.findViewById(R.id.rdgUnitOfAngle);
        final RadioGroup rdgNorm = view.findViewById(R.id.rdgNorm);
        btnSave = view.findViewById(R.id.btnSaveSetup);
        btnCancel = view.findViewById(R.id.btnCancelSetup);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitOfAngle = view.findViewById(unitOfAngleRadioGroup.getCheckedRadioButtonId()).getTag().toString();
                norm = Integer.parseInt(view.findViewById(rdgNorm.getCheckedRadioButtonId()).getTag().toString());
                Log.i("SETUP", "Unit of Angle: "+unitOfAngle);
                Log.i("SETUP", "Norm: "+norm);
                ((MainActivity)getActivity()).refreshSetting();
                getDialog().dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;

    }
}
