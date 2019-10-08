package com.example.project6.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project6.R;
import com.example.project6.adapters.NewsFeedAdapter;
import com.example.project6.loaders.NewsFeedLoader;
import com.example.project6.objects.New;

import java.util.ArrayList;
import java.util.List;

/**
 * The type News feed activity.
 */
public class NewsFeedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<New>> {


    private static final String URL = "https://content.guardianapis.com/search";

    // Parameters/Queries used in URL
    private static final String QUERY_TAG = "q";
    private static final String QUERY_VALUE = "debates";
    private static final String FROM_DATE_TAG = "from-date";
    private static final String FROM_DATE_VALUE = "2019-01-01";
    private static final String TO_DATE_TAG = "to-date";
    private static final String TO_DATE_VALUE = "2019-01-31";
    private static final String SHOW_TAGS_TAG = "show-tags";
    private static final String SHOW_TAGS_VALUE = "contributor";
    private static final String PAGE_TAG = "page";
    private static final String PAGE_VALUE = "1";
    private static final String API_KEY_TAG = "api-key";
    private static final String API_KEY_VALUE = "test";

    private static final int NEWS_FEED_LOADER_ID = 1;

    private NewsFeedAdapter mAdapter;

    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fill ListView object
        ListView newsFeedListView = findViewById(R.id.list_view_id);

        // Fill EmptyView TextView object and set it in the ListView object
        mEmptyStateTextView = findViewById(R.id.empty_view_tv_id);
        newsFeedListView.setEmptyView(mEmptyStateTextView);

        // Create adapter with an empty list on start
        mAdapter = new NewsFeedAdapter(this, new ArrayList<New>());

        // Set adapter in ListView
        newsFeedListView.setAdapter(mAdapter);

        // Set ListView onItemClickListener to create implicit intent to show the new item URL on a browser
        newsFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                New newItem = mAdapter.getItem(position);
                if (newItem != null && !TextUtils.isEmpty(newItem.getNewUrl())) {
                    Uri itemUri = Uri.parse(newItem.getNewUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, itemUri);
                    startActivity(intent);
                }
            }
        });

        // Get connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get Network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Check if network/internet is available
        if (networkInfo != null && networkInfo.isConnected()) {
            // If its available, init loader
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_FEED_LOADER_ID, null, this);
        } else {
            // If not available, display no internet connection warning
            View loadingProgressBar = findViewById(R.id.loading_pb_id);
            loadingProgressBar.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    // Method t
    @Override
    public Loader<List<New>> onCreateLoader(int id, Bundle args) {


        // Build Uri with parameters indicated
        Uri uri = Uri.parse(URL);
        Uri.Builder uriBuilder = uri.buildUpon();

        uriBuilder.appendQueryParameter(QUERY_TAG, QUERY_VALUE);
        uriBuilder.appendQueryParameter(FROM_DATE_TAG, FROM_DATE_VALUE);
        uriBuilder.appendQueryParameter(TO_DATE_TAG, TO_DATE_VALUE);
        uriBuilder.appendQueryParameter(SHOW_TAGS_TAG, SHOW_TAGS_VALUE);
        uriBuilder.appendQueryParameter(PAGE_TAG, PAGE_VALUE);
        uriBuilder.appendQueryParameter(API_KEY_TAG, API_KEY_VALUE);

        return new NewsFeedLoader(this, uriBuilder.toString());
    }

    // Method called when loading of information/background thread is finished, updating the UI
    @Override
    public void onLoadFinished(Loader<List<New>> loader, List<New> data) {
        View loadingProgressBar = findViewById(R.id.loading_pb_id);
        loadingProgressBar.setVisibility(View.GONE);

        // In case no news are available to display
        mEmptyStateTextView.setText(R.string.no_news_available);

        // Clear adapter before updating it
        mAdapter.clear();

        // Add data to adapter
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    // Method that is called when loader is reset, and then adapter is cleared
    @Override
    public void onLoaderReset(Loader<List<New>> loader) {
        mAdapter.clear();
    }
}
