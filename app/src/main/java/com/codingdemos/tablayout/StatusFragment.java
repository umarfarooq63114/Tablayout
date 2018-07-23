package com.codingdemos.tablayout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.codingdemos.tablayout.Model.Technician;
import com.codingdemos.tablayout.Retrofit.GetRetrofit;
import com.codingdemos.tablayout.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codingdemos.tablayout.Drawer.*;
import static com.codingdemos.tablayout.Drawer.userEmaiId;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Switch statusSwitch;
    RatingBar ratingBar;
    TextView tvStatus;
    Button buttonDone;
    private RetrofitClient apiInterface;
    double rat;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view= inflater.inflate(R.layout.fragment_status, container, false);

        statusSwitch=(Switch) view.findViewById(R.id.statusSwitch);
        tvStatus=view.findViewById(R.id.tvStatus);
        buttonDone=view.findViewById(R.id.btnDone);
        ratingBar=view.findViewById(R.id.ratingBar);
        statusSwitch.setOnCheckedChangeListener(this);
        Boolean state;//=Boolean.parseBoolean(String.valueOf(technicianStatus));
        int x=technicianStatus;
         state = (technicianStatus != 0);


        Toast.makeText(getActivity(), "Welcome ", Toast.LENGTH_SHORT).show();
            statusSwitch.setChecked(state);
         Toast.makeText(getActivity(), "switch value "+state, Toast.LENGTH_SHORT).show();
        /*if(technicianStatus.toString().equals(1))
        {
            statusSwitch.setChecked(Boolean.parseBoolean(String.valueOf(1)));
        }
else
        {
            //statusSwitch.setChecked(Boolean.parseBoolean(String.valueOf(0)));
        }*/






        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTechnician();
                Toast.makeText(getActivity(), "Send notification to user for feedback", Toast.LENGTH_SHORT).show();
            }
        });



        return view;




    }
    public void updateTechnician() {
        apiInterface = GetRetrofit.getInstance().create(RetrofitClient.class);
        Call<List<Technician>> cal = apiInterface.getTechniciansList();
        RetrofitClient apiInterface = GetRetrofit.getInstance().create(RetrofitClient.class);
        cal.enqueue(new Callback<List<Technician>>() {
            @Override
            public void onResponse(Call<List<Technician>> call, Response<List<Technician>> response) {
                List<Technician> list = response.body();
                if (response.isSuccessful()) {

                    for (int i = 1; i < list.size(); i++) {
                        if (userEmaiId.equals(list.get(i).getEmail())) {
                            rat = list.get(i).getRating();

                            break;
                        }

                    }
                    ratingBar.setRating((float) rat);


                    Toast.makeText(getActivity(), "connection successfull", Toast.LENGTH_SHORT).show();
                    Log.d("MTAG", "onResponse: is successfully: " + response.body());


                }

            }

            @Override
            public void onFailure(Call<List<Technician>> call, Throwable t) {
                Log.d("MTAG", "No Internet Connection " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        if(statusSwitch.isChecked())
        {
            tvStatus.setText("ON");
        }
        else{
            tvStatus.setText("OFF");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_status, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_status) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }
}
