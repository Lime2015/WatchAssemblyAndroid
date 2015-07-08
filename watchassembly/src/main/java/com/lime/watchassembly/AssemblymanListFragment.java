package com.lime.watchassembly;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lime.watchassembly.vo.AssemblymanListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SeongSan on 2015-06-29.
 */
public class AssemblymanListFragment extends ListFragment {

    private final String TAG = "AssemblymanListFragment";

    private List list;
    private AssemblymanListAdapter myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_assemblyman_list, container, false);

        Log.d(TAG, "onCreateView start");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList();
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));

        myAdapter = new AssemblymanListAdapter(getActivity(), list);

        setListAdapter(myAdapter);

//        ListView listView = (ListView)getView().findViewById(R.id.listview_ass);

//        listView.setAdapter(myAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                AssemblymanListItem item = (AssemblymanListItem) list.get(i);
//                Intent intent = new Intent(getActivity(), AssemblymanActivity.class);
//                intent.putExtra("name", item.getAssemblymanName());
//                startActivity(intent);
//            }
//        });
//        getView().invalidate();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
        AssemblymanListItem item = (AssemblymanListItem) list.get(position);
        Intent intent = new Intent(getActivity(), AssemblymanActivity.class);
        intent.putExtra("name", item.getAssemblymanName());
        startActivity(intent);
//        Toast.makeText(getActivity(), item.getAssemblymanName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    private class AssemblymanListAdapter extends ArrayAdapter {

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


//        try {
//            URL url = new URL(item.getImageUrl());
//            URLConnection conn = url.openConnection();
//            conn.connect();
//            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
//            Bitmap bm = BitmapFactory.decodeStream(bis);
//            bis.close();
//            holder.imgAss.setImageBitmap(bm);
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }

//        holder.imgAss.setImageURI(Uri.parse(item.getImageUrl()));
//        Log.d(TAG, Uri.parse(item.getImageUrl()).toString());
            holder.locationAss.setText(item.getLocalConstituency());
            holder.nameAss.setText(item.getAssemblymanName());
            holder.partyAss.setText(item.getPartyName());

            return viewToUse;
        }
    }
}
