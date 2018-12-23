package com.example.ajoan.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Joan on 21/03/2017.
 */

public class FragmentInjecter {

    public static void inject(
            int activityID,
            FragmentManager fragmentManager,
            LinkedHashMap<Fragment,Bundle> fragmentConfigs,
            String sharedBaseTag
    ){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int i=0; //i unique for an injection session
        for(Map.Entry<Fragment,Bundle> entry : fragmentConfigs.entrySet()) {
            entry.getKey().setArguments(entry.getValue());
            fragmentTransaction.add(activityID, entry.getKey(),sharedBaseTag+i++);
        }

        fragmentTransaction.commit();
    }
}
