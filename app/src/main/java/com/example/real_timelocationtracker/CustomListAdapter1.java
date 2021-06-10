package com.example.real_timelocationtracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomListAdapter1 extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;


    public CustomListAdapter1(Activity context, String[] maintitle) {
        super(context, R.layout.customlistview1, maintitle);


        this.context=context;
        this.maintitle=maintitle;


    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlistview1, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.customListViewTitle);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.customListViewIcon);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(R.drawable.user3dicon2);

        return rowView;

    };
}
