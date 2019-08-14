package com.click.lib_banner.vgbanner;

import android.view.View;
import android.view.ViewGroup;


import androidx.viewpager.widget.PagerAdapter;

import com.click.lib_banner.R;

import java.util.List;

/**
 * @ClassName :  ComPageAdapter
 * @Description : 公用adapter
 * @Author jhonjson
 * @Date 2019.8.14
 */
public class ComPageAdapter<T> extends PagerAdapter {
    /**
     * 数据源
     */
    protected List<T> mDatas;
    /**
     * 默认数据个数
     */
    private final int MULTIPLE_COUNT = 300;
    protected ComViewHolderCreator holderCreator;
    private boolean canLoop = true;
    private ComLoopViewPager viewPager;


    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        int realPosition = position % realCount;
        return realPosition;
    }

    @Override
    public int getCount() {
        return canLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);

        View view = getView(realPosition, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = viewPager.getFristItem();
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(ComLoopViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ComPageAdapter(ComViewHolderCreator holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
    }

    public View getView(int position, View view, ViewGroup container) {
        ComHolder holder;
        if (view == null) {
            holder = (ComHolder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.com_item_tag, holder);
        } else {
            holder = (ComHolder<T>) view.getTag(R.id.com_item_tag);
        }
        if (mDatas != null && !mDatas.isEmpty()) {
            holder.UpdateUI(container.getContext(), position, mDatas.get(position));
        }
        return view;
    }
}
