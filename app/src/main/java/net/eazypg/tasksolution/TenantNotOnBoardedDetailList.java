package net.eazypg.tasksolution;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TenantNotOnBoardedDetailList extends RecyclerView.Adapter<TenantNotOnBoardedDetailList.MyHolder> {

    ArrayList<TenantDetails> tenantDetailsArrayList;


    public TenantNotOnBoardedDetailList(ArrayList<TenantDetails> tenantDetailsArrayList) {

        this.tenantDetailsArrayList = tenantDetailsArrayList;

        for (TenantDetails tenantDetails : tenantDetailsArrayList) {

            Log.e("Name", tenantDetails.name);
        }

    }

    @NonNull
    @Override
    public TenantNotOnBoardedDetailList.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tenant_row, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TenantNotOnBoardedDetailList.MyHolder holder, final int position) {

        TenantDetails tenantDetails = tenantDetailsArrayList.get(position);

        holder.nameTextView.setText(tenantDetails.name);
        holder.phoneTextView.setText(tenantDetails.phone);
        holder.roomTextView.setText(tenantDetails.room);
        holder.rentTextView.setText(tenantDetails.rent);

    }

    @Override
    public int getItemCount() {
        return tenantDetailsArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView nameTextView, phoneTextView, roomTextView, rentTextView;

        public MyHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            roomTextView = itemView.findViewById(R.id.roomTextView);
            rentTextView = itemView.findViewById(R.id.rentTextView);

        }
    }
}
