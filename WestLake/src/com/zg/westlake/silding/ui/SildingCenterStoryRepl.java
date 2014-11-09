package com.zg.westlake.silding.ui;

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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_Result;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;

public class SildingCenterStoryRepl extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterStoryRepl.class);
	private String _userId;
	private String _fatherId;
	private String _suibiId;
	private String _sbuserName;
	private String _commContent;
	
	private EditText _contentEt;
	private TextView _cancelTv;
	private TextView _sendTv;
	private TextView _titleTv;
	
	private ProgressDialog progressDialog;
	private static final int SEND_FINISH = 1009;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.story_content_response);
		initUserMsg();
		initView();
	}

	private void initUserMsg(){
		SharedPreferences userInfo = getSharedPreferences("_userloginMsg", Context.MODE_PRIVATE);
		_userId = userInfo.getString("_userid", "");
	   	if(_userId==null&&"".equals(_userId)){
	   		startActivity(new Intent(this, SildingCenterLoginPageActivity.class));
	   	}
	}
	
	private void initView() {
		_fatherId = getIntent().getStringExtra("pl_fatherid");
		_suibiId = getIntent().getStringExtra("pl_suibiid");
		_sbuserName = getIntent().getStringExtra("pl_username");
		
		_titleTv = (TextView) findViewById(R.id.story_respon_title);
		_cancelTv = (TextView) findViewById(R.id.story_respon_cancel);
		_sendTv = (TextView) findViewById(R.id.story_respon_send);
		_contentEt = (EditText) findViewById(R.id.story_respon_content);
		
		_titleTv.setText("回复" + _sbuserName);

		_cancelTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SildingCenterStoryRepl.this.finish();
			}
		});
		_sendTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendData();
				progressDialog = new ProgressDialog(SildingCenterStoryRepl.this);
				progressDialog.setMessage("正在发送中");
				progressDialog.show();
			}
		});
		
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SEND_FINISH:
				Dm_Result _result = (Dm_Result) msg.obj;
				progressDialog.dismiss();
				SildingCenterStoryRepl.this.finish();
				break;
			}}
	};
	
	
	private void sendData(){
		new Thread() {
			public void run() {
				TSocket socket = null;
				try {
					socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
					socket.open();
					TFramedTransport framedtransport = new TFramedTransport(
							socket);
					TProtocol protocol = new TBinaryProtocol(framedtransport);
					DmService.Client client = new DmService.Client(protocol);
					
					_commContent = _contentEt.getText().toString();
					
					Dm_Result result = client.selectSuibi_hfpl(_commContent, _fatherId, _userId, _suibiId);
					
					Message _Msg = mHandler.obtainMessage(SEND_FINISH, result);
					mHandler.sendMessage(_Msg);
					
				} catch (TException e) {
					e.printStackTrace();
				} finally {
					if (socket != null) {
						socket.close();
					}
				}
			}
		}.start();
	}
	
	
}
