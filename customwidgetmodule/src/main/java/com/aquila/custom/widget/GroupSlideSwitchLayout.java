package com.aquila.custom.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aquila.custom.widget.view.SlideSwitch;

public class GroupSlideSwitchLayout extends RelativeLayout {
    private String text;
    private int textColor;
    private int textSize;
    private int switchWidth, switchHeight;
    private boolean isOpen;

    private TextView textView;
    private SlideSwitch slideSwitch;

    public GroupSlideSwitchLayout(Context context){ super(context);}

    public GroupSlideSwitchLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        initializeAttrs(context, attrs);
        initializeViews(context);
    }

    private void initializeAttrs(Context context, AttributeSet attrs){
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.GroupSlideSwitchLayout);
        textColor = t.getColor(R.styleable.GroupSlideSwitchLayout_app_text_color,getResources().getColor(R.color.content_text_color));
        textSize = t.getDimensionPixelSize(R.styleable.GroupSlideSwitchLayout_app_text_size, (int) getResources().getDimension(R.dimen.text_size_16));
        text = t.getString(R.styleable.GroupSlideSwitchLayout_app_text);
        isOpen = t.getBoolean(R.styleable.GroupSlideSwitchLayout_app_is_selected, false);
        switchWidth = t.getDimensionPixelOffset(R.styleable.GroupSlideSwitchLayout_app_switch_width, 30);
        switchHeight = t.getDimensionPixelOffset(R.styleable.GroupSlideSwitchLayout_app_switch_height, 60);
        // 获取资源后要及时回收
        t.recycle();
    }

    private void initializeViews(Context context){
        View.inflate(context, R.layout.group_slide_switch_layout,this);
        textView = (TextView) findViewById(R.id.slide_switch_textView);
        slideSwitch = (SlideSwitch) findViewById(R.id.slide_switch);

        slideSwitch.setState(isOpen);

        LayoutParams params = (LayoutParams) slideSwitch.getLayoutParams();
        params.width = switchWidth;
        params.height = switchHeight;
        slideSwitch.setLayoutParams(params);

        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setSwitchState(boolean isOpen){
        slideSwitch.setState(isOpen);
    }

    public void setText(String text){ textView.setText(text);}

}



























