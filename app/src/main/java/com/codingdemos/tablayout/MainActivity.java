//package com.codingdemos.tablayout;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.support.design.widget.TabItem;
//import android.support.design.widget.TabLayout;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.pusher.client.Pusher;
//import com.pusher.client.PusherOptions;
//import com.pusher.client.channel.Channel;
//import com.pusher.client.channel.SubscriptionEventListener;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String TAG = "MTAG";
//    Toolbar toolbar;
//    TabLayout tabLayout;
//    ViewPager viewPager;
//    PageAdapter pageAdapter;
//    TabItem tabChats;
//    TabItem tabStatus;
//    TabItem tabCalls;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(getResources().getString(R.string.app_name));
//        setSupportActionBar(toolbar);
//
//        tabLayout = findViewById(R.id.tablayout);
//        tabChats = findViewById(R.id.tabChats);
//        tabStatus = findViewById(R.id.tabStatus);
//
//        viewPager = findViewById(R.id.viewPager);
//
//        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pageAdapter);
//
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 1) {
//
//                } else if (tab.getPosition() == 2)
//
//                {
//
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        PusherOptions options = new PusherOptions();
//        options.setCluster("ap2");
//        Pusher pusher = new Pusher("d994b17cd676131c6cb2", options);
//
//        Channel channel = pusher.subscribe("sFinder.435");
//
//        channel.bind("info", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                Log.d(TAG,data);
//            }
//        });
//
//        pusher.connect();
//
//    }
//}

package com.codingdemos.tablayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.codingdemos.tablayout.Model.Notificaton;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MTAG";
    Toolbar toolbar;
    TabLayout tabLayout;
    private List<Notificaton> notif;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabChats;
    Context context;
    TabItem tabStatus;
    TabItem tabCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notif=new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tablayout);
        tabChats = findViewById(R.id.tabChats);
        tabStatus = findViewById(R.id.tabStatus);

        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {

                } else if (tab.getPosition() == 2)

                {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("d994b17cd676131c6cb2", options);

        final Channel channel = pusher.subscribe("sFinder");
        Log.d(TAG, "test");
        channel.bind("info", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.d(TAG,data.toString());
                Gson gson = new Gson();

String json="{\"data\":{ \"id\":\"44\",\"brand_name\":\"s9\",\"item_name\":\"samsung\",\"fault\":\"screen\",\"technician_id\":\"1\"," +
        "\"dateOfBooking\":\"2018-07-26\",\"timeOfBooking\":\"08:06:15\",\"created_at\":\"2018-07-20 09:19:00\"," +
        "\"updated_at\":\"2018-07-20 09:19:00\"} }";







                // Serializing Json to Respective POJO
                Notificaton message = gson.fromJson(data,Notificaton.class);
                //notif.add(new Notificaton(noti));
                Intent i = new Intent(MainActivity.this, Notification.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, "Channel1")
                        .setContentTitle(message.getFault())
                        .setContentText(message.getBrand_name())
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                        .setSmallIcon(R.drawable.error)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(101, notification.build());



//Notificaton notificaton=new Notificaton();
                //Toast.makeText(MainActivity.this, "abc: "+noti.getTechnician_id().toString(), Toast.LENGTH_SHORT).show();
                //Eventbus code for posting data to set in views
                //EventBus.getDefault().post(message);*/





            }
        });

        pusher.connect();

    }
}
