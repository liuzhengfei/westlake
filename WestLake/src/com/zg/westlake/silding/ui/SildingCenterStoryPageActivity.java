package com.zg.westlake.silding.ui;

import java.util.ArrayList;
import java.util.List;

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
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_suibi;
import com.dm.thrift.Dm_suibi_list;
import com.dm.thrift.Pageparm;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.CustomListView;
import com.zg.westlake.homepage.common.CustomListView.OnLoadMoreListener;
import com.zg.westlake.homepage.common.CustomListView.OnRefreshListener;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.model.CenterStoryModel;
import com.zg.westlake.util.CommonUtil;

public class SildingCenterStoryPageActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterStoryPageActivity.class);

	private static final int LOAD_DATA_FINISH = 1000;
	private static final int REFRESH_DATA_FINISH = 1001;
	private static final int INIT_LISTVIEW = 1002;

	private List<CenterStoryModel> _storyList = new ArrayList<CenterStoryModel>();
	private SildingCenterStoryListAdapter _storyListAdapter;
	private CustomListView _mListView;
	private int _startPage = 1;
	private int _pageCount = 4;
	private String totalpage = null;
	private String totalcount = null;
	private ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏，充满全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.silding_center_story_page_activity);
		initView();
		initData();
	}

	private void initView() {
		_storyListAdapter = new SildingCenterStoryListAdapter(this, _storyList);
		_mListView = (CustomListView) findViewById(R.id.silding_center_fullrefresh);
		_mListView.setAdapter(_storyListAdapter);
		progressDialog = new ProgressDialog(this);
    	progressDialog.setMessage("正在努力加载中");  //正在加载
    	progressDialog.show();
		_mListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {// 下拉刷新
				loadData(0);
			}
		});

		_mListView.setOnLoadListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {//加载更多
				loadData(1);
			}
		});
		
		_mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent _intent = new Intent(SildingCenterStoryPageActivity.this, SildingCenterStoryDetail.class);
				CenterStoryModel _ckModel = _storyListAdapter.mList.get(position-1);
				_intent.putExtra("userimg", _ckModel.getUserImg());
				_intent.putExtra("contentimg", _ckModel.getContentImg());
				_intent.putExtra("username", _ckModel.getAuthor());
				_intent.putExtra("date", _ckModel.getCreateDate());
				_intent.putExtra("content", _ckModel.getContent());
				_intent.putExtra("contentimgurl", _ckModel.getContentImgUrl());
				_intent.putExtra("id", _ckModel.getId());
				startActivity(_intent);
			}
		});
		
	}
	
	private void changeLoadState(){
		if(totalpage!=null&&!"".equals(totalpage)&&!"null".equals(totalpage)){
			if(_startPage<(Integer.parseInt(totalpage))){
				
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
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case INIT_LISTVIEW:
				if(_storyListAdapter!=null){
					_storyListAdapter.mList = (List<CenterStoryModel>) msg.obj;
					progressDialog.dismiss();
					changeLoadState();
					_storyListAdapter.notifyDataSetChanged();
				}
				_mListView.onRefreshComplete();//初始化加载数据完成
				break;
			case REFRESH_DATA_FINISH:
				if(_storyListAdapter!=null){
					_storyListAdapter.mList = (List<CenterStoryModel>) msg.obj;
					changeLoadState();
					_storyListAdapter.notifyDataSetChanged();
				}
				_mListView.onRefreshComplete();//下拉刷新
				break;
			case LOAD_DATA_FINISH:
				if(_storyListAdapter!=null){
					_storyListAdapter.mList.addAll((ArrayList<CenterStoryModel>)msg.obj);
					changeLoadState();
					_storyListAdapter.notifyDataSetChanged();
				}
				_mListView.onLoadMoreComplete();	//加载更多完成
				break;
			default:
				break;
			}
			
		}
	};

	public void loadData(final int type) {
		new Thread() {
			public void run() {
				List<CenterStoryModel> _mList = null;
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);

					switch(type){
					case 0:
						_startPage = 1;
						_mList = new ArrayList<CenterStoryModel>();
						Dm_suibi_list _suibiList = client.ge_AttentionList(
								SocketUtil.COMMONUSERID, _startPage, _pageCount);
						if (_suibiList.isSucess) {
							List<Dm_suibi> _osuibi = _suibiList.sblist;
							Pageparm _page = _suibiList.pageparm;
							if (_osuibi.size() > 0) {
								totalpage = _page.getTotalpage();
								totalcount = _page.getTotal();
								for (int i = 0; i < _osuibi.size(); i++) {
									CenterStoryModel _story = new CenterStoryModel();
									Dm_suibi _subsuibi = _osuibi.get(i);
									
									String url = _subsuibi.getUrl();
									String img = _subsuibi.getImg();
									if (url != null && !"".equals(url)&& !"null".equals(url)) {
										_story.setUserImg(Picutil.returnBitMap(_subsuibi.getUrl()));
									}
									if (img != null && !"".equals(img)&& !"null".equals(img)) {
										Bitmap bitmap = Picutil.returnBitMap(_subsuibi.getImg());
										_story.setContentImg(bitmap);
										CommonUtil._cacheMap.put(_subsuibi.getImg(), bitmap);
									}
									_story.setAuthor(_subsuibi.getCreateuser());
									_story.setCreateDate(_subsuibi.getCreatedate());
									_story.setContent(_subsuibi.getNr());
									_story.setContentImgUrl(img);
									_story.setId(_subsuibi.getId());
									_mList.add(_story);
								}
							}
						}
						break;
					case 1:
						_startPage += 1;
						_mList = new ArrayList<CenterStoryModel>();
						Dm_suibi_list _osuibiList = client.ge_AttentionList(
								SocketUtil.COMMONUSERID, _startPage, _pageCount);
						if (_osuibiList.isSucess) {
							List<Dm_suibi> _osuibi = _osuibiList.sblist;
							Pageparm _page = _osuibiList.pageparm;
							if (_osuibi.size() > 0) {
								totalpage = _page.getTotalpage();
								totalcount = _page.getTotal();
								for (int i = 0; i < _osuibi.size(); i++) {
									CenterStoryModel _story = new CenterStoryModel();
									Dm_suibi _subsuibi = _osuibi.get(i);
									
									String url = _subsuibi.getUrl();
									String img = _subsuibi.getImg();
									if (url != null && !"".equals(url)&& !"null".equals(url)) {
										_story.setUserImg(Picutil.returnBitMap(_subsuibi.getUrl()));
									}
									if (img != null && !"".equals(img)&& !"null".equals(img)) {
										Bitmap bitmap = Picutil.returnBitMap(_subsuibi.getImg());
										_story.setContentImg(bitmap);
										CommonUtil._cacheMap.put(_subsuibi.getImg(), bitmap);
									}
									_story.setAuthor(_subsuibi.getCreateuser());
									_story.setCreateDate(_subsuibi.getCreatedate());
									_story.setContent(_subsuibi.getNr());
									_story.setContentImgUrl(img);
									_story.setId(_subsuibi.getId());
									_mList.add(_story);
								}
							}
						}
						break;
					}
					Thread.sleep(2000);
					if(type==0){
						Message _Msg = mHandler.obtainMessage(REFRESH_DATA_FINISH, _mList);
						mHandler.sendMessage(_Msg);
					}else if(type==1){
						Message _Msg = mHandler.obtainMessage(LOAD_DATA_FINISH, _mList);
						mHandler.sendMessage(_Msg);
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

	public void initData() {
		new Thread() {
			public void run() {
				List<CenterStoryModel> _mList = new ArrayList<CenterStoryModel>();
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);

					Dm_suibi_list _suibiList = client.ge_AttentionList(
							SocketUtil.COMMONUSERID, _startPage, _pageCount);
					if (_suibiList.isSucess) {
						List<Dm_suibi> _osuibi = _suibiList.sblist;
						Pageparm _page = _suibiList.pageparm;
						if (_osuibi.size() > 0) {
							totalpage = _page.getTotalpage();
							totalcount = _page.getTotal();
							for (int i = 0; i < _osuibi.size(); i++) {
								CenterStoryModel _story = new CenterStoryModel();
								Dm_suibi _subsuibi = _osuibi.get(i);
								
								String url = _subsuibi.getUrl();
								String img = _subsuibi.getImg();
								if (url != null && !"".equals(url)&& !"null".equals(url)) {
									_story.setUserImg(Picutil.returnBitMap(_subsuibi.getUrl()));
								}
								if (img != null && !"".equals(img)&& !"null".equals(img)) {
									Bitmap bitmap = Picutil.returnBitMap(_subsuibi.getImg());
									_story.setContentImg(bitmap);
									CommonUtil._cacheMap.put(_subsuibi.getImg(), bitmap);
								}
								_story.setAuthor(_subsuibi.getCreateuser());
								_story.setCreateDate(_subsuibi.getCreatedate());
								_story.setContent(_subsuibi.getNr());
								_story.setContentImgUrl(img);
								_story.setId(_subsuibi.getId());
								_mList.add(_story);
							}
						}
					}
					Message _Msg = mHandler.obtainMessage(INIT_LISTVIEW, _mList);
					mHandler.sendMessage(_Msg);
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

	private class SildingCenterStoryListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<CenterStoryModel> mList;

		public SildingCenterStoryListAdapter(Context pContext,
				List<CenterStoryModel> pList) {
			mInflater = LayoutInflater.from(pContext);
			if (pList != null) {
				mList = pList;
			} else {
				mList = new ArrayList<CenterStoryModel>();
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
			if (getCount() == 0) {
				return null;
			}
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				
				convertView = mInflater.inflate(R.layout.silding_center_list_item, null);
			} 
			holder._userImage = (ImageView) convertView.findViewById(R.id.center_list_userimg);
			holder._creatorTv = (TextView) convertView.findViewById(R.id.center_list_creator);
			holder._dateTv = (TextView) convertView.findViewById(R.id.center_list_createdate);
			holder._contentTv = (TextView) convertView.findViewById(R.id.center_list_content);
			holder._contentImage = (ImageView) convertView.findViewById(R.id.center_list_contentimg);
			
			TextView _commentTv = (TextView) convertView.findViewById(R.id.center_list_comment);
			TextView _transTv = (TextView) convertView.findViewById(R.id.center_list_transmit);
			
			CenterStoryModel _storyModel = mList.get(position);
			holder._userImage.setImageBitmap(_storyModel.getUserImg());
			holder._contentImage.setImageBitmap(_storyModel.getContentImg());
			holder._creatorTv.setText(_storyModel.getAuthor());
			holder._dateTv.setText(_storyModel.getCreateDate());
			holder._contentTv.setText(_storyModel.getContent());
			holder._contentImage.setTag(_storyModel.getContentImgUrl());
			
			_commentTv.setTag(_storyModel.getId());
			
			holder._contentImage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String _t = (String) v.getTag();
					if (_t != null && !"".equals(_t) && !"null".equals(_t)) {
						Intent _intent = new Intent(SildingCenterStoryPageActivity.this,
								SildingCenterScreenImgActivity.class);
						_intent.putExtra("large_img", (String) v.getTag());
						SildingCenterStoryPageActivity.this.startActivity(_intent);
					}
				}
			});
			
			_commentTv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent _intent = new Intent(SildingCenterStoryPageActivity.this,
							SildingCenterStoryComment.class);
					_intent.putExtra("suibi_id", (String)v.getTag());
					SildingCenterStoryPageActivity.this.startActivity(_intent);
				}
			});
			
			_transTv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent _intent = new Intent(SildingCenterStoryPageActivity.this,
							SildingCenterStoryTrans.class);
					_intent.putExtra("suibi_id", (String)v.getTag());
					SildingCenterStoryPageActivity.this.startActivity(_intent);
				}
			});
			
			return convertView;
		}
	}
	
	private static class ViewHolder {
		private ImageView _userImage;
		private TextView _creatorTv;
		private TextView _dateTv;
		private TextView _contentTv;
		private ImageView _contentImage;
	}

}
