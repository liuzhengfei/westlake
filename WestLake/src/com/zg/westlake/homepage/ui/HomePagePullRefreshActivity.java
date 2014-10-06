package com.zg.westlake.homepage.ui;

import java.util.LinkedList;

import com.zg.westlake.R;
import com.zg.westlake.pullrefresh.RefreshableView;
import com.zg.westlake.pullrefresh.RefreshableView.OnRefreshListener;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomePagePullRefreshActivity extends Activity {
	private LinkedList<String> data;
	private BaseAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		// 去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏，取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pull_refresh);

		data = new LinkedList<String>();
		for (int i = 40; i < 50; i++) {
			// 输出list——item上的数据
			data.add(String.valueOf("暑假第" + i + "天，在南阳理工，想家ing ..."));
		}

		final RefreshableView listView = (RefreshableView) findViewById(R.id.pulllistview);
		adapter = new BaseAdapter() {

			// 得到一个视图,显示在指定位置上的数据在数据集，可以创建一个视图从XML布局文件
			public View getView(int position, View convertView, ViewGroup parent) {
				// 上下文全局应用程序对象
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.list_item, null);
				// 实例化组件
				TextView textView = (TextView) convertView
						.findViewById(R.id.textView_item);
				// 设置文本本内容
				textView.setText(data.get(position));
				return convertView;
			}

			// 得到行相关联id列表中指定的位置。
			public long getItemId(int position) {
				return position;
			}

			// 获得相关的数据项中的指定位置的数据集
			public Object getItem(int position) {
				return data.get(position);
			}

			// 获得项目在数据集适配器的个数。
			public int getCount() {
				return data.size();
			}

		};

		listView.setAdapter(adapter);

		listView.setonRefreshListener(new OnRefreshListener() {
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
						data.addFirst("刷新后内容：每天都是新的一天！！！，親！要努力奋斗哦！！！");

						return null;
					}

					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}

				}.execute();
			}
		});
	}
}
