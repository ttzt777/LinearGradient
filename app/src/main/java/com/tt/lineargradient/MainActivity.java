package com.tt.lineargradient;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private static final int[] colors = new int[] {
            0xff008000,
            0xff00ff00,
            0xffffff00,
            0xffff0000
    };

    private SeekBar mSeekBar;
    private View mDisView;
    private TextView mDisText;

    private LinearGradientCalculator colorCalcutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        colorCalcutor = new LinearGradientCalculator(colors);
//
//        mSeekBar = (SeekBar) findViewById(R.id.sb_progress);
//        mDisView = findViewById(R.id.view);
//        mDisText = (TextView) findViewById(R.id.tv_dis);
//
//        mSeekBar.setOnSeekBarChangeListener(this);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LinearGradientView view = (LinearGradientView) findViewById(R.id.lgv);
//
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                params.width = 600;
//                params.height = 200;
//                view.setLayoutParams(params);
//            }
//        }, 2000);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        float progress = i / 1000000f;
        mDisText.setText(String.valueOf(progress));
        mDisView.setBackgroundColor(colorCalcutor.getColor(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
