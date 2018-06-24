package com.codingdemos.tablayout;

import android.Manifest;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.codingdemos.tablayout.Model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> products;
    Context context;
    Dialog dialog;
    private static final int REQUEST_CALL = 1;
    public ProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, final int position) {

        holder.productName.setText(products.get(position).getpName());
        holder.productFault.setText(products.get(position).getpFault());
        holder.productImage.setImageResource(products.get(position).getpImage());


        holder.recyclerViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String prodName,prodFault,prodBrand,ownerName,ownerPhone,ownerLocation;
                int prodImg;
                ImageView call,msg;
                prodName= products.get(position).getpName();
                prodFault= products.get(position).getpFault();
                prodBrand= products.get(position).getpBrand();
                ownerName= products.get(position).getpOwnerName();
                ownerPhone= products.get(position).getGetpOwnerPhone();
                ownerLocation= products.get(position).getGetpOwnerLocation();
                prodImg= products.get(position).getpImage();
final TextView brand,name,fault,ownName,ownPhone,ownLoacation;

                dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialogue);

                fault = (TextView) dialog.findViewById(R.id.pFault);
                ownName = (TextView) dialog.findViewById(R.id.pOwnerName);
                ownPhone = (TextView) dialog.findViewById(R.id.pOwnerPhone);
                ownLoacation = (TextView) dialog.findViewById(R.id.pOwnerLocation);
                call =  dialog.findViewById(R.id.call);
                msg =  dialog.findViewById(R.id.msg);

                fault.setText("Product faults: "+prodFault);
                ownName.setText("Product owner name: "+ownerName);
                ownPhone.setText("Product owner phone: "+ownerPhone);
                ownLoacation.setText("Product owner location: "+ownerLocation);


                dialog.show();

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(ownerPhone.trim().length() > 0) {
                            if (ContextCompat.checkSelfPermission(context,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) context,
                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                            } else {
                                String dial = "tel:" + ownerPhone;
                                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                            }

                        }
                    }

                });


                msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", ""+ownerPhone, null));
                        intent.putExtra("sms body", "hello baby.....");
                        context.startActivity(intent);
                    }
                });




            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,productBrand,productFault;
        ImageView productImage;
        LinearLayout recyclerViewList;


        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.pImage);
            productName = itemView.findViewById(R.id.pName);
            productBrand = itemView.findViewById(R.id.pBrand);
            productFault = itemView.findViewById(R.id.pFault);
            recyclerViewList=itemView.findViewById(R.id.recyclerViewList);

        }
    }
}
