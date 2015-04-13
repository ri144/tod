package com.github.rosjava.android_apps.teleop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

/**
 * Created by turtlebot on 02/03/15.
 */
public class AnotherTest extends Activity {

    Button t;
    ExtendingView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}

