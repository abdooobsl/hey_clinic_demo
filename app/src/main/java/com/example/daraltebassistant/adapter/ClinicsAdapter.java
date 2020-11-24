package com.example.daraltebassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.daraltebassistant.R;
import com.example.daraltebassistant.model.Clinicinfo;

public class ClinicsAdapter extends RecyclerView.Adapter<ClinicsAdapter.clinicinfoViewHolder> {
    private List<Clinicinfo> newclinicinfoList = new ArrayList<>();
    onItemClickListener listener;
    Context context;

    public ClinicsAdapter(List<Clinicinfo> clinicinfoes, Context context) {
        this.newclinicinfoList = clinicinfoes;
        this.context = context;
    }

    public void updateList(List<Clinicinfo> clinicinfoList) {
        newclinicinfoList.clear();
        newclinicinfoList.addAll(clinicinfoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public clinicinfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new clinicinfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull clinicinfoViewHolder holder, int position) {


        if (context != null) {
            //Log.d("tag","http://heyaclinic.brandoctor.net/final/uploads/business/"+newclinicinfoList.get(position).bus_logo);
            Glide.with(context)
                    .load("http://heyaclinic.brandoctor.net/final/uploads/business/"+newclinicinfoList.get(position).bus_logo)
                    .into(holder.imageView);
        }
        holder.doct_name.setText(newclinicinfoList.get(position).doct_name);
        holder.bus_title.setText(newclinicinfoList.get(position).bus_title);
    }

    @Override
    public int getItemCount() {
        return newclinicinfoList.size();
    }

    public void setList(List<Clinicinfo> clinicinfoList) {
        this.newclinicinfoList = clinicinfoList;
        notifyDataSetChanged();
    }

    public class clinicinfoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView doct_name;
        TextView bus_title;
        public clinicinfoViewHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.clinic_image);
             doct_name = itemView.findViewById(R.id.clinic_doct_name);
             bus_title = itemView.findViewById(R.id.clinic_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(newclinicinfoList.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Clinicinfo item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
