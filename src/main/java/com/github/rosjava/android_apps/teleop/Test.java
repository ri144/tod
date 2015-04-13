package com.github.rosjava.android_apps.teleop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by turtlebot on 11/12/14.
 */
public class Test extends RelativeLayout implements  View.OnTouchListener{

    TextView hi;
    private RelativeLayout mainLayout;
    DrawView dView;
    Paint myColor = new Paint();
    List<Point2> spots = new ArrayList<Point2>();

    public Test(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        initTest(context);
    }

    public Test(Context context, AttributeSet attrs){
        super(context,attrs);
        initTest(context);
    }

    public Test(Context context){
        super(context);
        initTest(context);
    }

    private void initTest(Context context){
        LayoutInflater.from(context).inflate(R.layout.test,this,true);
        mainLayout = (RelativeLayout) findViewById(R.id.test_layout);
        mainLayout.setBackgroundColor(Color.WHITE);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        myColor.setColor(Color.BLACK);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Point2 point : spots) {
            canvas.drawCircle(point.x,point.y,5,myColor);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Point2 point = new Point2();
        point.x = event.getX();
        point.y = event.getY();
        spots.add(point);
        invalidate();

        return true;
    }

}

class NewView extends View implements View.OnTouchListener {
    List<Point2> points = new ArrayList<Point2>();
    Paint paint = new Paint();

    public NewView(Context context ){
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Point2 point : points) {
            canvas.drawCircle(point.x,point.y,2,paint);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Point2 point = new Point2();
        point.x = event.getX();
        point.y = event.getY();
        points.add(point);
        invalidate();
        return true;
    }
}

class Point2 {

    float x, y;
}