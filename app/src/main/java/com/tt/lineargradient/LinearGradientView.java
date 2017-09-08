package com.tt.lineargradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zhaotao on 2017/9/2.
 */

public class LinearGradientView extends View {
    private static final int WIDTH_DEFAULT = 100;  // dp
    private static final int HEIGHT_DEFAULT = 300; // dp
    private static final String TAG = "LinearGradientView";
    int[] colors = new int[] {
        0xff008000,
        0xff00ff00,
        0xffffff00,
        0xffff0000
    };

    private int mMinWidth;
    private int mMinHeight;

    private int textCount = 5;
    private int textSize = 36;
    private double minValue = 0.0;
    private double maxValue = 100.0;

    private String[] textValues;

    public LinearGradientView(Context context) {
        this(context, null);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mMinWidth = DensityUtil.dp2px(context, WIDTH_DEFAULT);
        mMinHeight = DensityUtil.dp2px(context, HEIGHT_DEFAULT);

        textValues = new String[textCount];
        for (int i = 0; i < textCount; i++) {
            double value = (maxValue - minValue) / (textCount - 1) * i + minValue;
            textValues[i] = String.format("%.1f", value);
            Log.d(TAG, "index " + i + " value " + textValues[i]);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, String.format("onSizeChanged: w -- %d, h -- %d, oldw -- %d, oldh -- %d", w, h, oldw, oldh));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");

        // 支持wrap_content属性
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightSize < mMinHeight) {
            heightSize = mMinHeight;
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mMinWidth, mMinHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mMinWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mMinHeight);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, String.format("onDraw, height -- %d, measureHeight -- %d", getHeight(), getMeasuredHeight()));
        Log.d(TAG, String.format("onDraw, width -- %d, measureWidth -- %d", getWidth(), getMeasuredWidth()));
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        LinearGradient linearGradient = new LinearGradient(0, 0, 0, height, colors, null, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);

        int ruleMarginTop = 13;
        int ruleMarginBottom = 13;

        paint.setTextSize(textSize);

        Paint paint1 = new Paint();
        paint1.setColor(Color.BLACK);

        canvas.drawRect(paddingLeft, paddingTop + ruleMarginTop, paddingLeft + width / 2, paddingTop + height - ruleMarginBottom, paint);

        for (int i = 0; i < textValues.length; i++) {
            // 测量当前字体下的字高度
            Rect rect = new Rect();
            paint.getTextBounds(textValues[i], 0, textValues[i].length(), rect);
            int textWidth = rect.width();
            int textHeight = rect.height();

            if (i == 0) {
                ruleMarginTop = textHeight / 2;
            } else if (i == textValues.length - 1) {
                ruleMarginBottom = textHeight / 2;
            }

            canvas.drawLine(paddingLeft, paddingTop + i * height / (textValues.length - 1), paddingLeft + width / 2, 3 + paddingTop + i * height / (textValues.length - 1), paint1);

            canvas.drawText(textValues[i], paddingLeft + width / 2, paddingTop + textHeight + i * height / (textValues.length - 1), paint);
        }



        canvas.drawText("53465", paddingLeft,  + 26, paint);




    }
}
