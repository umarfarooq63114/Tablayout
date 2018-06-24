package com.codingdemos.tablayout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Switch statusSwitch;
    TextView tvStatus;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view= inflater.inflate(R.layout.fragment_status, container, false);
        statusSwitch=(Switch) view.findViewById(R.id.statusSwitch);
        tvStatus=view.findViewById(R.id.tvStatus);
        statusSwitch.setOnCheckedChangeListener(this);
        return view;


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
