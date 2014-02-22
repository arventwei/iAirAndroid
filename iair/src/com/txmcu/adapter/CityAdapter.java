package com.txmcu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txmcu.activity.CityManageActivity;
import com.txmcu.iair.R;

public class CityAdapter extends BaseAdapter
{
	
	private List<City> cities = new ArrayList<City>();;
	
	
	CityManageActivity cityManageActivity;
	
	
	public CityAdapter(CityManageActivity activity)
	{
		cityManageActivity = activity;
	}
	
	public  void addCity(int index,String name) {
		City book = new City();
    	book.setId(index);
    	book.setName(name);
    	//book.setBitmapId(R.drawable.b001);
    	cities.add(book);
	}
	public void waspping(int oldIndex, int newIndex) {
		City book = cities.get(oldIndex);
		cities.remove(oldIndex);
		cities.add(newIndex, book);
	}
	
	@Override
	public int getCount() {
		//return 5;
		return cities.size();
	}

	@Override
	public Object getItem(int position) {
		return cities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return cities.get(position).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = View.inflate(cityManageActivity, R.layout.gridview_change_city_item, null);
		}
		
		City b = cities.get(position);
		
		//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
		
		((TextView)convertView.findViewById(R.id.city_name)).setText(b.getName());
		
		return convertView;
	}
	
}