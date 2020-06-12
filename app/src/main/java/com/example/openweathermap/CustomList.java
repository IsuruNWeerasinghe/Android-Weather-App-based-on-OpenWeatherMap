
package com.example.openweathermap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] dayofweek;
    private final String[] tempmin;
    private final String[] tempmax;
    private final String unittxt;
    private final Integer[] imgid;

    public CustomList(Activity context, String[] days ,Integer[] imgid,String[] tempmax, String[] tempmin, String unittxt){
        super(context,R.layout.my_list,days);
        this.context = context;
        this.dayofweek = days;
        this.tempmax = tempmax;
        this.tempmin = tempmin;
        this.unittxt = unittxt;
        this.imgid = imgid;

    }

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.my_list,null,true);

        TextView day = (TextView)rowView.findViewById(R.id.day);
        TextView temmin = (TextView)rowView.findViewById(R.id.tempmin);
        TextView temmax = (TextView)rowView.findViewById(R.id.tempmax);
        ImageView img = (ImageView)rowView.findViewById(R.id.icon);

        day.setText(dayofweek[position]);
        img.setImageResource(imgid[position]);
        temmax.setText(" "+ tempmax[position]+ unittxt);
        temmin.setText(" "+ tempmin[position]+unittxt);

        return rowView;
    };
}