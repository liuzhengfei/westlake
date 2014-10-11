package com.zg.westlake;

import com.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends Activity implements
		OnCheckedChangeListener {
	private TabHost mHost;
	private RadioGroup radioderGroup;
	LocalActivityManager localManager;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);

		mHost = (TabHost) findViewById(R.id.tabhost);
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
				.setContent(new Intent(this, KnowsPageActivity.class)));
		mHost.addTab(mHost.newTabSpec("FIVE").setIndicator("FIVE")
				.setContent(new Intent(this, MorePageActivity.class)));

		radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioderGroup.setOnCheckedChangeListener(this);
		
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
		menu.setBehindWidth(200);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.more_page_activity);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_page:
			mHost.setCurrentTabByTag("ONE");
			break;
		case R.id.search_page:
			mHost.setCurrentTabByTag("TWO");
			break;
		case R.id.sites_page:
			mHost.setCurrentTabByTag("THREE");
			break;
		case R.id.knows_page:
			mHost.setCurrentTabByTag("FOUR");
			break;
		case R.id.more_page:
			mHost.setCurrentTabByTag("FIVE");
			break;
		}
	}
}
