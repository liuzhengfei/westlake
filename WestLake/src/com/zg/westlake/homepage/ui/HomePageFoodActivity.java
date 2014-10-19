package com.zg.westlake.homepage.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_ActivitySimplify;
import com.dm.thrift.Dm_ActivitySimplifyList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.NewsAdapter;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.homepage.common.TitleImgResult;
import com.zg.westlake.pullrefresh.RefreshableView;
import com.zg.westlake.pullrefresh.RefreshableView.OnRefreshListener;

public class HomePageFoodActivity extends Activity {
	private NewsAdapter newsAdapter;
	private static final Logger logger = LoggerFactory.getLogger(HomePageActiveActivity.class);
	private String TypeId = null;
	private List<TitleImgResult> _imgList;
	RefreshableView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏，充满全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		TypeId = this.getIntent().getStringExtra("typeid");
		_imgList = (List<TitleImgResult>) this.getIntent().getSerializableExtra("imgList");
		this.setContentView(R.layout.home_page_food);
		listview = (RefreshableView) findViewById(R.id.foodlistview);
		new Thread(runnable).start();
	}

	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        	List<Map<String, Object>> _list = (List<Map<String, Object>>) msg.obj;
        	if(_list!=null){
        		newsAdapter = new NewsAdapter(HomePageFoodActivity.this,_imgList,_list);
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
    
    						new Thread(runnable).start();
    
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
	};
	
	
	// 调用获取图片接口
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				TSocket socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport=new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);
				
				Dm_ActivitySimplifyList activityList= client.searchActivitySimplifyByType(SocketUtil.VALIDSTRING,TypeId,1,5);
				List<Dm_ActivitySimplify> _actiList = activityList.getActivitySimplifyList(); 
				List<Map<String, Object>> _olist = new ArrayList<Map<String, Object>>();
				
				//头部位置占一个空的数据
				Map<String,Object> _oomap = new HashMap<String,Object>();
				_oomap.put("acid","");
				_oomap.put("acimg","");
				_oomap.put("acname","");
				_oomap.put("acdate","");
				_olist.add(_oomap);
				
				for(int i=0;i<_actiList.size();i++){
					Dm_ActivitySimplify _dmactivity = _actiList.get(i);
					Map<String,Object> _actiMap = new HashMap<String,Object>();
					_actiMap.put("acid", _dmactivity.getId());
					_actiMap.put("acimg", Picutil.returnBitMap(_dmactivity.getPicture()));
					_actiMap.put("acname", _dmactivity.getName());
					_actiMap.put("acdate", _dmactivity.getDate());
					_olist.add(_actiMap);
				}
				
				
				Message msg = handler.obtainMessage();
				msg.obj = _olist;
			    handler.sendMessage(msg);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
