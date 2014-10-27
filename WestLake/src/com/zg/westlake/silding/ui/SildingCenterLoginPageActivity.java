package com.zg.westlake.silding.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.slidingmenu.lib.SlidingMenu;
import com.zg.westlake.MainActivity;
import com.zg.westlake.R;

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

	}

	private void adjustHeight(View view) {
		int _height = this.getWindowManager().getDefaultDisplay().getHeight() / 10;
		LayoutParams titleparams = view.getLayoutParams();
		titleparams.height = _height;
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
