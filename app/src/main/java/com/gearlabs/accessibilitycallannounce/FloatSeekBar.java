package com.gearlabs.accessibilitycallannounce;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.gearlabs.accessibilitycallannounce.R;

public class FloatSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    private float min = -20.00f;
    private float max = 20.00f;

    public FloatSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyAttrs(attrs);
    }

    public FloatSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyAttrs(attrs);
    }

    public FloatSeekBar(Context context) {
        super(context);
    }

    public float getValue() {
        return (max - min) * ((float) getProgress() / (float) getMax()) + min;
    }

    public void setValue(float value) {
        setProgress((int) ((value - min) / (max - min) * getMax()));
    }

    private void applyAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FloatSeekBar);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.FloatSeekBar_floatMax:
                    this.max = a.getFloat(attr, 1.0f);
                    break;
                case R.styleable.FloatSeekBar_floatMin:
                    this.min = a.getFloat(attr, 0.0f);
                    break;
            }
        }
        a.recycle();
    }
}