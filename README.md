# ComBanner
此项目用于大家快速集成banner

1. 基于viewpage实现的banner
2. 基于RecyclerView实现的banner

效果图：

![效果图1](img/device-2019-08-14-182600.gif)

1. 基于viewpage实现的banner功能实现：

	1. 高度高度配置
	2. 选中原点自己配置
	3. 选中原点高度位置自己配置
	4. 是否自动播放可配置
2. 基于RecyclerView实现的banner
   1. 高度高度配置


集成步骤：

gradle

```
allprojects {
  repositories {
	    maven { url 'https://jitpack.io' }
		}
}

```

```
dependencies {
	implementation 'com.github.Johnjson:ComBanner:Tag'
	}

```

maven

```
<repositories>
	<repository>
		 <id>jitpack.io</id>
		 <url>https://jitpack.io</url>
	</repository>
</repositories>

```

```
<dependency>
	<groupId>com.github.Johnjson</groupId>
	  <artifactId>ComBanner</artifactId>
	<version>Tag</version>
</dependency>

```

基于viewpage实现的banner代码中实现：

```
<com.click.lib_banner.vgbanner.ComBanner
        android:id="@+id/cBanner"
        android:layout_marginTop="10dp"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        card_view:canLoop="true" />
```

```
 comBanner.setPages((ComViewHolderCreator<ComImageHolderView>) () -> new ComImageHolderView(), bannerList)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.ic_slices_foused, R.drawable.ic_slices})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ComBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setPageIndicatorBottom(40)
                    .setOnItemClickListener(position -> {

                    });
            // 大于1张才轮播
            boolean on = bannerList.size() > 1 ? true : false;
            comBanner.setCanLoop(on);
            comBanner.setManualPageable(on);
            comBanner.setPointViewVisible(on);
            comBanner.startTurning(3000);

```

```
public class ComImageHolderView implements ComHolder<Banner> {
    private View inflate;
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        inflate = LayoutInflater.from(context).inflate(R.layout.view_banner_item, null);
        imageView = inflate.findViewById(R.id.iv_banner);
        return inflate;
    }

    @Override
    public void UpdateUI(Context context, int position, Banner data) {
        Glide.with(context).load(data.getPicUrl()).into(imageView);
//        ImageLoaderUtils.getInstance().loadPicture(data.getImage(), imageView, R.drawable.game_area_image_bg, R.drawable.game_area_image_bg);

    }
}

```

```
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

```

基于RecyclerView实现的banner代码中实现：
```
 /** 基于RecyclerView实现的banner*/
        comRecyclerViewBanner.initBannerImageView(sList, new ComRecyclerViewBannerBase.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "clicked:" + position, Toast.LENGTH_SHORT).show();
            }
        });
```

*****************************************************************************
v1.1.0
基于RecyclerView实现的bannner

v1.0.0
基于viewpage实现的bannner

此项目持续维护中，如有问题 请加QQ：254547297
![效果图1](img/C80925D365ADDABBC60EF71DE1C5B152.jpg)




