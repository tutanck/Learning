package com.example.ajoan.utils.jeez.reqstr;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ajoan.utils.WebAppDirectory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Joan on 16/04/2017.
 */

public class ReQstr {
    private String serverAdr;
    private String appRouterUrl;
    private RequestQueue requestQueue;
    private JSONObject router;
    private ReQstr me =this;

    public ReQstr(
            String serverRootUrl,
            String appRouterUrl,
            RequestQueue requestQueue,
            Response.ErrorListener errorListener
    ){
        this.serverAdr = serverRootUrl;
        this.appRouterUrl = appRouterUrl;
        this.requestQueue = requestQueue;

        requestQueue.add(
                new StringRequest(
                        com.android.volley.Request.Method.GET,
                        appRouterUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    me.router = new JSONObject(response);
                                    Log.i("ReQstr::router",me.router.toString());
                                } catch (Exception e) {router=null;}
                            }
                        },
                        errorListener
                )
        );
    }

    private boolean configured(){return router!=null;}


    /**
     * @param serviceID the service name on client side
     * @param urlParams */
    public void send(
            String serviceID,
            Response.Listener<String> responseListener,
            Response.ErrorListener errorListener,
            Object tag,
            String... urlParams
    ) {
        try{
            send(serviceID,responseListener,errorListener,tag,strTabToMap(urlParams));
        } catch (Exception e) {
            errorListener.onErrorResponse(new VolleyError(e));
        }
    }



    /**
     * @param serviceID the service name on client side
     * @param urlParams */
    public void send(
            String serviceID,
            Response.Listener<String> responseListener,
            Response.ErrorListener errorListener,
            Object tag,
            Map<String,String> urlParams
    ) {
        try {
            if(!configured())
                throw new AppRouterNotLoadedException("Unable to send any request while the webapp's router is not defined");

            if(!router.has(serviceID))
                throw new InvalidRequestException("Fatal : Unknown serviceID '" + serviceID + "' on server "+serverAdr);

            JSONObject serviceInfos = router.getJSONObject(serviceID);
            Log.i("ReQstr/send","::serviceInfos : "+serviceInfos.toString());

            validParams(serviceInfos,urlParams);

            String reqStr = compileRequestURL(getMainPath(serviceID,serviceInfos),urlParams);

            Log.i("ReQstr/send"," Sending this request:\n  -->" + reqStr);

            StringRequest strReq = new StringRequest(
                    getHTTPM(serviceID,serviceInfos), reqStr , responseListener, errorListener);

            if(tag!=null) strReq.setTag(tag);

            this.requestQueue.add(strReq);

        } catch (Exception e) {
            errorListener.onErrorResponse(new VolleyError(e));
        }
    }



    private void validParams(
            JSONObject serviceInfos,
            Map<String,String> requestParams
    ) throws JSONException, InvalidRequestException, InvalidWebServiceDescriptionException {

        ArrayList<JSONArray> setList = new ArrayList<>();
        setList.add(0, (JSONArray)serviceInfos.get("expIN"));
        setList.add(1, (JSONArray)serviceInfos.get("optIN"));

        for(int i=0;i<setList.size();i++) {
            JSONArray jar =setList.get(i);
            for (int j = 0; j < jar.length(); j++) {
                String[] typedParameterTab = jar.getString(j).split("\\:");

                String paramName = typedParameterTab[0].trim();
                if (i == 0)
                    if (!requestParams.keySet().contains(paramName))
                        throw new InvalidRequestException("Fatal : Request parameters does not contains the server's expected parameter '" + paramName + "'");

                String paramType = "string";
                if (typedParameterTab.length >= 2) {//typedef is provided in the template
                    String tmp = typedParameterTab[1].trim().toLowerCase();
                    paramType = tmp.length() == 0 ? paramType : tmp;

                    try {
                        switch (paramType) {
                            case "int":
                                Integer.parseInt(requestParams.get(paramName));
                                break;
                            case "long":
                                Long.parseLong(requestParams.get(paramName));
                                break;
                            case "float":
                                Float.parseFloat(requestParams.get(paramName));
                                break;
                            case "double":
                                Double.parseDouble(requestParams.get(paramName));
                                break;
                            case "boolean":
                                String boolStr = requestParams.get(paramName);
                                if (!boolStr.equals("true") || !boolStr.equals("false"))
                                    throw new IllegalArgumentException(paramName + "'s value: " + boolStr + " is not a boolean value");
                                Boolean.parseBoolean(requestParams.get(paramName));
                                break;
                            case "string":
                                requestParams.get(paramName);
                                break;
                            default:
                                throw new InvalidWebServiceDescriptionException(
                                        "Unsupported type for expected parameter " + paramName + ": " + paramType + ". "
                                                + "Supported types are : int, long, float, double, boolean and string.");
                        }
                    } catch (IllegalArgumentException iae) {
                        throw new InvalidRequestException("Fatal : Request parameter '" + paramName + "' does not match the type '" + paramType + "' expected by the server");
                    }
                }
            }
        }
    }



    private int getHTTPM(
            String serviceID,
            JSONObject serviceInfos
    ) throws JSONException, InvalidWebServiceDescriptionException {
        if (!serviceInfos.has("httpM"))
            throw new InvalidWebServiceDescriptionException("Undefined HTTP Method for WebService '"+serviceID+"'");;

        int httpMethod=serviceInfos.getInt("httpM");
        switch(httpMethod) {
            case 0 : //GET
                httpMethod = Request.Method.GET;break;
            case 1 : //POST
                httpMethod = Request.Method.POST;break;
            default : throw new InvalidWebServiceDescriptionException("Unknown HTTP Method for WebService '"+serviceID+"'");
        }
        return httpMethod;
    }



    private String getMainPath(
            String serviceID,
            JSONObject serviceInfos
    ) throws JSONException, InvalidWebServiceDescriptionException {
        JSONArray paths = (JSONArray) serviceInfos.get("paths");
        if(paths.length()==0)
            throw new InvalidWebServiceDescriptionException("Undefined URL for the WebService '"+serviceID+"'");;
        return WebAppDirectory.serverUrl+paths.getString(0);
    }



    private String compileRequestURL(
            String url,
            Map<String,String> params
    ){
        String reqStr = url;
        if(params!=null && params.size()>0)
            reqStr+="?";

        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = it.next();
            reqStr += entry.getKey() + "=" + entry.getValue();
            if(it.hasNext())
                reqStr+="&";
        }
        return reqStr;
    }



    private Map<String,String> strTabToMap(
            String... valuedParams
    ) throws InvalidRequestException {
        HashMap<String,String> paramsMap = new HashMap<>();
        for(String str : valuedParams) {
            if (!str.contains("->"))
                throw new InvalidRequestException("valued parameter '"+str+"' does not contains the key-value separator '->'");
            String[]entry = str.split("->");
            paramsMap.put(entry[0],entry[1]); //no performance here
        }
        return paramsMap;
    }

}
