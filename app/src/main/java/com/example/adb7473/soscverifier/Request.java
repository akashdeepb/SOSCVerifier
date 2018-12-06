package com.example.adb7473.soscverifier;

import android.content.Context;
import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Request {
    interface NetworkResponseListener {
        void changeField(String responseText);
    }

    NetworkResponseListener networkResponseListener;

    public void setNetworkResponseListener(NetworkResponseListener networkResponseListener) {
        this.networkResponseListener = networkResponseListener;
    }

    public void verifyRequest(final Context ctx, final String codeText){
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        String url = "https://c377416.000webhostapp.com/sosc_verify.php";          //URL to send request to

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        networkResponseListener.changeField(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkResponseListener.changeField("Error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("code",codeText);
                params.put("veri_by", Build.DEVICE);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

}
