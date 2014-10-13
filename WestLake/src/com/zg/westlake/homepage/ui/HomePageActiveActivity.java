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
import com.dm.thrift.Dm_PreImageList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.NewsAdapter;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.pullrefresh.RefreshableView;
import com.zg.westlake.pullrefresh.RefreshableView.OnRefreshListener;

public class HomePageActiveActivity extends Activity {
	private NewsAdapter newsAdapter;
	private static final Logger logger = LoggerFactory.getLogger(HomePageActiveActivity.class);
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
		this.setContentView(R.layout.home_page_food);
		listview = (RefreshableView) findViewById(R.id.foodlistview);
		new Thread(runnable).start();
	}

	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        	List<Map<String,Object>> _piclist= (List<Map<String, Object>>) msg.obj;
        	if(_piclist!=null){
        		newsAdapter = new NewsAdapter(HomePageActiveActivity.this,_piclist);
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
    						//data.addFirst("刷新后内容：每天都是新的一天！！！，親！要努力奋斗哦！！！");
    
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
				
				Dm_PreImageList piclist= client.getPreImageList(SocketUtil.VALIDSTRING);
				List<Dm_ActivitySimplify> oPicList = piclist.getPreImageList();
				List<Map<String,Object>> picImgList = new ArrayList<Map<String,Object>>();
				for(int i=0;i<oPicList.size();i++){
					Map<String,Object> picMap = new HashMap<String,Object>();
					Dm_ActivitySimplify oPic = (Dm_ActivitySimplify) oPicList.get(i);
					picMap.put("titleImg", Picutil.returnBitMap(oPic.getPicture()));
					picImgList.add(picMap);
				}
				Message msg = handler.obtainMessage();
				msg.obj = picImgList;
			    handler.sendMessage(msg);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

}
