/*
 * Copyright (C) 2015 Quinn Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aquila.custom.widget.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.aquila.custom.widget.R;


public class SlideSwitch extends View {

	public static final int SHAPE_RECT = 1;
	public static final int SHAPE_CIRCLE = 2;

	private static final int RIM_SIZE = 2;
	private static final int DEFAULT_COLOR_THEME = Color.parseColor("#ff0033");
    private boolean isOpen;
    // 3 attributes
	private int colorTheme;
    private int shapeStyle;
    // varials of drawing
    private Paint paint;
    private Rect backRect;
	private Rect frontRect;
	private RectF frontCircleRect;
	private RectF backCircleRect;

	private int alpha;
	private int maxLeft;
	private int minLeft;
	private int frontRectLeft;
	private int frontRectLeftBegin = RIM_SIZE;
	private int eventStartX;
	private int eventLastX;
	private int diffX = 0;
	private boolean slideAble = true;
	private SlideListener slideListener;

	public interface SlideListener {
		void onCheckState(SlideSwitch slideSwitch, boolean isOpen);
	}


	public SlideSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		slideListener = null;
		paint = new Paint();
		paint.setAntiAlias(true);
		initAttrs(context, attrs);
	}

	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SlideSwitch);
		colorTheme = a.getColor(R.styleable.SlideSwitch_theme_color,
				DEFAULT_COLOR_THEME);
		isOpen = a.getBoolean(R.styleable.SlideSwitch_isOpen, false);
		shapeStyle = a.getInt(R.styleable.SlideSwitch_shape, SHAPE_RECT);
		a.recycle();
	}

	public SlideSwitch(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideSwitch(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = measureDimension(50, widthMeasureSpec);
		int height = measureDimension(25, heightMeasureSpec);
		if (shapeStyle == SHAPE_CIRCLE) {
			if (width < height) {
				width = height * 2;
			}
		}
		setMeasuredDimension(width, height);
		initDrawingVal();
	}

	public void initDrawingVal() {
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		backCircleRect = new RectF();
		frontCircleRect = new RectF();
		frontRect = new Rect();
		backRect = new Rect(0, 0, width, height);
		minLeft = RIM_SIZE;
		if (shapeStyle == SHAPE_RECT) {
            maxLeft = width / 2;
        } else {
            maxLeft = width - (height - 2 * RIM_SIZE) - RIM_SIZE;
        }
		if (isOpen) {
			frontRectLeft = maxLeft;
			alpha = 255;
		} else {
			frontRectLeft = RIM_SIZE;
			alpha = 0;
		}
		frontRectLeftBegin = frontRectLeft;
	}

	public void setThemeColor(int color){
		colorTheme = color;
		invalidate();
	}

	public int measureDimension(int defaultSize, int measureSpec) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = defaultSize; // UNSPECIFIED
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (shapeStyle == SHAPE_RECT) {
			paint.setColor(Color.GRAY);
			canvas.drawRect(backRect, paint);
			paint.setColor(colorTheme);
			paint.setAlpha(alpha);
			canvas.drawRect(backRect, paint);
			frontRect.set(frontRectLeft, RIM_SIZE, frontRectLeft
					+ getMeasuredWidth() / 2 - RIM_SIZE, getMeasuredHeight()
					- RIM_SIZE);
			paint.setColor(Color.WHITE);
			canvas.drawRect(frontRect, paint);
		} else {
			// draw circle
			int radius;
			radius = backRect.height() / 2 - RIM_SIZE;
			paint.setColor(Color.GRAY);
			backCircleRect.set(backRect);
			canvas.drawRoundRect(backCircleRect, radius, radius, paint);
			paint.setColor(colorTheme);
			paint.setAlpha(alpha);
			canvas.drawRoundRect(backCircleRect, radius, radius, paint);
			frontRect.set(frontRectLeft, RIM_SIZE, frontRectLeft
					+ backRect.height() - 2 * RIM_SIZE, backRect.height()
					- RIM_SIZE);
			frontCircleRect.set(frontRect);
			paint.setColor(Color.WHITE);
			canvas.drawRoundRect(frontCircleRect, radius, radius, paint);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (slideAble == false) {
            return super.onTouchEvent(event);
        }
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			eventStartX = (int) event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			eventLastX = (int) event.getRawX();
			diffX = eventLastX - eventStartX;
			int tempX = diffX + frontRectLeftBegin;
			tempX = (tempX > maxLeft ? maxLeft : tempX);
			tempX = (tempX < minLeft ? minLeft : tempX);
			if (tempX >= minLeft && tempX <= maxLeft) {
				frontRectLeft = tempX;
				alpha = (int) (255 * (float) tempX / (float) maxLeft);
				invalidateView();
			}
			break;
		case MotionEvent.ACTION_UP:
			int wholeX = (int) (event.getRawX() - eventStartX);
			frontRectLeftBegin = frontRectLeft;
			boolean toRight;
			toRight = (frontRectLeftBegin > maxLeft / 2 ? true : false);
			if (Math.abs(wholeX) < 3) {
				toRight = !toRight;
			}
			moveToDest(toRight);
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * draw again
	 */
	private void invalidateView() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	public void setSlideListener(SlideListener listener) {
		this.slideListener = listener;
	}

	public void moveToDest(final boolean toRight) {
		ValueAnimator toDestAnim = ValueAnimator.ofInt(frontRectLeft,
				toRight ? maxLeft : minLeft);
		toDestAnim.setDuration(300);
		toDestAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		toDestAnim.start();
		toDestAnim.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				frontRectLeft = (Integer) animation.getAnimatedValue();
				alpha = (int) (255 * (float) frontRectLeft / (float) maxLeft);
				invalidateView();
			}
		});
		toDestAnim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				if (toRight) {
					isOpen = true;
					if (slideListener != null) {
                        slideListener.onCheckState(SlideSwitch.this, true);
                    }
					frontRectLeftBegin = maxLeft;
				} else {
					isOpen = false;
					if (slideListener != null) {
                        slideListener.onCheckState(SlideSwitch.this, false);
                    }
					frontRectLeftBegin = minLeft;
				}
			}
		});
	}

    @Override
    public boolean performClick() {
        boolean result = super.performClick();
		moveToDest(!isOpen);
        return result;
    }

    public void setState(boolean isOpen) {
		this.isOpen = isOpen;
		initDrawingVal();
		invalidateView();
		if (slideListener != null) {
			slideListener.onCheckState(this, isOpen);
		}
	}

	public void setShapeType(int shapeType) {
		this.shapeStyle = shapeType;
	}

	public void setSlideAble(boolean slideAble) {
		this.slideAble = slideAble;
	}


	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			this.isOpen = bundle.getBoolean("isOpen");
			state = bundle.getParcelable("instanceState");
		}
		super.onRestoreInstanceState(state);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable("instanceState", super.onSaveInstanceState());
		bundle.putBoolean("isOpen", this.isOpen);
		return bundle;
	}
}
