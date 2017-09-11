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

import static android.R.attr.bottom;
import static android.R.attr.paddingLeft;

/**
 * Created by zhaotao on 2017/9/2.
 */

public class LinearGradientView extends View {
    private static final int WIDTH_DEFAULT = 100;  // dp
    private static final int HEIGHT_DEFAULT = 300; // dp
    private static final int CONTRACT_WIDTH_DEFAULT = 4;
    private static final int BACKGROUND_WIDTH_DEFAULT = 20; // dp
    private static final int LINE_OFFSET_DEFALUT = 5; //dp
    private static final int LINE_STROKE_DEFAULT = 4;
    private static final double MIN_VALUE_DEFAULT = 0.0;
    private static final double MAX_VALUE_DEFAULT = 100.0;
    private static final int PORTION_DEFAULT = 3;
    private static final float TEXT_SIZE_DEFAULT = 36.0f;
    private static final String TAG = "LinearGradientView";
    private static final int[] colors = new int[] {
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

    private int lineStroke = 4;
    private int rulerWidth = 36;


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
        Paint textPaint = new Paint();
        Paint gradientPaint = new Paint();

        textPaint.setTextSize(textSize);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        // 先计算刻度尺背景的高度等信息
        // 矩形计算顶行字体和末行字体高度
        Rect rect = new Rect();
        textPaint.getTextBounds(textValues[0], 0, textValues[0].length(), rect);
        int firstTextHeight = rect.height();
        textPaint.getTextBounds(textValues[textCount - 1], 0, textValues[textCount - 1].length(), rect);
        int lastTextHeight = rect.height();

        int rulerHeight = height - firstTextHeight / 2 - lastTextHeight / 2;

        // 画出背景
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, height, colors, null, Shader.TileMode.MIRROR);
        gradientPaint.setShader(linearGradient);
        gradientPaint.setColor(Color.GREEN);

        canvas.drawRect(paddingLeft, paddingTop + firstTextHeight / 2, paddingLeft + rulerWidth, paddingTop + height - lastTextHeight / 2, gradientPaint);

        // 画出横线
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(lineStroke);

        for (int i = 0; i < textValues.length; i++) {
            // 测量当前字体下的字高度
            Rect rect1 = new Rect();
            textPaint.getTextBounds(textValues[i], 0, textValues[i].length(), rect);
            int textWidth = rect1.width();
            int textHeight = rect1.height();

            canvas.drawLine(paddingLeft, paddingTop + firstTextHeight / 2 + i * rulerHeight / (textValues.length - 1) - lineStroke / 2,
                    paddingLeft + rulerWidth + 20, paddingTop + firstTextHeight / 2 + i * rulerHeight / (textValues.length - 1) - lineStroke / 2, textPaint);

            // 计算文本的baseline
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            canvas.drawText(textValues[i], paddingLeft + rulerWidth + 20,
                    paddingTop + firstTextHeight / 2 + i * rulerHeight / (textValues.length - 1) + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, textPaint);
        }
    }
}
