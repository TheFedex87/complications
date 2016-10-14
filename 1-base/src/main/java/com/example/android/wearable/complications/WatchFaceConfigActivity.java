/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.wearable.complications;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.complications.ComplicationHelperActivity;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * The watch-side config activity for {@link ComplicationWatchFaceService}, which
 * allows for setting complications on right and left of watch face.
 */
public class WatchFaceConfigActivity extends Activity implements
        WearableListView.ClickListener {

    private static final String TAG = "WatchFaceConfig";

    private static final int PROVIDER_CHOOSER_REQUEST_CODE = 1;

    private WearableListView mWearableConfigListView;
    private ConfigurationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_face_config);

        mAdapter = new ConfigurationAdapter(getApplicationContext(), getComplicationItems());

        mWearableConfigListView = (WearableListView) findViewById(R.id.wearable_list);
        mWearableConfigListView.setAdapter(mAdapter);
        mWearableConfigListView.setClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROVIDER_CHOOSER_REQUEST_CODE
                && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d(TAG, "onClick()");

        Integer tag = (Integer) viewHolder.itemView.getTag();
        ComplicationItem complicationItem = mAdapter.getItem(tag);

        // TODO: Step 3, onClick()
    }

    private List<ComplicationItem> getComplicationItems() {
        // TODO: Step 3, getComplicationItems()
        return new ArrayList<>();
    }

    @Override
    public void onTopEmptyRegionClick() {
        Log.d(TAG, "onTopEmptyRegionClick()");
    }

    /*
     * Inner class representing items of the ConfigurationAdapter (WearableListView.Adapter) class.
     */
    // TODO: Step 3, intro
    private final class ComplicationItem {
        ComponentName watchFace;
        int complicationId;
        int[] supportedTypes;
        Drawable icon;
        String title;

        public ComplicationItem(ComponentName watchFace, int complicationId, int[] supportedTypes,
                                Drawable icon, String title) {
            this.watchFace = watchFace;
            this.complicationId = complicationId;
            this.supportedTypes = supportedTypes;
            this.icon = icon;
            this.title = title;
        }
    }

    private static class ConfigurationAdapter extends WearableListView.Adapter {

        private Context mContext;
        private final LayoutInflater mInflater;
        private List<ComplicationItem> mItems;


        public ConfigurationAdapter (Context context, List<ComplicationItem> items) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mItems = items;
        }

        // Provides a reference to the type of views you're using
        public static class ItemViewHolder extends WearableListView.ViewHolder {
            private ImageView iconImageView;
            private TextView textView;
            public ItemViewHolder(View itemView) {
                super(itemView);
                iconImageView = (ImageView) itemView.findViewById(R.id.icon);
                textView = (TextView) itemView.findViewById(R.id.name);
            }
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // Inflate custom layout for list items.
            return new ItemViewHolder(
                    mInflater.inflate(R.layout.activity_watch_face_config_list_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {

            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            ImageView imageView = itemHolder.iconImageView;
            imageView.setImageDrawable(mItems.get(position).icon);

            TextView textView = itemHolder.textView;
            textView.setText(mItems.get(position).title);

            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public ComplicationItem getItem(int position) {
            return mItems.get(position);
        }
    }
}