package com.zg.westlake.silding.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.zg.westlake.R;

public class SildingCenterPageActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterPageActivity.class);

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.silding_center_page_activity);
	}
}
