package com.arifcebe.artistdemenan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arifcebe.artistdemenan.R;
import com.arifcebe.artistdemenan.model.Artist;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by arifcebe on 16/01/16.
 */
public class ListArtistAdapter extends BaseAdapter {

    private Context context;
    private List<Artist> list;
    private ListArtistHolder holder;

    public ListArtistAdapter(Context context, List<Artist> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_artist_item,null);
            holder = new ListArtistHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.item_img);
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.status = (TextView) convertView.findViewById(R.id.item_status);
            holder.panggilan = (TextView) convertView.findViewById(R.id.item_panggilan);

            convertView.setTag(holder);
        }else{
            holder = (ListArtistHolder) convertView.getTag();
        }
        Artist artist = list.get(position);
        holder.name.setText(artist.getName());
        holder.status.setText(artist.getHoby());
        holder.panggilan.setText(artist.getPanggilan());

        Glide.with(context)
                .load(artist.getPhoto())
                .into(holder.img);

        return convertView;
    }

    private class ListArtistHolder {
        ImageView img;
        TextView name,status,panggilan;
    }
}

