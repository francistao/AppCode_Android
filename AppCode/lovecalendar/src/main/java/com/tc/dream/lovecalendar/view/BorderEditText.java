package com.tc.dream.lovecalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 带边框的EditText
 * Created by dream on 15/11/20.
 */
public class BorderEditText extends EditText{

    public BorderEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(android.graphics.Color.GRAY);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(2, 0, this.getWidth() - 2, this.getHeight() - 2);
        canvas.drawRoundRect(rectF, 8, 8, paint);
    }
}
