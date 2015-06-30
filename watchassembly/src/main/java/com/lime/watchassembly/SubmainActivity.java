package com.lime.watchassembly;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.lime.watchassembly.vo.MemberInfo;

/**
 * Created by SeongSan on 2015-06-29.
 */
public class SubmainActivity extends ActionBarActivity {

    private final String TAG = "SubmainActivity";


    private String[] navItems = {"국회의원", "의안", "명예의전당", "국민참여", "마이페이지"};

    private ListView lvNavList;
    private FrameLayout flContainer;

    private Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    private MemberInfo memberInfo;
    private int subIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submain);

        Intent intent = getIntent();
        memberInfo = (MemberInfo) intent.getSerializableExtra("memberInfo");
        subIndex = (Integer) intent.getSerializableExtra("index");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvNavList = (ListView) findViewById(R.id.drawer);
        flContainer = (FrameLayout) findViewById(R.id.container);

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.assemblymanlist_name, R.string.assemblymanlist_name);
        dlDrawer.setDrawerListener(dtToggle);

        if (savedInstanceState == null) initFragment(subIndex);      // sub menu 초기화
    }

    private void initFragment(int position) {

        Fragment fr = null;

        switch (position) {
            case 0:
                getSupportActionBar().setTitle("국회의원");
                fr = new AssemblymanListFragment();
                break;
            case 1:
                getSupportActionBar().setTitle("의안");
                fr = new BillListFragment();
                break;
            case 2:
                getSupportActionBar().setTitle("명예의전당");
                fr = new HallOfFameFragment();
                break;
            case 3:
                getSupportActionBar().setTitle("국민참여");
                fr = new PublicOpinionFragment();
                break;
            case 4:
                getSupportActionBar().setTitle("마이페이지");
                fr = new MypageFragment();
                break;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.container, fr);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_submain, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) SubmainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SubmainActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            if (subIndex != position) setFragment(position);
            dlDrawer.closeDrawer(lvNavList); // 추가됨
        }
    }

    private void setFragment(int position) {

        Fragment fr = null;

        switch (position) {
            case 0:
                getSupportActionBar().setTitle("국회의원");
                subIndex = position;
                fr = new AssemblymanListFragment();
                break;
            case 1:
                getSupportActionBar().setTitle("의안");
                subIndex = position;
                fr = new BillListFragment();
                break;
            case 2:
                getSupportActionBar().setTitle("명예의전당");
                subIndex = position;
                fr = new HallOfFameFragment();
                break;
            case 3:
                getSupportActionBar().setTitle("국민참여");
                subIndex = position;
                fr = new PublicOpinionFragment();
                break;
            case 4:
                getSupportActionBar().setTitle("마이페이지");
                subIndex = position;
                fr = new MypageFragment();
                break;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fr);
        fragmentTransaction.commit();
    }
}
