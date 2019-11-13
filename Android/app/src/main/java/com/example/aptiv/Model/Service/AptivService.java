package com.example.aptiv.Model.Service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aptiv.Model.Classe.Zone;
import com.example.aptiv.Model.Interface.IAptivService;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aptiv.Model.Interface.IVolleyCollback;
import com.example.aptiv.View.MainActivity;
import com.example.aptiv.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AptivService implements IAptivService {
    private MainActivity _activity;
    private boolean _isUpdatingDriverReadings = false;
    private boolean _isUpdatingPassReadings = false;
    private boolean _isUpdatingMiddleReadings = false;
    private boolean _isUpdatingBackReadings = false;
    public  AptivService(MainActivity activity){ _activity = activity; }

    @Override
    public void GetDriverReadings(final IVolleyCollback _collback) {
        if(!_isUpdatingDriverReadings){
            _isUpdatingDriverReadings = true;
            RequestQueue queue = Volley.newRequestQueue(_activity);
            String url = _activity.getResources().getString(R.string.GetDriverReadings);;

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    _isUpdatingDriverReadings = false;
                    Gson gson = new Gson();
                    String dataArray = null;
                    try{
                        dataArray = response.toString();
                        final Zone Zone = gson.fromJson(dataArray, Zone.class);
                        _collback.GetDriverReadings(Zone);
                    }catch (Exception e){
                        Log.e(this.getClass().toString(), e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    _isUpdatingDriverReadings = false;
                    Log.e(this.getClass().toString(), error.getMessage());
                }
            });
            queue.add(stringRequest);
        }
    }

    @Override
    public void GetPassengerReadings(final IVolleyCollback _collback) {
        if(!_isUpdatingPassReadings){
            _isUpdatingPassReadings = true;
            RequestQueue queue = Volley.newRequestQueue(_activity);
            String url = _activity.getResources().getString(R.string.GetPassengerReadings);;

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    _isUpdatingPassReadings = false;
                    Gson gson = new Gson();
                    String dataArray = null;
                    try {
                        dataArray = response.toString();
                        final Zone Zone = gson.fromJson(dataArray, Zone.class);
                        _collback.GetPassengerReadings(Zone);
                    } catch (Exception e) {
                        Log.e(this.getClass().toString(), e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    _isUpdatingPassReadings = false;
                    Log.e(this.getClass().toString(), error.getMessage());
                }
            });
            queue.add(stringRequest);
        }

    }

    @Override
    public void GetAverageReadings(final IVolleyCollback _collback) {
        if(!_isUpdatingMiddleReadings){
            _isUpdatingMiddleReadings = true;
            RequestQueue queue = Volley.newRequestQueue(_activity);
            String url = _activity.getResources().getString(R.string.GetAverageReadings);;

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    _isUpdatingMiddleReadings = false;
                    Gson gson = new Gson();
                    String dataArray = null;
                    try {
                        dataArray = response.toString();
                        final Zone Zone = gson.fromJson(dataArray, Zone.class);
                        _collback.GetAverageReadings(Zone);
                    } catch (Exception e) {
                        Log.e(this.getClass().toString(), e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    _isUpdatingMiddleReadings = false;
                    Log.e(this.getClass().toString(), error.getMessage());
                }
            });
            queue.add(stringRequest);
        }

    }

    @Override
    public void GetBackseatReadings(final IVolleyCollback _collback) {
        if(!_isUpdatingBackReadings){
            _isUpdatingBackReadings = true;
            RequestQueue queue = Volley.newRequestQueue(_activity);
            String url = _activity.getResources().getString(R.string.GetBackseatReadings);;

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    _isUpdatingBackReadings = false;
                    Gson gson = new Gson();
                    String dataArray = null;
                    try {
                        dataArray = response.toString();
                        final Zone Zone = gson.fromJson(dataArray, Zone.class);
                        _collback.GetBackseatReadings(Zone);
                    } catch (Exception e) {
                        Log.e(this.getClass().toString(), e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    _isUpdatingBackReadings = false;
                    Log.e(this.getClass().toString(), error.getMessage());
                }
            });

            queue.add(stringRequest);
        }

    }
}
