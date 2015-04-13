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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.rosjava.android_apps.application_management.RosAppActivity;

import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.namespace.NameResolver;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
import org.ros.address.InetAddressFactory;
import org.ros.android.view.RosTextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import geometry_msgs.Twist;
import std_msgs.String;
import std_msgs.*;


/**
 * @author murase@jsk.imi.i.u-tokyo.ac.jp (Kazuto Murase)
 */

public class Odom extends RosAppActivity {

    private Button backButton;
    private static final java.lang.String virtualJoystickTopic = "android/virtual_joystick/cmd_vel";
    private RosTextView<geometry_msgs.Twist> rosTextView, textTheta, linearx, lineary, linearz, angx, angy, angz, quat;

    public Odom() {
        // The RosActivity constructor configures the notification title and
        // ticker
        // messages.
        super("odometry", "odometry");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        java.lang.String defaultRobotName = getString(R.string.default_robot);
        java.lang.String defaultAppName = getString(R.string.default_app);
        setDefaultRobotName(defaultRobotName);
        setDefaultAppName(defaultAppName);
        setDashboardResource(R.id.top_bar2);
        setMainWindowResource(R.layout.odom);
        super.onCreate(savedInstanceState);
        backButton = (Button) findViewById(R.id.back_button2);
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

        rosTextView = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextx2);
        textTheta = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextTheta2);
        rosTextView.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        textTheta.setTopicName(getAppNameSpace().resolve(virtualJoystickTopic).toString());
        rosTextView.setMessageType(geometry_msgs.Twist._TYPE);
        textTheta.setMessageType(geometry_msgs.Twist._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
            @Override
            public java.lang.String call(Twist message) {
                double messageV = message.getLinear().getX();
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
        nodeMainExecutor.execute(rosTextView, nodeConfiguration.setNodeName("android/textx"));
        nodeMainExecutor.execute(textTheta, nodeConfiguration.setNodeName("android/textT"));

        nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory
                .newNonLoopback().getHostAddress(), getMasterUri());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,0,0,R.string.stop_app);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case 0:
                onDestroy();
                break;
        }
        return true;
    }

}