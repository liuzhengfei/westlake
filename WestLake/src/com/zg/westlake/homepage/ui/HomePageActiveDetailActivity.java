package com.zg.westlake.homepage.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_Activity;
import com.dm.thrift.Dm_ActivityOne;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;
import com.zg.westlake.homepage.common.Picutil;

public class HomePageActiveDetailActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(HomePageActiveDetailActivity.class);
	private String _activeId;
	private TextView _activeName;
	private TextView _activeDate;
	private ImageView _activePic;
	private TextView _activeEventTime;
	private TextView _activePlace;
	private TextView _activePrice;
	private TextView _activeContent;
	private ProgressDialog progressDialog;
	private Button _backBt;
	private TextView _backTv;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_page_active_detail);
		_activeId = getIntent().getStringExtra("_acid");
		_activeName = (TextView) findViewById(R.id.active_name);
		_activeDate = (TextView) findViewById(R.id.active_date);
		_activePic = (ImageView) findViewById(R.id.active_pic);
		_activeEventTime = (TextView) findViewById(R.id.active_eventtime);
		_activePlace = (TextView) findViewById(R.id.active_place);
		_activePrice = (TextView) findViewById(R.id.active_price);
		_activeContent = (TextView) findViewById(R.id.active_content);
		_backBt = (Button) findViewById(R.id.active_left);
		_backTv = (TextView) findViewById(R.id.active_text);
		_backBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HomePageActiveDetailActivity.this.finish();
			}
		});
		_backTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HomePageActiveDetailActivity.this.finish();
			}
		});
		
		
		new Thread(runnable).start();
		progressDialog = new ProgressDialog(this);
    	progressDialog.setMessage("正在努力加载中");  //正在加载
    	progressDialog.show();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Map<String,Object> _result =  (Map<String, Object>) msg.obj;
			if(_result!=null){
				progressDialog.dismiss();
			}
			_activeName.setText((CharSequence) _result.get("name"));
			_activeDate.setText((CharSequence) _result.get("date"));
			_activePic.setImageBitmap((Bitmap) _result.get("pic"));
			int _height = HomePageActiveDetailActivity.this.getWindowManager().getDefaultDisplay().getHeight();
			LayoutParams params;
			params = _activePic.getLayoutParams();
			params.height = _height/3;
			_activePic.setLayoutParams(params);
			
			String eventtime = (String) _result.get("eventtime");
			String eventplace = (String) _result.get("eventplace");
			String eventprice = (String) _result.get("eventprice");
			if(eventtime==null||"null".equals(eventtime)){
				eventtime = "活动时间：暂无";
			}else{
				eventtime= "活动时间：" + eventtime;
			}
			if(eventplace==null||"null".equals(eventplace)){
				eventplace = "活动地点：暂无";
			}else{
				eventplace="活动地点：" + eventplace;
			}
			if(eventprice==null||"null".equals(eventprice)){
				eventprice = "活动票价：暂无";
			}else{
				eventprice+="活动票价："+ eventprice;
			}
			_activeEventTime.setText(eventtime);
			_activeEventTime.setBackgroundResource(R.drawable.textview_border);
			_activePlace.setText(eventplace);
			_activePlace.setBackgroundResource(R.drawable.textview_border);
			_activePrice.setText(eventprice);
			_activePrice.setBackgroundResource(R.drawable.textview_border);
			_activeContent.setText((CharSequence) _result.get("content"));
		}
	};

	// 调用获取图片接口
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			TSocket socket = null;
			try {
				socket = new TSocket(SocketUtil.SOCKETIP, SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);

				Dm_ActivityOne _activeOne = client.viewActivity(
						SocketUtil.VALIDSTRING, _activeId);
				Dm_Activity _active = _activeOne.activity;
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("name", _active.getName());
				map.put("date", _active.getDate());
				map.put("pic", Picutil.returnBitMap(_active.getPicture()));
				map.put("eventtime", _active.getEventime());
				map.put("eventplace", _active.getAddress());
				map.put("eventprice", _active.getPrice());
				map.put("content", _active.getContent());

				Message msg = handler.obtainMessage();
				msg.obj = map;
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (socket != null) {
					socket.close();
				}
			}
		}

	};

}