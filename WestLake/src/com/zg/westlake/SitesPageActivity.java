package com.zg.westlake;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_ScenerySimplify;
import com.dm.thrift.Dm_ScenerySimplifyList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.homepage.common.CustomListView;
import com.zg.westlake.homepage.common.CustomListView.OnLoadMoreListener;
import com.zg.westlake.homepage.common.CustomListView.OnRefreshListener;
import com.zg.westlake.homepage.common.Picutil;

public class SitesPageActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SitesPageActivity.class);
	private CustomListView _mListView;
	private int _startPage = 1;
	private int _pageCount = 10;
	private SitesListAdapter _mListAdapter;
	private List<Map<String, Object>> _resultList;
	private static final int LOAD_DATA_FINISH = 1000;
	private static final int REFRESH_DATA_FINISH = 1001;
	private static final int INIT_LISTVIEW = 1002;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.sites_page_activity);
		
		initView();
		initData();
	}

	private void initView() {
		_mListAdapter = new SitesListAdapter(this, _resultList);
		_mListView = (CustomListView) findViewById(R.id.sites_listview);
		_mListView.setAdapter(_mListAdapter);
		
		progressDialog = new ProgressDialog(this);
    	progressDialog.setMessage("正在努力加载中");  //正在加载
    	progressDialog.show();
		
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

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case INIT_LISTVIEW:
				if(_mListAdapter!=null){
					progressDialog.dismiss();
					_mListAdapter.mList = (List<Map<String, Object>>) msg.obj;
					changeLoadState();
					_mListAdapter.notifyDataSetChanged();
				}
				_mListView.onRefreshComplete();//初始化加载数据完成
				break;
			case REFRESH_DATA_FINISH:
				if(_mListAdapter!=null){
					_mListAdapter.mList = (List<Map<String, Object>>) msg.obj;
					changeLoadState();
					_mListAdapter.notifyDataSetChanged();
				}
				_mListView.onRefreshComplete();//下拉刷新
				break;
			case LOAD_DATA_FINISH:
				if(_mListAdapter!=null){
					_mListAdapter.mList.addAll((Collection<Map<String, Object>>)msg.obj);
					changeLoadState();
					_mListAdapter.notifyDataSetChanged();
				}
				_mListView.onLoadMoreComplete();	//加载更多完成
				break;
			default:
				break;
			}
		}
	};
	
	private void changeLoadState(){
		if(_mListAdapter.mList.size()>0){
			if((_startPage*_pageCount)<=_mListAdapter.mList.size()){
				
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
	
	
	private void loadData(final int type) {
		new Thread() {
			public void run() {
				List<Map<String, Object>> _mList = null;
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
						_startPage = 1;
						_mList = new ArrayList<Map<String, Object>>();
						
						Dm_ScenerySimplifyList _sitesList = client.searchScenerySimplifyByCity(SocketUtil.VALIDSTRING
								, SocketUtil.CITYNAME, _startPage, _pageCount);
						List<Dm_ScenerySimplify> _rList = _sitesList.getSceneryLSimplifylist();
						if(_rList!=null){
							for(int i=0;i<_rList.size();i++){
								Map<String, Object> _oomap = new HashMap<String, Object>();
								Dm_ScenerySimplify _scenery = _rList.get(i);
								_oomap = new HashMap<String,Object>();
								_oomap.put("site_id", _scenery.getId());
								_oomap.put("site_name", _scenery.getName());
								String picUrl = _scenery.getPicture();
								if(picUrl!=null&&!"".equals(picUrl)&&!"null".equals(picUrl)){
									_oomap.put("site_pic", Picutil.returnBitMap(_scenery.getPicture()));
								}
								_mList.add(_oomap);
							}
						}
					break;
					case 1:
						_startPage += 1;
						_mList = new ArrayList<Map<String, Object>>();
						Dm_ScenerySimplifyList _sitesList_n = client.searchScenerySimplifyByCity(SocketUtil.VALIDSTRING
								, SocketUtil.CITYNAME, _startPage, _pageCount);
						List<Dm_ScenerySimplify> _rList_n = _sitesList_n.getSceneryLSimplifylist();
						if(_rList_n!=null){
							for(int i=0;i<_rList_n.size();i++){
								Map<String, Object> _oomap = new HashMap<String, Object>();
								Dm_ScenerySimplify _scenery = _rList_n.get(i);
								_oomap = new HashMap<String,Object>();
								_oomap.put("site_id", _scenery.getId());
								_oomap.put("site_name", _scenery.getName());
								String picUrl = _scenery.getPicture();
								if(picUrl!=null&&!"".equals(picUrl)&&!"null".equals(picUrl)){
									_oomap.put("site_pic", Picutil.returnBitMap(_scenery.getPicture()));
								}
								_mList.add(_oomap);
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
	
	private void initData() {
		new Thread() {
			public void run() {
				List<Map<String, Object>> _mList = new ArrayList<Map<String, Object>>();
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(
							socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);
					
					Dm_ScenerySimplifyList _sitesList = client.searchScenerySimplifyByCity(SocketUtil.VALIDSTRING
							, SocketUtil.CITYNAME, _startPage, _pageCount);
					List<Dm_ScenerySimplify> _rList = _sitesList.getSceneryLSimplifylist();
					logger.debug(_rList.size()+ "===");
					if(_rList!=null){
						for(int i=0;i<_rList.size();i++){
							Dm_ScenerySimplify _scenery = _rList.get(i);
							Map<String, Object> _oomap = new HashMap<String,Object>();
							_oomap.put("site_id", _scenery.getId());
							_oomap.put("site_name", _scenery.getName());
							String picUrl = _scenery.getPicture();
							if(picUrl!=null&&!"".equals(picUrl)&&!"null".equals(picUrl)){
								_oomap.put("site_pic", Picutil.returnBitMap(_scenery.getPicture()));
							}
							_mList.add(_oomap);
						}
					}
					Message _Msg = handler.obtainMessage(INIT_LISTVIEW, _mList);
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
	
	private class SitesListAdapter extends BaseAdapter {
		private Context mContext;
		private List<Map<String, Object>> mList;

		public SitesListAdapter(Context pContext,
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
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.sites_list_item, null);
			}
			
			holder._siteImg = (ImageView) convertView.findViewById(R.id.site_image_view);
			holder._siteName = (TextView) convertView.findViewById(R.id.site_name_view);
			
			holder._siteImg.setImageBitmap((Bitmap) mList.get(position).get("site_pic"));
			holder._siteName.setText((CharSequence) mList.get(position).get("site_name"));
			
			return convertView;
		}

	}
	
	private static class ViewHolder {
		private ImageView _siteImg;
		private TextView _siteName;
	}
}
