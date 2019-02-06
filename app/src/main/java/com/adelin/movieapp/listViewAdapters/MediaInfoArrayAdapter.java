package com.adelin.movieapp.listViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adelin.movieapp.utils.MediaInfo;
import com.adelintmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MediaInfoArrayAdapter extends ArrayAdapter<MediaInfo> {
    private Context context;

    public MediaInfoArrayAdapter(Context context, int resourceId, //resourceId=your layout
                                 List<MediaInfo> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView yearText;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        MediaInfo rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.media_list_item, null);
            holder = new ViewHolder();
            holder.txtTitle = convertView.findViewById(R.id.mediaTitle);
            holder.yearText = convertView.findViewById(R.id.mediaYear);
            holder.imageView = convertView.findViewById(R.id.mediaCover);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        assert rowItem != null;
        holder.yearText.setText(rowItem.getYear());
        holder.txtTitle.setText(rowItem.getTitle());
        Picasso.get().load(rowItem.getPosterLink()).into(holder.imageView);

        return convertView;
    }
}

