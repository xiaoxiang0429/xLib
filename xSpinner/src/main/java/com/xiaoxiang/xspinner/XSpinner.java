package com.xiaoxiang.xspinner;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author xiaoxiang
 * @date 2021/7/23
 * email：tianzh@ccbsun.com
 * description：
 */
public class XSpinner extends AppCompatTextView {

    public XSpinner(@NonNull Context context) {
        super(context);
    }

    public XSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
