package com.example.adb7473.soscverifier;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Request {

    //Constructor for Request Class
    public Request(final Context ctx, final String codeText, final TextView lastverified){
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        String url = "<URL_HERE>";          //URL to send request to

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lastverified.setText(response);
                        Toast.makeText(ctx,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lastverified.setText("Error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("code",codeText);
                params.put("veri_by", Build.ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
