package com.zg.westlake.wikipage.ui;

import com.zg.westlake.R;
import com.zg.westlake.homepage.common.WordWrapView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WikiShortListPageActivity extends Activity {
	private String _wikiid;
	private String _wikiname;
	private WordWrapView wordWrapView;
	private String[] strs = new String[] { "哲学系", "新疆维吾尔族自治区", "新闻学", "心理学",
			"犯罪心理学", "明明白白", "西方文学史", "计算机", "掌声", "心太软", "生命", "程序开发" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wiki_short_list_page);

		_wikiid = getIntent().getStringExtra("_wikiid");
		_wikiname = getIntent().getStringExtra("_wikiname");
		
		wordWrapView = (WordWrapView) this.findViewById(R.id.view_wordwrap);
		for (int i = 0; i < 12; i++) {
			TextView textview = new TextView(this);
			textview.setText(strs[i]);
			wordWrapView.addView(textview);
		}
	}
}
