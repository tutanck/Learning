package com.example.ajoan.events.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ajoan.maps.R;

import org.json.JSONObject;


/**
 * A placeholder fragment containing a simple view.
 */
public class EventHeaderFragment extends Fragment {

    private Listener mListener;

    public EventHeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_header, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final JSONObject event =  mListener.getEvent();

        ((TextView)view.findViewById(R.id.auth_tv)).setText(
                "@"+event.optString("author","(unknown-author)"));
        ((TextView)view.findViewById(R.id.summary_tv)).setText(
                event.optString("title")+" ("+event.optString("dist","?")+"m)" );

        ImageView authIMV = (ImageView)view.findViewById(R.id.auth_imv);
        if(event.has("image"))
            Glide.with(this).load(event.optString("image")).asBitmap()
                    .placeholder(R.drawable.wait100)
                    .fallback(R.drawable.placeholder100)
                    .into(authIMV);
        else
            authIMV.setImageResource(R.drawable.placeholder100);
    }


    public interface Listener {
        JSONObject getEvent();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener)
            mListener = (Listener) context;
        else
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}