package com.optimize.optimize.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.optimize.optimize.R;
import com.optimize.optimize.models.OTEvent;
import com.optimize.optimize.utilities.DateUtils;

import java.util.List;

/**
 * Created by BillyKwok on 15/1/15.
 */
public class OTEventAdapter extends RecyclerView.Adapter<OTEventAdapter.ViewHolder> {

    private List<OTEvent> otEventList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivEventIcon;
        public TextView tvEventName;
        public TextView tvEventDateTime;
        public TextView tvEventDesc;

        public int numOfParticipants;
        public boolean isTimeConfirmed;
        public OTEvent otEvent;

        public ViewHolder(View v) {
            super(v);
            tvEventName = (TextView) v.findViewById(R.id.tv_event_name);
            tvEventDateTime = (TextView) v.findViewById(R.id.tv_event_datetime);
            tvEventDesc = (TextView) v.findViewById(R.id.tv_event_desc);
        }
    }

    public OTEventAdapter(List<OTEvent> myDataset) {
        otEventList = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_oteventlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.tvEventName.setText(otEventList.get(i).getTitle());
        holder.tvEventDateTime.setText(
            DateUtils.convertDate(otEventList.get(i).getBegin()) + " to " +
            DateUtils.convertDate(otEventList.get(i).getEnd()));
        holder.tvEventDesc.setText(otEventList.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return otEventList.size();
    }

}