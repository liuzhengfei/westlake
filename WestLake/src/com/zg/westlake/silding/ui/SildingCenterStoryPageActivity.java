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
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_suibi;
import com.dm.thrift.Dm_suibi_list;
import com.dm.thrift.Pageparm;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.CenterPageListAdapter;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.pullrefresh.RefreshableView;
import com.zg.westlake.pullrefresh.RefreshableView.OnRefreshListener;
import com.zg.westlake.util.CommonUtil;

public class SildingCenterStoryPageActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterStoryPageActivity.class);
	private static final int UPDATE_LISTVIEW = 1025;
	RefreshableView listview;
	private int nowPage = 1;
	private int pageSize = 4;
	private String totalpage = null;
	private String totalcount = null;
	private CenterPageListAdapter _listAdapter;
	private List<Map<String, Object>> _resultList;
	private boolean _refresh = false;
	private ProgressDialog progressDialog;
	private View loadmoreView;
	private Button _loadBt;
	private ProgressBar _loadPg;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏，充满全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.silding_center_story_page_activity);

		listview = (RefreshableView) findViewById(R.id.silding_center_fullrefresh);
		loadmoreView = getLayoutInflater().inflate(R.layout.loadmore,null);

		_resultList = new ArrayList<Map<String, Object>>();

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在努力加载中"); // 正在加载
		progressDialog.show();

		_listAdapter = new CenterPageListAdapter(
				SildingCenterStoryPageActivity.this, _resultList);
		listview.setAdapter(_listAdapter);
		new Thread(runnable).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_LISTVIEW:
				_listAdapter.notifyDataSetChanged();
				addData((List<Map<String, Object>>) msg.obj);
			}
		}
	};

	private void addData(List<Map<String, Object>> _ooresult) {
//		if (totalpage != null && !"null".equals(totalpage)
//				&& (Integer.parseInt(totalpage)) > nowPage) {
//			listview.addFooterView(loadmoreView);
//		}
		if (_ooresult != null) {
			if (_refresh) {
				_resultList = new ArrayList<Map<String, Object>>();
				_refresh = false;
			}
			for (int j = 0; j < _ooresult.size(); j++) {
				_resultList.add(_ooresult.get(j));
			}

			progressDialog.dismiss();

			
//			_loadBt = (Button) findViewById(R.id.bt_load);
//			_loadPg = (ProgressBar) findViewById(R.id.pg_load);
//			if(_resultList.size()>0){
//				if (_resultList.size() >= (nowPage*pageSize)) {
//					_loadBt.setVisibility(View.VISIBLE);
//					_loadPg.setVisibility(View.GONE);
//				} else {
//					_loadBt.setVisibility(View.GONE);
//					_loadPg.setVisibility(View.GONE);
//				}
//			}else{
//				_loadBt.setVisibility(View.GONE);
//				_loadPg.setVisibility(View.GONE);
//			}
//
//			_loadBt.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					_loadPg.setVisibility(View.VISIBLE);// 将进度条可见
//					_loadBt.setVisibility(View.GONE);// 按钮不可见
//
//					handler.postDelayed(new Runnable() {
//
//						@Override
//						public void run() {
//							nowPage += 1;
//							new Thread(runnable).start();
//							_listAdapter.notifyDataSetChanged();
//						}
//
//					}, 2000);
//				}
//			});
			
			
			listview.setonRefreshListener(new OnRefreshListener() {
				@Override
				public void onRefresh() {
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (!_refresh) {
								_refresh = true;
								nowPage = 1;
								new Thread(runnable).start();
								_listAdapter.notifyDataSetChanged();
								listview.onRefreshComplete();
							}
						}
					}, 2000);
				}
			});
		}
	}

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

				Dm_suibi_list _suibiList = client.ge_AttentionList(
						SocketUtil.COMMONUSERID, nowPage, pageSize);
				if (_suibiList.isSucess) {
					List<Dm_suibi> _osuibi = _suibiList.sblist;
					Pageparm _page = _suibiList.pageparm;
					if (_osuibi.size() > 0) {
						totalpage = _page.getTotalpage();
						totalcount = _page.getTotal();
						for (int i = 0; i < _osuibi.size(); i++) {
							Map<String, Object> _map = new HashMap<String, Object>();
							Dm_suibi _subsuibi = _osuibi.get(i);
							String url = _subsuibi.getUrl();
							String img = _subsuibi.getImg();
							if (url != null && !"".equals(url)
									&& !"null".equals(url)) {
								_map.put("userimg", Picutil
										.returnBitMap(_subsuibi.getUrl()));
							}
							if (img != null && !"".equals(img)
									&& !"null".equals(img)) {
								Bitmap bitmap = Picutil
										.returnBitMap(_subsuibi.getImg());
								_map.put("contentimg", bitmap);
								_map.put("contentimgurl", _subsuibi.getImg());
								CommonUtil._cacheMap.put(_subsuibi.getImg(), bitmap);
							}
							_map.put("creator", _subsuibi.getCreateuser());
							_map.put("createdate", _subsuibi.getCreatedate());
							_map.put("content", _subsuibi.getNr());
							_olist.add(_map);
						}
					}
				}

				Message msg = handler.obtainMessage();
				msg.what = UPDATE_LISTVIEW;
				msg.obj = _olist;
				handler.sendMessage(msg);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (socket != null) {
					socket.close();
				}
			}
		}
	};

}
