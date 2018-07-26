package com.codingdemos.tablayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.codingdemos.tablayout.Model.Product;
import com.codingdemos.tablayout.Retrofit.GetRetrofit;
import com.codingdemos.tablayout.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    List<Data> data;
    Context context;
    Dialog dialog;
    private static final int REQUEST_CALL = 1;
    public DataAdapter(List<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, final int position) {

        holder.ItemName.setText(data.get(position).getItemName());
        holder.tvFault.setText(data.get(position).getFault());
        //holder.productImage.setImageResource(data.get(position).getId());
        Picasso.with(context).load(data.get(position).getImage())
                .resize(250, 250)
                .into(holder.ivOwnImage);

        holder.recyclerViewList.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                final String prodName,prodFault,prodBrand,ownerName,ownerPhone,ownerLocation;
                int prodImg;
                final Button btnBeforeDone,btnAfterDone;
                prodName= data.get(position).getBrandName();
                prodFault= data.get(position).getFault();
                prodBrand= data.get(position).getBrandName();


                //ownerName= data.get(position).getBrandName();
                //ownerPhone= data.get(position).;
                //ownerLocation= data.get(position).getGetpOwnerLocation();
                //prodImg= data.get(position).getpImage();
final TextView brand,name,fault,ownName,ownPhone,ownLoacation;

                dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialogue);

                fault = (TextView) dialog.findViewById(R.id.pFault);
                ownName = (TextView) dialog.findViewById(R.id.pOwnerName);
                ownPhone = (TextView) dialog.findViewById(R.id.pOwnerPhone);
                ownLoacation = (TextView) dialog.findViewById(R.id.pOwnerLocation);
                btnBeforeDone =  dialog.findViewById(R.id.btnBeforeDone);
                btnAfterDone =  dialog.findViewById(R.id.btnAfterDone);

                fault.setText("Product faults: "+Drawer.fault);
                ownName.setText("Product owner name: "+Drawer.own_name);
                ownPhone.setText("Product owner phone: "+Drawer.own_mobile);
                ownLoacation.setText("Product owner location: "+Drawer.own_location);


                dialog.show();
                btnAfterDone.setVisibility(View.GONE);

                btnBeforeDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnBeforeDone.setVisibility(View.GONE);
                        btnAfterDone.setVisibility(View.VISIBLE);
                    }
                });
                btnAfterDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendNotify sendNotify=new SendNotify(1,4,"abc.jpd","abbb");
                        //Toast.makeText(context, "Done Work", Toast.LENGTH_SHORT).show();
                        RetrofitClient apiInterface = GetRetrofit.getInstance().create(RetrofitClient.class);
                        Call<SendNotify> call = apiInterface.postNotify(sendNotify);
                        call.enqueue(new Callback<SendNotify>() {
                            @Override
                            public void onResponse(Call<SendNotify> call, Response<SendNotify> response) {
                                //Toast.makeText(context, "Request successfully submitted", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<SendNotify> call, Throwable t) {
                                //Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show();


                            }
                        });


                    }
                });






            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ItemName,Category,tvFault;
        ImageView ivOwnImage;
        LinearLayout recyclerViewList;


        public ViewHolder(View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.ItemName);
            Category = itemView.findViewById(R.id.Category);
            tvFault = itemView.findViewById(R.id.tvFault);
            ivOwnImage = itemView.findViewById(R.id.ivOwnImage);
            recyclerViewList=itemView.findViewById(R.id.recyclerViewList);

        }
    }
}
