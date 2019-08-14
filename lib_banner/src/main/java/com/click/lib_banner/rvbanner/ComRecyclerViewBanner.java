package com.click.lib_banner.rvbanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.click.lib_banner.rvbanner.adapter.ComCustomRecyclerAdapter;

import java.util.List;

/**
 * @ClassName :  ComRecyclerViewBanner
 * * @Description : ComRecyclerViewBanner
 * * @Author jhonjson
 * * @Date 2019.8.14
 */

public class ComRecyclerViewBanner extends ComRecyclerViewBannerBase<LinearLayoutManager, ComCustomRecyclerAdapter> {

    private ComCustomRecyclerAdapter mMyCustomRecyclerAdapter;

    public ComRecyclerViewBanner(Context context) {
        this(context, null);
    }

    public ComRecyclerViewBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComRecyclerViewBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBannerScrolled(RecyclerView recyclerView, int dx, int dy) {
        //解决连续滑动时指示器不更新的问题
        if (bannerSize < 2) return;
        int firstReal = mLayoutManager.findFirstVisibleItemPosition();
        View viewFirst = mLayoutManager.findViewByPosition(firstReal);
        //当前布局的长度
        int layoutDistance = 0;
        if (LinearLayoutManager.HORIZONTAL == mLayoutManager.getOrientation()) {
            layoutDistance = null == mMyCustomRecyclerAdapter ? 0 : mMyCustomRecyclerAdapter.getItemWidth();
        } else {
            layoutDistance = null == mMyCustomRecyclerAdapter ? 0 : mMyCustomRecyclerAdapter.getItemHeight();
        }
        if (layoutDistance != 0 && viewFirst != null) {
            float viewDistance = 0;
            if (LinearLayoutManager.HORIZONTAL == mLayoutManager.getOrientation()) {
                viewDistance = viewFirst.getRight();
            } else {
                viewDistance = viewFirst.getBottom();
            }
            //item滑动的距离与布局长度的比例
            float ratio = viewDistance / layoutDistance;
            if (ratio > 0.8) {
                if (currentIndex != firstReal) {
                    currentIndex = firstReal;
                    refreshIndicator();
                }
            } else if (ratio < 0.2) {
                if (currentIndex != firstReal + 1) {
                    currentIndex = firstReal + 1;
                    refreshIndicator();
                }
            }
        }
    }

    @Override
    protected void onBannerScrollStateChanged(RecyclerView recyclerView, int newState) {
        int first = mLayoutManager.findFirstVisibleItemPosition();
        int last = mLayoutManager.findLastVisibleItemPosition();
        if (currentIndex != first && first == last) {
            currentIndex = first;
            refreshIndicator();
        }
    }

    @Override
    protected LinearLayoutManager getLayoutManager(Context context, int orientation) {
        return new LinearLayoutManager(context, orientation, false);
    }

    @Override
    protected ComCustomRecyclerAdapter getAdapter(Context context, List<String> list, OnBannerItemClickListener onBannerItemClickListener) {
        mMyCustomRecyclerAdapter = new ComCustomRecyclerAdapter(context, list, onBannerItemClickListener);
        return mMyCustomRecyclerAdapter;
    }


}