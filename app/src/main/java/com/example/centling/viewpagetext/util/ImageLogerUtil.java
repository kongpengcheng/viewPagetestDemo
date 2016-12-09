package com.example.centling.viewpagetext.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by centling on 2016/10/12.
 */
public class ImageLogerUtil {

    /**
     * 初始化ImageLoader
     *
     * @param context
     * @date 2016-3-16 上午11:10:28
     * @fileTag 方法说明：
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize((int) (2 * 1024 * 1024))
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 显示图片带有默认的图片显示
     * Shorr 2016/10/24 add
     *
     * @param imageView  要显示的ImageView
     * @param imageUrl   图片Url
     * @param defaultRes 默认的图片资源
     */
    public static void showImageWithDefault(ImageView imageView, String imageUrl, int defaultRes) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultRes)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    /**
     * 显示图片带有默认的图片显示
     * Shorr 2016/10/24 add
     *
     * @param imageView  要显示的ImageView
     * @param imageUrl   图片Url
     * @param defaultRes 默认的图片资源
     */
    public static void showImageWithDefaultViewpage(ImageView imageView, String imageUrl, int defaultRes) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultRes)
                .showImageForEmptyUri(defaultRes)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    /**
     * 显示圆角图片带有默认的图片显示
     * Shorr 2016/10/24 add
     *
     * @param imageView  要显示的ImageView
     * @param imageUrl   图片Url
     * @param defaultRes 默认的图片资源
     */
    public static void showCornerImageWithDefault(ImageView imageView, String imageUrl, int defaultRes,int corner) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultRes)
                .showImageForEmptyUri(defaultRes)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(corner))
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

}
