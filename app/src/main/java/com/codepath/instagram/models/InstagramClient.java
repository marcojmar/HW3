package com.codepath.instagram.models;

import android.content.Context;

import com.codepath.instagram.helpers.Constants;
import com.codepath.instagram.networking.InstagramApi;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;



/*
AysnHttpClient, JsonHttpResponseHandler are part of android-async-http:1.4.9
*
 */
/**
 * Created by mmar on 10/28/15.
 */
public class InstagramClient extends OAuthBaseClient {
    public static Class<? extends Api> REST_API_CLASS = InstagramApi.class;
    public static String REST_URL = "https://api.instagram.com/v1/";

    // assignment ID
//    public static String REST_CONSUMER_KEY = "7f5321002cc04089b778e463cd87953f";
//    public static String REST_CONSUMER_SECRET = "a9980e6933814fd3848dba9f6b370b63";

    //Marco's ID
    public static String REST_CONSUMER_KEY = "5783bc65d4fc4b0d9fc6b987329e5810";
    public static String REST_CONSUMER_SECRET = "521150d047024d51a5fc913ae647babf";

    public static String REDIRECT_URI = Constants.REDIRECT_URI;  //aka rest_callback_url
    public static String SCOPE = Constants.SCOPE;
    public Context context;

    public InstagramClient(Context c, Class<? extends Api> apiClass, String consumerUrl, String consumerKey, String consumerSecret, String callbackUrl, String scope) {
        super(c, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REDIRECT_URI, SCOPE);
        this.context = c;
    }

    public void getCommentsFeed(String media, JsonHttpResponseHandler handler) {
        // client ID: 5783bc65d4fc4b0d9fc6b987329e5810
        String url = "https://api.instagram.com/v1/media/" + media + "/comments?client_id=" + this.REST_CONSUMER_KEY;
        client.get(url, null, handler);
    }

    public void getPopularFeed(JsonHttpResponseHandler handler) {
        // client ID: 5783bc65d4fc4b0d9fc6b987329e5810
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + this.REST_CONSUMER_KEY;
        client.fetchRequestToken();
        Token token = client.getAccessToken();
        client.get(url, null, handler);
    }
}
