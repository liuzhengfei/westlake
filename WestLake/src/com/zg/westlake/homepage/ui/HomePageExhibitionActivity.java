package com.zg.westlake.homepage.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.zg.westlake.R;
import com.zg.westlake.homepage.common.NewsAdapter;
import com.zg.westlake.pullrefresh.RefreshableView;
import com.zg.westlake.pullrefresh.RefreshableView.OnRefreshListener;

public class HomePageExhibitionActivity extends Activity {
	private NewsAdapter newsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.home_page_food);
		
		final RefreshableView listview = (RefreshableView) findViewById(R.id.foodlistview);
		newsAdapter = new NewsAdapter(this);
		listview.setAdapter(newsAdapter);

		listview.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {

				new AsyncTask<Void, Void, Void>() {
					// ...b表示多个参数
					protected Void doInBackground(Void... params) {
						try {
							//
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}

						// 增加一条数据到list中
						// data.addFirst("刷新后内容：每天都是新的一天！！！，親！要努力奋斗哦！！！");

						return null;
					}

					protected void onPostExecute(Void result) {
						newsAdapter.notifyDataSetChanged();
						listview.onRefreshComplete();
					}

				}.execute();
			}
		});
	}
}
