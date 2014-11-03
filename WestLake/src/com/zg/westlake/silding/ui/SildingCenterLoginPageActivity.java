package com.zg.westlake.silding.ui;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_User;
import com.dm.thrift.Dm_User_Result;
import com.slidingmenu.lib.SlidingMenu;
import com.zg.socket.SocketUtil;
import com.zg.westlake.MainActivity;
import com.zg.westlake.R;
import com.zg.westlake.util.CommonUtil;

public class SildingCenterLoginPageActivity extends Activity implements OnCheckedChangeListener{
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterLoginPageActivity.class);
	private EditText _username;
	private EditText _password;
	private Button _loginbt;
	private Button _registerbt;
	private View _sildingView;
	private RadioGroup sildingRadioGroup;
	private TextView _sildingTitle;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.silding_center_login_page_activity);
		_username = (EditText) findViewById(R.id.login_username);
		_password = (EditText) findViewById(R.id.login_password);
		_loginbt = (Button) findViewById(R.id.longin_bt);
		_registerbt = (Button) findViewById(R.id.register_bt);

		adjustHeight(_username);
		adjustHeight(_password);
		adjustHeight(_loginbt);
		adjustHeight(_registerbt);
		
		setSildingMenu();
		_registerbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SildingCenterLoginPageActivity.this, SildingCenterRegisterPageActivity.class));
			}
		});
		
		_loginbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = _username.getText().toString();
				String password = _password.getText().toString();
				if(username==null||"".equals(username)){
					showDialogMsg("用户名或者密码错误");
				}else if(password==null||"".equals(password)){
					showDialogMsg("用户名或者密码错误");
				}else{
					new Thread(runnable).start();
				}
			}
		});
	}

	private void adjustHeight(View view) {
		int _height = this.getWindowManager().getDefaultDisplay().getHeight() / 12;
		int _width = this.getWindowManager().getDefaultDisplay().getWidth()*2/5;
		LayoutParams titleparams = view.getLayoutParams();
		titleparams.height = _height;
		if(view instanceof Button){
			titleparams.width = _width;
		}
		view.setLayoutParams(titleparams);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.silding_home:
			startActivity(new Intent(this, MainActivity.class));
			break;
		
		}
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Dm_User_Result _returnResult = (Dm_User_Result) msg.obj; 
			String _resultMsg = _returnResult.message;
			boolean _resultSucc = _returnResult.isSucess;
			if(_resultSucc){
				Dm_User _returnUser = _returnResult.getUser();
				SharedPreferences userInfo = getSharedPreferences("_userloginMsg", Context.MODE_PRIVATE);
		        userInfo.edit().putString("_userid", _returnUser.getId()).commit();
				Intent intent = new Intent(SildingCenterLoginPageActivity.this,SildingCenterPageActivity.class);
				SildingCenterLoginPageActivity.this.startActivity(intent);
				
			}else{
				showDialogMsg(_resultMsg);
			}
		}
	};
	
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			String username = _username.getText().toString();
			String password = _password.getText().toString();
			TSocket socket = null;
			try {
				socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);
				Dm_User_Result _result = client.login(username, CommonUtil.stringToMD5(password));
				
				Message msg = handler.obtainMessage();
				msg.obj = _result;
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
	
	private void showDialogMsg(String str){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(str);
		builder.setPositiveButton("好", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	
	private void setSildingMenu(){
		_sildingView = LayoutInflater.from(SildingCenterLoginPageActivity.this).inflate(R.layout.silding_page,null);
		sildingRadioGroup = (RadioGroup) _sildingView.findViewById(R.id.silding_radio);
		((RadioButton)sildingRadioGroup.findViewById(R.id.silding_center)).setChecked(true);
		int height = SildingCenterLoginPageActivity.this.getWindowManager().getDefaultDisplay().getHeight()/10;
		_sildingTitle = (TextView) _sildingView.findViewById(R.id.silding_title);
		
		LayoutParams params = sildingRadioGroup.getLayoutParams();
		params.height = height*4;
		sildingRadioGroup.setLayoutParams(params);
		
		LayoutParams titleparams = _sildingTitle.getLayoutParams();
		titleparams.height = height;
		_sildingTitle.setLayoutParams(titleparams);
		sildingRadioGroup.setOnCheckedChangeListener(SildingCenterLoginPageActivity.this);
		
		final SlidingMenu menu = new SlidingMenu(SildingCenterLoginPageActivity.this);
		Button button = (Button) findViewById(R.id.silding_longin_menu);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menu.toggle();
			}
		});

		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setBehindWidth(this.getWindowManager().getDefaultDisplay().getWidth() / 2);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(_sildingView);
	}

}
