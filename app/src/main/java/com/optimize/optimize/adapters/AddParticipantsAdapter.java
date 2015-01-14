package com.optimize.optimize.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.optimize.optimize.R;
import com.parse.ParseUser;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samwalker on 14/1/15.
 */
public class AddParticipantsAdapter extends BaseAdapter {

    Context context;
    List<ParseUser> parseUsers;
    ViewHolder holder;

    public AddParticipantsAdapter(Context context, List<ParseUser> parseUsers) {
        this.context = context;
        this.parseUsers = parseUsers;
    }

    @Override
    public int getCount() {
        return parseUsers.size();
    }

    @Override
    public ParseUser getItem(int position) {
        return parseUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_add_participants, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtParticipantUsername.setText(getItem(position).getUsername());
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.txtParticipantUsername)
        TextView txtParticipantUsername;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
