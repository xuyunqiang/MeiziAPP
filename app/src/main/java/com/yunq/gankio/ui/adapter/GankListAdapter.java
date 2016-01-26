package com.yunq.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunq.gankio.R;
import com.yunq.gankio.data.entity.Gank;
import com.yunq.gankio.ui.activity.WebActivity;
import com.yunq.gankio.util.StringStyleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 16/1/6.
 */
public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.ViewHolder> {

    private List<Gank> mGankList;

    public GankListAdapter() {
        mGankList = new ArrayList<>();
    }

    public void updateWithClear(List<Gank> data) {
        mGankList.clear();
        mGankList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Gank mGank = mGankList.get(position);
        if (position == 0) {
            showCategory(holder);
        } else {
            boolean doesLastAndThis = mGankList.get(position - 1).type.equals(mGankList.get(position).type);
            if (!doesLastAndThis) {
                showCategory(holder);
            } else if (holder.mTvCategory.isShown()) {
                holder.mTvCategory.setVisibility(View.GONE);
            }
        }

        holder.mTvCategory.setText(mGank.type);
        if (holder.mTvTitle.getTag() == null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(mGank.desc).append(
                    StringStyleUtils.format(holder.mTvTitle.getContext(), " (via. " + mGank.who + ")",
                            R.style.ViaTextAppearance));
            CharSequence mTvTitltText = builder.subSequence(0, builder.length());
            holder.mTvTitle.setTag(mTvTitltText);
        }
        CharSequence text = (CharSequence) holder.mTvTitle.getTag();
        holder.mTvTitle.setText(text);

        holder.mLlGankParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.gotoWebActivity(holder.mLlGankParent.getContext(), mGank.url, mGank.desc);
            }
        });
    }

    private void showCategory(ViewHolder holder) {
        if (!holder.mTvCategory.isShown()) {
            holder.mTvCategory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_category)
        TextView mTvCategory;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.ll_gank_parent)
        LinearLayout mLlGankParent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
