package com.yunq.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yunq.gankio.R;
import com.yunq.gankio.data.entity.Girl;
import com.yunq.gankio.ui.widget.RatioImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 16/1/5.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private List<Girl> mListData;

    private Context mContext;

    private IClickItem mIClickItem;

    public MainListAdapter(Context context) {
        mContext = context;
        mListData = new ArrayList<>();
    }

    public void setIClickItem(IClickItem iClickItem) {
        mIClickItem = iClickItem;
    }

    public void updateWithClear(List<Girl> data) {
        mListData.clear();
        mListData.addAll(data);
        notifyDataSetChanged();
    }

    public void update(List<Girl> data) {
        mListData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Girl entity = mListData.get(position);
        Picasso.with(mContext).load(entity.url).into(holder.mIvIndexPhoto);
        holder.mTvIndexIntro.setText(entity.desc);
        if (mIClickItem != null) {
            holder.mIvIndexPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickPhoto(position, holder.mIvIndexPhoto);
                }
            });
            holder.mTvIndexIntro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickDesc(position, holder.mTvIndexIntro);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public Girl getGirl(int position) {
        return mListData.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_index_photo)
        RatioImageView mIvIndexPhoto;

        @Bind(R.id.tv_index_intro)
        TextView mTvIndexIntro;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mIvIndexPhoto.setOriginalSize(50, 50);

        }
    }

    public interface IClickItem {
        void onClickPhoto(int position, View view);

        void onClickDesc(int position, View view);
    }
}
