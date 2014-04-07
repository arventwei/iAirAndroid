package com.txmcu.iair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.activity.CityManageActivity;
import com.txmcu.iair.common.iAirApplication;

public class CityAdapter extends BaseAdapter
{
	
	private List<City> cities = new ArrayList<City>();;
	
	
	CityManageActivity cityManageActivity;
	iAirApplication application;
	
	public CityAdapter(CityManageActivity activity)
	{
		cityManageActivity = activity;
		application = (iAirApplication)cityManageActivity.getApplication();
	}
	public void sync()
	{
		cities.clear();
		for (City city : application.cityList) {
			cities.add(city);
		}
		cities.add(new City(""));
		//cities = application.cityList;
		
	}
//	public  void addCity(String name) {
//		City book = new City();
//    	//book.(index);
//    	book.name = (name);
//    	//book.setBitmapId(R.drawable.b001);
//    	cities.add(book);
//	}
	public void waspping(int oldIndex, int newIndex) {
		
		if (oldIndex == cities.size()-1
				||newIndex == cities.size()-1) {
			return;
		}
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
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		if (null == convertView) {
//			convertView = View.inflate(cityManageActivity, R.layout.gridview_city_item, null);
//		}
		
		City b = cities.get(position);
		
		if (b.areaId.equals("")) {
			convertView = View.inflate(cityManageActivity, R.layout.gridview_city_add, null);
		}
		else {
			convertView = View.inflate(cityManageActivity, R.layout.gridview_city_item, null);
			((TextView)convertView.findViewById(R.id.city_name)).setText(b.name);
			((TextView)convertView.findViewById(R.id.city_temperature)).setText(b.temp_info);
			((TextView)convertView.findViewById(R.id.bottom_area)).setText(b.weather);
			
			if (cityManageActivity.editMode) {
				convertView.findViewById(R.id.delete_btn).setVisibility(View.VISIBLE);
			}
			else {
				convertView.findViewById(R.id.delete_btn).setVisibility(View.INVISIBLE);
			}
			
		}
		
		//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
		
		
		
		setitemSize(convertView);
		return convertView;
	}
	
	  void setitemSize(View paramView)
	  {
	    AbsListView.LayoutParams localLayoutParams = new AbsListView.LayoutParams(200,300);
	    localLayoutParams.width = 230;
	    localLayoutParams.height = 300;
	    paramView.setLayoutParams(localLayoutParams);
	  }
	  
	
}