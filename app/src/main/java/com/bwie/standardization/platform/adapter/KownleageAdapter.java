package com.bwie.standardization.platform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.standardization.platform.R;
import com.bwie.standardization.platform.common.Constants;
import com.bwie.standardization.platform.entity.KnowledgeEntity;
import com.bwie.standardization.platform.entity.KnowledgeEntity;

import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/04/27
 * Description:
 */
public class KownleageAdapter extends BaseAdapter {
    private Context mContext;
    private List<KnowledgeEntity> mList;

    public KownleageAdapter(Context context, List<KnowledgeEntity> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoldler holdler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kownledge_item_layout, parent, false);
            holdler = new ViewHoldler();
            convertView.setTag(holdler);
            holdler.mTitleTv = convertView.findViewById(R.id.title);
        } else {
            holdler = (ViewHoldler) convertView.getTag();
        }

        holdler.mTitleTv.setText(mList.get(position).title);

        return convertView;
    }

    static class ViewHoldler {
        private TextView mTitleTv;
    }
}
