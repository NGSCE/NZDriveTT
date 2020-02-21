package com.ngsce.keisuke.nzdrivett_x;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class Lines extends View {
    //線の描画

    Size m = new Size();
    float multiple = m.displaySize();

    Paint paint;
    Paint paintt;
    Paint painttt;
    Paint paintttt;

    public Lines(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paintt = new Paint();
        painttt = new Paint();
        paintttt = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paintt.setColor(Color.WHITE);
        paintt.setStrokeWidth(4);
        painttt.setColor(Color.WHITE);
        painttt.setStrokeWidth(8);
        paintttt.setColor(Color.BLACK);
        paintttt.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawColor(Color.argb(0,0,0,0));

        canvas.drawRect(1348*multiple, 738*multiple,2222*multiple, 1080*multiple,paintttt);

        canvas.drawLine(0*multiple, 105*multiple, 2222*multiple, 105*multiple, paint);
        canvas.drawLine(0*multiple, 316*multiple, 2222*multiple, 316*multiple, paint);
        canvas.drawLine(0*multiple, 527*multiple, 2222*multiple, 527*multiple, paint);
        canvas.drawLine(0*multiple, 738*multiple, 2222*multiple, 738*multiple, paint);
        canvas.drawLine(967*multiple, 0*multiple, 967*multiple, 738*multiple, paintt);
        canvas.drawLine(1475*multiple, 0*multiple, 1475*multiple, 738*multiple, paintt);
        canvas.drawLine(1348*multiple, 738*multiple, 1348*multiple, 1080*multiple, painttt);
    }
}

