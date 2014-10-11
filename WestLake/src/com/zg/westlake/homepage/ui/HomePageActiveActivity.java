package com.zg.westlake.homepage.ui;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dm.thrift.Dm_ActivitySimplifyList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.NewsAdapter;
import com.zg.westlake.pullrefresh.RefreshableView;
import com.zg.westlake.pullrefresh.RefreshableView.OnRefreshListener;

public class HomePageActiveActivity extends Activity {
	private NewsAdapter newsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏，充满全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
	
	
	//调用获取图片接口
	Runnable runnable = new Runnable() {
		TTransport transport = null;
		@Override
		public void run() {
			transport = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT, SocketUtil.TIMEOUT);
			TProtocol protocol = new TBinaryProtocol(transport);
			
		}
	};

}
