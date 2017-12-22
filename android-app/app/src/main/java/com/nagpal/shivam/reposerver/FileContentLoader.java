package com.nagpal.shivam.reposerver;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class FileContentLoader extends AsyncTaskLoader<String> {

    private String url;

    public FileContentLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return FetchUtil.fetchData(url);
    }
}
