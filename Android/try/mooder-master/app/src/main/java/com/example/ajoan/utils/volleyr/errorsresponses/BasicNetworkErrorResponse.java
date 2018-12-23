package com.example.ajoan.utils.volleyr.errorsresponses;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ajoan.utils.Messages;

/**
 * Created by Joan on 01/04/2017.
 */

public class BasicNetworkErrorResponse implements Response.ErrorListener{

    private Context ctx;

    public BasicNetworkErrorResponse(Context ctx){
        this.ctx=ctx;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(ctx.toString()+"::ReQstr ", "onErrorResponse : '" + error + "'", error);
        Messages.displayMSGOnNetworkError(ctx,error);
    }
}
