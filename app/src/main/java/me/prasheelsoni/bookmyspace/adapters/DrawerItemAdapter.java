package me.prasheelsoni.bookmyspace.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.prasheelsoni.bookmyspace.R;

public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.DrawerItem>{
    ArrayList<String> drawerItems = new ArrayList<>();
    ArrayList<Integer> images = new ArrayList<>();
    public DrawerItemAdapter(ArrayList<String> dataSet, ArrayList<Integer> images){
        drawerItems = dataSet;
        this.images = images;

    }
    @Override
    public DrawerItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false);
        DrawerItem vh = new DrawerItem(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(DrawerItem holder, int position) {
        holder.icon.setImageResource(images.get(position));
        holder.getTextView().setText(drawerItems.get(position));
        holder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    @Override
    public int getItemCount() {
        return drawerItems.size();
    }
    public class DrawerItem extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView text;
        public DrawerItem(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            icon = (ImageView) itemView.findViewById(R.id.image);
        }
        public TextView getTextView(){return text;}
    }


}
