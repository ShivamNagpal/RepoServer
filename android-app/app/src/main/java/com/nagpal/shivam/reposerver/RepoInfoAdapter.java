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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RepoInfoAdapter extends ArrayAdapter<RepoInfo> {

    public RepoInfoAdapter(@NonNull Context context, ArrayList<RepoInfo> repoInfoArrayList) {
        super(context, 0, repoInfoArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.text_view_layout, parent, false);
        }
        RepoInfo currentRepoInfo = getItem(position);
        TextView textView = listItemView.findViewById(R.id.text_view_layout);
        if (currentRepoInfo != null) {
            textView.setText(currentRepoInfo.getTitle());
        }
        return listItemView;
    }
}
