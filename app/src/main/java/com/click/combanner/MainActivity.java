package com.click.combanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.click.combanner.bean.Banner;
import com.click.lib_banner.vgbanner.ComBanner;
import com.click.lib_banner.vgbanner.ComViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ComBanner comBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        comBanner = findViewById(R.id.cBanner);
        List<Banner> bannerList = new ArrayList<>();
        bannerList.add(new Banner("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3594295685,223203405&fm=58&bpow=400&bpoh=578", "这是banner0", 1));
        bannerList.add(new Banner("https://upload.jianshu.io/users/upload_avatars/5967497/9576ae38-d524-49f2-99dc-c406c13296be?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240", "这是banner1", 1));
        bannerList.add(new Banner("https://desk-fd.zol-img.com.cn/t_s208x130c5/g5/M00/01/0E/ChMkJ1bKwYeIX2fUAAiuu_mEKkgAALGZgNRy6AACK7T964.jpg", "这是banner2", 0));
        bannerList.add(new Banner("https://desk-fd.zol-img.com.cn/t_s208x130c5/g5/M00/01/0E/ChMkJ1bKwXaIXdnDAAjG6xki36wAALGYgPXmb0ACMcD412.jpg", "这是banner3", 0));
        bannerList.add(new Banner("https://upload.jianshu.io/users/qrcodes/5967497/IMG_0658.JPG?imageMogr2/auto-orient/strip|imageView2/1/w/84/h/84", "这是banner4", 1));

        if (bannerList != null && bannerList.size() > 0) {
            comBanner.setPages((ComViewHolderCreator<ComImageHolderView>) () -> new ComImageHolderView(), bannerList)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.ic_slices_foused, R.drawable.ic_slices})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ComBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setPageIndicatorBottom(40)
                    .setOnItemClickListener(position -> {
                        Toast.makeText(this, "点击的位置  " + position, Toast.LENGTH_LONG).show();
                    });
            // 大于1张才轮播
            boolean on = bannerList.size() > 1 ? true : false;
            comBanner.setCanLoop(on);
            comBanner.setManualPageable(on);
            comBanner.setPointViewVisible(on);
            comBanner.startTurning(3000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始轮播
        if (comBanner != null) {
            comBanner.startTurning(3000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (comBanner != null) {
            comBanner.stopTurning();
        }
    }
}
