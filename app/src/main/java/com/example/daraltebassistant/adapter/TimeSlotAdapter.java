package com.example.daraltebassistant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.model.Slots;
import com.example.daraltebassistant.model.Time;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
    private List<Time> newTimeList = new ArrayList<Time>();
    onItemClickListener listener;
    Context context;

    public TimeSlotAdapter(List<Time> newTimeList, Context context) {
        this.newTimeList = newTimeList;
        this.context = context;
    }

    public void updateList(List<Time> timeList) {
        newTimeList.clear();
        newTimeList.addAll(timeList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeSlotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_slot, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        holder.time.setText(newTimeList.get(position).slot);
        ConstraintLayout constraintLayout = holder.itemView.findViewById(R.id.constla);
        if (newTimeList.get(position).is_booked) {
            holder.time.setText(newTimeList.get(position).slot+"  booked");

        }
    }

    @Override
    public int getItemCount() {
        if (newTimeList != null) {
            return newTimeList.size();
        } else
            return 0;
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        TextView time;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeslot_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        if (!newTimeList.get(position).is_booked) {
                            listener.onItemClick(newTimeList.get(position));
                        }

                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Time item);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
