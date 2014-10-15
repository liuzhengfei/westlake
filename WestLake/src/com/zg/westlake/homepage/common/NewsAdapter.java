package com.zg.westlake.homepage.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zg.westlake.R;

public class NewsAdapter extends BaseAdapter {
	// 定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;
	// 定义一个ArrayList来存放View
	private ArrayList<View> views;
	//头部图片资源
//	private List<Map<String,Object>> picImgList; 
	private List<Map<String,Object>> _olist;
	private List<TitleImgResult> picImgList;
	
	private ViewPager viewPager;
	private Context mContext;
	private View topView;
	// 底部小点的图片
	private ImageView[] points;
	// 记录当前选中位置
	private int currentIndex;

	public NewsAdapter(Context context,List<TitleImgResult> picImgList,List<Map<String,Object>> list) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.picImgList = picImgList;
		this._olist = list;
		views = new ArrayList<View>();
		vpAdapter = new ViewPagerAdapter(views);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _olist.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (position == 0) {
			return getTopView(convertView);
		} else {
			if(convertView==null){
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.list_item, null);
			}
			ImageView _imgView = (ImageView) convertView.findViewById(R.id.imageView_item);
			TextView _text = (TextView) convertView.findViewById(R.id.textView_item);
			TextView _date = (TextView) convertView.findViewById(R.id.dateView_item);
			Bitmap _bitmap = (Bitmap) _olist.get(position).get("acimg");
			_imgView.setImageBitmap(_bitmap);
			
			LayoutParams params;
			params = _imgView.getLayoutParams();
			params.height= ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth()/6;     
			params.width =((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth()/3;        
			_imgView.setLayoutParams(params);
			
			
			_text.setText((String)_olist.get(position).get("acname"));
			_date.setText((String)_olist.get(position).get("acdate"));

			return convertView;

		}
	}

	private View getTopView(View convertView) {

		if (topView == null) {
			topView = LayoutInflater.from(mContext).inflate(
					R.layout.home_page_title_viewpager, null);
			int _height = ((Activity)mContext).getWindowManager().getDefaultDisplay().getHeight();
			
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, _height/3);

			layoutParams.gravity = Gravity.TOP;
			// 页码指示点
			// 图片切换控件
			viewPager = (ViewPager) topView.findViewById(R.id.title_viewpager);
			viewPager.setLayoutParams(layoutParams);

			for (int i = 0; i < picImgList.size(); i++) {
				ImageView image = new ImageView(mContext);
				image.setBackgroundColor(Color.WHITE);
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				image.setImageBitmap(picImgList.get(i).getBitmap());
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
		points = new ImageView[picImgList.size()];
		for (int j = 0; j < picImgList.size(); j++) {
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
		if (positon < 0 || positon > picImgList.size() - 1 || currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(false);
		points[currentIndex].setEnabled(true);

		currentIndex = positon;
	}
	
}
