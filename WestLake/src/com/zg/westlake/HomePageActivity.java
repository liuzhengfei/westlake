package com.zg.westlake;

import java.util.ArrayList;

import com.zg.westlake.R;
import com.zg.westlake.homepage.ui.HomePageActiveActivity;
import com.zg.westlake.homepage.ui.HomePageExhibitionActivity;
import com.zg.westlake.homepage.ui.HomePageFoodActivity;
import com.zg.westlake.homepage.ui.HomePageShowActivity;
import com.zg.westlake.homepage.ui.HomePageWestLakeActivity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


public class HomePageActivity extends ActivityGroup implements OnClickListener {

	private ViewPager viewPager;
	private String titleArray[] = { "文化活动", "美食活动", "演出活动", "文化会展", "西湖寻梅" };
	private LinearLayout linearLayout;
	private ArrayList<TextView> textViews;
	private ArrayList<View> pageViews;
	private HorizontalScrollView horizontalScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏，充满全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.setContentView(R.layout.home_page_activity);
		linearLayout = (LinearLayout) super.findViewById(R.id.linearlayout);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);

		// 调用实现方法
		initTextView();
		initview();
		selectText(0);
		viewPager.setAdapter(new PagerAdapter() {
			// 得到数目
			public int getCount() {
				return pageViews.size();
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				return super.getItemPosition(object);
			}

			@Override
			public void destroyItem(View view, int id, Object arg2) {
				// TODO Auto-generated method stub
				((ViewPager) view).removeView(pageViews.get(id));
			}

			// 获取每一个item的id
			@Override
			public Object instantiateItem(View view, int id) {
				((ViewPager) view).addView(pageViews.get(id));
				return pageViews.get(id);
			}

		});
		// 页面改变时候的监听事件
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int i) {
				selectText(i);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	// 初始化视图
	@SuppressWarnings("deprecation")
	void initview() {
		pageViews = new ArrayList<View>();
		View cultureactive = getLocalActivityManager().startActivity(
				"home_page_cultureactive",
				new Intent(this, HomePageActiveActivity.class)).getDecorView();
		View food = getLocalActivityManager().startActivity("home_page_food",
				new Intent(this, HomePageFoodActivity.class)).getDecorView();
		View show = getLocalActivityManager().startActivity("home_page_show",
				new Intent(this, HomePageShowActivity.class)).getDecorView();
		View cultureexhibition = getLocalActivityManager().startActivity(
				"home_page_cultureexhibition",
				new Intent(this, HomePageExhibitionActivity.class))
				.getDecorView();
		View westlake = getLocalActivityManager().startActivity(
				"home_page_westlake",
				new Intent(this, HomePageWestLakeActivity.class))
				.getDecorView();
		pageViews.add(cultureactive);
		pageViews.add(food);
		pageViews.add(show);
		pageViews.add(cultureexhibition);
		pageViews.add(westlake);
	}

	// 初始化文本
	void initTextView() {
		textViews = new ArrayList<TextView>();
		int width = 90;
		int height = 60;
		for (int i = 0; i < titleArray.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(titleArray[i]);
			textView.setTextSize(14);
			textView.setWidth(width);
			textView.setHeight(height - 30);
			textView.setGravity(Gravity.CENTER);
			textView.setBackgroundResource(R.drawable.home_page_title_shu);
			textView.setId(i);

			// 设置文本监听事件
			textView.setOnClickListener(this);
			textViews.add(textView);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.width = 1;
			layoutParams.height = height - 40;
			layoutParams.gravity = Gravity.CENTER;
			// 添加子视图。如果没有布局参数对孩子已经设置,默认参数对于这个ViewGroup上设置的孩子。
			linearLayout.addView(textView);
		}
	}

	@Override
	public void onClick(View v) {
		selectText(v.getId());
	}

	// 选中头部标题栏或者移动后的操作
	public void selectText(int id) {
		for (int i = 0; i < titleArray.length; i++) {
			if (id == i) {
				textViews.get(i).setTextColor(
						getResources().getColor(R.color.selected));
				viewPager.setCurrentItem(i);
			} else {
				textViews.get(i).setTextColor(
						getResources().getColor(R.color.disselected));
			}
		}
	}
}
