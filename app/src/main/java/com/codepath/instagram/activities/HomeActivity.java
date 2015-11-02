package com.codepath.instagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramClient;
import com.codepath.instagram.models.InstagramPost;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

//import cz.msebera.android.httpclient.Header;
import org.apache.http.Header;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    ArrayList<InstagramPost> allPosts = new ArrayList<InstagramPost>() ;
    InstagramPostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home);

        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rvPosts);

        InstagramClient instagramClient = new InstagramClient(
                this, InstagramClient.REST_API_CLASS, InstagramClient.REST_CONSUMER_KEY, InstagramClient.REST_CONSUMER_KEY,
                InstagramClient.REST_CONSUMER_SECRET, InstagramClient.REDIRECT_URI, InstagramClient.SCOPE);

        instagramClient.getPopularFeed(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                allPosts.addAll(Utils.decodePostsFromJsonResponse(response));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
            });

        adapter = new InstagramPostsAdapter(allPosts, this);

        // create adapter
        rvPosts.setAdapter(adapter);

        // set adapter
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void fetchPost() {
//
//        try {
//            InstagramClient.getPopularFeed(new JsonHttpResponseHandler() {
//                @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                allPosts.addAll(Utils.decodePostsFromJsonResponse(response));
//                    adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                try {
//                    Log.e("getPoularFeed", "OnFailure", t);
//                }
//                catch (Throwable e) {
//                    Log.e("Error", e.getMessage());
//                }
//            }
//            });
//        }
//        catch (Throwable e) {
//            e.getStackTrace();
//        }
//    }
}
