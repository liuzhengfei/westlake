package com.zg.westlake.wikipage.ui;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dm.thrift.DmService;
import com.dm.thrift.Dm_Baike;
import com.dm.thrift.Dm_BaikeOne;
import com.zg.socket.SocketUtil;
import com.zg.westlake.R;

public class WikiShortShowPageActivity extends Activity {
	private static final Logger logger = LoggerFactory
			.getLogger(WikiShortShowPageActivity.class);
	private String _wikishortid;
	private TextView wiki_short_title;
	private TextView wiki_short_text;
	private Button wikibutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wiki_short_show_page);
		_wikishortid = getIntent().getStringExtra("wikishortid");
		wiki_short_title = (TextView) findViewById(R.id.wiki_short_title);
		wiki_short_text = (TextView) findViewById(R.id.wiki_short_text);
		wikibutton = (Button) findViewById(R.id.wiki_left);
		wikibutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				WikiShortShowPageActivity.this.finish();
			}
		});
		new Thread(runnable).start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Dm_BaikeOne _wiki = (Dm_BaikeOne) msg.obj;
			Dm_Baike _baike = _wiki.baike;
			wiki_short_title.setText(_baike.getName());
			wiki_short_text.setText(Html.fromHtml(_baike.getContent()));
		}
	};

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				TSocket socket = new TSocket(SocketUtil.SOCKETIP,
						SocketUtil.PORT);
				socket.open();
				TFramedTransport framedtransport = new TFramedTransport(socket);
				TProtocol protocol = new TBinaryProtocol(framedtransport);
				DmService.Client client = new DmService.Client(protocol);

				Dm_BaikeOne wikiShort = client.viewBaike(
						SocketUtil.VALIDSTRING, _wikishortid);

				Message msg = handler.obtainMessage();
				msg.obj = wikiShort;
				handler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};
}
