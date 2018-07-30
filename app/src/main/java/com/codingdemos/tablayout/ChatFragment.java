package com.codingdemos.tablayout;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codingdemos.tablayout.Model.Notificaton;
import com.codingdemos.tablayout.Model.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.codingdemos.tablayout.Drawer.listCreate;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    Button not;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Data> own;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //sendNotification();


        swipeRefreshLayout=view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResfreshData();
                removeSingleItem();
                dataInitilization();
                dataAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },500);
            }
        });

        onResfreshData();
        return view;
    }








    public void onResfreshData()
    {
        dataAdapter = new DataAdapter(own, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(dataAdapter);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pic1 = R.drawable.pix;
        own = new ArrayList<>();

        String name = null, phone = null;
        float ratingbar = (float) 1.5;
        if(listCreate==1) {
            dataInitilization();
        }

    }


public void dataInitilization()
{
    own.add(new Data(Drawer.id, "" + Drawer.brand_name, "" + Drawer.item_name, "" + Drawer.fault,
            "" + Drawer.own_name, "" + Drawer.own_mobile, "" + Drawer.own_location,
            "" + Drawer.own_image, Drawer.tech_id,
            "" + Drawer.dateOfBooking, "" + Drawer.timeOfBooking));
}


    private void removeSingleItem() {
        int removeIndex = 0;
        own.remove(removeIndex);
        dataAdapter.notifyItemRemoved(removeIndex);
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
