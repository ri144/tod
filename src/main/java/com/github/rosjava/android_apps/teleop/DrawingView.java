package com.github.rosjava.android_apps.teleop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;

import com.github.rosjava.android_apps.application_management.RosAppActivity;

/**
 * Created by turtlebot on 09/02/15.
 */
/*
public class DrawingView extends View {

    private Paint paint;
    private Path path;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(Color.BLACK);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(5f);
        this.path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX,eventY);
                return  true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                path.lineTo(eventX,eventY);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    public void clear(){
        path.reset();
        invalidate();
    }

    public void setPaintColor(int color){
        paint.setColor(color);
    }
    public int getCurrentColor(){
        return paint.getColor();
    }
}
*/
public class DrawingView extends RosAppActivity implements View.OnTouchListener {
    private float x;
    private float y;


    public DrawingView(){
        super("Painting","Painting");
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_drawing);
        MyCustomPanel view = new MyCustomPanel(this);

        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT,
                        AbsoluteLayout.LayoutParams.FILL_PARENT);
        addContentView(view, params);
        view.setOnTouchListener(this);

    }

    private class MyCustomPanel extends View {

        public MyCustomPanel(Context context) {
            super(context);

        }

        @Override
        public void draw(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(6);

            canvas.drawLine(10, 10, 50, 50, paint);
            paint.setColor(Color.RED);

            canvas.drawLine(50, 50, 90, 10, paint);
            canvas.drawCircle(50, 50, 3, paint);

            canvas.drawCircle(x, y, 10, paint);

        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        v.invalidate();
        return true;
    }
}