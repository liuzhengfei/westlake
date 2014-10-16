package com.zg.westlake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_ActivitySimplify;
import com.dm.thrift.Dm_ActivityType;
import com.dm.thrift.Dm_ActivityTypeList;
import com.dm.thrift.Dm_PreImageList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.homepage.common.TitleImgResult;
import com.zg.westlake.homepage.ui.HomePageActiveActivity;
import com.zg.westlake.homepage.ui.HomePageExhibitionActivity;
import com.zg.westlake.homepage.ui.HomePageFoodActivity;
import com.zg.westlake.homepage.ui.HomePageShowActivity;
import com.zg.westlake.homepage.ui.HomePageWestLakeActivity;

public class HomePageActivity extends ActivityGroup implements OnClickListener {

	private ViewPager viewPager;
	private String[] titleArray;
	private String[] idArray;
	private LinearLayout linearLayout;
	private ArrayList<TextView> textViews;
	private ArrayList<View> pageViews;
	private List<Object> picImgList;
	private HorizontalScrollView horizontalScrollView;
	private Intent cultureactiveIntent;
	private Intent foodIntent;
	private Intent showIntent;
	private Intent cultureexhibitionIntent;
	private Intent westlakeIntent;
	
	private ProgressDialog progressDialog = null;

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

		new Thread(runnable).start();
		
		progressDialog = new ProgressDialog(this);
    	progressDialog.setMessage("正在努力加载中");  //正在加载
    	progressDialog.show();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			List<List<Object>> _oResult = (List<List<Object>>) msg.obj;
			List<Object> _oTypeList =_oResult.get(0);
			picImgList =_oResult.get(1);
			
			if (_oTypeList != null) {
				titleArray = new String[_oTypeList.size()];
				idArray = new String[_oTypeList.size()];
				for (int j = 0; j < _oTypeList.size(); j++) {
					Dm_ActivityType _oType = (Dm_ActivityType) _oTypeList.get(j);
					titleArray[j] = _oType.getName();
					idArray[j] = _oType.getId();
				}
			}

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
			
			if(picImgList!=null){
	    		progressDialog.dismiss();
	    	}
		}
	};

	// 接口调用获取活动分类
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				TSocket socket = new TSocket(SocketUtil.SOCKETIP,
						SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);

				Dm_ActivityTypeList activeTypeList = client.activityType(SocketUtil.VALIDSTRING);
				List oTypeList = activeTypeList.getActivityTypeList();
				
				
				Dm_PreImageList piclist= client.getPreImageList(SocketUtil.VALIDSTRING);
				List<Dm_ActivitySimplify> oPicList = piclist.getPreImageList();
				List picImgList = new ArrayList();
				for(int i=0;i<oPicList.size();i++){
					TitleImgResult _titleImg = new TitleImgResult();
					Dm_ActivitySimplify oPic = (Dm_ActivitySimplify) oPicList.get(i);
					_titleImg.setImgId(oPic.getId());
					_titleImg.setImgName(oPic.getName());
					_titleImg.setBitmap(Picutil.returnBitMap(oPic.getPicture()));
					_titleImg.setImgDate(oPic.getDate());
					picImgList.add(_titleImg);
				}

				List<List<Object>> _oResult = new ArrayList<List<Object>>();
				_oResult.add(oTypeList);
				_oResult.add(picImgList);
				Message msg = handler.obtainMessage();
				msg.obj = _oResult;
				handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	// 初始化视图
	@SuppressWarnings("deprecation")
	void initview() {
		pageViews = new ArrayList<View>();
		cultureactiveIntent = new Intent(this, HomePageActiveActivity.class);
		cultureactiveIntent.putExtra("typeid", idArray[0]);
		cultureactiveIntent.putExtra("imgList", (Serializable)picImgList);

		foodIntent = new Intent(this, HomePageFoodActivity.class);
		foodIntent.putExtra("typeid", idArray[1]);
		foodIntent.putExtra("imgList", (Serializable)picImgList);

		showIntent = new Intent(this, HomePageShowActivity.class);
		showIntent.putExtra("typeid", idArray[2]);
		showIntent.putExtra("imgList", (Serializable)picImgList);

		cultureexhibitionIntent = new Intent(this,
				HomePageExhibitionActivity.class);
		cultureexhibitionIntent.putExtra("typeid", idArray[3]);
		cultureexhibitionIntent.putExtra("imgList", (Serializable)picImgList);

		westlakeIntent = new Intent(this, HomePageWestLakeActivity.class);
		westlakeIntent.putExtra("typeid", idArray[4]);
		westlakeIntent.putExtra("imgList", (Serializable)picImgList);

		View cultureactive = getLocalActivityManager().startActivity(
				"home_page_cultureactive", cultureactiveIntent).getDecorView();
		View food = getLocalActivityManager().startActivity("home_page_food",
				foodIntent).getDecorView();
		View show = getLocalActivityManager().startActivity("home_page_show",
				showIntent).getDecorView();
		View cultureexhibition = getLocalActivityManager().startActivity(
				"home_page_cultureexhibition", cultureexhibitionIntent)
				.getDecorView();
		View westlake = getLocalActivityManager().startActivity(
				"home_page_westlake", westlakeIntent).getDecorView();
		pageViews.add(cultureactive);
		pageViews.add(food);
		pageViews.add(show);
		pageViews.add(cultureexhibition);
		pageViews.add(westlake);
	}

	// 初始化文本
	void initTextView() {
		textViews = new ArrayList<TextView>();
		int _width = this.getWindowManager().getDefaultDisplay().getWidth();
		
		for (int i = 0; i < titleArray.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(titleArray[i]);
			textView.setTextSize(18);
			textView.setWidth(_width/7*2);
			textView.setHeight(_width/8);
			textView.setGravity(Gravity.CENTER);
			Drawable d = getResources().getDrawable(R.drawable.home_page_title_shu);
			d.setBounds(0, 0, 1, 30); //必须设置图片大小，否则不显示
			textView.setCompoundDrawables(d , null, null, null);
			textView.setId(i);

			// 设置文本监听事件
			textView.setOnClickListener(this);
			textViews.add(textView);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.width = 1;
			layoutParams.height = _width/8;
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
