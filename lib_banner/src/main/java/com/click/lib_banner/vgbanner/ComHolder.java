package com.click.lib_banner.vgbanner;

import android.content.Context;
import android.view.View;

/**
 * @ClassName :  ComHolder
 * @Description : 适用于任何
 * @Author jhonjson
 * @Date 2019.8.14
 */
public interface ComHolder<T> {
    /**
     * 创建
     */
    View createView(Context context);

    /**
     * 更新
     */
    void UpdateUI(Context context, int position, T data);
}