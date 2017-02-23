package com.example.agupt23.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private static final int NEWS_LOADER_ID = 1;
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";
    NewsAdapter newsAdapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        emptyView = (TextView) findViewById(R.id.empty_state_textview);

        if (isNetworkAvailable()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, NewsActivity.this);
        } else {
            ProgressBar spinning_progress = (ProgressBar) findViewById(R.id.loading_spinner);
            spinning_progress.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet_connection);
        }
        final ListView newsListView = (ListView) findViewById(R.id.listview);
        newsListView.setEmptyView(emptyView);

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(newsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews =  newsAdapter.getItem(position);
                if (currentNews.getmWebLink() != null) {
                    Uri webUrl = Uri.parse(currentNews.getmWebLink());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, webUrl);
                    startActivity(websiteIntent);
                }

            }
        });
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String newsTopic = sharedPrefs.getString(
                getString(R.string.settings_news_topic_key),
                getString(R.string.settings_news_topic_default));
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(getString(R.string.query_param), newsTopic);
        uriBuilder.appendQueryParameter(getString(R.string.form_date), getString(R.string.date_latest));
        uriBuilder.appendQueryParameter(getString(R.string.api_key), getString(R.string.test_key));
        Log.v("NewsActivity",uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> news) {

        ProgressBar spinning_progress = (ProgressBar) findViewById(R.id.loading_spinner);
        spinning_progress.setVisibility(View.GONE);
        emptyView.setText(R.string.no_results_found);
        newsAdapter.clear();

        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        }


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
