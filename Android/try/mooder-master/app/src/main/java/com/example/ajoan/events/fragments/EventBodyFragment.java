package com.example.ajoan.events.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajoan.maps.R;

import org.json.JSONObject;


/**
 * A placeholder fragment containing a simple view.
 */
public class EventBodyFragment extends Fragment {

    private Listener mListener;

    public EventBodyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_body, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JSONObject event =  mListener.getEvent();
        System.out.println("event : "+event);//debug

        ((TextView)view.findViewById(R.id.desc_tv)).setText(event.optString("desc"));
        String tags="";
        for(String tag : event.optString("tags").split(" "))
        tags+="#"+tag;
        ((TextView)view.findViewById(R.id.tags_tv)).setText("Tags: ");
        ((TextView)view.findViewById(R.id.infos_tv)).setText(
                "address : "+event.optString("address")+"\n"
                +"website : "+event.optString("website")+"\n"
                +"begining : "+event.optString("start")+"\n"
                +"end : "+event.optString("end")+"\n"
                +"pricing : "+event.optString("pricing"));
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