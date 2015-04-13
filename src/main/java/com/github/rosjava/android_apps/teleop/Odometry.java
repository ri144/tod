package com.github.rosjava.android_apps.teleop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.github.rosjava.android_apps.application_management.RosAppActivity;

import org.ros.android.view.RosTextView;

/**
 * Created by turtlebot on 12/01/15.
 */
public class Odometry extends RosAppActivity {

    RosTextView<geometry_msgs.Twist> rosTextView, textTheta, linearx, lineary, linearz, angx, angy, angz, quat;

    public Odometry(){
        super("teleop odom","teleop odom");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.odom);
        rosTextView = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextx2);
        textTheta= (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextTheta2);
        linearx= (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rosposex);
        lineary= (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rosposey);
        linearz= (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rosposez);
        angx= (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rosposetx);
        angy= (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rosposety);
        angz= (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rosposetz);
        quat = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rosposeq);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        String string =bundle.getString("velocity");
        rosTextView.setText(string);
        String string2 =bundle.getString("theta");
        String string3 =bundle.getString("linearx");
        String string4 =bundle.getString("lineary");
        String string5 =bundle.getString("linearz");
        String string6 =bundle.getString("angx");
        String string7 =bundle.getString("angy");
        String string8 =bundle.getString("angz");
        String string9 =bundle.getString("quat");
        textTheta.setText(string2);
        linearx.setText(string3);
        lineary.setText(string4);
        linearz.setText(string5);
        angx.setText(string6);
        angy.setText(string7);
        angz.setText(string8);
        quat.setText(string9);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,0,0,R.string.stop_app);
        menu.add(0,1,0,"Main Activity");
        menu.add(0,2,0,"Testerooni");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case 0:
                onDestroy();
                break;
            case 1:
                Intent intent = new Intent(Odometry.this, MainActivity.class);
                startActivity(intent);
            case 2:
                Intent intent2 = new Intent(Odometry.this, com.github.rosjava.android_apps.teleop.Test.class);
                startActivity(intent2);
        }

        return true;
    }

}
