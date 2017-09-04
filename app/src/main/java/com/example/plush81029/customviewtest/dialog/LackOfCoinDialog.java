package com.example.plush81029.customviewtest.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.plush81029.customviewtest.R;


/**
 * Created by HONGDA on 2017/8/18.
 */

public class LackOfCoinDialog extends DialogFragment {

    public static String TAG = LackOfCoinDialog.class.getSimpleName();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() != null && getDialog().getWindow() != null) {
            WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            WindowManager wm = (WindowManager)getContext()
                    .getSystemService(Context.WINDOW_SERVICE);

            int wd = wm.getDefaultDisplay().getWidth();

            layoutParams.width = dip2px(getContext(), 300);
//            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().setAttributes(layoutParams);
            getDialog().setCanceledOnTouchOutside(true);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
