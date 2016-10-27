package com.southernbox.indexbar.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by SouthernBox on 2016/10/25 0025.
 */

public class DisplayUtil {

    /**
     * 将dp值转换为px值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, int dpValue) {
        //获取屏幕密度
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        //屏幕密度的比例值
        float density = displayMetrics.density;
        //将dp转换为px
        return (int) (dpValue * density + 0.5);
    }

}
