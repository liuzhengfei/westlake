package com.zg.westlake.homepage.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_ActivitySimplify;
import com.dm.thrift.Dm_ActivitySimplifyList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.ListADAdapter;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.homepage.common.TitleImgResult;
import com.zg.westlake.homepage.common.ViewPagerAdapter;
import com.zg.westlake.pullrefresh.RefreshableView;
import com.zg.westlake.pullrefresh.RefreshableView.OnRefreshListener;

public class HomePageWestLakeActivity extends Activity {
	private String TypeId = null;
	private List<TitleImgResult> _imgList;
	RefreshableView listview;
	private int _nowpage = 1;
	private int _pagesize = 3;
	private List<Map<String, Object>> _resultList;
	private View moreView;
	private Button _loadBt;
	private ProgressBar _loadPg;
	private View _topView;
	private ViewPager viewPager;
	private ArrayList<View> views;
	private ViewPagerAdapter vpAdapter;
	// 底部小点的图片
	private ImageView[] points;
	// 记录当前选中位置
	private int currentIndex;
	private ListADAdapter _listAdapter;
	private boolean _refresh = false;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏，充满全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		TypeId = this.getIntent().getStringExtra("typeid");
		_imgList = (List<TitleImgResult>) this.getIntent()
				.getSerializableExtra("imgList");
		this.setContentView(R.layout.home_page_westlake);

		moreView = getLayoutInflater().inflate(R.layout.loadmore, null);
		
		listview = (RefreshableView) findViewById(R.id.westlakelistview);
		
		// 设置头部图片浏览效果
		if (_topView == null) {
			_topView = getLayoutInflater().inflate(
					R.layout.home_page_title_viewpager, null);
			int _height = HomePageWestLakeActivity.this.getWindowManager().getDefaultDisplay()
					.getHeight();
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, _height / 3);
			layoutParams.gravity = Gravity.TOP;
			// 图片切换控件
			viewPager = (ViewPager) _topView.findViewById(R.id.title_viewpager);
			viewPager.setLayoutParams(layoutParams);
			views = new ArrayList<View>();
			for (int i = 0; i < _imgList.size(); i++) {
				ImageView image = new ImageView(HomePageWestLakeActivity.this);
				image.setBackgroundColor(Color.WHITE);
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				image.setImageBitmap(_imgList.get(i).getBitmap());
				views.add(image);
			}
			vpAdapter = new ViewPagerAdapter(views);
			viewPager.setAdapter(vpAdapter);
			// viewPager.setCurrentItem(0);
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					setCurDot(position);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});

			initPoint();
		}
		
		listview.addHeaderView(_topView);
		listview.addFooterView(moreView);
		_resultList = new ArrayList<Map<String,Object>>();
		new Thread(runnable).start();
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			List<Map<String, Object>> _ooresult = (List<Map<String, Object>>) msg.obj;
			if (_ooresult != null) {
				if(_refresh){
					_resultList = new ArrayList<Map<String,Object>>();
					_refresh = false;
				}
				for(int j=0;j<_ooresult.size();j++){
					_resultList.add(_ooresult.get(j));
				}
				_listAdapter = new ListADAdapter(HomePageWestLakeActivity.this,_resultList);
			}
			listview.setAdapter(_listAdapter);
			
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					Intent mIntent = new Intent(HomePageWestLakeActivity.this, HomePageActiveDetailActivity.class);
					HeaderViewListAdapter _hview = (HeaderViewListAdapter) parent.getAdapter(); 
					ListADAdapter listAdapter = (ListADAdapter) _hview.getWrappedAdapter();
					String acid = (String) listAdapter._olist.get(position-2).get("acid");
					mIntent.putExtra("_acid", acid);
					startActivity(mIntent);
				}
				
			});
			
			_loadBt = (Button) findViewById(R.id.bt_load);
			_loadPg = (ProgressBar) findViewById(R.id.pg_load);
			if(_resultList.size()>0){
				if (_resultList.size() >= (_pagesize*_nowpage)) {
					_loadBt.setVisibility(View.VISIBLE);
					_loadPg.setVisibility(View.GONE);
				} else {
					_loadBt.setVisibility(View.GONE);
					_loadPg.setVisibility(View.GONE);
				}
			}else{
				_loadBt.setVisibility(View.GONE);
				_loadPg.setVisibility(View.GONE);
			}

			_loadBt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					_loadPg.setVisibility(View.VISIBLE);// 将进度条可见
					_loadBt.setVisibility(View.GONE);// 按钮不可见

					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							_nowpage += 1;
							new Thread(runnable).start();
							_listAdapter.notifyDataSetChanged();
							listview.onRefreshComplete();
						}

					}, 1000);
				}
			});
			
			listview.setonRefreshListener(new OnRefreshListener() {
				@Override
				public void onRefresh() {
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							_refresh = true;
							_nowpage = 1;
							new Thread(runnable).start();
							_listAdapter.notifyDataSetChanged();
							listview.onRefreshComplete();
						}
					}, 1000);
				}
			});
			
		}
	};
	

	// 调用获取图片接口
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			List<Map<String, Object>> _olist = new ArrayList<Map<String, Object>>();
			TSocket socket = null;
			try {
				socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);
				Dm_ActivitySimplifyList activityList = client
						.searchActivitySimplifyByType(SocketUtil.VALIDSTRING,
								TypeId, _nowpage, _pagesize);
				List<Dm_ActivitySimplify> _actiList = activityList
						.getActivitySimplifyList();

				// 头部位置占一个空的数据
				Map<String, Object> _oomap = new HashMap<String, Object>();
				for (int i = 0; i < _actiList.size(); i++) {
					Dm_ActivitySimplify _dmactivity = _actiList.get(i);
					Map<String, Object> _actiMap = new HashMap<String, Object>();
					_actiMap.put("acid", _dmactivity.getId());
					_actiMap.put("acimg",
							Picutil.returnBitMap(_dmactivity.getPicture()));
					_actiMap.put("acname", _dmactivity.getName());
					_actiMap.put("acdate", _dmactivity.getDate());
					_olist.add(_actiMap);
				}
				
				Message msg = handler.obtainMessage();
				msg.obj = _olist;
				handler.sendMessage(msg);
				
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(socket!=null){
					socket.close();
				}
			}
		}
	};

	// 初始化底部小点
	private void initPoint() {
		LinearLayout lineLayout = (LinearLayout) _topView
				.findViewById(R.id.imagelinearLayout);
		points = new ImageView[_imgList.size()];
		for (int j = 0; j < _imgList.size(); j++) {
			ImageView pointImgView = new ImageView(this);
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
		if (positon < 0 || positon > _imgList.size() - 1
				|| currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(false);
		points[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

}
