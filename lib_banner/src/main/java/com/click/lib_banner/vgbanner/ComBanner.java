package com.click.lib_banner.vgbanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;


import com.click.lib_banner.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * viewPage实现的广告栏
 * 支持无限循环，自动翻页，翻页特效
 *
 * @ClassName :  ConvenientBanner
 * @Description : 公用adapter
 * @Author jhonjson
 * @Date 2019.8.14
 */
public class ComBanner<T> extends LinearLayout {
    /**
     * view数据
     */
    private List<T> mDatas;
    /**
     * 点点数据
     */
    private int[] page_indicatorId;
    /**
     * 图片数据
     */
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    /**
     * 滑动位置数据
     */
    private ComPageChangeListener pageChangeListener;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ComPageAdapter pageAdapter;
    private ComLoopViewPager viewPager;
    private ComViewPagerScroller scroller;
    private ViewGroup lPagePoint;
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private boolean canLoop = true;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    private AdSwitchTask adSwitchTask;

    public ComBanner(Context context) {
        super(context);
    }

    public ComBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ComBanner);
        canLoop = a.getBoolean(R.styleable.ComBanner_canLoop, true);
        a.recycle();
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ComBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ComBanner);
        canLoop = a.getBoolean(R.styleable.ComBanner_canLoop, true);
        a.recycle();
        init(context);
    }


    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(
                R.layout.view_com_banner_viewpager, this, true);
        viewPager = (ComLoopViewPager) hView.findViewById(R.id.cLoopViewPager);
        lPagePoint = (ViewGroup) hView
                .findViewById(R.id.lPagePoint);
        initViewPagerScroll();

        adSwitchTask = new AdSwitchTask(this);
    }

    static class AdSwitchTask implements Runnable {

        private final WeakReference<ComBanner> reference;

        AdSwitchTask(ComBanner comBanner) {
            this.reference = new WeakReference<ComBanner>(comBanner);
        }

        @Override
        public void run() {
            ComBanner comBanner = reference.get();

            if (comBanner != null) {
                if (comBanner.viewPager != null && comBanner.turning) {
                    int page = comBanner.viewPager.getCurrentItem() + 1;
                    comBanner.viewPager.setCurrentItem(page);
                    // 先停止
                    if (comBanner.turning) {
                        comBanner.removeCallbacks(comBanner.adSwitchTask);
                    }
                    comBanner.postDelayed(comBanner.adSwitchTask, comBanner.autoTurningTime);
                }
            }
        }
    }

    public ComBanner setPages(ComViewHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        pageAdapter = new ComPageAdapter(holderCreator, mDatas);
        viewPager.setAdapter(pageAdapter, canLoop);

        if (page_indicatorId != null) {
            setPageIndicator(page_indicatorId);
        }
        return this;
    }

    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (page_indicatorId != null) {
            setPageIndicator(page_indicatorId);
        }
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public ComBanner setPointViewVisible(boolean visible) {
        lPagePoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public ComBanner setPageIndicator(int[] page_indicatorId) {
        lPagePoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (mDatas == null) {
            return this;
        }
        for (int count = 0; count < mDatas.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(10, 10, 10, 0);
            if (mPointViews.isEmpty()) {
                pointView.setImageResource(page_indicatorId[1]);
            } else {
                pointView.setImageResource(page_indicatorId[0]);
            }
            mPointViews.add(pointView);
            lPagePoint.addView(pointView);
        }
        pageChangeListener = new ComPageChangeListener(mPointViews,
                page_indicatorId);
        viewPager.addOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(viewPager.getRealItem());
        if (onPageChangeListener != null) {
            pageChangeListener.addOnPageChangeListener(onPageChangeListener);
        }

        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public ComBanner setPageIndicator(int[] page_indicatorId, int padding) {
        lPagePoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (mDatas == null) {
            return this;
        }
        for (int count = 0; count < mDatas.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(padding, padding, padding, 0);
            if (mPointViews.isEmpty()) {
                pointView.setImageResource(page_indicatorId[1]);
            } else {
                pointView.setImageResource(page_indicatorId[0]);
            }
            mPointViews.add(pointView);
            lPagePoint.addView(pointView);
        }
        pageChangeListener = new ComPageChangeListener(mPointViews,
                page_indicatorId);
        viewPager.addOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(viewPager.getRealItem());
        if (onPageChangeListener != null) {
            pageChangeListener.addOnPageChangeListener(onPageChangeListener);
        }

        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public ComBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lPagePoint.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        lPagePoint.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 指示器居底部的位置
     *
     * @param bottom
     * @return
     */
    public ComBanner setPageIndicatorBottom(int bottom) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lPagePoint.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, bottom);
        lPagePoint.setLayoutParams(layoutParams);
        return this;
    }


    /**
     * viewPager宽高
     */
    public ComBanner setViewPagerSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) viewPager.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        viewPager.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     * @return
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public ComBanner startTurning(long autoTurningTime) {
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public ComBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }


    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ComViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return viewPager.isCanScroll();
    }

    public void setManualPageable(boolean manualPageable) {
        viewPager.setCanScroll(manualPageable);
    }

    /**
     * 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) {
                startTurning(autoTurningTime);
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) {
                stopTurning();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 获取当前的页面index
     *
     * @return
     */
    public int getCurrentItem() {
        if (viewPager != null) {
            return viewPager.getRealItem();
        }
        return -1;
    }

    /**
     * 设置当前的页面index
     *
     * @param index
     */
    public void setCurrentItem(int index) {
        if (viewPager != null) {
            viewPager.setCurrentItem(index);
        }
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     *
     * @param onPageChangeListener
     * @return
     */
    public ComBanner addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (pageChangeListener != null) {
            pageChangeListener.addOnPageChangeListener(onPageChangeListener);
        } else {
            viewPager.addOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    public boolean isCanLoop() {
        return viewPager.isCanLoop();
    }

    /**
     * 监听item点击
     *
     * @param onItemClickListener
     */
    public ComBanner setOnItemClickListener(ComOnBannerItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            viewPager.setOnItemClickListener(null);
            return this;
        }
        viewPager.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 设置ViewPager的滚动速度
     *
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration) {
        scroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return scroller.getScrollDuration();
    }

    public ComLoopViewPager getViewPager() {
        return viewPager;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        viewPager.setCanLoop(canLoop);
    }
}
