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
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_User;
import com.dm.thrift.Dm_User_Result;
import com.slidingmenu.lib.SlidingMenu;
import com.zg.socket.SocketUtil;
import com.zg.westlake.MainActivity;
import com.zg.westlake.R;

public class SildingCenterRegisterPageActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterRegisterPageActivity.class);
	private EditText _regusername;
	private EditText _regmail;
	private EditText _regpassword;
	private EditText _regcheckpw;
	private Button _registeryes;
	private Button _registerno;
	private CheckBox _checkbox;
	private Button _backbt;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.silding_center_register_page_activity);
		_regusername = (EditText) findViewById(R.id.register_username);
		_regmail = (EditText) findViewById(R.id.register_mail);
		_regpassword = (EditText) findViewById(R.id.register_password);
		_regcheckpw = (EditText) findViewById(R.id.register_re_password);
		_registeryes = (Button) findViewById(R.id.register_yes_bt);
		_registerno = (Button) findViewById(R.id.register_no_bt);
		_checkbox = (CheckBox) findViewById(R.id.register_check);
		_backbt = (Button) findViewById(R.id.silding_register_back);
		adjustHeight(_regusername);
		adjustHeight(_regmail);
		adjustHeight(_regpassword);
		adjustHeight(_regcheckpw);
		adjustHeight(_registeryes);
		adjustHeight(_registerno);
		_checkbox.setText(Html.fromHtml("我同意<font color=\"#0080FF\">服务条款</font>和<font color=\"#0080FF\">隐私条款</font>"));
		_registerno.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SildingCenterRegisterPageActivity.this.finish();
			}
		});
		_backbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SildingCenterRegisterPageActivity.this.finish();
			}
		});
		_registeryes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = _regusername.getText().toString();
				String email = _regmail.getText().toString();
				String password = _regpassword.getText().toString();
				String checkpassword = _regcheckpw.getText().toString();
				boolean agreecheck = _checkbox.isChecked();
				checkRegister(username, email, password, checkpassword,agreecheck);
			}
		});
		
	}

	private void adjustHeight(View view) {
		int _height = this.getWindowManager().getDefaultDisplay().getHeight() / 10;
		LayoutParams titleparams = view.getLayoutParams();
		titleparams.height = _height;
		view.setLayoutParams(titleparams);
	}
	
	private void checkRegister(String username,String email,String password,String checkpassword,boolean ischecked){
		if(username==null||"".equals(username)){
			showDialogMsg("请输入用户名！");
		}else if(email==null||"".equals(email)){
			showDialogMsg("请输入邮箱！");
		}else if(password==null||"".equals(password)){
			showDialogMsg("请输入密码！");
		}else if(checkpassword==null||"".equals(checkpassword)){
			showDialogMsg("请确认密码！");
		}else if(!password.equals(checkpassword)){
			showDialogMsg("两次输入的密码不一致！");
		}else if(!ischecked){
			showDialogMsg("请同意条款");
		}else{
			new Thread(runnable).start();
		}
		
		
	}
	
	private void showDialogMsg(String str){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(str);
		builder.setPositiveButton("好", null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			String username = _regusername.getText().toString();
			String email = _regmail.getText().toString();
			String password = _regpassword.getText().toString();
			TSocket socket = null;
			try {
				socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);
				Dm_User dm_user=new Dm_User();
				dm_user.setUsername(username);
				dm_user.setEmail(email);
				dm_user.setPassword(password);
				Dm_User_Result _result = client.regesiter(dm_user);
				
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
