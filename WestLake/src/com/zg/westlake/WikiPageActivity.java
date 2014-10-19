package com.zg.westlake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_BaikeType;
import com.dm.thrift.Dm_BaikeTypeList;
import com.zg.socket.SocketUtil;
import com.zg.westlake.homepage.common.Picutil;
import com.zg.westlake.wikipage.ui.WikiShortListPageActivity;

public class WikiPageActivity extends Activity {
	private static final Logger logger = LoggerFactory.getLogger(WikiPageActivity.class);
	private List<Map<String, Object>> _owikilist;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.wiki_page_activity);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		new Thread(runnable).start();
		progressDialog = new ProgressDialog(this);
    	progressDialog.setMessage("正在努力加载中");  //正在加载
    	progressDialog.show();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			_owikilist = (List<Map<String, Object>>) msg.obj;
			GridView gridView = (GridView) findViewById(R.id.wiki_gridview);
			gridView.setAdapter(new WikiAdapter(WikiPageActivity.this,_owikilist));
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent _intent = new Intent(WikiPageActivity.this, WikiShortListPageActivity.class);
					WikiAdapter _wikiAdapter = (WikiAdapter) parent.getAdapter();
					String _wikiid = (String) _wikiAdapter.owiki.get(position).get("wikiid");
					String _wikiname = (String) _wikiAdapter.owiki.get(position).get("wikiname");
					_intent.putExtra("_wikiid",_wikiid);
					_intent.putExtra("_wikiname",_wikiname);
					startActivity(_intent);
					
				}
				
			});
			
			if(_owikilist!=null){
				progressDialog.dismiss();
			}
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				TSocket socket = new TSocket(SocketUtil.SOCKETIP,
						SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);

				Dm_BaikeTypeList wikiTypeList = client
						.baikeType(SocketUtil.VALIDSTRING);
				List<Dm_BaikeType> wikiLists = wikiTypeList.baikeTypeList;
				List<Map<String, Object>> _wikiList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < wikiLists.size(); i++) {
					Map<String, Object> _wikiMap = new HashMap<String, Object>();
					Dm_BaikeType _baike = wikiLists.get(i);
					_wikiMap.put("wikiid", _baike.getId());
					_wikiMap.put("wikiname", _baike.getName());
					_wikiMap.put("wikiimg",
							Picutil.returnBitMap(_baike.getThumbnail()));
					_wikiList.add(_wikiMap);
				}

				Message msg = handler.obtainMessage();
				msg.obj = _wikiList;
				handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	};

	public class WikiAdapter extends BaseAdapter {
		private Context context;
		private List<Map<String, Object>> owiki;

		public WikiAdapter(Context context, List<Map<String, Object>> _wikilist) {
			this.context = context;
			this.owiki = _wikilist;
		}

		@Override
		public int getCount() {
			return owiki.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.wiki_page_grid_item, null);
				holder.sceneryImg = (ImageView) convertView.findViewById(R.id.wiki_page_imageview);
				LayoutParams params;
				params = holder.sceneryImg.getLayoutParams();
				params.height= ((Activity) context).getWindowManager().getDefaultDisplay().getHeight()/4;     
				holder.sceneryImg.setLayoutParams(params);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.sceneryImg.setImageBitmap((Bitmap) owiki.get(position).get("wikiimg"));

			return convertView;
		}

		// viewHolder静态类
		class ViewHolder {
			// public TextView sceneryName;
			public ImageView sceneryImg;
		}

	}

}
