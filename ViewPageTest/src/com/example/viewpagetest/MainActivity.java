package com.example.viewpagetest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected static final String TAG = "MainActivity";
	private ViewPager viewPage;
	private int[] images = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
	private String[] descs = { "巩俐不低俗，我就不能低俗", "扑树又回来啦！再唱经典老歌引万人大合唱", "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀", };
	private TextView pic_desc;
	private LinearLayout dots_containtor;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPage.setCurrentItem(viewPage.getCurrentItem() + 1);
			sendEmptyMessageDelayed(0, 3000);
		};
	};
	// 标记page是否是拖动状态
	private boolean isdragging = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPage = (ViewPager) findViewById(R.id.viewpage);
		pic_desc = (TextView) findViewById(R.id.pic_desc);
		dots_containtor = (LinearLayout) findViewById(R.id.dots_containtor);

		// 获取手机屏幕的相关信息
		init();
		// 初始化小圆点
		initDots();
		viewPage.setAdapter(new PagerAdapter() {

			// 有多少个条目
			@Override
			public int getCount() {
				// return images.length;
				return 2000000;
			}

			// 条目是否从缓存中取出
			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			// 初始化一个条目
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				int realPosition = position % images.length;
				Log.d(TAG, "instantiateItem" + position);
				ImageView imageView = new ImageView(container.getContext());
				imageView.setImageResource(images[realPosition]);
				// 设置触摸事件
				imageView.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							// 移除消息队列中的所有的消息
							handler.removeCallbacksAndMessages(null);
							break;

						case MotionEvent.ACTION_UP:
							handler.sendMessageDelayed(new Message(), 3000);
							break;
						}
						return true;
					}
				});
				container.addView(imageView);
				return imageView;
			}

			// 销毁一个条目
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				Log.d(TAG, "destroyItem" + position);
				container.removeView((View) object);
			}

		});
		// 设置中间位置
		int item = 1000000;
		// 一开始就选择了第0个
		// 设置文本
		pic_desc.setText(descs[item % images.length]);
		// 设置小圆点
		for (int i = 0; i < dots_containtor.getChildCount(); i++) {
			if (i == (item % images.length)) {
				dots_containtor.getChildAt(i).setSelected(true);
			} else {
				dots_containtor.getChildAt(i).setSelected(false);
			}

		}
		viewPage.setCurrentItem(item);
		// 发消息
		handler.sendMessageDelayed(new Message(), 3000);

		// 给ViewPage设置监听器
		viewPage.setOnPageChangeListener(new OnPageChangeListener() {

			// 选择某一个page时调用
			@Override
			public void onPageSelected(int position) {
				Log.d(TAG, "position====" + position);
				int realPosition = position % images.length;
				change(realPosition);
			}

			// 滑动page时调用
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			// 状态改变
			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_DRAGGING) {
					isdragging = true;
					handler.removeCallbacksAndMessages(null);
				} else if (state == ViewPager.SCROLL_STATE_IDLE && isdragging) {
					isdragging = false;
					handler.removeCallbacksAndMessages(null);
					handler.sendMessageDelayed(new Message(), 3000);
				} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
					
				}
			}
		});
	}

	private void change(int position) {
		// 一开始就选择了第0个
		// 设置文本
		pic_desc.setText(descs[position]);
		// 设置小圆点
		for (int i = 0; i < dots_containtor.getChildCount(); i++) {
			if (i == position) {
				dots_containtor.getChildAt(i).setSelected(true);
			} else {
				dots_containtor.getChildAt(i).setSelected(false);
			}

		}
	}

	private void initDots() {
		for (int i = 0; i < images.length; i++) {
			View dot = new View(this);
			int _8dp = px2dip(8);
			android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(_8dp, _8dp);
			dot.setBackgroundResource(R.drawable.dot_selector);
			if (i > 0) {
				params.leftMargin = _8dp;
			}
			dots_containtor.addView(dot, params);
		}
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public int px2dip(float pxValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public int dip2px(float dpValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	private void init() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		// 图片充满屏幕所对应的高 970*350
		int height = 350 * width / 970;
		// 获取父容器的参数,因为自己不能设置自己的参数,必须由父容器来设置
		// TODO
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewPage.getLayoutParams();
		// LayoutParams params = viewPage.getLayoutParams();
		System.out.println("height" + params.height);
		System.out.println("width" + params.width);
		params.height = height;
		viewPage.setLayoutParams(params);

	}

}
