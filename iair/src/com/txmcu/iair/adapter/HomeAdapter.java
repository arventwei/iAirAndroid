package com.txmcu.iair.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.activity.HomeManageActivity;
import com.txmcu.iair.common.iAirApplication;

public class HomeAdapter extends BaseAdapter
{
	
	private List<Home> homes = new ArrayList<Home>();;
	
	
	HomeManageActivity homeManageActivity;
	
	
	public HomeAdapter(HomeManageActivity activity)
	{
		homeManageActivity = activity;
	}
	
//	public  void addCity(int index,String name) {
//		Home book = new Home();
//    	//book.id = index;
//    	book.name = name;
//    	//book.setName(name);
//    	//book.setBitmapId(R.drawable.b001);
//    	homes.add(book);
//	}
	public void waspping(int oldIndex, int newIndex) {
		Home book = homes.get(oldIndex);
		homes.remove(oldIndex);
		homes.add(newIndex, book);
	}
	
	@Override
	public int getCount() {
		//return 5;
		return homes.size();
	}

	@Override
	public Object getItem(int position) {
		return homes.get(position);
	}

	@Override
	public long getItemId(int position) {
		//return homes.get(position).id;
		return 0;
	}
	
	public void syncHomes(){
		homes.clear();
		
		iAirApplication application = (iAirApplication)homeManageActivity.getApplication();
//		List<String> snList = application.getHomeSnList();
//		for (String sn : snList) {
//			//Home xiaoxinDevice = application.getHome(sn);
//			//homes.add(xiaoxinDevice);
//		}
		for (Home home : application.homeList) {
			homes.add(home);
		}
		homes.add(new Home(""));
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Home b = homes.get(position);
		if(b.homeid.equals(""))
		{
			convertView = View.inflate(homeManageActivity, 
					R.layout.gridview_home_add, null);
			//ImageView addbtn =(ImageView)convertView.findViewById(R.id.grid_device_add_btn);
			//addbtn.setOnClickListener(new DeviceAddClickListener(this));
		}
		else
		{
			convertView = View.inflate(homeManageActivity, R.layout.gridview_home_item, null);
			((TextView)convertView.findViewById(R.id.city_name)).setText(b.homename);
			//if (deviceManageActivity.editMode) {
			//	convertView.findViewById(R.id.delete_btn).setVisibility(View.VISIBLE);
			//}
			//else {
			//	convertView.findViewById(R.id.delete_btn).setVisibility(View.INVISIBLE);
			//}
		}
		
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