package com.zg.westlake.homepage.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_ActivitySimplify;
import com.dm.thrift.Dm_ActivitySimplifyList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.CustomListView;
import com.zg.westlake.homepage.common.CustomListView.OnLoadMoreListener;
import com.zg.westlake.homepage.common.CustomListView.OnRefreshListener;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.homepage.common.TitleImgResult;
import com.zg.westlake.homepage.common.ViewPagerAdapter;

public class HomePageExhibitionActivity extends Activity {

	private String TypeId = null;
	private List<TitleImgResult> _imgList;
	private CustomListView _mListView;
	private int _nowpage = 1;
	private int _pagesize = 5;
	private List<Map<String, Object>> _resultList = new ArrayList<Map<String, Object>>();
	private View _topView;
	private ViewPager viewPager;
	private ArrayList<View> views;
	private ViewPagerAdapter vpAdapter;
	// 底部小点的图片
	private ImageView[] points;
	// 记录当前选中位置
	private int currentIndex;
	private ActiveListAdapter _listAdapter;

	private static final int INIT_LISTVIEW = 1000;
	private static final int REFRESH_DATA_FINISH = 1001;
	private static final int LOAD_DATA_FINISH = 1002;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏，充满全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.home_page_cultureexhibition);
		initGetData();
		initView();
		initloadData();
	}

	private void initView() {
		_listAdapter = new ActiveListAdapter(HomePageExhibitionActivity.this,_resultList);

		_mListView = (CustomListView) findViewById(R.id.exhibitionlistview);
		getTopView();
		_mListView.addHeaderView(_topView);
		_mListView.setAdapter(_listAdapter);
		
		_mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				loadData(0);
			}
		});

		_mListView.setOnLoadListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {// 加载更多
				loadData(1);
			}
		});

		_mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent mIntent = new
						 Intent(HomePageExhibitionActivity.this,HomePageActiveDetailActivity.class);
				 String acid = (String) _listAdapter.mList.get(position-2).get("acid");
				 mIntent.putExtra("_acid", acid);
				 startActivity(mIntent);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initGetData() {
		TypeId = this.getIntent().getStringExtra("typeid");
		_imgList = (List<TitleImgResult>) this.getIntent().getSerializableExtra("imgList");
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case INIT_LISTVIEW:
				if(_listAdapter!=null){
					_listAdapter.mList = (List<Map<String, Object>>) msg.obj;
					changeLoadState();
					_listAdapter.notifyDataSetChanged();
				}
				_mListView.onRefreshComplete();//初始化加载数据完成
				break;
			case REFRESH_DATA_FINISH:
				if(_listAdapter!=null){
					_listAdapter.mList = (List<Map<String, Object>>) msg.obj;
					changeLoadState();
					_listAdapter.notifyDataSetChanged();
				}
				_mListView.onRefreshComplete();//下拉刷新
				break;
			case LOAD_DATA_FINISH:
				if(_listAdapter!=null){
					_listAdapter.mList.addAll((Collection<Map<String, Object>>)msg.obj);
					changeLoadState();
					_listAdapter.notifyDataSetChanged();
				}
				_mListView.onLoadMoreComplete();	//加载更多完成
				break;
			default:
				break;
			}
		}
	};

	private void changeLoadState(){
		if(_listAdapter.mList.size()>0){
			if((_nowpage*_pagesize)<=_listAdapter.mList.size()){
				
				_mListView.setCanLoadMore(true);
				_mListView.setAutoLoadMore(true);
			}else{
				_mListView.setCanLoadMore(false);
				_mListView.setAutoLoadMore(false);
			}
		}else{
			_mListView.setCanLoadMore(false);
			_mListView.setAutoLoadMore(false);
		}
	}
	
	
	private void getTopView() {
		// 设置头部图片浏览效果
		if (_topView == null) {
			_topView = HomePageExhibitionActivity.this.getLayoutInflater().inflate(R.layout.home_page_title_viewpager, null);
		}
		int _height = HomePageExhibitionActivity.this.getWindowManager()
				.getDefaultDisplay().getHeight();
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, _height / 3);
		layoutParams.gravity = Gravity.TOP;
		// 图片切换控件
		viewPager = (ViewPager) _topView.findViewById(R.id.title_viewpager);
		viewPager.setLayoutParams(layoutParams);
		views = new ArrayList<View>();
		for (int i = 0; i < _imgList.size(); i++) {
			ImageView image = new ImageView(HomePageExhibitionActivity.this);
			image.setBackgroundColor(Color.WHITE);
			image.setScaleType(ImageView.ScaleType.CENTER_CROP);
			image.setImageBitmap(_imgList.get(i).getBitmap());
			views.add(image);
		}
		vpAdapter = new ViewPagerAdapter(views);
		viewPager.setAdapter(vpAdapter);
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

	public void loadData(final int type) {
		new Thread() {
			public void run() {
				List<Map<String, Object>> _mList = null;
				Map<String, Object> _oomap = null;
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(
							socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);

					switch (type) {
					case 0:
						_nowpage = 1;
						_mList = new ArrayList<Map<String, Object>>();
						Dm_ActivitySimplifyList activityList = client
								.searchActivitySimplifyByType(
										SocketUtil.VALIDSTRING, TypeId,
										_nowpage, _pagesize);
						List<Dm_ActivitySimplify> _actiList = activityList
								.getActivitySimplifyList();

						_oomap = new HashMap<String, Object>();
						if (_actiList != null) {
							for (int i = 0; i < _actiList.size(); i++) {
								Dm_ActivitySimplify _dmactivity = _actiList
										.get(i);
								Map<String, Object> _actiMap = new HashMap<String, Object>();
								_actiMap.put("acid", _dmactivity.getId());
								_actiMap.put("acimg", Picutil
										.returnBitMap(_dmactivity.getPicture()));
								_actiMap.put("acname", _dmactivity.getName());
								_actiMap.put("acdate", _dmactivity.getDate());
								_mList.add(_actiMap);
							}
						}
						break;
					case 1:
						_nowpage += 1;
						_mList = new ArrayList<Map<String, Object>>();
						Dm_ActivitySimplifyList activityList_n = client
								.searchActivitySimplifyByType(
										SocketUtil.VALIDSTRING, TypeId,
										_nowpage, _pagesize);
						List<Dm_ActivitySimplify> _actiList_n = activityList_n
								.getActivitySimplifyList();

						_oomap = new HashMap<String, Object>();
						if (_actiList_n != null) {
							for (int i = 0; i < _actiList_n.size(); i++) {
								Dm_ActivitySimplify _dmactivity = _actiList_n
										.get(i);
								Map<String, Object> _actiMap = new HashMap<String, Object>();
								_actiMap.put("acid", _dmactivity.getId());
								_actiMap.put("acimg", Picutil
										.returnBitMap(_dmactivity.getPicture()));
								_actiMap.put("acname", _dmactivity.getName());
								_actiMap.put("acdate", _dmactivity.getDate());
								_mList.add(_actiMap);
							}
						}
						break;
					}
					Thread.sleep(2000);
					if (type == 0) {
						Message _Msg = handler.obtainMessage(
								REFRESH_DATA_FINISH, _mList);
						handler.sendMessage(_Msg);
					} else if (type == 1) {
						Message _Msg = handler.obtainMessage(LOAD_DATA_FINISH,
								_mList);
						handler.sendMessage(_Msg);
					}

				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (socket != null) {
						socket.close();
					}
				}

			}

		}.start();
	}

	private void initloadData() {
		new Thread() {
			public void run() {
				List<Map<String, Object>> _olist = new ArrayList<Map<String, Object>>();
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(
							socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);
					Dm_ActivitySimplifyList activityList = client
							.searchActivitySimplifyByType(
									SocketUtil.VALIDSTRING, TypeId, _nowpage,
									_pagesize);
					List<Dm_ActivitySimplify> _actiList = activityList
							.getActivitySimplifyList();

					Map<String, Object> _oomap = new HashMap<String, Object>();
					if (_actiList != null) {
						for (int i = 0; i < _actiList.size(); i++) {
							Dm_ActivitySimplify _dmactivity = _actiList.get(i);
							Map<String, Object> _actiMap = new HashMap<String, Object>();
							_actiMap.put("acid", _dmactivity.getId());
							_actiMap.put("acimg", Picutil
									.returnBitMap(_dmactivity.getPicture()));
							_actiMap.put("acname", _dmactivity.getName());
							_actiMap.put("acdate", _dmactivity.getDate());
							_olist.add(_actiMap);
						}
					}
					Message _Msg = handler.obtainMessage(INIT_LISTVIEW, _olist);
					handler.sendMessage(_Msg);

				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (socket != null) {
						socket.close();
					}
				}
			}
		}.start();
	}

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

	private class ActiveListAdapter extends BaseAdapter {
		private Context mContext;
		public List<Map<String, Object>> mList;

		public ActiveListAdapter(Context pContext,
				List<Map<String, Object>> pList) {
			mContext = pContext;
			if (pList != null) {
				mList = pList;
			} else {
				mList = new ArrayList<Map<String, Object>>();
			}
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.list_item, null);
			}
			ImageView _imgView = (ImageView) convertView
					.findViewById(R.id.imageView_item);
			TextView _text = (TextView) convertView
					.findViewById(R.id.textView_item);
			TextView _date = (TextView) convertView
					.findViewById(R.id.dateView_item);
			Bitmap _bitmap = (Bitmap) mList.get(position).get("acimg");
			_imgView.setImageBitmap(_bitmap);

			LayoutParams params;
			params = _imgView.getLayoutParams();
			params.height = ((Activity) mContext).getWindowManager()
					.getDefaultDisplay().getWidth() / 4;
			params.width = ((Activity) mContext).getWindowManager()
					.getDefaultDisplay().getWidth() / 2;
			_imgView.setLayoutParams(params);

			_text.setText((String) mList.get(position).get("acname"));
			_date.setText((String) mList.get(position).get("acdate"));

			return convertView;
		}

	}
	
	

}
