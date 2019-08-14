package com.click.lib_banner.rvbanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.click.lib_banner.R;
import com.click.lib_banner.rvbanner.ComRecyclerViewBannerBase;

import java.util.List;

/**
 * * @ClassName :  ComCustomRecyclerAdapter
 * * @Description : RecyclerView适配器
 * * @Author jhonjson
 * * @Date 2019.8.14
 */
public class ComCustomRecyclerAdapter extends ComBaseAdapter<ComCustomRecyclerAdapter.NormalHolder> {

    private ComRecyclerViewBannerBase.OnBannerItemClickListener onBannerItemClickListener;
    private Context context;
    private List<String> urlList;
    private NormalHolder mNormalHolder;
    private int itemWidth, itemHeight;

    public ComCustomRecyclerAdapter(Context context, List<String> urlList, ComRecyclerViewBannerBase.OnBannerItemClickListener onBannerItemClickListener) {
        this.context = context;
        this.urlList = urlList;
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public ComCustomRecyclerAdapter.NormalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mNormalHolder = new NormalHolder(LayoutInflater.from(context).inflate(R.layout.view_rv_banner_item, parent, false));
        return mNormalHolder;
    }


    @Override
    public void onBindViewHolder(final NormalHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty())
            return;
        String url = urlList.get(position % urlList.size());
        ImageView img = (ImageView) holder.bannerItem;
        Glide.with(context).load(url).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(position % urlList.size());
                }
            }
        });
        if (itemHeight == 0 || itemWidth == 0) {
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    // this will give you cell width dynamically
                    itemWidth = holder.itemView.getWidth();
                    // this will give you cell height dynamically
                    itemHeight = holder.itemView.getHeight();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //所谓的无限轮播，就是把数据重复到一个很大的总和，总有循环完的一天，只是这个时间很长，基本可以忽略
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemWidth() {

        return itemWidth;
    }

    @Override
    public int getItemHeight() {
        return itemHeight;
    }

    static class NormalHolder extends RecyclerView.ViewHolder {
        ImageView bannerItem;

        NormalHolder(final View itemView) {
            super(itemView);
            bannerItem = (ImageView) itemView.findViewById(R.id.iv_banner);
        }

    }

}
