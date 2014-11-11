package com.zg.westlake.silding.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.slidingmenu.lib.SlidingMenu;
import com.zg.westlake.R;

public class SildingCenterPageActivity extends Activity implements OnCheckedChangeListener{
	
	private View _sildingView;
	private RadioGroup sildingRadioGroup;
	private TextView _sildingTitle;
	private TextView _pageTextView;
	private TabHost mHost;
	LocalActivityManager localManager;
	private RadioGroup radioderGroup;
	private Button _writeEssayBt;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.silding_center_page_activity);
		localManager = new LocalActivityManager(SildingCenterPageActivity.this, false);
		localManager.dispatchCreate(savedInstanceState);
		
		initView();
		
		setSildingMenu();
//		SharedPreferences userInfo = getSharedPreferences("_userloginMsg", Context.MODE_PRIVATE);
//	   	String _userid = userInfo.getString("_userid", "");
	   	
	}
	
	private void initView(){
		mHost = (TabHost) findViewById(R.id.center_page_tabhost);
		_pageTextView = (TextView) findViewById(R.id.center_page_textview);
		_writeEssayBt = (Button) findViewById(R.id.center_page_title_right_bt);
		
		mHost.setup(localManager);
		mHost.addTab(mHost.newTabSpec("STORY").setIndicator("STORY")
				.setContent(new Intent(this, SildingCenterStoryPageActivity.class)));
		mHost.addTab(mHost.newTabSpec("ESSAY").setIndicator("ESSAY")
				.setContent(new Intent(this, SildingCenterEssayPageActivity.class)));
		radioderGroup = (RadioGroup) findViewById(R.id.center_page_radios);
		radioderGroup.setOnCheckedChangeListener(this);
		
		_writeEssayBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SildingCenterPageActivity.this, SildingCenterWriteEssayPageActivity.class));
			}
		});
		
	}
	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.center_page_story:
			_pageTextView.setText(R.string.story);
			mHost.setCurrentTabByTag("STORY");
			_writeEssayBt.setVisibility(View.INVISIBLE);
			break;
		case R.id.center_page_essay:
			_pageTextView.setText(R.string.essay);
			mHost.setCurrentTabByTag("ESSAY");
			_writeEssayBt.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	private void setSildingMenu(){
		_sildingView = LayoutInflater.from(SildingCenterPageActivity.this).inflate(R.layout.silding_page,null);
		sildingRadioGroup = (RadioGroup) _sildingView.findViewById(R.id.silding_radio);
		((RadioButton)sildingRadioGroup.findViewById(R.id.silding_center)).setChecked(true);
		int height = SildingCenterPageActivity.this.getWindowManager().getDefaultDisplay().getHeight()/10;
		_sildingTitle = (TextView) _sildingView.findViewById(R.id.silding_title);
		
		LayoutParams params = sildingRadioGroup.getLayoutParams();
		params.height = height*4;
		sildingRadioGroup.setLayoutParams(params);
		
		LayoutParams titleparams = _sildingTitle.getLayoutParams();
		titleparams.height = height;
		_sildingTitle.setLayoutParams(titleparams);
		sildingRadioGroup.setOnCheckedChangeListener(SildingCenterPageActivity.this);
		
		final SlidingMenu menu = new SlidingMenu(SildingCenterPageActivity.this);
		Button button = (Button) findViewById(R.id.center_page_title_left_bt);
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
