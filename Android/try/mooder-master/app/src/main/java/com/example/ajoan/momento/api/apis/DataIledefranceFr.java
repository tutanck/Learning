package com.example.ajoan.momento.api.apis;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataIledefranceFr {

	/**URL pattern :
	 * "http://data.iledefrance.fr/api/records/1.0/search/?
	 * dataset=evenements-publics-cibul&
	 * facet=updated_at&
	 * facet=tags&
	 * facet=department&
	 * facet=region&
	 * facet=city&
	 * facet=date_start&
	 * facet=date_end&
	 * pretty_print=true&
	 * geofilter.distance="+lat+"%2C+"+lon+"%2C"+rayon+"&rows="+row
	 * Get json data from url about api's knowledge on a place */
	public static String baseUrl="http://data.iledefrance.fr/api/records/1.0/search/?"
			+ "dataset=evenements-publics-cibul&facet=updated_at&facet=tags"
			+ "&facet=department&facet=region&facet=city&facet=date_start"
			+ "&facet=date_end&pretty_print=true&geofilter.distance=";

	private static final String name=DataIledefranceFr.class.getSimpleName();

	/** Return refined and filtered JSON data instead of gross String data
	 * @return JSONArray
	 * @throws JSONException */
	public static Map<String,JSONObject> refineApiResults(String data) throws JSONException {

		//get api's data
		JSONObject jsondata = new JSONObject(data);
		//System.out.println(jsondata+"\n\n\n\n\n");//debug

		//records
		JSONArray records =  (JSONArray) jsondata.get("records");
		//System.out.println("records : "+records);//debug

		Map<String,JSONObject> events = new HashMap<>();
		for (int i = 0; i < records.length(); i++) {
			//record
			JSONObject gross = records.getJSONObject(i);
			//System.out.println("\ngross record"+i+" : "+gross); //debug

			//api's post identification informations
			String recordid=gross.getString("recordid");//debug
			String datasetid=gross.getString("datasetid");//debug
			String recordtimestamp=gross.getString("record_timestamp");//debug

			//fields
			JSONObject fields = (JSONObject) gross.get("fields");

			//real-time :  date comparison filter
			if(fields.has("date_end")){
				Date currentDate =new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",// TODO HH:mm:ss later
						Locale.FRANCE);
				try {
					if(currentDate.compareTo(sdf.parse(fields.getString("date_end")))>0){
						//System.out.println("Event "+datasetid+"-"+recordid+" is outdated: "+fields.getString("date_end")); //debug
						continue;
					}
				} catch (ParseException e) {continue;}//skip this problematic record
			}

			JSONObject fine = new JSONObject();//clean and filtered json result

			//post's id
			fine.put("id",recordid);

			//post's author
			fine.put("author",name);
			if(fields.has("image"))
				fine.put("image", fields.getString("image"));

			//post's position
			if(fields.has("latlon"))
				fine.put("latlon",fields.getJSONArray("latlon"));

			//post's date
			if(fields.has("updated_at"))
				fine.put("date",fields.getString("updated_at"));
			else fine.put("date",recordtimestamp);

			//post's title
			if(fields.has("title"))
				fine.put("title",fields.getString("title"));

			//post's description
			String desc="";
			if(fields.has("description"))
				desc+=fields.getString("description")+" ";
			if(fields.has("free_text"))
				desc+=fields.getString("free_text");
			fine.put("desc",desc);

			//post's tags
			if(fields.has("tags"))
				fine.put("tags",fields.getString("tags"));

			//post's informations
			if(fields.has("dist"))
				fine.put("dist", fields.getString("dist"));
			if(fields.has("address"))
				fine.put("address",fields.getString("address"));
			if(fields.has("date_start"))
				fine.put("start",fields.getString("date_start"));
			if(fields.has("date_end"))
				fine.put("end",fields.getString("date_end"));
			if(fields.has("placename"))
				fine.put("placename",fields.getString("placename"));
			if(fields.has("space_time_info"))
				fine.put("spacetime",fields.getString("space_time_info"));
			if(fields.has("pricing_info"))
				fine.put("pricing",fields.getString("pricing_info"));
			if(fields.has("link"))
				fine.put("website",fields.getString("link"));


			//System.out.println("fine record"+i+": "+fine);//debug
			events.put(recordid,fine);
		}
		return events;
	}
}