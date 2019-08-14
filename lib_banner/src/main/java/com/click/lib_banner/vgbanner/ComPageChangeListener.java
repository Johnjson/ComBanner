package com.click.lib_banner.vgbanner;

import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * @ClassName :  ComPageChangeListener
 * @Description : 公用adapter
 * @Author jhonjson
 * @Date 2019.8.14
 * 翻页指示器适配器
 */
public class ComPageChangeListener implements ViewPager.OnPageChangeListener {
    /**
     * 图片集合
     */
    private ArrayList<ImageView> pointViews;
    /**
     * 点点集合
     */
    private int[] page_indicatorId;
    /**
     * 滑动事件
     */
    private ViewPager.OnPageChangeListener onPageChangeListener;

    public ComPageChangeListener(ArrayList<ImageView> pointViews, int[] page_indicatorId) {
        this.pointViews = pointViews;
        this.page_indicatorId = page_indicatorId;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    /**
     * 滑动位置
     */
    @Override
    public void onPageSelected(int index) {
        for (int i = 0; i < pointViews.size(); i++) {
            pointViews.get(index).setImageResource(page_indicatorId[1]);
            if (index != i) {
                pointViews.get(i).setImageResource(page_indicatorId[0]);
            }
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(index);
        }

    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
