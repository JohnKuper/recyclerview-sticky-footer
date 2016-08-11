package com.korobeinikov.recyclerviewstickyfooter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy_Korobeinikov on 8/3/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int DEFAULT_ITEMS_COUNT = 5;

    public static final int NORMAL = 1;
    public static final int FOOTER = 2;

    private List<String> mItems;
    private Context mContext;

    public RecyclerViewAdapter(Context context) {
        mItems = generateItems(DEFAULT_ITEMS_COUNT);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mItems.size() ? FOOTER : NORMAL;
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL:
                return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
            case FOOTER:
                return new FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.footer_item, parent, false));
            default:
                throw new IllegalStateException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).mTitle.setText(mItems.get(position));
        }
    }

    private List<String> generateItems(int count) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add("Item " + i);
        }
        return items;
    }

    public void addOne() {
        mItems.add("Item " + mItems.size());
        notifyDataSetChanged();
    }

    public void deleteOne() {
        if (mItems.size() > 0) {
            mItems.remove(0);
            notifyDataSetChanged();
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
