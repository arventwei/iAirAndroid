package com.txmcu.iair.activity;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.classd.dragablegrid.widget.DragableGridview;
import cn.classd.dragablegrid.widget.DragableGridview.OnItemClickListener;
import cn.classd.dragablegrid.widget.DragableGridview.OnSwappingListener;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.DeviceAdapter;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class DeviceManageActivity extends Activity 
implements OnClickListener{

	public static final String TAG = "DeviceManageActivity";
	
	public static DeviceManageActivity instance;
	//private List<City> books;
	
	private DragableGridview mGridview;
	
	public  DeviceAdapter adapter;
	
	public Boolean editMode = false;
	iAirApplication application ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_manage);
		
		application = (iAirApplication)getApplication();
		findViewById(R.id.back_img).setOnClickListener(this);
		
		findViewById(R.id.device_choose_refresh_img).setOnClickListener(this);
		findViewById(R.id.device_edit_btn).setOnClickListener(this);
		
		mGridview= ((DragableGridview)findViewById(R.id.device_gridview));
		
		
		
		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);
	        
        adapter = new DeviceAdapter(this);
        
        adapter.syncDevices();
        adapter.notifyDataSetChanged();
        //initTestData();
        addGridViewListener();
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance = null;
	}

	protected void addGridViewListener() {
		mGridview.setAdapter(adapter);
		mGridview.setOnItemClick(new OnItemClickListener() {
			
			@Override
			public void click(int index) {
				Log.d(TAG, "item : " + index + " -- clicked!");
				
				 Device device = (Device)adapter.getItem(index);
				final String snString = device.sn;
				if (snString.equals("")) {
					Intent localIntent = new Intent(DeviceManageActivity.this,DeviceAddActivity.class);
					
					startActivity(localIntent);
				    overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
				}
				else {
					if (editMode) {
						
						XinServerManager.unbind(DeviceManageActivity.this, application.getUserid(), snString,
								new XinServerManager.onSuccess() {
							
							@Override
							public void run(String response) {
								if (response.equals("Ok")) {
									application.removeXiaoxin(snString);
									adapter.syncDevices();
									adapter.notifyDataSetChanged();
									if (MainActivity.instance!=null) {
										MainActivity.instance.refreshlist();
									}
								}
								
								// TODO Auto-generated method stub
								//Toast.makeText(MainActivity.this, R.string.xiaoxin_login_ok, Toast.LENGTH_LONG).show();
							}
						});
						
					}
					else {
						Intent localIntent = new Intent(DeviceManageActivity.this,
								DeviceModifyActivity.class);
						localIntent.putExtra("sn", device.sn);
						startActivity(localIntent);
					    overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
					}
					
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
	    	case R.id.device_choose_refresh_img:
	    	{
	    		
	    		break;
	    	}
	    	case R.id.device_edit_btn:
	    	{
	    		editMode = !editMode;
	    		adapter.notifyDataSetChanged();
	    		break;
	    	}
	    	
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
