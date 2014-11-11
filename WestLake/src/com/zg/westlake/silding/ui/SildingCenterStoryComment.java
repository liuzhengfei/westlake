package com.zg.westlake.silding.ui;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_Result;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;

public class SildingCenterStoryComment extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterStoryComment.class);
	private String suibiId;
	private EditText _contentEt;
	private TextView _cancelTv;
	private TextView _sendTv;
	private String _userId;
	private ProgressDialog progressDialog;
	private static final int SEND_FINISH = 1006;
	private boolean iscansend = false;

	private String _commContent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.story_content_comment);
		initUserMsg();
		initView();
	}

	private void initUserMsg() {
		SharedPreferences userInfo = getSharedPreferences("_userloginMsg",
				Context.MODE_PRIVATE);
		_userId = userInfo.getString("_userid", "");
		if (_userId == null && "".equals(_userId)) {
			startActivity(new Intent(this, SildingCenterLoginPageActivity.class));
		}
	}

	private void initView() {
		suibiId = getIntent().getStringExtra("suibi_id");
		_cancelTv = (TextView) findViewById(R.id.story_comment_cancel);
		_sendTv = (TextView) findViewById(R.id.story_comment_send);
		_contentEt = (EditText) findViewById(R.id.story_comment_content);

		_cancelTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SildingCenterStoryComment.this.finish();
			}
		});

		_contentEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String tx = _contentEt.getText().toString();
				if(tx!=null&&!"".equals(tx)){
					_sendTv.setTextColor(SildingCenterStoryComment.this.getResources().getColor(R.color.selected));
					iscansend = true;
				}else{
					_sendTv.setTextColor(SildingCenterStoryComment.this.getResources().getColor(R.color.gray));
					iscansend = false;
				}
			}
		});

		_sendTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iscansend) {
					sendData();
					progressDialog = new ProgressDialog(SildingCenterStoryComment.this);
					progressDialog.setMessage("正在发送中");
					progressDialog.show();
				}
			}
		});

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SEND_FINISH:
				Dm_Result _result = (Dm_Result) msg.obj;
				progressDialog.dismiss();
				SildingCenterStoryComment.this.finish();
				break;
			}
		}
	};

	private void sendData() {
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

					Dm_Result result = client.selectSuibi_hf(suibiId,
							_commContent, _userId);

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
