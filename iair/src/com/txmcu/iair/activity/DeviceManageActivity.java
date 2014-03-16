package com.txmcu.iair.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import cn.classd.dragablegrid.widget.DragableGridview;
import cn.classd.dragablegrid.widget.DragableGridview.OnItemClickListener;
import cn.classd.dragablegrid.widget.DragableGridview.OnSwappingListener;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.City;
import com.txmcu.iair.adapter.DeviceAdapter;

public class DeviceManageActivity extends Activity 
implements OnClickListener{

	public static final String TAG = "DeviceManageActivity";
	
	//private List<City> books;
	
	private DragableGridview mGridview;
	
	private  DeviceAdapter adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_manage_activity);
		findViewById(R.id.back_img).setOnClickListener(this);
		mGridview= ((DragableGridview)findViewById(R.id.device_gridview));
		
		
		
		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);
	        
        adapter = new DeviceAdapter(this);
        
        initTestData();
        addGridViewListener();
		
	}

	protected void addGridViewListener() {
		mGridview.setAdapter(adapter);
		mGridview.setOnItemClick(new OnItemClickListener() {
			
			@Override
			public void click(int index) {
				Log.d(TAG, "item : " + index + " -- clicked!");
			}
		});
		
		mGridview.setOnSwappingListener(new OnSwappingListener() {
			
			@Override
			public void waspping(int oldIndex, int newIndex) {
				adapter.waspping(oldIndex,newIndex);
				
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	@Override
	public void onClick(View paramView)
	{
	    switch (paramView.getId())
	    {
	    	case R.id.back_img:
	    		finish();
	    		break;
	    	
	    }
	}
	
	
	 
	  private void initTestData() {
		  adapter.addDevice(-1, "");
		  //adapter.addDevice(2,"father");
	    	//books = new ArrayList<City>();
	    	
			//for (int i = 0; i < 1; i++) {
				//setBooks();
			//}
	    }
	    
	  
}
