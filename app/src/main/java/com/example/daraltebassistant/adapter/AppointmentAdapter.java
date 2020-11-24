package com.example.daraltebassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.model.Appointment;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.appointmentViewHolder> {
    private List<Appointment> newappointmentList = new ArrayList<>();
    onItemClickListener listener;
    Context context;

    public AppointmentAdapter(List<Appointment> appointmentes, Context context) {
        this.newappointmentList = appointmentes;
        this.context = context;
    }

    public void updateList(List<Appointment> appointmentList) {
        newappointmentList.clear();
        newappointmentList.addAll(appointmentList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public appointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new appointmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull appointmentViewHolder holder, int position) {

        TextView date = holder.itemView.findViewById(R.id.appointment_date);
        TextView time = holder.itemView.findViewById(R.id.appointment_time);
        TextView patientName = holder.itemView.findViewById(R.id.appointment_patientName);
        TextView patientPhone = holder.itemView.findViewById(R.id.appointment_patientPhone);

        date.setText(newappointmentList.get(position).appointment_date);
        time.setText(newappointmentList.get(position).start_time);
        patientName.setText(newappointmentList.get(position).app_name);
        patientPhone.setText(newappointmentList.get(position).app_phone);
    }

    @Override
    public int getItemCount() {
        return newappointmentList.size();
    }

    public void setList(List<Appointment> appointmentList) {
        this.newappointmentList = appointmentList;
        notifyDataSetChanged();
    }

    public class appointmentViewHolder extends RecyclerView.ViewHolder {

        public appointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(newappointmentList.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Appointment item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
