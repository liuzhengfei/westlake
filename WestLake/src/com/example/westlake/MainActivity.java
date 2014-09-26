package com.example.westlake;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends TabActivity implements OnCheckedChangeListener{
	private TabHost mHost;
	private RadioGroup radioderGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 //实例化TabHost
        mHost=this.getTabHost();
        
        //添加选项卡
        mHost.addTab(mHost.newTabSpec("ONE").setIndicator("ONE")
        			.setContent(new Intent(this,OneActivity.class)));
        mHost.addTab(mHost.newTabSpec("TWO").setIndicator("TWO")
        		.setContent(new Intent(this,TwoActivity.class)));
        mHost.addTab(mHost.newTabSpec("THREE").setIndicator("THREE")
        		.setContent(new Intent(this,ThreeActivity.class)));
        mHost.addTab(mHost.newTabSpec("FOUR").setIndicator("FOUR")
        		.setContent(new Intent(this,FourActivity.class)));
        mHost.addTab(mHost.newTabSpec("FIVE").setIndicator("FIVE")
        		.setContent(new Intent(this,FiveActivity.class)));
        
        radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioderGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
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
