package com.aquila.custom.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yulong_wang on 2017/10/24 15:07.
 */

public class GroupImageLeftTextRightLayout extends AbstractGroupImageTextLayout {


    public GroupImageLeftTextRightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initializeView(Context context) {
        inflate(context, R.layout.group_image_left_text_right_layout, this);
        leftImageView = (ImageView) findViewById(R.id.group_left_ImageView);
        rightTextView = (TextView) findViewById(R.id.group_right_TextView);

        if (imageDrawable !=null){
            leftImageView.setImageDrawable(imageDrawable);
        }

        LayoutParams imageParams = (LayoutParams) leftImageView.getLayoutParams();
        if (imageWidth > 0){
            imageParams.width = imageWidth;
        }
        if (imageHeight > 0){
            imageParams.height = imageHeight;
        }
        leftImageView.setLayoutParams(imageParams);

        if (scaleTypeIndex >= 0) {
            setScaleType(sScaleTypeArray[scaleTypeIndex]);
        }

        if (textColor != null){
            rightTextView.setTextColor(textColor);
        }
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        if (textString != null) {
            rightTextView.setText(textString);
        }

        rightTextView.setGravity(gravityArray[gravityIndex]);

        if (textMarginImageSize > 0) {
            LayoutParams textParams = (LayoutParams) rightTextView.getLayoutParams();
            textParams.leftMargin = textMarginImageSize;
            rightTextView.setLayoutParams(textParams);
        }

    }

    @Override
    protected void initializeAttrs(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GroupImageLeftTextRightLayout);
        imageWidth = a.getDimensionPixelOffset(R.styleable.GroupImageLeftTextRightLayout_app_image_width, 0);
        imageHeight = a.getDimensionPixelOffset(R.styleable.GroupImageLeftTextRightLayout_app_image_height, 0);
        imageDrawable = a.getDrawable(R.styleable.GroupImageLeftTextRightLayout_app_image_src);
        scaleTypeIndex = a.getInt(R.styleable.GroupImageLeftTextRightLayout_app_scale_type, -1);

        textSize = a.getDimensionPixelSize(R.styleable.GroupImageLeftTextRightLayout_app_text_size, 16);
        textColor = a.getColorStateList(R.styleable.GroupImageLeftTextRightLayout_app_text_color);
        textString = a.getString(R.styleable.GroupImageLeftTextRightLayout_app_text);
        gravityIndex = a.getInt(R.styleable.GroupImageLeftTextRightLayout_app_text_gravity, 0);

        textMarginImageSize = a.getInt(R.styleable.GroupImageLeftTextRightLayout_app_text_margin_image_size, 0);
        a.recycle();
    }


}
