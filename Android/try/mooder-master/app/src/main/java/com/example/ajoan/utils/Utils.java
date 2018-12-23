package com.example.ajoan.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by AJoan on 24/12/2016.
 */

public class Utils {

    public static float pixelsInDP(int dp,Resources resources){
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return pixels;
    }

    public static boolean matchRule(String rule,String s){
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(s);

        boolean respected = matcher.matches();
        Log.i("Utils.matchRule", s + " respects "+ rule +" : "+ respected);
        return respected;
    }

    public static Intent intent(
            Intent intent,
            String action,
            String type
    ){
        intent.setAction(action!=null?action:Intent.ACTION_SEND);

        intent.setType(type!=null?type:"text/plain");

        return intent;
    }

}
