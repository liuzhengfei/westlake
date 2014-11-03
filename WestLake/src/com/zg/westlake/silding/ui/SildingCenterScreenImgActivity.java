package com.zg.westlake.silding.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.zg.westlake.R;
import com.zg.westlake.util.CommonUtil;

public class SildingCenterScreenImgActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(SildingCenterScreenImgActivity.class);
	private ImageView _largeImg;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.silding_center_page_screenimg);

		String keyname = getIntent().getStringExtra("large_img");
		logger.debug("=========kao=>" + keyname);
		Bitmap _bitmap = (Bitmap) CommonUtil._cacheMap.get(keyname);
		
		_largeImg = (ImageView) findViewById(R.id.large_image);
		_largeImg.setImageBitmap(_bitmap);
		
		_largeImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SildingCenterScreenImgActivity .this.finish();
			}
		});
		
	}
}
