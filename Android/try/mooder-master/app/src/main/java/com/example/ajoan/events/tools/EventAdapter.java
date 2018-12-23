package com.example.ajoan.events.tools;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ajoan.events.activities.EventActivity;
import com.example.ajoan.maps.R;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by AJoan on 29/11/2016.
 */
public class EventAdapter extends ArrayAdapter<JSONObject> {
    private final int ressource;

    public EventAdapter(Context context, int ressource, List<JSONObject> items) {
        super(context, ressource, items);
        this.ressource = ressource;
    }

    /**
     * Manual for creating a view of the list view
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(int position, View view, ViewGroup parent) {
        //return super.getView(position,convertView,parent);
        //TODO ask cmt rendre plus performant les sans trop faire de findVBID en stockant :: cours 2

        final JSONObject event = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(ressource, null);


        ((TextView) view.findViewById(R.id.auth_tv))
                .setText("@"+event.optString("author","(unknown-author)"));
        ((TextView) view.findViewById(R.id.summary_tv))
                .setText(event.optString("title")+" ("+event.optString("dist","?")+"m)");

        ImageView authIMV = (ImageView) view.findViewById(R.id.auth_imv);
        if(event.has("image"))
            Glide.with(getContext()).load(event.optString("image")).asBitmap()
                    .placeholder(R.drawable.wait100)
                    .fallback(R.drawable.placeholder100)
                    .into(authIMV);
        else
            authIMV.setImageResource(R.drawable.placeholder100);


        view.setOnClickListener(//finaly functionalize the view
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openEvent(event.optString("id"));
                    }
                }
        );
        return view;
    }

    /**
     * local method that open new intent for an event's details
     * @param eventID
     */
    private void openEvent(String eventID){
        getContext().startActivity(
                new Intent(getContext(),EventActivity.class)
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(EventActivity.access_key,eventID)
        );
    }
}
