package com.example.project6.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.Nullable;


import com.example.project6.objects.New;
import com.example.project6.utils.HttpUtils;

import java.util.List;

/**
 * The type News feed loader.
 */
public class NewsFeedLoader extends AsyncTaskLoader<List<New>> {

    private String mUrl;

    /**
     * Instantiates a new News feed loader.
     *
     * @param context the context
     * @param url     the url
     */
    public NewsFeedLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    // First is executed OnStartLoading() that will call forceLoad() method which triggers the loadInBackground() method to execute
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    // The background thread, that will fetch the News feed data
    @Nullable
    @Override
    public List<New> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return HttpUtils.fetchNewsFeedData(mUrl);
    }


}
