package com.example.plush81029.customviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuj on 2016/6/21.
 * 倒计时文本框
 */
public class CountDownTextView extends TextView {

    private static long existTime=-1;

    private static int DEFAULT_SECONDS = 60;
    public static boolean isCounterStart= false;


    private String initTxt;
    private String formatString;
    private int initTxtColor;
    private int countDownTxtColor;

//    private int currentSecond;
    private ScheduledExecutorService executorService;

    private CountDownListener countDownListener;

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        this.onPageStart();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.onPageExit();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CountDownTextView);
        formatString = typedArray.getString(R.styleable.CountDownTextView_countdown_format);
        initTxt = typedArray.getString(R.styleable.CountDownTextView_countdown_init_txt);
        initTxtColor = typedArray.getColor(R.styleable.CountDownTextView_countdown_init_txtcolor, Color.BLACK);
        countDownTxtColor = typedArray.getColor(R.styleable.CountDownTextView_countdown_txtcolor, Color.BLACK);
        typedArray.recycle();
    }


    /**
     * 页面进入
     */
    private void onPageStart(){
        if (existTime!=-1&&isCounterStart) {
            long currentTime = System.currentTimeMillis();
            int seconds = (int) ((currentTime - existTime) / 1000);
            if (seconds<DEFAULT_SECONDS){
                DEFAULT_SECONDS-=seconds;
                runCountDown();
                return;
            }
        }
        resetTotalSecond();
    }




    /**
     * 页面退出
     */
    private void onPageExit(){
        stopCountDown();
        if (isCounterStart){
            existTime= System.currentTimeMillis();
        }
    }


    /**
     * 开始计时
     */
    public void startCountDown() {
        isCounterStart=true;
        runCountDown();
    }


    private void runCountDown(){

        setEnabled(false);

        executeDown();
        if (countDownListener != null) {
            countDownListener.onCountDownStart();
        }
    }

    private void executeDown(){
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                DEFAULT_SECONDS--;
                if (DEFAULT_SECONDS > 0) {
                    updateText();
                } else {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (countDownListener != null) {
                                countDownListener.onCountDownEnd();
                            }
                        }
                    });
                    reset();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 停止计时
     */
    private void stopCountDown() {
        if (executorService != null) {
            executorService.shutdownNow();
            executorService = null;
        }
    }

    /**
     * 重置
     */
    private void reset() {
        isCounterStart=false;
        stopCountDown();
        updateText();
    }

    /**
     * 更新时间
     *
     * @param
     */
    private void updateText() {
            post(new Runnable() {
                @Override
                public void run() {
                    if (DEFAULT_SECONDS <= 0) {
                        setText(initTxt);
                        setTextColor(initTxtColor);
                        setEnabled(true);
                        resetTotalSecond();
                    } else {
                        setTextColor(countDownTxtColor);
                        setText(String.format(formatString, DEFAULT_SECONDS));
                    }
                }
            });
    }

    /**
     * 重置秒数
     */
    private void resetTotalSecond(){
        existTime=-1;
        DEFAULT_SECONDS=60;
        isCounterStart=false;
    }


    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public interface CountDownListener {
        void onCountDownStart();

        void onCountDownEnd();
    }



}
