package com.louisgeek.louisnumberpickerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.louisgeek.numberpickerviewlib.NumberPickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumberPickerView numberPickerView= (NumberPickerView) findViewById(R.id.id_npv);
        //numberPickerView.setMinAndMaxAndIncrement(1,10,0.5);
        // numberPickerView.setMinAndMaxAndIncrement(1,10,1);
        //  numberPickerView.setNowValue("xx");
    }
}
