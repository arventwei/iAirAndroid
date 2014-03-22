package com.txmcu.iair.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import cn.classd.dragablegrid.widget.DragableGridview;
import cn.classd.dragablegrid.widget.DragableGridview.OnItemClickListener;
import cn.classd.dragablegrid.widget.DragableGridview.OnSwappingListener;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
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
		setContentView(R.layout.activity_device_manage);
		findViewById(R.id.back_img).setOnClickListener(this);
		mGridview= ((DragableGridview)findViewById(R.id.device_gridview));
		
		
		
		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);
	        
        adapter = new DeviceAdapter(this);
        
        adapter.syncDevices();
        adapter.notifyDataSetChanged();
        //initTestData();
        addGridViewListener();
		
	}

	protected void addGridViewListener() {
		mGridview.setAdapter(adapter);
		mGridview.setOnItemClick(new OnItemClickListener() {
			
			@Override
			public void click(int index) {
				Log.d(TAG, "item : " + index + " -- clicked!");
				
				Device device = (Device)adapter.getItem(index);
				if (device.sn.equals("")) {
					Intent localIntent = new Intent(DeviceManageActivity.this,DeviceAddActivity.class);
					startActivity(localIntent);
				    overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
				}
				else {
					Intent localIntent = new Intent(DeviceManageActivity.this,DeviceModifyActivity.class);
					startActivity(localIntent);
				    overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
				}
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
	
	
	 
//	  private void initTestData() {
//		
//		  adapter.addDevice(2,"father");
//		  adapter.addDevice(3,"father");
//		  adapter.addDevice(4,"father");
//		  
//		  adapter.addDevice(25,"father");
//		  adapter.addDevice(266,"father");
//		  adapter.addDevice(45,"father");
//		  
//		  adapter.addDevice(-1, "");
//	    	//books = new ArrayList<City>();
//	    	
//			//for (int i = 0; i < 1; i++) {
//				//setBooks();
//			//}
//	    }
	    
	  
}
