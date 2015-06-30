package com.lime.watchassembly.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.watchassembly.R;
import com.lime.watchassembly.vo.AssemblymanListItem;

import java.io.BufferedInputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by SeongSan on 2015-06-30.
 */
public class AssemblymanListAdapter extends ArrayAdapter {

    private final String TAG = "AssemblymanListAdapter";

    private Context context;
    private boolean useList = true;

    public AssemblymanListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder {
        ImageView imgAss;
        TextView nameAss;
        TextView locationAss;
        TextView partyAss;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        AssemblymanListItem item = (AssemblymanListItem) getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if (useList) {
                viewToUse = mInflater.inflate(R.layout.list_item_assemblyman, null);
            }
//            else {
//                viewToUse = mInflater.inflate(R.layout.example_grid_item, null);
//            }

            holder = new ViewHolder();
            holder.imgAss = (ImageView) viewToUse.findViewById(R.id.img_assemblyman);
            holder.locationAss = (TextView) viewToUse.findViewById(R.id.location_assemblyman);
            holder.nameAss = (TextView) viewToUse.findViewById(R.id.name_assemblyman);
            holder.partyAss = (TextView) viewToUse.findViewById(R.id.party_assemblyman);
            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }


        try {
            URL url = new URL(item.getImageUrl());
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            holder.imgAss.setImageBitmap(bm);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

//        holder.imgAss.setImageURI(Uri.parse(item.getImageUrl()));
//        Log.d(TAG, Uri.parse(item.getImageUrl()).toString());
        holder.locationAss.setText(item.getLocalConstituency());
        holder.nameAss.setText(item.getAssemblymanName());
        holder.partyAss.setText(item.getPartyName());

        return viewToUse;
    }
}
