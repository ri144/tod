package com.github.rosjava.android_apps.teleop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by turtlebot on 16/02/15.
 */
public class DrawView extends View implements View.OnTouchListener {
    List<Point5> points = new ArrayList<Point5>();
    Paint paint = new Paint();

    public DrawView(Context context){
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.BLACK);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Point5 point : points) {
            canvas.drawCircle(point.x,point.y,2,paint);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Point5 point = new Point5();
        point.x = event.getX();
        point.y = event.getY();
        points.add(point);
        invalidate();
        return true;
    }
}

class Point5 extends android.graphics.Point {

    float x, y;
}