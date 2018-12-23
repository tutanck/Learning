package com.example.ajoan.maps;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ajoan.MyApp;
import com.example.ajoan.events.activities.EventActivity;
import com.example.ajoan.events.activities.EventsListActivity;
import com.example.ajoan.momento.api.apis.DataIledefranceFr;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.ajoan.maps.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private Map<Marker,String> markers = new HashMap<>();
    private GoogleApiClient mGoogleApiClient;
    private boolean mGoogleApiClientConnected;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private boolean mMapReady;
    private double initLocationLatitude = 48.866667; //center of paris latitude
    private double initLocationLongitude = 2.333333; //center of paris longitude
    private int initZoom = 10;

    private Activity meGod=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list_mode);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEventsList();
            }
        });

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this,this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
    }

    @Override protected void onStart() {mGoogleApiClient.connect(); super.onStart();}

    @Override protected void onStop() {mGoogleApiClient.disconnect(); super.onStop();}



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mGoogleApiClientConnected = true;
        Log.i("refreshEventsMap", "refreshDate = " + ((MyApp) getApplication()).refreshDate);

        if (((MyApp) getApplication()).refreshDate == null)
            refreshEvents();
        else {
            Log.i("refreshEventsMap", "may need for Refresh..");
            Calendar needRefreshDate = Calendar.getInstance();
            needRefreshDate.setTime(((MyApp) getApplication()).refreshDate);
            needRefreshDate.add(Calendar.MINUTE, ((MyApp) getApplication()).timeLaps);
            Log.i("refreshEventsMap", "needRefreshDate = " + needRefreshDate.getTime());

            Calendar comebackDate = Calendar.getInstance();
            comebackDate.setTime(new Date());
            Log.i("refreshEventsMap", "comebackDate = " + comebackDate.getTime());

            if (comebackDate.after(needRefreshDate))
                refreshEvents();
            else if(markers.size()==0) {
                Log.i("refreshEventsMap","map is filled light way : no events refresh before");
                fillMap();
            }
            else
                Log.i("refreshEventsMap","No kind of refresh");
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


    @Override public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(initLocationLatitude, initLocationLongitude),initZoom));
        mMapReady=true;
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
                initLocationLatitude = ((MyApp) getApplication()).mLastLocation.getLatitude();
                initLocationLongitude = ((MyApp) getApplication()).mLastLocation.getLongitude();

                RequestQueue queue = Volley.newRequestQueue(this); // Instantiate the RequestQueue
                // ReQstr a string response from the provided URL
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
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
                                while (!mMapReady)try {Thread.sleep(500);} catch (InterruptedException ie) {}
                                ((MyApp) getApplication()).refreshDate =new Date();
                                fillMap();
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
     * Fill the Google map (and the local markers map) with marker that are created from MyApp's shared eventsMap
     */
    private void fillMap(){
        try {
            mMap.setMyLocationEnabled(true);

            mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                    new LatLng(((MyApp) getApplication()).mLastLocation.getLatitude(),
                            ((MyApp) getApplication()).mLastLocation.getLongitude()),11
                    ),1000,null
            );

            for(Map.Entry<String,JSONObject> event : ((MyApp)getApplication()).getEventsMap().entrySet())
                try {
                    markers.put(mMap.addMarker(new MarkerOptions()
                                    .title(event.getValue().getString("title")) //required field
                                    .snippet("@"+event.getValue().optString("author","unknown author"))
                                    .position(new LatLng(
                                            event.getValue().getJSONArray("latlon").getDouble(0), //required field
                                            event.getValue().getJSONArray("latlon").getDouble(1)) //required field
                                    )),
                            event.getValue().getString("id") //required field
                    );
                }catch (Exception e) {/*skip*/}

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    openEvent(markers.get(marker));
                }
            });
        } catch (SecurityException e) {
            Log.e("PERMISSION_EXCEPTION","LOCATION's NOT GRANTED");
            refreshEvents();
        }
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


    /**
     * Open the list mode intent
     */
    private void openEventsList(){
        Intent launchNextActivity =new Intent(meGod, EventsListActivity.class);
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

    /**
     * local method that open new intent for an event's details
     * @param eventID
     */
    private void openEvent(String eventID){
        startActivity(new Intent(meGod,EventActivity.class)
                .setAction(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(EventActivity.access_key,eventID));
    }

}
