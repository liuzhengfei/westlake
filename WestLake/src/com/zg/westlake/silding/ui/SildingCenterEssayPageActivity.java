package com.zg.westlake.silding.ui;

import com.zg.westlake.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SildingCenterEssayPageActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.silding_center_essay_page_activity);

	}
}
