package com.example.westlake;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OneActivity extends Activity  {

	private View active,food,show,exhibition,westlake;//需要滑动的页卡
	private ViewPager viewPager;
	private PagerTitleStrip pagerTitleStrip;//viewpager的标题
	private PagerTabStrip pagerTabStrip;//一个viewpager的指示器，效果就是一个横的粗下划线
	private List<View> viewList;//把需要滑动的页卡添加到这个list中
	private List<String> titleList;//viewpager的标题
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.oneactivity);
		initView();
	}

	private void initView(){
		viewPager = (ViewPager) super.findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) super.findViewById(R.id.pagertab);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.gold));
		pagerTabStrip.setDrawFullUnderline(false);//显示全宽的下划线
		pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.azure));
		
		active = super.findViewById(R.layout.home_page_cultureactive);
		food = super.findViewById(R.layout.home_page_food);
		show = super.findViewById(R.layout.home_page_show);
		exhibition = super.findViewById(R.layout.home_page_cultureexhibition);
		westlake = super.findViewById(R.layout.home_page_westlake);
		
		getLayoutInflater();
		LayoutInflater inf = LayoutInflater.from(this);
		active = inf.inflate(R.layout.home_page_cultureactive, null);
		food = inf.inflate(R.layout.home_page_food, null);
		show = inf.inflate(R.layout.home_page_show, null);
		exhibition = inf.inflate(R.layout.home_page_cultureexhibition, null);
		westlake = inf.inflate(R.layout.home_page_westlake, null);
		
		//将要分页显示的view装入到数组中
		viewList = new ArrayList<View>();
		viewList.add(active);
		viewList.add(food);
		viewList.add(show);
		viewList.add(exhibition);
		viewList.add(westlake);
		
		//每个分页的title数据
		titleList = new ArrayList<String>();
		titleList.add("文化活动");
		titleList.add("美食活动");
		titleList.add("演出活动");
		titleList.add("文化会展");
		titleList.add("西湖寻梅");
		
		PagerAdapter pagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View view, Object ob) {
				return view==ob;
			}
			
			@Override
			public int getCount() {
				return viewList.size();
			}
			
			@Override
			public void destroyItem(ViewGroup container,int position,Object object){
				container.removeView(viewList.get(position));
			}
			
			@Override
			public int getItemPosition(Object object){
				return super.getItemPosition(object);
			}
			
			public CharSequence getPageTitle(int position){
				return titleList.get(position);//直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。
			}
			
			public Object instantiateItem(ViewGroup container,int position){
				container.addView(viewList.get(position));
				return viewList.get(position);
				
			}
		};
		
		viewPager.setAdapter(pagerAdapter);
	}

}
