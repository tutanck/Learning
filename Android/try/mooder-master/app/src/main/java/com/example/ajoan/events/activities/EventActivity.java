package com.example.ajoan.events.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ajoan.MyApp;
import com.example.ajoan.events.fragments.EventBodyFragment;
import com.example.ajoan.events.fragments.EventHeaderFragment;
import com.example.ajoan.maps.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventActivity extends AppCompatActivity
        implements EventBodyFragment.Listener,  EventHeaderFragment.Listener{

    public final static String access_key = "SET_EVENT" ;

    public String id;
    private Activity meGod = this;

    @Override
    public JSONObject getEvent() {
        return  ((MyApp)getApplication()).getEventsMap().get(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra(access_key);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event"); //intent predefined type : event
                intent.putExtra(CalendarContract.Events.CALENDAR_ID, id);
                intent.putExtra(CalendarContract.Events.TITLE,
                        (String)getEvent().optString("title","Momento Event "+id));
                intent.putExtra(CalendarContract.Events.DESCRIPTION,
                        (String)getEvent().optString("desc","No description found"));
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                        (String)getEvent().optString("address"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//Api doesn't provide hour
                try {
                    //start date
                    Log.i("start date",getEvent().optString("start"));
                    Date start= sdf.parse(getEvent().optString("start"));
                    Log.i("start date millis","Date in millis :: " + start.getTime());

                    if(new Date().after(start))
                        start = new Date();

                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.getTime());

                    //end date
                    Log.i("end date",getEvent().optString("end"));
                    Date end= sdf.parse(getEvent().optString("end"));
                    Log.i("end date millis","Date in millis :: " + end.getTime());

                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(end);

                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(start);
                    startCal.add(Calendar.HOUR_OF_DAY, 23);

                    if(endCal.after(startCal)){
                        end = startCal.getTime();
                        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    }

                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTime());

                } catch (Exception e) {Log.i("Date Error","Error on event date");  }

                startActivity(intent);
            }
        });
        //Set actvity's title
        setTitle((String)getEvent().optString("title","Momento Event"+id));
    }
}
