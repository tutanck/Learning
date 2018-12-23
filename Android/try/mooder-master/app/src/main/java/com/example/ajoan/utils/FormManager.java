package com.example.ajoan.utils;

import android.content.Context;
import android.content.res.Resources;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Joan on 26/03/2017.
 */

public class FormManager extends ComponentManager {

    private static int msgTVHeight = 36; // 2 lines size


    public static JSONObject initInput(
            TextView titleTV,
            String title,
            EditText inputET,
            String hint,
            int type,
            ProgressBar checkingPB,
            TextView msgTV
    ) throws JSONException {
        titleTV.setText(title);
        inputET.setHint(hint);
        inputET.setInputType(type);
        dropProgressBar(checkingPB);
        dropMsgTV(msgTV);
        return new JSONObject().put("input",inputET).put("title",titleTV).put("pb",checkingPB).put("msg",msgTV);
    }


    public static ProgressBar showProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        return progressBar;
    }

    public static ProgressBar hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.INVISIBLE);
        return progressBar;
    }

    public static ProgressBar dropProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.GONE);
        return progressBar;
    }


    public static TextView showMsgTV(TextView msgTV,String msg,Resources resources){
        msgTV.setText(msg);
        msgTV.setHeight(getMSGTVHeight(resources));
        msgTV.setVisibility(View.VISIBLE);
        return msgTV;
    }

    public static TextView dropMsgTV(TextView msgTV){
        msgTV.setText("");
        msgTV.setHeight(0);
        msgTV.setVisibility(View.GONE);
        return msgTV;
    }

    public static TextView hideMsgTV(TextView msgTV){
        msgTV.setVisibility(View.INVISIBLE);
        return msgTV;
    }



    public static boolean validInput(
            Map<String,Boolean> formValidationMap,
            String inputValidationKey,
            JSONObject inputConf,
            Context context,
            Button submitBtn
    ) throws JSONException {
        if(formValidationMap!=null)
            formValidationMap.put(inputValidationKey,false);
        disableButton(submitBtn);
        dropProgressBar((ProgressBar) inputConf.get("pb"));

        if(inputConf.has("rule")) {
            TextView msgTV = (TextView) inputConf.get("msg");
            EditText inputET = (EditText) inputConf.get("input");
            if (!Utils.matchRule(inputConf.getString("rule"), inputET.getText().toString())) {
                if (inputConf.has("manual"))
                    showMsgTV(msgTV,inputConf.getString("manual"),context.getResources());
                return false;
            }
            dropMsgTV(msgTV); //Reset/clear warning message if it passes the rule
        }
        return true;
    }

    public static int getMSGTVHeight(Resources resources){
        return (int)Utils.pixelsInDP(msgTVHeight,resources);
    }


    public static boolean validFormOnInputChange(Map<String,Boolean> formValidationMap, String mapKey, Button submitBtn) {
        synchronized (formValidationMap) {
            formValidationMap.put(mapKey,true);
            for (Boolean inputState : formValidationMap.values())
                if (!inputState)
                    return false;
            enableButton(submitBtn);
            return true;
        }
    }
}
