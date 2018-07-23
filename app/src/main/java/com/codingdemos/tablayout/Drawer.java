package com.codingdemos.tablayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingdemos.tablayout.Activities.MainActivity;
import com.codingdemos.tablayout.Model.Notificaton;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    TabLayout tabLayout;
    TextView userName;
    ViewPager viewPager;
    public static String userEmaiId;
    public static String technicianName;
    public static String technicianPhoto;
    public static int technicianStatus;

    private static final String TAG = "MTAG";
    PageAdapter pageAdapter;
    TabItem tabChats;
    TabItem tabStatus;
    TabItem tabCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            userEmaiId = intent.getStringExtra("email");
            technicianName = intent.getStringExtra("user_name");
            technicianPhoto = intent.getStringExtra("user_image");
            technicianStatus = intent.getIntExtra("user_status",0);


        }
        Toast.makeText(this, "Login user is " + userEmaiId + "user name: " + technicianName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "User status"+technicianStatus, Toast.LENGTH_LONG).show();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tablayout);
        tabChats = findViewById(R.id.tabChats);
        tabStatus = findViewById(R.id.tabStatus);
        userName = findViewById(R.id.uName);
        //userName.setText("abc");
        viewPager = findViewById(R.id.viewPager);

        //Set image and name at drawer

        NavigationView navigationV = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationV.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.uName);

        navUsername.setText(technicianName);

        ImageView technicianImage = (ImageView) headerView.findViewById(R.id.techImg);
        Picasso.with(Drawer.this).load(technicianPhoto)
                .resize(100, 100)
                .into(technicianImage);


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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("d994b17cd676131c6cb2", options);

        final Channel channel = pusher.subscribe("sFinder");
        Log.d(TAG, "test");
        channel.bind("info", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.d(TAG,data);
                Gson gson = new Gson();

                String json="{\"id\":\"44\",\"brand_name\":\"s9\",\"item_name\":\"samsung\",\"fault\":\"screen\",\"technician_id\":\"1\"," +
                        "\"dateOfBooking\":\"2018-07-26\",\"timeOfBooking\":\"08:06:15\",\"created_at\":\"2018-07-20 09:19:00\"," +
                        "\"updated_at\":\"2018-07-20 09:19:00\"}";







                // Serializing Json to Respective POJO
                Notificaton message = gson.fromJson(json,Notificaton.class);
                //notif.add(new Notificaton(noti));
                Intent i = new Intent(Drawer.this, Notification.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(Drawer.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notification = new NotificationCompat.Builder(Drawer.this, "Channel1")
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
                EventBus.getDefault().post(message);





            }
        });

        pusher.connect();










    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Drawer.this);
            builder.setMessage("Are you sure to quit?");
            builder.setCancelable(true);
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(Drawer.this, Drawer.class));
        } /*else if (id == R.id.user_image) {
            Picasso.with(Drawer.this).load(imagage)
                    .resize(100, 100)
                    .into(imageView);
        } else if (id == R.id.nav_technician) {
            startActivity(new Intent(Drawer.this, .class));
        } */ else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_logout) {
            // If savedinstnacestate is null then replace login fragment
            SharedPreferences sharedPreferences = getSharedPreferences("My", MODE_PRIVATE);
            //sharedPreferences.edit().putString("user",getIntent().getStringExtra("user")).clear();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(new Intent(Drawer.this, MainActivity.class));
               /* fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainer, new Login_Fragment(),
                                Utils.Login_Fragment).commit();*/
            //Toast.makeText(this, "Hi baby", Toast.LENGTH_SHORT).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
