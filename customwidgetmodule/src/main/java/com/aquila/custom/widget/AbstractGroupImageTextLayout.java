package com.aquila.custom.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yulong_wang on 2017/10/24 15:07.
 */

public abstract class AbstractGroupImageTextLayout extends LinearLayout {
    protected static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    protected static final int[] gravityArray = {
      Gravity.CENTER_HORIZONTAL,
      Gravity.LEFT,
      Gravity.RIGHT,
      Gravity.CENTER,
      Gravity.CENTER_VERTICAL
    };

    protected int imageWidth;
    protected int imageHeight ;
    protected Drawable imageDrawable;
    protected int scaleTypeIndex = -1;

    protected int textSize;
    protected String textString;
    protected ColorStateList textColor;
    protected int gravityIndex = 0;

    protected int textMarginImageSize;


    protected ImageView leftImageView;
    protected TextView rightTextView;


    public AbstractGroupImageTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttrs(context, attrs);
        initializeView(context);
    }

    protected abstract void initializeView(Context context) ;
    protected abstract void initializeAttrs(Context context, AttributeSet attrs);



    public void setTextSize(String text){
        rightTextView.setText(text);
    }
    public void setTextSize(int resid){
        rightTextView.setText(resid);
    }

    public ImageView getImageView() {
        return leftImageView;
    }

    public TextView getTextView() {
        return rightTextView;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        leftImageView.setScaleType(scaleType);
    }
}
