package com.july.pigeon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.july.pigeon.adapter.holder.CommViewHolder;
import com.july.pigeon.ui.weight.InnerListView;

import java.util.List;

public abstract class BaseListAdapter extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<?> mData = null;
    protected final int mItemLayoutId;

    /**
     * @param mContext     上下文对象
     * @param mData        数据源
     * @param itemLayoutId listview对应的Item
     */
    public BaseListAdapter(Context mContext, List<?> mData, int itemLayoutId) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return (mData != null && mData.size() > 0) ? mData.size() : 0;
    }

    @Override
    public List<?> getItem(int position) {
        return (List<?>) ((mData != null && mData.size() > 0) ? mData.get(position) : 0);
    }

    @Override
    public long getItemId(int position) {
        return (mData != null && mData.size() > 0) ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (parent instanceof InnerListView) {
            if (((InnerListView) parent).isOnMeasure()) {
                CommViewHolder viewHolder = CommViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);

                return viewHolder.getConvertView();
            }
        }

        CommViewHolder viewHolder = CommViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);

        convert(viewHolder);
        viewHolder.setPosition(position);

        return viewHolder.getConvertView();
    }

    public abstract void convert(CommViewHolder holder);

}
