package com.example.agupt23.newsapp;

import android.content.Context;
import android.content.AsyncTaskLoader;
import java.util.ArrayList;

/**
 * Created by agupt23 on 2/21/17.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public ArrayList<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        ArrayList<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
