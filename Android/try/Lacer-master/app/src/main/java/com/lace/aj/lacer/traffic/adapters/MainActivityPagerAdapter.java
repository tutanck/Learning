package com.lace.aj.lacer.traffic.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lace.aj.lacer.traffic.fragments.ProfilFragment;


public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private Fragment[] mFragmentTab;

    private FragmentManager mFragmentManager;

    public static int NB_PAGE_TABS = 4;


    public MainActivityPagerAdapter(
            Context context,
            FragmentManager fragmentManager
    ) {
        super(fragmentManager);
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mFragmentTab = new  Fragment[getCount()];
    }


    @Override
    public Fragment getItem(int i) {
        if (mFragmentTab.length > i && mFragmentTab[i] != null)
            return mFragmentTab[i];

        Fragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt("DemoObjectFragment.ARG_OBJECT", i + 1);
        fragment.setArguments(args);

        mFragmentTab[i] = fragment;
        return fragment;
    }

    @Override
    public int getCount() {return NB_PAGE_TABS;}

    @Override
    public CharSequence getPageTitle(int position) {
        //https://stackoverflow.com/questions/28313196/what-can-i-use-instead-of-setnavigationmode
        switch (position){
            case 0: return "Trafic";
            case 1: return "Profil";
            case 2: return "Besoins";
            case 3: return "Chat";
            default: return "";
        }
    }
}