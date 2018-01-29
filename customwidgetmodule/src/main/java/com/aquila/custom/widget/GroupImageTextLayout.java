package com.aquila.custom.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.StringRes;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by @author 王玉龙 on 2017/10/24 15:07.
 */
public class GroupImageTextLayout extends RelativeLayout {
    private static final ImageView.ScaleType[] SCALE_TYPE_ARRAY = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };


    private static final int[] GRAVITY_ARRAY = {
            Gravity.CENTER_HORIZONTAL,
            Gravity.LEFT,
            Gravity.RIGHT,
            Gravity.CENTER,
            Gravity.CENTER_VERTICAL
    };

    private static final TextUtils.TruncateAt[] TEXT_ELLIPSE ={
            TextUtils.TruncateAt.START,
            TextUtils.TruncateAt.MIDDLE,
            TextUtils.TruncateAt.END,
            TextUtils.TruncateAt.MARQUEE,
    };
//    private static final int ORIENTATION_VERTICAL = 0;

    public static final int IMAGE_LEFT_TEXT_RIGHT = 0;
    public static final int IMAGE_TOP_TEXT_BOTTOM= 1;
    public static final int IMAGE_RIGHT_TEXT_LEFT= 2;
    public static final int IMAGE_BOTTOM_TEXT_TOP= 3;

//    @IntDef({IMAGE_LEFT_TEXT_RIGHT, IMAGE_TOP_TEXT_BOTTOM, IMAGE_RIGHT_TEXT_LEFT, IMAGE_BOTTOM_TEXT_TOP})
//    public @interface OrientationType{}


    private int imageWidth;
    private int imageHeight;
//    private Drawable imageDrawable;
    private Drawable imageBackgroundDrawable;
    private Drawable textBackgroundDrawable;
//    private int scaleTypeIndex = -1;

    private int textSize;
    private String textString;
    private ColorStateList textColor;

    private int textMarginImageSize;

//    private @OrientationType int orientationType = IMAGE_TOP_TEXT_BOTTOM;

    private ImageView imageView;
    private TextView textView;



    public GroupImageTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
        initializeAttrs(context, attrs);
        initializeView();
    }

    private void initializeAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GroupImageTextLayout);
        imageWidth = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_image_width, 0);
        imageHeight = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_image_height, 0);

        imageBackgroundDrawable = a.getDrawable(R.styleable.GroupImageTextLayout_app_image_background);
        textBackgroundDrawable = a.getDrawable(R.styleable.GroupImageTextLayout_app_text_background);

        textSize = a.getDimensionPixelSize(R.styleable.GroupImageTextLayout_app_text_size, 16);
        textColor = a.getColorStateList(R.styleable.GroupImageTextLayout_app_text_color);
        textString = a.getString(R.styleable.GroupImageTextLayout_app_text);
        textMarginImageSize = a.getDimensionPixelOffset(R.styleable.GroupImageTextLayout_app_text_margin_image_size, 0);


        Drawable imageDrawable = a.getDrawable(R.styleable.GroupImageTextLayout_app_image_src);
        if (imageDrawable != null) {
            imageView.setImageDrawable(imageDrawable);
        }
        int scaleTypeIndex = a.getInt(R.styleable.GroupImageTextLayout_app_scale_type, -1);
        if (scaleTypeIndex >= 0) {
            setScaleType(SCALE_TYPE_ARRAY[scaleTypeIndex]);
        }

        int orientationType = a.getInt(R.styleable.GroupImageTextLayout_app_image_relative_text_type, IMAGE_TOP_TEXT_BOTTOM);
        initTextOrientationStyle(orientationType);

        int gravityIndex = a.getInt(R.styleable.GroupImageTextLayout_app_text_gravity, -1);
        if (gravityIndex >= 0){
            textView.setGravity(GRAVITY_ARRAY[gravityIndex]);
        }

        textView.setSingleLine(a.getBoolean(R.styleable.GroupImageTextLayout_app_text_single_line, false));

        int maxLines =a.getInt(R.styleable.GroupImageTextLayout_app_text_max_lines, -10);
        if (maxLines >= 0){
            textView.setMaxLines(maxLines);
        }

        int ellipsizeIndex = a.getInt(R.styleable.GroupImageTextLayout_app_text_ellipse, -1);
        if (ellipsizeIndex != -1){
            textView.setEllipsize(TEXT_ELLIPSE[ellipsizeIndex]);
        }

        int maxLength = a.getInt(R.styleable.GroupImageTextLayout_app_text_max_length, -1);
        if (maxLength >= 0) {
            textView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
        } else {
            textView.setFilters(new InputFilter[0]);
        }



        a.recycle();





    }

    private void initUI(Context context){
        inflate(context, R.layout.group_image_text_layout, this);
        imageView =   (ImageView) findViewById(R.id.group_image_ImageView);
        textView = (TextView) findViewById(R.id.group_text_TextView);
    }

    private void initializeView() {





        if (textColor != null) {
            textView.setTextColor(textColor);
        }

        if (textBackgroundDrawable != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(textBackgroundDrawable);
            } else {
                textView.setBackgroundDrawable(textBackgroundDrawable);
            }
        }

        if (imageBackgroundDrawable != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageView.setBackground(imageBackgroundDrawable);
            } else {
                imageView.setBackgroundDrawable(imageBackgroundDrawable);
            }
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        if (textString != null) {
            textView.setText(textString);
        }

//        initTextOrientationStyle();

    }

    private void initTextOrientationStyle(int orientationType) {
        LayoutParams textParams = (LayoutParams) textView.getLayoutParams();
        LayoutParams imageParams = (LayoutParams) imageView.getLayoutParams();

        if (imageWidth > 0) {
            imageParams.width = imageWidth;
        }
        if (imageHeight > 0) {
            imageParams.height = imageHeight;
        }

        switch (orientationType){
            case IMAGE_LEFT_TEXT_RIGHT:
                textParams.addRule(RelativeLayout.RIGHT_OF, R.id.group_image_ImageView);
                textParams.addRule(RelativeLayout.CENTER_VERTICAL);

                imageParams.addRule(RelativeLayout.CENTER_VERTICAL);

                textParams.leftMargin = textMarginImageSize / 2;
                imageParams.rightMargin = textMarginImageSize / 2;
                break;
            case IMAGE_RIGHT_TEXT_LEFT:

                textParams.addRule(RelativeLayout.CENTER_VERTICAL);

                imageParams.addRule(RelativeLayout.RIGHT_OF, R.id.group_text_TextView);
                imageParams.addRule(RelativeLayout.CENTER_VERTICAL);

                textParams.rightMargin = textMarginImageSize / 2;
                imageParams.leftMargin = textMarginImageSize / 2;
                break;
            case IMAGE_TOP_TEXT_BOTTOM:
                textParams.addRule(RelativeLayout.BELOW, R.id.group_image_ImageView);
                textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                textParams.topMargin = textMarginImageSize / 2;
                imageParams.bottomMargin = textMarginImageSize / 2;
                break;
            case IMAGE_BOTTOM_TEXT_TOP:
                textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                imageParams.addRule(RelativeLayout.BELOW, R.id.group_text_TextView);
                imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                textParams.bottomMargin = textMarginImageSize / 2;
                imageParams.topMargin = textMarginImageSize / 2;
                break;
            default:
        }

        textView.setLayoutParams(textParams);
        imageView.setLayoutParams(imageParams);
    }


    public void setText(String text) {
        textView.setText(text);
    }

    public void setText(SpannableString text) {
        textView.setText(text);
    }

    public void setText(@StringRes int resId) {
        textView.setText(resId);
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
