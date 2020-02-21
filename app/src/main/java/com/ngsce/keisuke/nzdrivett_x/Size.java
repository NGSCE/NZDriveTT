package com.ngsce.keisuke.nzdrivett_x;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;


public class Size{
    //文字サイズの倍率計算

    float multiple = 1.0f;

    public float displaySize() {
        float widthf = 1.0f;
        float heightf = 1.0f;

        TT tt = new TT();
        Context context = tt.getAppContext();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();




        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Point size = new Point();
            display.getRealSize(size);
            widthf = size.x;
            heightf = size.y;
        }else{
            try {
                Method getRawWidth = Display.class.getMethod("getRawWidth");
                Method getRawHeight = Display.class.getMethod("getRawHeight");
                widthf = (Integer) getRawWidth.invoke(display);
                heightf = (Integer) getRawHeight.invoke(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //縦横比16:9と比較して倍率計算
        if (16f/9f < widthf/heightf) {
            multiple = heightf/1080f;
        }else {
            multiple = widthf/1920f;
        }
        return multiple;
    }
}
