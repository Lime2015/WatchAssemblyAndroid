package com.lime.watchassembly;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lime.watchassembly.adapter.AssemblymanListAdapter;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // db에서 국회의원 리스트 불러오기
        list = new ArrayList();
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));
        list.add(new AssemblymanListItem("http://watch.peoplepower21.org/images/member/874.jpg", "이개호", "새정치미주연합", "전남 담양군함평군영광군장성군"));

        myAdapter = new AssemblymanListAdapter(getActivity(), list);

        setListAdapter(myAdapter);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.fragment_assemblyman_list, container, false);
//    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
        AssemblymanListItem item = (AssemblymanListItem) list.get(position);
        Intent intent = new Intent(getActivity(), AssemblymanActivity.class);
        intent.putExtra("name", item.getAssemblymanName());
        startActivity(intent);
//        Toast.makeText(getActivity(), item.getAssemblymanName() + " clicked", Toast.LENGTH_SHORT).show();
    }
}
