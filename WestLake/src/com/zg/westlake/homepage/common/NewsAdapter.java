package com.zg.westlake.homepage.common;

import java.util.ArrayList;

import com.zg.westlake.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class NewsAdapter extends BaseAdapter {
	// 定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;
	// 定义一个ArrayList来存放View
	private ArrayList<View> views;
	// 引导图片资源
	private static final int[] PICS = { R.drawable.image_1, R.drawable.image_2,
			R.drawable.image_3, R.drawable.image_4 };
	private ViewPager viewPager;
	Context mContext;
	private View topView;
	// 底部小点的图片
	private ImageView[] points;
	// 记录当前选中位置
	private int currentIndex;

	public NewsAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		views = new ArrayList<View>();
		vpAdapter = new ViewPagerAdapter(views);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub

		if (position == 0)
			return 0;
		else
			return position - 1;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position > 0 ? 0 : 1;

	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (position == 0) {
			return getTopView(convertView);
		} else {

			View view = LayoutInflater.from(mContext).inflate(
					R.layout.list_item, null);

			return view;

		}
	}

	private View getTopView(View convertView) {

		if (topView == null) {
			topView = LayoutInflater.from(mContext).inflate(
					R.layout.home_page_title_viewpager, null);

			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 240);

			layoutParams.gravity = Gravity.TOP;
			// 页码指示点
			// 图片切换控件
			viewPager = (ViewPager) topView.findViewById(R.id.title_viewpager);
			viewPager.setLayoutParams(layoutParams);

			for (int i = 0; i < PICS.length; i++) {
				ImageView image = new ImageView(mContext);
				image.setBackgroundColor(Color.WHITE);
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				image.setImageResource(PICS[i]);
				views.add(image);
			}

			viewPager.setAdapter(vpAdapter);
			//viewPager.setCurrentItem(0);
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					// TODO Auto-generated method stub
					setCurDot(position);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});

			initPoint();
		}

		return topView;
	}

	// 初始化底部小点
	private void initPoint() {
		LinearLayout lineLayout = (LinearLayout) topView
				.findViewById(R.id.imagelinearLayout);
		points = new ImageView[PICS.length];
		for (int j = 0; j < PICS.length; j++) {
			ImageView pointImgView = new ImageView(mContext);
			pointImgView.setImageResource(R.drawable.point);
			pointImgView.setPadding(15, 15, 15, 15);
			lineLayout.addView(pointImgView);
			points[j] = (ImageView) lineLayout.getChildAt(j);
			// 默认都设为灰色
			points[j].setEnabled(true);
			// 设置位置tag，方便取出与当前位置对应
			points[j].setTag(j);
		}
		// 设置当面默认的位置
		currentIndex = 0;
		// 设置为白色，即选中状态
		points[currentIndex].setEnabled(false);
	}
	
	/**
	 * 设置当前的小点的位置
	 */
	private void setCurDot(int positon) {
		// 排除异常情况
		if (positon < 0 || positon > PICS.length - 1 || currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(false);
		points[currentIndex].setEnabled(true);

		currentIndex = positon;
	}
}
