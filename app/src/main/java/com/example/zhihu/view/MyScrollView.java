package com.example.zhihu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.zhihu.R;

public class MyScrollView extends ScrollView {
    private int maxHeight;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyScrollView);
        maxHeight = typedArray.getLayoutDimension(R.styleable.MyScrollView_maxHeight, maxHeight);
        typedArray.recycle();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight > 0){
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
