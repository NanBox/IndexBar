package com.southernbox.indexbar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.southernbox.indexbar.R;
import com.southernbox.indexbar.entity.Entity;

import java.util.List;

/**
 * Created by SouthernBox on 2016/10/25 0025.
 * 适配器
 */

public class MainAdapter extends RecyclerView.Adapter {

    public final static int VIEW_INDEX = 0;
    public final static int VIEW_CONTENT = 1;

    private Context mContext;
    private List<Entity> mList;

    public MainAdapter(Context context, List<Entity> List) {
        this.mContext = context;
        this.mList = List;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_INDEX) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_index, parent, false);
            IndexViewHolder holder = new IndexViewHolder(view);
            holder.tvIndex = view.findViewById(R.id.tv_index);
            return holder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false);
            ContentViewHolder holder = new ContentViewHolder(view);
            holder.tvName = view.findViewById(R.id.tv_name);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_INDEX) {
            ((IndexViewHolder) holder).tvIndex.setText(mList.get(position).getFirstWord());
        } else {
            ((ContentViewHolder) holder).tvName.setText(mList.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).isIndex()) {
            return VIEW_INDEX;
        } else {
            return VIEW_CONTENT;
        }
    }

    private static class IndexViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;

        IndexViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        ContentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
