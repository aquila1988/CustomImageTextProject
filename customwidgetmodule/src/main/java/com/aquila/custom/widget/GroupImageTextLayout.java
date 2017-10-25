package com.aquila.custom.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by yulong_wang on 2017/10/24 15:07.
 */
public class GroupImageTextLayout extends RelativeLayout {
    protected static final ImageView.ScaleType[] SCALE_TYPE_ARRAY = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };


    protected static final int[] GRAVITY_ARRAY = {
            Gravity.CENTER_HORIZONTAL,
            Gravity.LEFT,
            Gravity.RIGHT,
            Gravity.CENTER,
            Gravity.CENTER_VERTICAL
    };

    private final static int ORIENTATION_VERTICAL = 0;
    private final static int ORIENTATION_HORIZONTAL = 1;


    protected int imageWidth;
    protected int imageHeight;
    protected Drawable imageDrawable;
    protected int scaleTypeIndex = -1;

    protected int textSize;
    protected String textString;
    protected ColorStateList textColor;
    protected int gravityIndex = 0;

    protected int textMarginImageSize;

    protected int orientationIndex;

    protected ImageView imageView;
    protected TextView textView;
//    protected TextView bottomTextView;

    protected int paddingLeft, paddingRight, paddingTop, paddingBottom;

    public GroupImageTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttrs(context, attrs);
        initializeView(context);
    }

    protected void initializeAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GroupImageTextLayout);
        imageWidth = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_image_width, 0);
        imageHeight = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_image_height, 0);
        imageDrawable = a.getDrawable(R.styleable.GroupImageTextLayout_app_image_src);
        scaleTypeIndex = a.getInt(R.styleable.GroupImageTextLayout_app_scale_type, -1);

        textSize = a.getDimensionPixelSize(R.styleable.GroupImageTextLayout_app_text_size, 16);
        textColor = a.getColorStateList(R.styleable.GroupImageTextLayout_app_text_color);
        textString = a.getString(R.styleable.GroupImageTextLayout_app_text);
        gravityIndex = a.getInt(R.styleable.GroupImageTextLayout_app_text_gravity, 0);


        paddingLeft = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_padding_left, 0);
        paddingRight = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_padding_right, 0);
        paddingBottom = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_padding_bottom, 0);
        paddingTop = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_padding_top, 0);

        int padding = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_padding, 0);
        if (padding > 0) {
            paddingTop = paddingBottom = paddingLeft = paddingRight = padding;
        }
        orientationIndex = a.getInt(R.styleable.GroupImageTextLayout_app_parent_orientation, 0);
        textMarginImageSize = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_text_margin_image_size, 0);
        a.recycle();
    }


    protected void initializeView(Context context) {
        inflate(context, R.layout.group_image_text_layout, this);
        imageView = (ImageView) findViewById(R.id.group_top_ImageView);
        // 表示垂直
        if (orientationIndex == 0) {
            textView = (TextView) findViewById(R.id.group_bottom_TextView);
            findViewById(R.id.group_right_TextView).setVisibility(GONE);
        } else {
            textView = (TextView) findViewById(R.id.group_right_TextView);
            findViewById(R.id.group_bottom_TextView).setVisibility(GONE);
        }

        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        ;


        if (imageDrawable != null) {
            imageView.setImageDrawable(imageDrawable);
        }


        LayoutParams imageParams = (LayoutParams) imageView.getLayoutParams();
        if (imageWidth > 0) {
            imageParams.width = imageWidth;
        }
        if (imageHeight > 0) {
            imageParams.height = imageHeight;
        }

        if (orientationIndex == 0) {
            imageParams.addRule(CENTER_HORIZONTAL);
        } else {
            imageParams.addRule(CENTER_VERTICAL);
        }

        imageView.setLayoutParams(imageParams);

        if (scaleTypeIndex >= 0) {
            setScaleType(SCALE_TYPE_ARRAY[scaleTypeIndex]);
        }

        initTextView(textView);
    }

    public void initTextView(TextView textView) {
        if (textColor != null) {
            textView.setTextColor(textColor);
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (textString != null) {
            textView.setText(textString);
        }
        textView.setGravity(GRAVITY_ARRAY[gravityIndex]);
        if (textMarginImageSize > 0) {
            LayoutParams textParams = (LayoutParams) this.textView.getLayoutParams();
            if (orientationIndex == ORIENTATION_HORIZONTAL) {
                textParams.leftMargin = textMarginImageSize;
            } else {
                textParams.topMargin = textMarginImageSize;
            }
            textView.setLayoutParams(textParams);
        }
    }


    public void setTextSize(String text) {
        textView.setText(text);
    }

    public void setTextSize(int resid) {
        textView.setText(resid);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        imageView.setScaleType(scaleType);
    }

}
