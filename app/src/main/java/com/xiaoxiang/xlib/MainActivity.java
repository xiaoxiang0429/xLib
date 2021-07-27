package com.xiaoxiang.xlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.Toast;

import com.xiaoxiang.xspinner.OnSpinnerSelectListener;
import com.xiaoxiang.xspinner.SpinnerTextFormatter;
import com.xiaoxiang.xspinner.XSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Bean> beans = new ArrayList<>();
        beans.add(new Bean("第一：", "销售"));
        beans.add(new Bean("第二：", "兑换"));
        beans.add(new Bean("第仨：", "赠送"));
        beans.add(new Bean("第四：", "白给"));
        SpinnerTextFormatter<Bean> textFormatter = new SpinnerTextFormatter<Bean>() {
            @Override
            public Spannable format(Bean bean) {
                return new SpannableString(bean.getType() + " " + bean.getContent());
            }
        };

        XSpinner xSpinner = findViewById(R.id.sp_sale_type);
        xSpinner.bindDataSource(beans, textFormatter);
        xSpinner.setOnSpinnerSelectListener(new OnSpinnerSelectListener<Bean>() {

            @Override
            public void onSpinnerSelect(XSpinner spinner, Bean bean, int position) {
                Toast.makeText(MainActivity.this, bean.getContent(), Toast.LENGTH_LONG).show();
            }
        });
    }
}