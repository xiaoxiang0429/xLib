package com.xiaoxiang.xspinner;

import android.text.Spannable;

public interface SpinnerTextFormatter<T> {
    Spannable format(T item);
}
