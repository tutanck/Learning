package com.example.ajoan.utils;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by Joan on 01/04/2017.
 */

public abstract class MoodClient implements Response.Listener<String>{

    public abstract void onReply(int rpcode, String message, JSONObject result);

    public abstract void onIssue(int iscode);

}
