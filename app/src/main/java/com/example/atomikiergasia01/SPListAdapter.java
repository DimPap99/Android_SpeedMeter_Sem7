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
    public List<String> latitude;
    public List<String> longtitude;
    public List<String> speed;

    public LayoutInflater layoutInflater;
    public Context context;

    public SPListAdapter(Context aContext, List<String> listData, List<String> listData2, List<String> listData3, List<String> listData4) {
        this.context = aContext;
        this.timestamps = listData;
        this.latitude = listData2;
        this.longtitude = listData3;
        this.speed = listData4;

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
            //holder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            holder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            holder.latitude = (TextView) convertView.findViewById(R.id.latitude);
            holder.longtitude = (TextView) convertView.findViewById(R.id.longtitude);
            holder.speed = (TextView) convertView.findViewById(R.id.speed);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        //holder.timestamp.setText(this.timestamps.get(position));
        holder.timestamp.setText(this.timestamps.get(position));
        holder.latitude.setText(this.latitude.get(position));
        holder.longtitude.setText(this.longtitude.get(position));
        holder.speed.setText(this.speed.get(position));

        return convertView;
    }


    static class ViewHolder {
        TextView timestamp;
        TextView latitude;
        TextView longtitude;
        TextView speed;



    }

}
