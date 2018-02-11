/*
 * Copyright 2017 Shivam Nagpal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nagpal.shivam.reposerver;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final String TITLE_INTENT_TAG = "title_intent_tag";
    public static final String URL_INTENT_TAG = "url_intent_tag";
    private TextView displayTextView;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ActionBar actionBar = getSupportActionBar();

        initViews();

        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE_INTENT_TAG);
        url = intent.getStringExtra(URL_INTENT_TAG);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DisplayActivity.this.setTitle(title);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(2, null, DisplayActivity.this);

        }
    }

    private void initViews() {
        displayTextView = findViewById(R.id.display_text_view);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new FileContentLoader(DisplayActivity.this, url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        s = s.replaceAll("\t", "\t\t");
        displayTextView.setText(s);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        displayTextView.setText(null);
    }
}
