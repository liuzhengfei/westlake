package com.zg.westlake.silding.ui;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_suibi_pl;
import com.dm.thrift.Dm_suibi_pllist;
import com.dm.thrift.Pageparm;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.CustomListView;
import com.zg.westlake.homepage.common.CustomListView.OnLoadMoreListener;
import com.zg.westlake.homepage.common.Picutil;

public class SildingCenterStoryDetail extends Activity implements OnClickListener{
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterStoryDetail.class);

	private String _suibiId;
	private StoryDetailAdapter _adapter;
	private CustomListView _mListView;
	private int _startPage = 1;
	private int _pageCount = 10;
	private String totalpage = null;
	private Button _backBt;
	private TextView _backTv;
	private TextView _transTv;
	private TextView _commentTv;

	private View topView;

	private static final int INIT_COMMONLIST = 1005;
	private static final int LOAD_MORE = 1008;

	private List<Map<String, Object>> _olist = new ArrayList<Map<String, Object>>();
	private Map<String, Object> _map = new HashMap<String, Object>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.silding_center_story_page_detail);
		initGetData();
		initView();
		initCommentData();
	}

	private void initView() {
		_adapter = new StoryDetailAdapter(this, _olist);
		_mListView = (CustomListView) findViewById(R.id.story_detail_fullrefresh);
		_backBt = (Button) findViewById(R.id.story_detail_titile_left);
		_backTv = (TextView) findViewById(R.id.story_detail_titile_lefttx);
		_transTv = (TextView) findViewById(R.id.story_detail_transmit);
		_commentTv = (TextView) findViewById(R.id.story_detail_comment);
		
		_backBt.setOnClickListener(this);
		_backTv.setOnClickListener(this);
		_transTv.setOnClickListener(this);
		_commentTv.setOnClickListener(this);

		getTopView();
		_mListView.addHeaderView(topView);
		_mListView.setAdapter(_adapter);
		
		_mListView.setOnLoadListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {//加载更多
				loadMore();
			}
		});
	}

	private void initGetData() {
		_suibiId = getIntent().getStringExtra("id");
		Bitmap userbitmap = getIntent().getParcelableExtra("userimg");
		Bitmap contentbitmap = getIntent().getParcelableExtra("contentimg");
		String userName = getIntent().getStringExtra("username");
		String createDate = getIntent().getStringExtra("date");
		String content = getIntent().getStringExtra("content");
		String contentimgUrl = getIntent().getStringExtra("contentimgurl");

		_map.put("userbitmap", userbitmap);
		_map.put("contentbitmap", contentbitmap);
		_map.put("userName", userName);
		_map.put("createDate", createDate);
		_map.put("content", content);
		_map.put("contentimgUrl", contentimgUrl);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.story_detail_titile_left:
			this.finish();
			break;
		case R.id.story_detail_titile_lefttx:
			this.finish();
			break;
		case R.id.story_detail_transmit:
			Intent _tsintent = new Intent(this,SildingCenterStoryTrans.class);
			_tsintent.putExtra("suibi_id", _suibiId);
			startActivity(_tsintent);
			break;
		case R.id.story_detail_comment:
			Intent _cmintent = new Intent(this,SildingCenterStoryComment.class);
			_cmintent.putExtra("suibi_id", _suibiId);
			startActivity(_cmintent);
			break;
		default:
			break;
		}
	}
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case INIT_COMMONLIST:
				if (_adapter != null) {
					_adapter.resultList.addAll((ArrayList<Map<String, Object>>) msg.obj);
					_adapter.notifyDataSetChanged();
					changeLoadState();
				}
				_mListView.onRefreshComplete();// 初始化加载数据完成
				break;
			case LOAD_MORE:
				if (_adapter != null) {
					_adapter.resultList.addAll((ArrayList<Map<String, Object>>) msg.obj);
					_adapter.notifyDataSetChanged();
					changeLoadState();
				}
				_mListView.onRefreshComplete();// 加载更多
				break;
			}
		}
	};

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
	
	private void initCommentData() {
		new Thread() {
			public void run() {
				List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(
							socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);

					Dm_suibi_pllist _cmList = client.suibiplList(_suibiId,
							_startPage, _pageCount);
					if (_cmList.isSucess) {
						List<Dm_suibi_pl> _ocommon = _cmList.pllist;
						Pageparm _page = _cmList.pageparm;
						if (_ocommon.size() > 0) {
							totalpage = _page.getTotalpage();
							for (int i = 0; i < _ocommon.size(); i++) {
								Dm_suibi_pl _subsuibi = _ocommon.get(i);
								Map<String, Object> _commonMap = new HashMap<String, Object>();
								String cm_content = _subsuibi.getNr();
								if (cm_content == null) {
									cm_content = _subsuibi.getPlnr();
								}
								_commonMap.put("pl_content", cm_content);
								_commonMap.put("pl_username",_subsuibi.getUser_name());
								_commonMap.put("pl_userimg", Picutil.returnBitMap(_subsuibi.getUser_tx()));
								_commonMap.put("pl_fatherid", _subsuibi.getFather_id());
								_commonMap.put("pl_suibiid", _subsuibi.getSuibi_id());
								_commonMap.put("pl_userid", _subsuibi.getUser_id());

								commentList.add(_commonMap);
							}
						}
					}
					Message _Msg = mHandler.obtainMessage(INIT_COMMONLIST,
							commentList);
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

	private void loadMore(){
		new Thread() {
			public void run() {
				List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(
							socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);

					_startPage += 1;
					Dm_suibi_pllist _cmList = client.suibiplList(_suibiId,_startPage, _pageCount);
					if (_cmList.isSucess) {
						List<Dm_suibi_pl> _ocommon = _cmList.pllist;
						Pageparm _page = _cmList.pageparm;
						if (_ocommon.size() > 0) {
							totalpage = _page.getTotalpage();
							for (int i = 0; i < _ocommon.size(); i++) {
								Dm_suibi_pl _subsuibi = _ocommon.get(i);
								Map<String, Object> _commonMap = new HashMap<String, Object>();
								String cm_content = _subsuibi.getNr();
								if (cm_content == null) {
									cm_content = _subsuibi.getPlnr();
								}
								_commonMap.put("pl_content", cm_content);
								_commonMap.put("pl_username",_subsuibi.getUser_name());
								_commonMap.put("pl_userimg", Picutil.returnBitMap(_subsuibi.getUser_tx()));
								_commonMap.put("pl_fatherid", _subsuibi.getFather_id());
								_commonMap.put("pl_suibiid", _subsuibi.getSuibi_id());
								_commonMap.put("pl_userid", _subsuibi.getUser_id());

								commentList.add(_commonMap);
							}
						}
					}
					Thread.sleep(2000);
					Message _Msg = mHandler.obtainMessage(LOAD_MORE,commentList);
					mHandler.sendMessage(_Msg);
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
	
	
	private void getTopView() {
		if (topView == null) {
			topView = LayoutInflater.from(this).inflate(
					R.layout.story_content_first_list, null);
		}
		ImageView _userIV = (ImageView) topView
				.findViewById(R.id.story_detail_userimg);
		ImageView _contentIV = (ImageView) topView
				.findViewById(R.id.story_detail_contentimg);
		TextView _userTV = (TextView) topView
				.findViewById(R.id.story_detail_creator);
		TextView _dateTV = (TextView) topView
				.findViewById(R.id.story_detail_createdate);
		TextView _contentTV = (TextView) topView
				.findViewById(R.id.story_detail_content);

		_userIV.setImageBitmap((Bitmap) _map.get("userbitmap"));
		_contentIV.setImageBitmap((Bitmap) _map.get("contentbitmap"));
		_contentIV.setTag(_map.get("contentimgUrl"));
		_userTV.setText((CharSequence) _map.get("userName"));
		_dateTV.setText((CharSequence) _map.get("createDate"));
		_contentTV.setText((CharSequence) _map.get("content"));

		_contentIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String _t = (String) v.getTag();
				if (_t != null && !"".equals(_t) && !"null".equals(_t)) {
					Intent _intent = new Intent(SildingCenterStoryDetail.this,
							SildingCenterScreenImgActivity.class);
					_intent.putExtra("large_img", (String) v.getTag());
					SildingCenterStoryDetail.this.startActivity(_intent);
				}
			}
		});

	}

	private class StoryDetailAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private List<Map<String, Object>> resultList;

		public StoryDetailAdapter(Context pContext,
				List<Map<String, Object>> pList) {
			mInflater = LayoutInflater.from(pContext);
			if (pList != null) {
				resultList = pList;
			} else {
				resultList = new ArrayList<Map<String, Object>>();
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return resultList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return resultList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.story_content_list_item, null);
			}

			holder._userImage = (ImageView) convertView.findViewById(R.id.st_content_userimg);
			holder._creatorTv = (TextView) convertView.findViewById(R.id.st_content_user);
			holder._contentTv = (TextView) convertView.findViewById(R.id.st_content_msg);
			holder._contentRp = (TextView) convertView.findViewById(R.id.st_content_rp);
			holder._userImage.setImageBitmap((Bitmap) resultList.get(position).get("pl_userimg"));
			holder._creatorTv.setText((CharSequence) resultList.get(position).get("pl_username"));
			holder._contentTv.setText((CharSequence) resultList.get(position).get("pl_content"));
			
			holder._contentRp.setTag(R.id.tag_pl_fatherid, resultList.get(position).get("pl_fatherid"));
			holder._contentRp.setTag(R.id.tag_pl_suibiid, resultList.get(position).get("pl_suibiid"));
			holder._contentRp.setTag(R.id.tag_pl_username, resultList.get(position).get("pl_username"));
			
			holder._contentRp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent _intent = new Intent(SildingCenterStoryDetail.this,SildingCenterStoryRepl.class);
					_intent.putExtra("pl_fatherid", (String)v.getTag(R.id.tag_pl_fatherid));
					_intent.putExtra("pl_suibiid", (String)v.getTag(R.id.tag_pl_suibiid));
					_intent.putExtra("pl_username", (String)v.getTag(R.id.tag_pl_username));
					SildingCenterStoryDetail.this.startActivity(_intent);
				}
			});

			return convertView;
		}

	}

	private static class ViewHolder {
		private ImageView _userImage;
		private TextView _creatorTv;
		private TextView _contentTv;
		private TextView _contentRp;
	}

}
