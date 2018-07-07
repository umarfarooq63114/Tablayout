package com.codingdemos.tablayout;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codingdemos.tablayout.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    Button not;
    View view;
    private List<Product> own;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view= inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.recyclerView);
        not= view.findViewById(R.id.not);
        //sendNotification();
        productAdapter = new ProductAdapter( own,getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimpleNotification();
            }
        });
        recyclerView.setAdapter(productAdapter);
        return  view;
    }


    private PendingIntent pendingIntentForNotification() {
        //Create the intent you want to show when the notification is clicked
        Intent intent = new Intent(getActivity(), MainActivity.class);

        //Add any extras (in this case, that you want to relaunch this fragment)
        //intent.putExtra(MainActivity.EXTRA_FRAGMENT_TO_LAUNCH, MainActivity.TAG_NOTIFICATION_FRAGMENT);

        //This will hold the intent you've created until the notification is tapped.
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, intent, 0);
        return pendingIntent;
    }
    private void showSimpleNotification() {
        //Use the NotificationCompat compatibility library in order to get gingerbread support.
        Notification notification = new NotificationCompat.Builder(getActivity())
                //Title of the notification
                .setContentTitle("ABC")
                //Content of the notification once opened
                .setContentText("ABC")
                //This bit will show up in the notification area in devices that support that
                .setTicker("ABC")
                //Icon that shows up in the notification area
                .setSmallIcon(R.drawable.calendar)
                //Icon that shows up in the drawer
                .setLargeIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher))
                //Set the intent
                .setContentIntent(pendingIntentForNotification())
                //Build the notification with all the stuff you've just set.
                .build();

        //Add the auto-cancel flag to make it dismiss when clicked on
        //This is a bitmask value so you have to pipe-equals it.
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //Grab the NotificationManager and post the notification
        NotificationManager notificationManager = (NotificationManager)
                getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        //Set a tag so that the same notification doesn't get reposted over and over again and
        //you can grab it again later if you need to.
        notificationManager.notify(TAG_SIMPLE_NOTIFICATION, notification);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pic1 = R.drawable.pix;
        own = new ArrayList<>();
        String name = null, phone = null;
        float ratingbar = (float) 1.5;

        for (int i = 1; i <= 5; i++) {
            own.add(new Product("SAMSUNG "+i, "MOBILE", pic1,
                    "Screen and microphone does not working and battery issue",
                    "Umar Farooq","03104187789","lahore"));
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chats, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_chat) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }
}
