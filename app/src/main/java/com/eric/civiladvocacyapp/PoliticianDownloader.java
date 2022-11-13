package com.eric.civiladvocacyapp;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class PoliticianDownloader {

    private final static String API_KEY = "AIzaSyCDPCMtfWRLS34FXwOlpp9hWZAtsLgRoz0";

    private static String url = "https://www.googleapis.com/civicinfo/v2/representatives";
    private static RequestQueue queue;


    public static void downloadData(String address){
        //queue = Volley.newRequestQueue(this);

        Uri.Builder buildURL = Uri.parse(url).buildUpon();
        buildURL.appendQueryParameter("key", API_KEY);
        buildURL.appendQueryParameter("address", address);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener =
             new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response){
                    try{
                        Log.d("response",response.toString());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

        };

        Response.ErrorListener error = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                try{
                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        JsonObjectRequest jsonObjectRequest =
            new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error);

        queue.add(jsonObjectRequest);



        Log.d("url", urlToUse);

    }

}
