package com.example.atomikiergasia01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SPListAdapter extends BaseAdapter {
    public List<String> timestamps;
    public List<String> info;

    public LayoutInflater layoutInflater;
    public Context context;

    public SPListAdapter(Context aContext, List<String> listData, List<String> listData2) {
        this.context = aContext;
        this.timestamps = listData;
        this.info = listData2;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return timestamps.size();
    }

    @Override
    public Object getItem(int position) {
        return timestamps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.geo_item, null);
            holder = new ViewHolder();
            holder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            holder.info = (TextView) convertView.findViewById(R.id.information);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.timestamp.setText(this.timestamps.get(position));
        holder.info.setText(this.info.get(position));

        return convertView;
    }


    static class ViewHolder {
        TextView timestamp;
        TextView info;
    }

}
