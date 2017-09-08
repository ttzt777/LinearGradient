package com.tt.lineargradient;

import android.graphics.Color;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class LinearGradientCalculator {
    private int[] mColors;

    private int mStartColor;  
    private int mEndColor;

    public LinearGradientCalculator() {

    }
  
    public LinearGradientCalculator(int startColor, int endColor) {
        this.mStartColor = startColor;  
        this.mEndColor = endColor;  
    }

    public LinearGradientCalculator(int[] colors) {
        this.mColors = colors;
    }

    public void setColors(int[] colors) {
        this.mColors = colors;
    }
  
    public void setStartColor(int startColor) {  
        this.mStartColor = startColor;  
    }  
  
    public void setEndColor(int endColor) {  
        this.mEndColor = endColor;  
    }

    public int getColor(float progress) {
        if (mColors == null || mColors.length == 0) {
            throw new RuntimeException("LinearGradientCalculator must have colors");
        }

        if (mColors.length == 1) {
            return mColors[0];
        }

        int phaseCount = mColors.length - 1;
        int phase = 0;

        for (int i = 0; i < phaseCount; i++) {
            if (progress <= (i + 1) / (float) phaseCount) {
                phase = i;
                break;
            }
        }

        int startColor = mColors[phase];
        int endColor = mColors[phase + 1];

        int redStart = Color.red(startColor);
        int greenStart = Color.green(startColor);
        int blueStart = Color.blue(startColor);

        int redEnd = Color.red(endColor);
        int greenEnd = Color.green(endColor);
        int blueEnd = Color.blue(endColor);

        float progressInPhase = (progress - phase * 1 / (float) phaseCount) / (1 / (float) phaseCount);

        int red = (int) (redStart + ((redEnd - redStart) * progressInPhase + 0.5));
        int green = (int) (greenStart + ((greenEnd - greenStart) * progressInPhase + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * progressInPhase + 0.5));

        if (red > 255) {
            Log.e("aa", "red");
            red = 255;
        }

        if (green > 255) {
            Log.e("aa", "green");
            green = 255;
        }

        if (blue > 255) {
            Log.e("aa", "blue");
            blue = 255;
        }

        return Color.argb(255, red, green, blue);
    }
  
    public int getColor1(float radio) {
        int redStart = Color.red(mStartColor);
        int blueStart = Color.blue(mStartColor);  
        int greenStart = Color.green(mStartColor);  
        int redEnd = Color.red(mEndColor);  
        int blueEnd = Color.blue(mEndColor);  
        int greenEnd = Color.green(mEndColor);  
  
        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));  
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));  
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));  
        return Color.argb(255,red, greed, blue);  
    }  
}  