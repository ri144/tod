/*
 * Copyright (C) 2013 OSRF.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.rosjava.android_apps.teleop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.rosjava.android_apps.application_management.RosAppActivity;

import org.ros.android.MessageCallable;
import org.ros.android.view.RosImageView;
import org.ros.namespace.NameResolver;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
import org.ros.address.InetAddressFactory;
import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.view.VirtualJoystickView;
import org.ros.android.view.RosTextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import geometry_msgs.Pose;
import geometry_msgs.Twist;
import std_msgs.String;
import std_msgs.*;


/**
 * @author murase@jsk.imi.i.u-tokyo.ac.jp (Kazuto Murase)
 */
public class PoseTest extends RosAppActivity{

    //private RosImageView<sensor_msgs.CompressedImage> cameraView;
    private VirtualJoystickView virtualJoystickView;
    private Button backButton;
    //private DrawView drawView;
    //private static final java.lang.String virtualJoystickTopic = "android/virtual_joystick/cmd_vel";

    private static final java.lang.String virtualJoystickTopic = "/cmd_vel_mux/input/teleop";

    private static final java.lang.String odomTopic = "/odom";
    private static final java.lang.String cameraTopic = "camera/rgb/image_color/compressed_throttle";
    private TextView tvInfo;
    List<Float> arrayX = new ArrayList<Float>();
    List<Float> arrayY = new ArrayList<Float>();
    MultiTouchView pic;
    private RosTextView<geometry_msgs.Twist> rosTextView,rosTexty,textTheta, linez,angux,anguy,anguz,quater;
    private RosTextView<Pose> linex, liney;
    java.lang.String vel, thetav,linx,liny,linz,angx,angy,angz,quat;
    int counter;
    java.lang.String previous, previous2;

