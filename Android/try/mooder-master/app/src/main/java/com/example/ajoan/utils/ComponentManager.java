package com.example.ajoan.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ajoan.maps.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Joan on 26/03/2017.
 */

public class ComponentManager {

    public static TextView initTextView(
            TextView textView,
            String text
    ){
        textView.setText(text);
        return textView;
    }


    public static Button initButton(
            Button button,
            String text,
            boolean enabled
    ){
        button.setText(text);
        if(!enabled)
            disableButton(button);
        else
            enableButton(button);
        return button;
    }

    public static Button enableButton(Button button){
        button.setBackgroundResource(R.drawable.submit_btn_enabled);
        button.setEnabled(true);
        return button;

    }

    public static Button disableButton(Button button){
        button.setBackgroundResource(R.drawable.submit_btn_disabled);
        button.setEnabled(false);
        return button;
    }


}
