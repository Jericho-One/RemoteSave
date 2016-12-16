package com.test.tech.remotesave;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements
        Response.Listener<String>,
        Response.ErrorListener {
    private static final String URL = "someURL";

    private Gson gson;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gson = new Gson();
        requestQueue = Volley.newRequestQueue(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = createGenericComment();
                String json = jsonifyComment(comment);
                //TODO shouldn't just toast this, but instead, implement a recyclerview list
                // where the comments are displayed
                if (hasNetworkConnection()) {
                    httpPost(json);
                    Toast.makeText(MainActivity.this, json, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No Network Connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void httpPost(String json) {
        new PostTask().execute(URL, json);
    }

    private String jsonifyComment(Comment comment) {
        return gson.toJson(comment);
    }

    private Comment createGenericComment() {
        return new Comment(Comment.generateHash("aaron", "dishman"), "aaron", "dishman", "proof, you know, of concept");
    }

    private boolean hasNetworkConnection() {
        //TODO check shared prefs for WIFI vs Cellular connection preferences
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(MainActivity.this, "Response is: "+ response.substring(0,500), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(MainActivity.this, "That didn't work, silly! Is this the right URL? " + URL, Toast.LENGTH_SHORT).show();
    }

    private class PostTask extends AsyncTask<Object, Object, Void> {
        @Override
        protected Void doInBackground(Object... data) {
            String url = (String) data[0];
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, MainActivity.this, MainActivity.this);
            // Add the request to the RequestQueue.
            requestQueue.add(stringRequest);
            return null;
        }
    }
}
