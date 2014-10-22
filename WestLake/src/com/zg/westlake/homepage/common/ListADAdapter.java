package com.zg.westlake.homepage.common;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zg.westlake.R;

public class ListADAdapter extends BaseAdapter {
	public List<Map<String,Object>> _olist;
	private Context mContext;

	public ListADAdapter(Context context,List<Map<String,Object>> list) {
		// TODO Auto-generated constructor stub
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
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.list_item, null);
			}
			ImageView _imgView = (ImageView) convertView.findViewById(R.id.imageView_item);
			TextView _text = (TextView) convertView.findViewById(R.id.textView_item);
			TextView _date = (TextView) convertView.findViewById(R.id.dateView_item);
			Bitmap _bitmap = (Bitmap) _olist.get(position).get("acimg");
			_imgView.setImageBitmap(_bitmap);
			
			LayoutParams params;
			params = _imgView.getLayoutParams();
			params.height= ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth()/4;     
			params.width =((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth()/2;        
			_imgView.setLayoutParams(params);
			
			
			_text.setText((String)_olist.get(position).get("acname"));
			_date.setText((String)_olist.get(position).get("acdate"));

			return convertView;
	}
}
