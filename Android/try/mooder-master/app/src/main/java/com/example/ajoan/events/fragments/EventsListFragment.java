package com.example.ajoan.events.fragments;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ajoan.events.tools.EventAdapter;
import com.example.ajoan.maps.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Listener} interface
 * to handle interaction events.
 * Use the {@link # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsListFragment extends Fragment {

    private Listener mListener;

    public EventsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventAdapter ea = new EventAdapter(
                getContext(), R.layout.fragment_event_header, new ArrayList<JSONObject>());

        ((ListView)view.findViewById(R.id.eventslist)).setAdapter(ea);
        ea.registerDataSetObserver(
                new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                    }
                }
        );
        mListener.setEventAdapter(ea);
    }


    public interface Listener {
        void setEventAdapter(EventAdapter ea);
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
