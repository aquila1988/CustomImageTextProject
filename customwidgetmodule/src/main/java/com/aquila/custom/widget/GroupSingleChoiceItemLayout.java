package com.aquila.custom.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lijian on 2017/8/9.
 */

public class GroupSingleChoiceItemLayout extends RelativeLayout {
    private String text;
    private int textColor;
    private int textSize;
    private boolean isShowRightImage;

    private TextView textView;
    private ImageView imageView;

    public GroupSingleChoiceItemLayout(Context context) {
        super(context);
    }

    public GroupSingleChoiceItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttrs(context, attrs);
        initializeViews(context);
    }

    private void initializeAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.GroupSingleChoiceItemLayout);
        textColor = mTypedArray.getColor(R.styleable.GroupSingleChoiceItemLayout_app_text_color, getResources().getColor(R.color.content_text_color));
        textSize = mTypedArray.getDimensionPixelSize(R.styleable.GroupSingleChoiceItemLayout_app_text_size, (int) getResources().getDimension(R.dimen.text_size_16));
        text = mTypedArray.getString(R.styleable.GroupSingleChoiceItemLayout_app_text);
        isShowRightImage = mTypedArray.getBoolean(R.styleable.GroupSingleChoiceItemLayout_app_is_show_right_image, false);
        //获取资源后要及时回收
        mTypedArray.recycle();
    }

    private void initializeViews(Context context) {
        View.inflate(context, R.layout.group_single_choice_item_layout, this);
        textView = (TextView) findViewById(R.id.rest_remind_time_textView);
        imageView = (ImageView) findViewById(R.id.rest_time_selected_imageView);

        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setRightImageShowState(isShowRightImage);
    }

    public void setRightImageShowState(boolean isShow) {
        imageView.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
