package net.eazypg.tasksolution;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TenantOnBoardedDetailList extends RecyclerView.Adapter<TenantOnBoardedDetailList.TenantHolder> {


     ArrayList<TenantDetails> tenantOnBoardedDetailsArrayList;


    public TenantOnBoardedDetailList(ArrayList<TenantDetails> tenantDetailsArrayList) {

        this.tenantOnBoardedDetailsArrayList = tenantDetailsArrayList;

        for (TenantDetails tenantDetails : tenantDetailsArrayList) {

            Log.e("OnBoarded Name", tenantDetails.name);
        }
    }

    @NonNull
    @Override
    public TenantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tenant_row, parent, false);

        return new TenantOnBoardedDetailList.TenantHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TenantHolder holder, int position) {

        TenantDetails tenantDetails = tenantOnBoardedDetailsArrayList.get(position);

        holder.nameTextView.setText(tenantDetails.name);
        holder.phoneTextView.setText(tenantDetails.phone);
        holder.roomTextView.setText(tenantDetails.room);
        holder.rentTextView.setText(tenantDetails.rent);

        Log.e("OnBoarded Adapter" , holder.nameTextView.getText().toString());

    }


    @Override
    public int getItemCount() {
        return tenantOnBoardedDetailsArrayList.size();
    }


    public class TenantHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, phoneTextView, roomTextView, rentTextView;

        public TenantHolder(@NonNull View itemView) {

            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            roomTextView = itemView.findViewById(R.id.roomTextView);
            rentTextView = itemView.findViewById(R.id.rentTextView);


        }
    }
}
