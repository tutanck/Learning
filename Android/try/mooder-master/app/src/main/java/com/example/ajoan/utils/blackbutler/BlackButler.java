package com.example.ajoan.utils.blackbutler;

import android.content.Context;
import android.util.Log;

import com.example.ajoan.utils.MoodClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Joan on 01/04/2017.
 */

/**
 * The Black butler pop the wine bottle then serve it to the clients
 */
public class BlackButler {

    /**
     * Decapsulates the answer and serve its content to the client in the right glass (method)
     * @param mc
     * @param response */
    public static void popNserve(Context context, MoodClient mc, String response) throws JSONException, UnknownStatusException {
        Log.i("BlackButler", "popNserve : '" + response + "'");

        JSONObject respJSON = new JSONObject(response);

        switch (respJSON.getInt("status")) {
            case -1: mc.onIssue(respJSON.getInt("issue"));break;
            case 0: mc.onReply(respJSON.getInt("rpcode"),respJSON.optString("message"),respJSON.optJSONObject("result"));break;
            default:throw new UnknownStatusException();
        }
    }
}
