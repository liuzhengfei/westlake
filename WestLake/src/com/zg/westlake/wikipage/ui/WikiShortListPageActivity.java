package com.zg.westlake.wikipage.ui;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_BaikeSimplify;
import com.dm.thrift.Dm_BaikeSimplifyList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.WordWrapView;

public class WikiShortListPageActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(WikiShortListPageActivity.class);
	private String _wikiid;
	private String _wikiname;
	private WordWrapView wordWrapView;
	private TextView titleTextView;
	private int startPage = 1;
	private int pageSize = 30;
	private ProgressDialog progressDialog;
	private Button _backBt;
	private TextView _backTv;
	private ScrollView scrollview;
	private boolean _scrollload = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.wiki_short_list_page);

		_wikiid = getIntent().getStringExtra("_wikiid");
		_wikiname = getIntent().getStringExtra("_wikiname");

		titleTextView = (TextView) findViewById(R.id.wikiTitle);
		titleTextView.setText(_wikiname);

		wordWrapView = (WordWrapView) this.findViewById(R.id.view_wordwrap);
		_backBt = (Button) findViewById(R.id.wikiLeft);
		_backTv = (TextView) findViewById(R.id.wikiText);
		_backBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WikiShortListPageActivity.this.finish();
			}
		});
		_backTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WikiShortListPageActivity.this.finish();
			}
		});
		scrollview = (ScrollView) findViewById(R.id.wikiscroll);
		new Thread(runnable).start();
		progressDialog = new ProgressDialog(this);
    	progressDialog.setMessage("正在努力加载中");  //正在加载
    	progressDialog.show();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			List<Dm_BaikeSimplify> _owikiList = (List<Dm_BaikeSimplify>) msg.obj;
			if(_owikiList!=null){
				progressDialog.dismiss();
			}
			for(int i=0;i<_owikiList.size();i++){
				Dm_BaikeSimplify _dm = _owikiList.get(i);
				TextView textview = new TextView(WikiShortListPageActivity.this);
				textview.setText(_dm.getName());
				textview.setTag(_dm.getId());
				textview.setTextColor(Color.WHITE);
				textview.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent _intent = new Intent(WikiShortListPageActivity.this, WikiShortShowPageActivity.class);
						_intent.putExtra("wikishortid",v.getTag().toString());
						startActivity(_intent);
					}
				});
				
				wordWrapView.addView(textview);
			}
			
			scrollview.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						break;
					case MotionEvent.ACTION_MOVE:
						if (v.getScrollY() <= 0) {//top
							
		                } else if (scrollview.getChildAt(0).getMeasuredHeight() <= v.getHeight() + v.getScrollY()) {//bottom
		                	handler.post(new Runnable() {
								
								@Override
								public void run() {
									if(_scrollload){
										_scrollload = false;
										startPage += 1;
					                	new Thread(runnable).start();
									}
								}
							});
		                }
		                break;
					default:
						break;
					}
					return false;
				}
			});
		}
	};

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			_scrollload = true;
			try {
				TSocket socket = new TSocket(SocketUtil.SOCKETIP,
						SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);
				Dm_BaikeSimplifyList wikiSimpleList = client
						.searchBaikeSimplifyByType(SocketUtil.VALIDSTRING,_wikiid, startPage, pageSize);
				List<Dm_BaikeSimplify> _wikiList = wikiSimpleList.baikeSimplifyList;
				Message msg = handler.obtainMessage();
				msg.obj = _wikiList;
				handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};
}
