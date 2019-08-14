package com.click.lib_banner.vgbanner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * viewPage实现的广告栏
 * 支持无限循环，自动翻页，翻页特效
 * @ClassName :  ComViewPagerScroller
 * @Description : 公用adapter
 * @Author jhonjson
 * @Date 2019.8.14
 */
public class ComViewPagerScroller extends Scroller {
	/**
	 * 滑动速度,值越大滑动越慢，滑动太快会使3d效果不明显
	 */
	private int mScrollDuration = 800;
	private boolean zero;

	public ComViewPagerScroller(Context context) {
		super(context);
	}

	public ComViewPagerScroller(Context context, Interpolator interpolator) {
		super(context, interpolator);
	}

	public ComViewPagerScroller(Context context, Interpolator interpolator,
								boolean flywheel) {
		super(context, interpolator, flywheel);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		super.startScroll(startX, startY, dx, dy, zero ? 0 : mScrollDuration);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy) {
		super.startScroll(startX, startY, dx, dy, zero ? 0 : mScrollDuration);
	}

	public int getScrollDuration() {
		return mScrollDuration;
	}

	public void setScrollDuration(int scrollDuration) {
		this.mScrollDuration = scrollDuration;
	}

}