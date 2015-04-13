package com.github.rosjava.android_apps.teleop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by turtlebot on 02/03/15.
 */
public class ExtendingView extends View {

    List<Point3> spots = new ArrayList<Point3>();
    Paint myColor;

    public ExtendingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        Paint myColor = new Paint();
        myColor.setColor(Color.BLACK);
        myColor.setStyle((Paint.Style.FILL_AND_STROKE));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Point3 point : spots) {
            canvas.drawCircle(point.x,point.y,5,myColor);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Point3 point = new Point3();
        point.x = event.getX(event.getActionIndex());
        point.y = event.getY(event.getActionIndex());
        spots.add(point);
        invalidate();

        return true;
    }
}


class Point3 {

    float x, y;
}