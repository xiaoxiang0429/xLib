package com.xiaoxiang.xspinner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2021/7/23
 * email：tianzh@ccbsun.com
 * description：
 */
public class XSpinner extends AppCompatTextView {
    private int backgroundSelector;
    private AttachListPopupView attachListPopupView;
    private int selectPosition;
    private OnSpinnerSelectListener onSpinnerSelectListener;
    private String[] spinnerStr;
    final private List<Object> dataSource = new ArrayList<>();

    private int textColor;

    private Drawable arrowDrawable;
    private boolean isArrowHidden;
    private int arrowDrawableTint;
    private @DrawableRes
    int arrowDrawableResId;


    public XSpinner(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public XSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public XSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final Resources resources = getResources();
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XSpinner);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        final int defaultPadding = resources.getDimensionPixelSize(R.dimen.default_padding);
        setPadding(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
        setClickable(true);


//        backgroundSelector = typedArray.getResourceId(R.styleable.XSpinner_backgroundSelector, R.drawable.selector);
//        setBackgroundResource(backgroundSelector);

        textColor = typedArray.getColor(R.styleable.XSpinner_textTint, getDefaultTextColor(context));
        setTextColor(textColor);

        isArrowHidden = typedArray.getBoolean(R.styleable.XSpinner_hideArrow, false);
        arrowDrawableTint = typedArray.getColor(R.styleable.XSpinner_arrowTint, getResources().getColor(android.R.color.black));
        arrowDrawableResId = typedArray.getResourceId(R.styleable.XSpinner_arrowDrawable, R.drawable.arrow);

        CharSequence[] entries = typedArray.getTextArray(R.styleable.XSpinner_entries);
        if (entries != null) {
            bindDataSource(Arrays.asList(entries), null);
        }
        attachListPopupView = new XPopup.Builder(getContext())
                .atView(this)
                .dismissOnBackPressed(true)
                .dismissOnBackPressed(true)
                .popupPosition(PopupPosition.Bottom)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                .asAttachList(spinnerStr, null, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        if (position >= selectPosition && position < dataSource.size()) {
                            position++;
                        }
                        selectPosition = position;

                        if (onSpinnerSelectListener != null) {
                            onSpinnerSelectListener.onSpinnerSelect(XSpinner.this, position);
                        }

                        setText(spinnerStr[position]);
                    }
                })
        ;

        typedArray.recycle();
    }

    public <T> void bindDataSource(List<T> list, SpinnerTextFormatter<T> spinnerTextFormatter) {
        dataSource.clear();
        dataSource.addAll(list);
        spinnerStr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            T item = list.get(i);
            if (spinnerTextFormatter == null) {
                spinnerStr[i] = item.toString();
            } else {
                spinnerStr[i] = spinnerTextFormatter.format(item).toString();
            }
        }
        setText(spinnerStr[0]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled() && event.getAction() == MotionEvent.ACTION_UP) {
            if (!attachListPopupView.isShow() && dataSource.size() > 0) {
                attachListPopupView.show();
            } else {
                attachListPopupView.dismiss();
            }
        }
        return super.onTouchEvent(event);
    }

    private int getDefaultTextColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme()
                .resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data,
                new int[]{android.R.attr.textColorPrimary});
        int defaultTextColor = typedArray.getColor(0, Color.BLACK);
        typedArray.recycle();
        return defaultTextColor;
    }

    public void setOnSpinnerSelectListener(OnSpinnerSelectListener onSpinnerSelectListener) {
        this.onSpinnerSelectListener = onSpinnerSelectListener;
    }

    public List<Object> getDataSource() {
        return dataSource;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        arrowDrawable = initArrowDrawable(arrowDrawableTint);
        setArrowDrawableOrHide(arrowDrawable);
    }

    private Drawable initArrowDrawable(int drawableTint) {
        if (arrowDrawableResId == 0) return null;
        Drawable drawable = ContextCompat.getDrawable(getContext(), arrowDrawableResId);
        if (drawable != null) {
            // Gets a copy of this drawable as this is going to be mutated by the animator
            drawable = DrawableCompat.wrap(drawable).mutate();
            if (drawableTint != Integer.MAX_VALUE && drawableTint != 0) {
                DrawableCompat.setTint(drawable, drawableTint);
            }
        }
        return drawable;
    }

    private void setArrowDrawableOrHide(Drawable drawable) {
        if (!isArrowHidden && drawable != null) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }
}
