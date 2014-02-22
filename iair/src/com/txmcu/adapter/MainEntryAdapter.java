package com.txmcu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.app.Activity;
import android.content.Context;

import com.txmcu.iair.R;

public class MainEntryAdapter extends BaseAdapter
{
	
	private List<MainEntry> entries = new ArrayList<MainEntry>();;
	
	
	Context deviceManageActivity;
	
	
	public MainEntryAdapter(Context contentContext)
	{
		deviceManageActivity = contentContext;
	}
	
	public  void addDevice(int index,String name) {
		MainEntry book = new MainEntry();
    	book.setId(index);
    	book.setName(name);
    	//book.setBitmapId(R.drawable.b001);
    	entries.add(book);
	}
//	public void waspping(int oldIndex, int newIndex) {
//		MainEntry book = entries.get(oldIndex);
//		entries.remove(oldIndex);
//		entries.add(newIndex, book);
//	}
	
	@Override
	public int getCount() {
		//return 5;
		return entries.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return entries.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return entries.get(position-1).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position==0 ) 
		{
			convertView = View.inflate(deviceManageActivity, R.layout.mystocks_portfolio_listitem_functionbar, null);			
		}
		else if(position >= 1)
		{
			convertView = View.inflate(deviceManageActivity, R.layout.mystocks_portfolio_listitem_stockinfo, null);
			
			MainEntry b = entries.get(position-1);
			
			//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
			
			((TextView)convertView.findViewById(R.id.mystocks_listitem_stockname_label)).setText(b.getName());
			
			
		}
		return convertView;
	}
	
}