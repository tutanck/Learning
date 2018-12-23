package com.example.ajoan.events.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ajoan.events.fragments.EventsListFragment;
import com.example.ajoan.events.tools.EventAdapter;
import com.example.ajoan.maps.MapsActivity;
import com.example.ajoan.MyApp;
import com.example.ajoan.maps.R;
import com.example.ajoan.momento.api.apis.DataIledefranceFr;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EventsListActivity extends AppCompatActivity implements EventsListFragment.Listener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private boolean mGoogleApiClientConnected;

    private Activity meGod=this;
    private EventAdapter ea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this,this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("All Events");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEventsMap();
            }
        });
    }

    @Override protected void onStart() {mGoogleApiClient.connect(); super.onStart();}

    @Override protected void onStop() {mGoogleApiClient.disconnect(); super.onStop();}


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClientConnected = true;
        Log.i("refreshEventsList", "refreshDate = " + ((MyApp) getApplication()).refreshDate);

        if (((MyApp) getApplication()).refreshDate == null)
            refreshEvents();
        else {
            Log.i("refreshEventsList", "may need for Refresh..");
            Calendar needRefreshDate = Calendar.getInstance();
            needRefreshDate.setTime(((MyApp) getApplication()).refreshDate);
            needRefreshDate.add(Calendar.MINUTE, ((MyApp) getApplication()).timeLaps);
            Log.i("refreshEventsList", "needRefreshDate = " + needRefreshDate.getTime());

            Calendar comebackDate = Calendar.getInstance();
            comebackDate.setTime(new Date());
            Log.i("refreshEventsList", "comebackDate = " + comebackDate.getTime());

            if (comebackDate.after(needRefreshDate))
                refreshEvents();
            else if(ea.isEmpty()) {
                Log.i("refreshEventsList","map is filled light way : no events refresh before");
                fillEventsList();
            }
            else
                Log.i("refreshEventsList","No kind of refresh");
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClientConnected =false;
        Log.i("GoogleAPIClient","ConnectionSuspended : i="+i);
        try {Thread.sleep(5000);} catch (InterruptedException ie) {}
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClientConnected =false;
        Log.i("GoogleAPIClient","ConnectionFailed connectionResult="+connectionResult);
        try {Thread.sleep(5000);} catch (InterruptedException ie) {}
        mGoogleApiClient.connect();
    }


    /**
     * Refresh From Server All Event Around the user into the shared MyApp's eventsMap
     */
    private void refreshEvents(){
        //Checking for user's location permissions
        if (!(ContextCompat.checkSelfPermission(meGod, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED))
            ActivityCompat.requestPermissions(meGod, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MyApp.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        else {
            Toast.makeText(meGod, "Updating events around you..", Toast.LENGTH_SHORT).show();

            ((MyApp) getApplication()).mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (((MyApp) getApplication()).mLastLocation != null){
                RequestQueue queue = Volley.newRequestQueue(this); // Instantiate the RequestQueue
                // ReQstr a string response from the provided URL
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        DataIledefranceFr.baseUrl
                                + ((MyApp) getApplication()).mLastLocation.getLatitude() + "%2C+"
                                + ((MyApp) getApplication()).mLastLocation.getLongitude() + "%2C"
                                + ((MyApp) getApplication()).radius + "&rows=" + ((MyApp) getApplication()).rows,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Log.i("VolleyCallResponse", response);
                                try {
                                    ((MyApp) getApplication()).setEventsMap(DataIledefranceFr.refineApiResults(response));
                                } catch (Exception e) {
                                    Toast.makeText(meGod, "Sorry, we can't find events around you", Toast.LENGTH_SHORT).show();
                                }
                                ((MyApp) getApplication()).refreshDate =new Date();
                                fillEventsList();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("VolleyCallError", error.getMessage());
                                Toast.makeText(meGod, "Unable to contact server! Please check your connection or try later",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(stringRequest); // Add the request to the RequestQueue (request can be started)
            }else
                Toast.makeText(meGod,
                        "We encountered issues retrieving your location", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Fill the intent's events list with events that are retrieved from MyApp's shared eventsMap
     */
    private void fillEventsList(){
        ea.clear(); //unload list first then reload it with fresh values
        for(Map.Entry<String,JSONObject> event : ((MyApp)getApplication()).getEventsMap().entrySet())
            ea.add(event.getValue());
    }



    /**
     * @param requestCode //code that defined to identify the permission request
     *                    (here : MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
     * @param permissions //tab of permissions requested for the request
     * @param grantResults for each permission in the permissions tab grantResults represents the results tab*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MyApp.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    refreshEvents();
                else{
                    Toast.makeText(meGod, "Geolocation is required! Please enable it", Toast.LENGTH_SHORT).show();
                    try {Thread.sleep(5000);} catch (InterruptedException ie) {}
                    finish();//quit app
                }
            }
        }
    }


    @Override
    public void setEventAdapter(EventAdapter ea) {
        this.ea=ea;
    }


    /**
     * Open the map mode intent
     */
    private void openEventsMap(){
        Intent launchNextActivity =new Intent(meGod, MapsActivity.class);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(launchNextActivity);
    }

    /**
     * Secure mode style for app exit
     */
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit)
            finish(); // finish intent
        else {
            Toast.makeText(this, "Press Back again to Exit",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

}