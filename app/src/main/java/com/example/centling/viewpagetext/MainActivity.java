package com.example.centling.viewpagetext;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {
    /**
     * ViewPager
     */
    private ViewPager viewPager;

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    /**
     * 图片资源id
     */
    private int[] imgIdArray;
    RadioGroup groupDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupDot = (RadioGroup) findViewById(R.id.rg_dot_group);//轮播图小红点
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);

        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.im_radio, R.drawable.im_recognise_top, R.drawable.im_test};


        //将点点加入到ViewGroup中
        tips = new ImageView[imgIdArray.length];
        initDot(imgIdArray.length);
/*        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.im_dot_normal);
            } else {
                tips[i].setBackgroundResource(R.drawable.im_dot_press);
            }

            groupDot.addView(imageView);
        }*/


        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }

        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((mImageViews.length) * 100);

    }

    /**
     * 轮播小红点
     *
     * @param size
     */
    private void initDot(int size) {

//        int count = size%12==0?size/12:size/12+1;
        int count = size;
        groupDot.removeAllViews();
        if (count == 1) {
            groupDot.setVisibility(View.GONE);
        } else {
            groupDot.setVisibility(View.VISIBLE);
            for (int i = 0; i < count; i++) {
                groupDot.addView(createRadioButton());
            }
            if (groupDot.getChildAt(0) != null) {
                ((RadioButton) groupDot.getChildAt(0)).setChecked(true);
            }
        }
    }

    /**
     * 创建选中点
     */
    private RadioButton createRadioButton() {
        RadioButton mRadioButton = new RadioButton(this);
        RadioGroup.LayoutParams mLayoutParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(12, 0, 12, 0);
        mRadioButton.setLayoutParams(mLayoutParams);
        mRadioButton.setClickable(false);
        mRadioButton.setButtonDrawable(getResources().getDrawable(R.drawable.selector_dot_shop_filter));
        return mRadioButton;
    }


    /**
     * @author xiaanming
     */
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }


    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int i) {
      //  ((RadioButton) (groupDot.getChildAt(i))).setChecked(true);

    }


}