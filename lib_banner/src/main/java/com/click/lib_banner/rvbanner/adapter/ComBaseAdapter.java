package com.click.lib_banner.rvbanner.adapter;


import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName :  ConvenientBanner
 * @Description : 对适配器封装一层，增加返回item的宽度与高度的方法
 * @Author jhonjson
 * @Date 2019.8.14
 */
public abstract class ComBaseAdapter<N extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<N> {
    /**
     * 获取Item的宽度
     */
    public abstract int getItemWidth();

    /**
     * 获取Item的高度
     */
    public abstract int getItemHeight();

    @Override
    public void onBindViewHolder(N holder, int position) {

    }
}
