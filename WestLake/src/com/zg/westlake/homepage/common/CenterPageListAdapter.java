package com.zg.westlake.homepage.common;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zg.westlake.R;
import com.zg.westlake.silding.ui.SildingCenterScreenImgActivity;

public class CenterPageListAdapter extends BaseAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(CenterPageListAdapter.class);
	public List<Map<String, Object>> _olist;
	private Context mContext;
	private String _contentimgurl = null;
	private ImageView _userImage;
	private TextView _creatorTv;
	private TextView _dateTv;
	private TextView _contentTv;
	private ImageView _contentImage;

	public CenterPageListAdapter(Context context, List<Map<String, Object>> list) {
		mContext = context;
		this._olist = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _olist.size();
	}

	@Override
	public Object getItem(int position) {
		return _olist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.silding_center_list_item, null);
		}
		_userImage = (ImageView) convertView
				.findViewById(R.id.center_list_userimg);
		_creatorTv = (TextView) convertView
				.findViewById(R.id.center_list_creator);
		_dateTv = (TextView) convertView
				.findViewById(R.id.center_list_createdate);
		_contentTv = (TextView) convertView
				.findViewById(R.id.center_list_content);
		_contentImage = (ImageView) convertView
				.findViewById(R.id.center_list_contentimg);

		Bitmap _userbtimg = (Bitmap) _olist.get(position).get("userimg");
		Bitmap _contentimg = (Bitmap) _olist.get(position).get("contentimg");
		_contentimgurl = (String) _olist.get(position).get("contentimgurl");

		_userImage.setImageBitmap(_userbtimg);
		_contentImage.setImageBitmap(_contentimg);
		_creatorTv.setText((String) _olist.get(position).get("creator"));
		_dateTv.setText((String) _olist.get(position).get("createdate"));
		_contentTv.setText((String) _olist.get(position).get("content"));

		_contentImage.setTag(_contentimgurl);
		_contentImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String _t = (String) v.getTag();
				if (_t != null && !"".equals(_t) && !"null".equals(_t)) {
					Intent _intent = new Intent(mContext,
							SildingCenterScreenImgActivity.class);
					_intent.putExtra("large_img", (String) v.getTag());
					mContext.startActivity(_intent);
				}
			}
		});

		return convertView;
	}

}
