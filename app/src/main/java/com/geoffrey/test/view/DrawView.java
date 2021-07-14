package com.geoffrey.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawView extends View {

    private Paint mPaint, mSecondPaint;
    private Rect mRect;
    private int width;
    private float textWidth;
    private int height, textHeight;
    private float density;
    private float textSize;
    private Path path;
    private Matrix matrix;
    private String text;
    private TextPaint tp;
    private Rect bounds;
    private Context context;
    private float startX, startY;
    private float textOffset;
    private float hourStrokeWidth, minStrokeWidth;
    private float hourStrokeHeight, minStrokeHeight;
    private float secondStrokeWidth;
    private int count = 0;
    private Handler myHandler;
    private Handler.Callback myCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {

                count++;
                count = count % 60;
                invalidate();
                myHandler.sendEmptyMessageDelayed(0, 1000);
            }
            return true;
        }
    };

    //clearCanvas
    private Paint paint;
    private PorterDuffXfermode clearMode, srcMode;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        mPaint = new Paint();
        mSecondPaint = new Paint();
        mRect = new Rect();
        density = context.getResources().getDisplayMetrics().density;
        textSize = 14;
        path = new Path();
        matrix = new Matrix();
        tp = new TextPaint();
        bounds = new Rect();
        mPaint.setAntiAlias(true);
        mSecondPaint.setAntiAlias(true);

        hourStrokeWidth = 2 * density;
        minStrokeWidth = 1 * density;
        secondStrokeWidth = 2 * density;
        hourStrokeHeight = 10 * density;
        minStrokeHeight = 6 * density;
        textOffset = hourStrokeHeight + 10 * density;

        //clearCanvas
        paint = new Paint();
        clearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        srcMode = new PorterDuffXfermode(PorterDuff.Mode.SRC);

        myHandler = new MyHandler(myCallback);
        myHandler.sendEmptyMessageDelayed(0, 1000);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
        drawScale(canvas);
        drawSeconds(canvas);
        drawCenter(canvas);
    }

    private void drawCircle(Canvas canvas) {

        width = getMeasuredWidth();
        height = getMeasuredHeight();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, height / 2, width / 2, mPaint);
    }

    private void drawScale(Canvas canvas) {

        mPaint.setTextSize(density * textSize);
        mPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < 60; i++) {
            canvas.save();
            canvas.rotate(i * 6, width / 2, height / 2);
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(hourStrokeWidth);
                canvas.drawLine(width / 2, (height - width) / 2, width / 2, (height - width) / 2 + hourStrokeHeight, mPaint);
            } else {
                mPaint.setStrokeWidth(minStrokeWidth);
                canvas.drawLine(width / 2, (height - width) / 2, width / 2, (height - width) / 2 + minStrokeHeight, mPaint);
            }
            canvas.restore();
        }

        for (int i = 0; i < 12; i++) {

            text = String.valueOf(i == 0 ? 12 : i);
            startX = (float) (width / 2 + (width / 2 - textOffset) * Math.sin(Math.PI / 6 * i) - mPaint.measureText(text) / 2);
            startY = (float) ((height) / 2 - (width / 2 - textOffset) * Math.cos(Math.PI / 6 * i) + mPaint.measureText(text) / 2);
            canvas.drawText(text, startX, startY, mPaint);
        }
    }

    private void drawSeconds(Canvas canvas) {

        canvas.save();
        mSecondPaint.setColor(Color.RED);
        mSecondPaint.setStrokeWidth(secondStrokeWidth);
        canvas.rotate(6 * count, width / 2, height / 2);
        canvas.drawLine(width / 2, (height - width) / 2 + 40 * density, width / 2, height / 2 + 20 * density, mSecondPaint);
        canvas.restore();
    }

    private void drawCenter(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(width / 2, height / 2, 10 * density, mPaint);
    }
}
