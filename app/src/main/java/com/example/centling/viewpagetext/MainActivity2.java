package com.example.centling.viewpagetext;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kong
 * on 2016/8/12.
 */
public class MainActivity2 extends Activity {
    private ViewPager viewPager;
    private List<ImageView> imageViews;
    private int[] imageResId;
    //  private List<View> dots;
    RadioGroup groupDot;
    /**
     * 图片资源id
     */
    private int currentItem = 1;
    private MyAdapter pageAdapter;
    boolean nowAction = false;// 当前用户正在滑动视图
    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        groupDot = (RadioGroup) findViewById(R.id.rg_dot_group);//轮播图小红点
        //载入图片资源ID
        imageResId = new int[]{R.drawable.im_radio, R.drawable.im_recognise_top, R.drawable.im_test, R.drawable.lunbo2
        };
        initDot(imageResId.length);
        imageViews = new ArrayList<ImageView>();
        //  循环把图片放在list里面
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        ImageView firstImageview = new ImageView(this);
        firstImageview.setImageResource(imageResId[imageResId.length - 1]);
        ImageView lastImageview = new ImageView(this);
        lastImageview.setImageResource(imageResId[0]);
        imageViews.add(0, firstImageview);
        imageViews.add(lastImageview);

        viewPager = (ViewPager) findViewById(R.id.vp);
        pageAdapter = new MyAdapter(MainActivity2.this, imageViews);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 4);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * ScrollTask--这是被调度的任务。
     * 1000*5--这是以毫秒为单位的延迟之前的任务执行。
     * 1000*10--这是在连续执行任务之间的毫秒的时间。
     */
    @Override
    protected void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3,
                TimeUnit.SECONDS);
        // scheduledExecutorService.scheduleWithFixedDelay(new ScrollTask(), 1000*5, 1000*10, TimeUnit.MILLISECONDS);
        super.onStart();
    }

    @Override
    protected void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    /**
     * @author kong
     */
    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (viewPager) {
                if (!nowAction) {
                    System.out.println("currentItem: " + currentItem);
                    currentItem = currentItem + 1;
                    handler.obtainMessage().sendToTarget();
                }
            }
        }
    }

    /**
     * @author kong
     *         这是viewpage的监听
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageSelected(int position) {
            currentItem = position;
            //   ((RadioButton) (groupDot.getChildAt(currentItem % imageViews.size()))).setChecked(true);
            //  ((RadioButton) (groupDot.getChildAt(position))).setChecked(true);
            int num = 1;
            int point ;
            if (position == 0) {
                point = imageResId.length - 1;
                num = imageViews.size() - 2;
                viewPager.setCurrentItem(imageViews.size() - 2, false);
            } else if (position == imageViews.size() - 1) {
                num = 1;
                point = 0;
                viewPager.setCurrentItem(1, false);
            } else {
                point = position - 1;
                num = position;
            }
            ((RadioButton) (groupDot.getChildAt(point))).setChecked(true);
            // changeDotsBg(currentItem % imageViews.size());
        }

        // 其中arg0这个参数
        // 有三种状态（0，1，2）。
        // arg0 == 1的时辰默示正在滑动，
        // arg0 == 2的时辰默示滑动完毕了，
        // arg0 == 0的时辰默示什么都没做。
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 0) {
                nowAction = false;
            }
            if (arg0 == 1) {
                nowAction = true;
            }
            if (arg0 == 2) {
            }
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 轮播小红点
     *
     * @param size
     */
    private void initDot(int size) {

//       int count = size%12==0?size/12:size/12+1;
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
        mLayoutParams.setMargins(18, 0, 18, 0);
        mRadioButton.setLayoutParams(mLayoutParams);
        mRadioButton.setClickable(false);
        mRadioButton.setButtonDrawable(getResources().getDrawable(R.drawable.rediogroup_pot));
        return mRadioButton;
    }


    /**
     * @author kong
     *         viewpage的adapter
     */
    private class MyAdapter extends PagerAdapter {

        private List<ImageView> imageList;

        public MyAdapter(Context context, List<ImageView> imageList) {
            this.imageList = imageList;
        }

        @Override
        public int getCount() {
            //   return Integer.MAX_VALUE / 2;
            return this.imageList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            imageViews.get(position % imageViews.size()).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(MainActivity2.this, position % imageViews.size() + "个数为", Toast.LENGTH_SHORT).show();
//                }
//            });
            //  ImageView imageView = imageViews.get(position % imageViews.size());\
            ImageView imageView = imageViews.get(position);
            if (imageView.getParent() != null) {
                ((ViewPager) imageView.getParent()).removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        //        @Override
//        public Object instantiateItem(View arg0, final int position) {
//
//            imageViews.get(position % imageViews.size()).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(MainActivity2.this, position % imageViews.size() + "个数为", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//            View view = null;
//            if (position % imageViews.size() < 0) {
//                view = imageViews.get(imageViews.size() + position);
//            } else {
//                view = imageViews.get(position % imageViews.size());
//            }
//            ViewParent vp = view.getParent();
//            if (vp != null) {
//                ViewGroup parent = (ViewGroup) vp;
//                parent.removeView(view);
//            }
//            ((ViewPager) arg0).addView(view);
//
//            return view;
//        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position % imageViews.size()));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}