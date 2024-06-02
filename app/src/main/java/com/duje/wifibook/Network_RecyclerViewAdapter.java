package com.duje.wifibook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Network_RecyclerViewAdapter extends RecyclerView.Adapter<Network_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<NetworkModel> networkModels;

    public Network_RecyclerViewAdapter(Context context, ArrayList<NetworkModel> networkModels){
        this.context = context;
        this.networkModels = networkModels;
    }

    @NonNull
    @Override
    public Network_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout (giving the look to each row)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Network_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Network_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to each of the rows coming back to the screen
        // grab from database
        //holder.tvSSID.setText();

        holder.tvSSID.setText(networkModels.get(position).getSSID());
        holder.tvType.setText(networkModels.get(position).getSecurityType());
        holder.tvPassword.setText(networkModels.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return networkModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing all the views from recycler_view_row.xml and assigning them as variables

        TextView tvSSID, tvType, tvPassword;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSSID = itemView.findViewById(R.id.tvSSID);
            tvType = itemView.findViewById(R.id.tvType);
            tvPassword = itemView.findViewById(R.id.tvPassword);
        }
    }
}
