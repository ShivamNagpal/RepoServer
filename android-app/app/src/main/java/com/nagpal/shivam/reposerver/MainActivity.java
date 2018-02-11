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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<RepoInfo>> {
    private final String url = "https://raw.githubusercontent.com/ShivamNagpal/RepoServer/master/Index.json";
    private ListView listView;
    private RepoInfoAdapter repoInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        repoInfoAdapter = new RepoInfoAdapter(MainActivity.this, new ArrayList<RepoInfo>());
        listView.setAdapter(repoInfoAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, MainActivity.this);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RepoInfo repoInfo = (RepoInfo) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(DisplayActivity.TITLE_INTENT_TAG, repoInfo.getTitle());
                intent.putExtra(DisplayActivity.URL_INTENT_TAG, repoInfo.getUrl());
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        listView = findViewById(R.id.list_view_activity_main);
    }

    @Override
    public Loader<List<RepoInfo>> onCreateLoader(int i, Bundle bundle) {
        return new RepoInfoLoader(MainActivity.this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<RepoInfo>> loader, List<RepoInfo> repoInfos) {
        if (repoInfos == null || repoInfos.isEmpty()) {
            return;
        }
        repoInfoAdapter.clear();
        repoInfoAdapter.addAll(repoInfos);
    }

    @Override
    public void onLoaderReset(Loader<List<RepoInfo>> loader) {
        repoInfoAdapter.clear();
    }
}