    public PoseTest() {
        // The RosActivity constructor configures the notification title and
        // ticker
        // messages.
        super("android teleop", "android teleop");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        java.lang.String defaultRobotName = getString(R.string.default_robot);
        java.lang.String defaultAppName = getString(R.string.default_app);
        setDefaultRobotName(defaultRobotName);
        setDefaultAppName(defaultAppName);
        setDashboardResource(R.id.top_bar);
        setMainWindowResource(R.layout.main);
        super.onCreate(savedInstanceState);
/*		cameraView = (RosImageView<sensor_msgs.CompressedImage>) findViewById(R.id.image);
		cameraView.setMessageType(sensor_msgs.CompressedImage._TYPE);
	    cameraView.setMessageToBitmapCallable(new BitmapFromCompressedImage());*/
		/*Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);*/
        //width = pic.getLayoutParams().width;              //size.x;
        //height = pic.getLayoutParams().height;            //size.y;
        pic = (MultiTouchView) findViewById(R.id.image);
        virtualJoystickView = (VirtualJoystickView) findViewById(R.id.virtual_joystick);
        backButton = (Button) findViewById(R.id.back_button);
        //tvInfo = (TextView) findViewById(R.id.tvInfo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        super.init(nodeMainExecutor);

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(
                InetAddressFactory.newNonLoopback().getHostAddress(),
                getMasterUri());

        NameResolver appNameSpace = getAppNameSpace();
        //cameraView.setTopicName(getAppNameSpace().resolve(cameraTopic).toString());
        virtualJoystickView.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        virtualJoystickView.setTopicName((virtualJoystickTopic).toString());
        rosTextView = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextx);
        rosTexty = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostexty);
        textTheta = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextTheta);
        linex = (RosTextView<Pose>) findViewById(R.id.rostextPX);
        liney = (RosTextView<Pose>) findViewById(R.id.rostextPY);
        linex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                counter+=1;
                if (counter%30==0) {
                    update();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        liney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                counter+=1;
                if (counter%30==0) {
                    update();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //rosTextView.setText(width);
        //rosTexty.setText(height);
        rosTextView.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        rosTexty.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        textTheta.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        linex.setTopicName(getAppNameSpace().resolve(odomTopic).toString());
        liney.setTopicName(getAppNameSpace().resolve(odomTopic).toString());
        /*linez.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        angux.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());;
        anguy.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        anguz.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        quater.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
*/
        rosTextView.setMessageType(geometry_msgs.Twist._TYPE);
        textTheta.setMessageType(geometry_msgs.Twist._TYPE);

        rosTexty.setMessageType(geometry_msgs.Twist._TYPE);
        linex.setMessageType(Pose._TYPE);
        liney.setMessageType(Pose._TYPE);
  /*      linez.setMessageType(geometry_msgs.Twist._TYPE);
        angux.setMessageType(geometry_msgs.Twist._TYPE);
        anguy.setMessageType(geometry_msgs.Twist._TYPE);
        anguz.setMessageType(geometry_msgs.Twist._TYPE);
        quater.setMessageType(geometry_msgs.Twist._TYPE);
*/
        rosTextView.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getLinear().getX();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        rosTexty.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getLinear().getY();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        textTheta.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getAngular().getZ();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });

        linex.setMessageToStringCallable(new MessageCallable<java.lang.String, Pose>() {
            @Override
            public java.lang.String call(Pose message) {
                double messageV = message.getPosition().getX();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        liney.setMessageToStringCallable(new MessageCallable<java.lang.String, Pose>() {
            @Override
            public java.lang.String call(Pose message) {
                double messageV = message.getPosition().getY();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });


  /*      linez.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getAngular().getZ();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        angux.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getAngular().getZ();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        anguy.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getAngular().getZ();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        anguz.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getAngular().getZ();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        quater.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getAngular().getZ();
                NumberFormat form = new DecimalFormat("#0.00000");
                return java.lang.String.valueOf(form.format(messageV));
            }
        });
        vel = rosTextView.getText().toString();
        thetav = textTheta.getText().toString();
        linx = linex.getText().toString();
        liny = liney.getText().toString();
        linz = linez.getText().toString();
        angx = angux.getText().toString();
        angy = anguy.getText().toString();
        angz = anguz.getText().toString();
        quat = quater.getText().toString();
*/
        nodeMainExecutor.execute(rosTextView, nodeConfiguration.setNodeName("android/textx"));
        nodeMainExecutor.execute(rosTexty, nodeConfiguration.setNodeName("android/texty"));
        nodeMainExecutor.execute(textTheta, nodeConfiguration.setNodeName("android/textT"));
        nodeMainExecutor.execute(linex, nodeConfiguration.setNodeName("android/posX"));
        //nodeMainExecutor.execute(cameraView, nodeConfiguration
        //		.setNodeName("android/camera_view"));
        nodeMainExecutor.execute(virtualJoystickView,
                nodeConfiguration.setNodeName("android/virtual_joystick"));

        nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory
                .newNonLoopback().getHostAddress(), getMasterUri());
        /*while(true){
            update();
        }*/
    }

    private void update() {
        double number = Double.parseDouble(linex.getText().toString()) / (0.1);
        double number2 = Double.parseDouble(liney.getText().toString()) / (0.1);
        Float numberf = (float) number;
        Float number2f = (float) number2;
        arrayX.add(numberf);
        arrayY.add(number2f);
        pic.paintSomething((arrayX.get(arrayX.size()-1)),arrayY.get(arrayY.size()-1));
        previous = arrayX.get(arrayX.size()-1).toString();
        previous2 = arrayY.get(arrayY.size()-1).toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,0,0,R.string.stop_app);
        menu.add(0,1,0,"Odometry");
        //menu.add(0,2,0,"Testerooni");
        menu.add(0,3,0,"PaintStuff");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case 0:
                onDestroy();
                break;
            case 1:
                try {
                    @SuppressWarnings("rawtypes")
                    Intent intent = new Intent(PoseTest.this, com.github.rosjava.android_apps.teleop.Odometry.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("velocity",vel);
                    bundle.putString("theta",thetav);
                    bundle.putString("linearx",linx);
                    bundle.putString("lineary",liny);
                    bundle.putString("linearz",linz);
                    bundle.putString("angx",angx);
                    bundle.putString("angy",angy);
                    bundle.putString("angz",angz);
                    bundle.putString("quat",quat);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (ClassCastException e){
                    e.printStackTrace();
                }
                //case 2:
                //  Intent intent = new Intent(MainActivity.this, com.github.rosjava.android_apps.teleop.Test.class);
                //startActivity(intent);
            case 3:
                Intent intent3 =new Intent(PoseTest.this, com.github.rosjava.android_apps.teleop.DrawingView.class);
                startActivity(intent3);
        }

        return true;
    }



}
