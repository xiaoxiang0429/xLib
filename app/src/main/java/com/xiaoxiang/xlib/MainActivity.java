package com.xiaoxiang.xlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.xiaoxiang.xspinner.OnSpinnerSelectListener;
import com.xiaoxiang.xspinner.XSpinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XSpinner xSpinner = findViewById(R.id.xSpinner);
        xSpinner.setOnSpinnerSelectListener(new OnSpinnerSelectListener() {
            @Override
            public void onSpinnerSelect(XSpinner spinner, int position) {
                Toast.makeText(MainActivity.this, spinner.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }
}