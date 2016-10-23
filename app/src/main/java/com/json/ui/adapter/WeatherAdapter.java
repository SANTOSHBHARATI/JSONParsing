package com.json.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.json.R;
import com.json.dataobject.WeatherInfo;

import java.util.List;

/**
 * Created by Santosh on 22-10-2016.
 */

public class WeatherAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WeatherInfo> weatherItems;

    public WeatherAdapter(Context context,List<WeatherInfo> weatherItems){
        this.weatherItems = weatherItems;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return weatherItems.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView = view;
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_item,null);
            holder.tvMax =(TextView)convertView.findViewById(R.id.tv_max);
            holder.tvMin =(TextView)convertView.findViewById(R.id.tv_min);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        WeatherInfo info = weatherItems.get(position);
        holder.tvMax.setText(info.getMax());
        holder.tvMin.setText(info.getMin());
        return convertView;
    }

    class ViewHolder{
        private TextView tvMax;
        private TextView tvMin;
    }
}
