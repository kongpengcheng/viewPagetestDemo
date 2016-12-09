package com.example.centling.viewpagetext;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.centling.viewpagetext.adapter.MyViewpageAdapter;
import com.example.centling.viewpagetext.model.VideoBannerBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Harry.Kong on 2016/12/9.
 */

public class ViewPagerDemo extends Activity {
    private ViewPager viewPager;
    private RadioGroup groupDot;
    private int currentItem = 1;
    Thread thread;
    private MyViewpageAdapter pageAdapter;
    boolean nowAction = false;// 当前用户正在滑动视图
    private List<VideoBannerBean> videoBannerBeenList = new ArrayList<>();//传到adapter的list
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 19://轮播时间
                    viewPager.setCurrentItem(currentItem);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page_demo);
        iniDate();
        initView();

        initDot(videoBannerBeenList.size() - 2);//初始化小圆点

        thread = new Thread(new ScrollTask());
        thread.start();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp);
        groupDot = (RadioGroup) findViewById(R.id.rg_dot_group);
        pageAdapter = new MyViewpageAdapter(ViewPagerDemo.this, videoBannerBeenList);
        pageAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        pageAdapter.notifyDataSetChanged();


    }

    private void iniDate() {
        VideoBannerBean videoBannerBean5 = new VideoBannerBean();
        videoBannerBean5.setType(3);
        videoBannerBean5.setPicture("drawable://" + R.drawable.lunbo2);
        videoBannerBeenList.add(videoBannerBean5);
        VideoBannerBean videoBannerBean = new VideoBannerBean();
        videoBannerBean.setType(3);
        videoBannerBean.setPicture("drawable://" + R.drawable.im_recognise_top);
        videoBannerBeenList.add(videoBannerBean);
        VideoBannerBean videoBannerBean1 = new VideoBannerBean();
        videoBannerBean1.setType(3);
        videoBannerBean1.setPicture("http://onehaiercms.oss-cn-qingdao.aliyuncs.com/201612061442140000.png");
        videoBannerBeenList.add(videoBannerBean1);
        VideoBannerBean videoBannerBean2 = new VideoBannerBean();
        videoBannerBean2.setType(3);
        videoBannerBean2.setPicture("http://zhuangbi.idagou.com/i/2015-10-19-23c9cad27ff55c766f97014069d22777.gif");
        videoBannerBeenList.add(videoBannerBean2);
        VideoBannerBean videoBannerBean3 = new VideoBannerBean();
        videoBannerBean3.setType(3);
        videoBannerBean3.setPicture("drawable://" + R.drawable.lunbo2);
        videoBannerBeenList.add(videoBannerBean3);
        videoBannerBeenList.add(videoBannerBean);
    }

    /**
     * @author kong
     */
    private class ScrollTask implements Runnable {

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentItem = currentItem + 1;
                Message message = new Message();
                message.what = 19;
                message.obj = currentItem;
                handler.sendMessage(message);
                System.out.println("ASD: " + currentItem);


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
            int point;
            if (position == 0) {
                point = videoBannerBeenList.size() - 3;
                viewPager.setCurrentItem(videoBannerBeenList.size() - 2, false);
            } else if (position == videoBannerBeenList.size() - 1) {
                point = 0;
                viewPager.setCurrentItem(1, false);
            } else {
                point = position - 1;
            }
            if (videoBannerBeenList.size() == 3) {

            } else {
                ((RadioButton) (groupDot.getChildAt(point))).setChecked(true);
            }

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
                nowAction = true;
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
        int count = size;
        groupDot.removeAllViews();
        if (count == 1) {
            groupDot.setVisibility(View.VISIBLE);
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
        RadioGroup.LayoutParams mLayoutParams = new RadioGroup.LayoutParams(22, 22);
        mLayoutParams.setMargins(0, 0, 10, 0);
        mRadioButton.setLayoutParams(mLayoutParams);
        mRadioButton.setClickable(false);
        mRadioButton.setButtonDrawable(getResources().getDrawable(R.drawable.rediogroup_pot));
        return mRadioButton;
    }
}
