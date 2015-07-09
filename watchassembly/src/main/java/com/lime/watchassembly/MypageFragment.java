package com.lime.watchassembly;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

public class MypageFragment extends Fragment {

    private final String TAG = "MypageFragment";

//    private FragmentTabHost tabHost;
//    private boolean createdTab = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mypage, container, false);
//        tabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost_mypage);
//        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.frame_mypage);

//        Bundle arg1 = new Bundle();
//        arg1.putInt("index", 0);
//        tabHost.addTab(tabHost.newTabSpec("InfoTab").setIndicator("내정보"), MypageSubInfoFragment.class, null);
//        tabHost.addTab(tabHost.newTabSpec("AssmanTab").setIndicator("관심의원"), MypageSubFavoriteAssemblymanFragment.class, null);
//        tabHost.addTab(tabHost.newTabSpec("BillTab").setIndicator("관심의안"), MypageSubFavoriteBillFragment.class, null);
//        tabHost.addTab(tabHost.newTabSpec("DataTab").setIndicator("데이터"), MypageSubDataFragment.class, null);

//        for (int i = 0; i < 4; i++) {
//            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.tumblr_orange));
//        }

//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String s) {
//                Log.d(TAG, "select tab : " + s);
//            }
//        });

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_mypage);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getActivity().getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs_mypage);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        if (!createdTab) {
//            createdTab = true;
//            tabHost.setup(getActivity(), getActivity().getSupportFragmentManager());
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        tabHost = null;
//    }

    private class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 4;
        private String tabTitles[] = new String[] { "내정보", "관심의원", "관심의안", "데이터" };

        private MypageSubInfoFragment infoFragment = new MypageSubInfoFragment();
        private MypageSubFavoriteAssemblymanFragment assemblymanFragment = new MypageSubFavoriteAssemblymanFragment();
        private MypageSubFavoriteBillFragment billFragment = new MypageSubFavoriteBillFragment();
        private MypageSubDataFragment dataFragment = new MypageSubDataFragment();

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return infoFragment;
                case 1:
                    return assemblymanFragment;
                case 2:
                    return billFragment;
                case 3:
                    return dataFragment;
                default:
                    return infoFragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
