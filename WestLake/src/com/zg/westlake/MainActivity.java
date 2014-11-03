package com.zg.westlake;

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
import com.zg.westlake.silding.ui.SildingCenterLoginPageActivity;
import com.zg.westlake.silding.ui.SildingCenterPageActivity;

public class MainActivity extends Activity implements OnCheckedChangeListener{
	private static final Logger logger = LoggerFactory
			.getLogger(MainActivity.class);
	private TabHost mHost;
	private RadioGroup radioderGroup;
	private TextView textView;
	LocalActivityManager localManager;
	private View _sildingView;
	private RadioGroup sildingRadioGroup;
	private TextView _sildingTitle;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		mHost = (TabHost) findViewById(R.id.tabhost);
		textView = (TextView) findViewById(R.id.textView);
		localManager = new LocalActivityManager(MainActivity.this, false);
		localManager.dispatchCreate(savedInstanceState);
		mHost.setup(localManager);

		mHost.addTab(mHost.newTabSpec("ONE").setIndicator("ONE")
				.setContent(new Intent(this, HomePageActivity.class)));
		mHost.addTab(mHost.newTabSpec("TWO").setIndicator("TWO")
				.setContent(new Intent(this, SearchPageActivity.class)));
		mHost.addTab(mHost.newTabSpec("THREE").setIndicator("THREE")
				.setContent(new Intent(this, SitesPageActivity.class)));
		mHost.addTab(mHost.newTabSpec("FOUR").setIndicator("FOUR")
				.setContent(new Intent(this, WikiPageActivity.class)));
		mHost.addTab(mHost.newTabSpec("FIVE").setIndicator("FIVE")
				.setContent(new Intent(this, MorePageActivity.class)));

		radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioderGroup.setOnCheckedChangeListener(this);

		_sildingView = LayoutInflater.from(this).inflate(R.layout.silding_page,null);
		sildingRadioGroup = (RadioGroup) _sildingView.findViewById(R.id.silding_radio);
		((RadioButton)sildingRadioGroup.findViewById(R.id.silding_home)).setChecked(true);
		int height = this.getWindowManager().getDefaultDisplay().getHeight()/10;
		_sildingTitle = (TextView) _sildingView.findViewById(R.id.silding_title);
		
		LayoutParams params = sildingRadioGroup.getLayoutParams();
		params.height = height*4;
		sildingRadioGroup.setLayoutParams(params);
		
		LayoutParams titleparams = _sildingTitle.getLayoutParams();
		titleparams.height = height;
		_sildingTitle.setLayoutParams(titleparams);
		sildingRadioGroup.setOnCheckedChangeListener(this);
		
		final SlidingMenu menu = new SlidingMenu(this);
		Button button = (Button) super.findViewById(R.id.title_bt_left);
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
		menu.setBehindWidth(this.getWindowManager().getDefaultDisplay()
				.getWidth() / 2);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(_sildingView);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_page:
			textView.setText(R.string.honmepage);
			mHost.setCurrentTabByTag("ONE");
			break;
		case R.id.search_page:
			textView.setText(R.string.search);
			mHost.setCurrentTabByTag("TWO");
			break;
		case R.id.sites_page:
			textView.setText(R.string.sites);
			mHost.setCurrentTabByTag("THREE");
			break;
		case R.id.knows_page:
			textView.setText(R.string.wiki);
			mHost.setCurrentTabByTag("FOUR");
			break;
		case R.id.more_page:
			textView.setText(R.string.more);
			mHost.setCurrentTabByTag("FIVE");
			break;
//		case R.id.silding_map:
//		case R.id.silding_setting:	
		case R.id.silding_center:
			SharedPreferences userInfo = getSharedPreferences("_userloginMsg", Context.MODE_PRIVATE);
		   	String _userid = userInfo.getString("_userid", "");
		   	if(_userid!=null&&!"".equals(_userid)){
		   		startActivity(new Intent(this, SildingCenterPageActivity.class));
		   	}else{
		   		startActivity(new Intent(this, SildingCenterLoginPageActivity.class));
		   	}
			break;
		
		}
	}

}
