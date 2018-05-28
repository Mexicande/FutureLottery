package com.xinhe.haoyuncaipiao.utils;

import android.widget.Toast;

import com.xinhe.haoyuncaipiao.base.BaseApplication;


/**
 * @author apple
 *
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast( CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


}
