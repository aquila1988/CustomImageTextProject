package com.aquila.custom.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.aquila.custom.widget.R;


/**
 * Created by yulong on 2017/7/21.
 */

public class TriangleTextView extends android.support.v7.widget.AppCompatTextView {

    private int bgColor = Color.TRANSPARENT;
    private Path path;
    private Paint paint;
    private float rate;

    public TriangleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.TriangleTextView);
        bgColor = t.getColor(R.styleable.TriangleTextView_triangle_bg_color, Color.TRANSPARENT);
        rate = t.getFloat(R.styleable.TriangleTextView_rate, 0.5f);
        t.recycle();

        paint = new Paint();
        paint.setColor(bgColor);
        path = new Path();

    }

    private float getResultRate() {
        //根据需求得出的算法公式为 √2 * (1 - rate) / 4
        return (float) (Math.sqrt(2) * (1 - rate) / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        path.moveTo(0, 0);// 此点为多边形的起点
        path.lineTo((float) -(getWidth() * (1 - rate)), 0);
        path.lineTo(getWidth(), (float) (getHeight() * rate));
        path.lineTo(getWidth(), (float) getHeight());
        path.close(); // 使这些点构成封闭的多边形

        canvas.drawPath(path, paint);
        canvas.rotate(45, getWidth() / 2, getHeight() / 2);
        canvas.translate(0, -getHeight() * getResultRate());

        super.onDraw(canvas);
    }
}
