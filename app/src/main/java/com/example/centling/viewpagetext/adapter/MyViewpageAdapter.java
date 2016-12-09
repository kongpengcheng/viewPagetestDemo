package com.example.centling.viewpagetext.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.centling.viewpagetext.R;
import com.example.centling.viewpagetext.model.VideoBannerBean;
import com.example.centling.viewpagetext.util.ImageLogerUtil;

import java.util.List;


/**
 * Created by kong.harry on 2016/10/24.
 */
public class MyViewpageAdapter extends PagerAdapter {

    private List<VideoBannerBean> imageList;
    private Context myContext;

    public void steImageLIst(List<VideoBannerBean> imageList) {
        this.imageList = imageList;
    }

    public MyViewpageAdapter(Context context, List<VideoBannerBean> imageList) {
        this.imageList = imageList;
        myContext = context;
    }

    @Override
    public int getCount() {
        return this.imageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(myContext);
        //动态设置宽高
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = 400;
        params.width = 400;
        imageView.setLayoutParams(params);
        imageView.setLayoutParams(params);
        Log.d("image--->", "地址" + imageList.get(position).getPicture());
        ImageLogerUtil.showImageWithDefaultViewpage(imageView, imageList.get(position).getPicture(), R.drawable.lunbo2);
        if (imageView.getParent() != null) {
            ((ViewPager) imageView.getParent()).removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //  container.removeView(imageViews.get(position % imageViews.size()));
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
