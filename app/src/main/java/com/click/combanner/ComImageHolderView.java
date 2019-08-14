package com.click.combanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.click.combanner.bean.Banner;
import com.click.lib_banner.vgbanner.ComHolder;

/**
 * @Author: jhonjson
 * @Date: 2019-08-14
 * @Description: banner 展示
 */

public class ComImageHolderView implements ComHolder<Banner> {
    private View inflate;
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        inflate = LayoutInflater.from(context).inflate(R.layout.view_rv_banner_item, null);
        imageView = inflate.findViewById(R.id.iv_banner);
        return inflate;
    }

    @Override
    public void UpdateUI(Context context, int position, Banner data) {
        Glide.with(context).load(data.getPicUrl()).into(imageView);
//        ImageLoaderUtils.getInstance().loadPicture(data.getImage(), imageView, R.drawable.game_area_image_bg, R.drawable.game_area_image_bg);

    }
}
