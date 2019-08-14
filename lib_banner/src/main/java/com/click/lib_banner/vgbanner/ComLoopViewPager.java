package com.click.lib_banner.vgbanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * @ClassName :  ComLoopViewPager
 * @Description : 循环ViewPager
 * @Author jhonjson
 * @Date 2019.8.14
 */
public class ComLoopViewPager extends ViewPager {
    /**
     * 滑动事件
     */
    private OnPageChangeListener mOuterPageChangeListener;
    /**
     * 点击事件
     */
    private ComOnBannerItemClickListener onItemClickListener;
    /**
     * Adapter
     */
    private ComPageAdapter mAdapter;

    /**
     * 是否可以滑动
     */
    private boolean isCanScroll = true;
    /**
     * 是否滑动
     */
    private boolean canLoop = true;

    public void setOnItemClickListener(ComOnBannerItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setAdapter(PagerAdapter adapter, boolean canLoop) {
        mAdapter = (ComPageAdapter) adapter;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);

        setCurrentItem(getFristItem(), false);
    }

    public int getFristItem() {
        return canLoop ? mAdapter.getRealCount() : 0;
    }

    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    private float oldX = 0, newX = 0;
    private static final float sens = 5;
    private float oldY = 0, newY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        click(ev);
        return isCanScroll ? super.onTouchEvent(ev) : true;
    }

    /**
     * 点击事件
     */

    private void click(MotionEvent ev) {
        if (onItemClickListener != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    oldX = ev.getX();
                    oldY = ev.getY();
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    newX = ev.getX();
                    newY = ev.getY();
                    if (Math.abs(oldX - newX) < sens
                            && Math.abs(oldY - newY) < sens) {
                        onItemClickListener.onItemClick((getRealItem()));
                    }
                    oldX = 0;
                    newX = 0;
                    oldY = 0;
                    newY = 0;
                    break;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public ComPageAdapter getAdapter() {
        return mAdapter;
    }

    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }


    public ComLoopViewPager(Context context) {
        super(context);
        init();
    }

    public ComLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.addOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;

            if (mOuterPageChangeListener != null) {
                if (realPosition != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (canLoop == false) {
            setCurrentItem(getRealItem(), false);
        }
        if (mAdapter == null) {
            return;
        }
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }


}
