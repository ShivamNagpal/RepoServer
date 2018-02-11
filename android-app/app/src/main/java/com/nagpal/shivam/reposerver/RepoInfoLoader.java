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

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RepoInfoLoader extends AsyncTaskLoader<List<RepoInfo>> {
    private String url;

    public RepoInfoLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<RepoInfo> loadInBackground() {
        if (url == null) {
            return null;
        }
        String response = FetchUtil.fetchData(url);
        return extractFeaturesFromResponse(response);
    }

    private List<RepoInfo> extractFeaturesFromResponse(String response) {
        if (TextUtils.isEmpty(response)) {
            return null;
        }
        List<RepoInfo> repoInfoList = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(response);
            JSONArray jsonArray = rootObject.getJSONArray("index");
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject info = jsonArray.getJSONObject(i);
                String title = info.getString("title");
                String url = info.getString("url");
                repoInfoList.add(new RepoInfo(title, url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repoInfoList;
    }
}
