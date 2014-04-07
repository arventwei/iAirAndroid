package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.classd.dragablegrid.widget.DragableGridview;
import cn.classd.dragablegrid.widget.DragableGridview.OnItemClickListener;
import cn.classd.dragablegrid.widget.DragableGridview.OnSwappingListener;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.DeviceAdapter;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class DeviceManageActivity extends Activity implements OnClickListener {

	public static final String TAG = "DeviceManageActivity";

	public static DeviceManageActivity instance;
	// private List<City> books;

	private DragableGridview mGridview;

	public DeviceAdapter adapter;

	public Home home;
	public Boolean editMode = false;
	iAirApplication application;

	int isAutoAddBoolean =0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_manage);

		application = (iAirApplication) getApplication();
		home  = (Home)XinSession.getSession().get("home");
		findViewById(R.id.back_img).setOnClickListener(this);

		Intent intent = getIntent();
		isAutoAddBoolean = intent.getIntExtra("autoadd", 0);
		findViewById(R.id.device_choose_refresh_img).setOnClickListener(this);
		findViewById(R.id.device_edit_btn).setOnClickListener(this);

		mGridview = ((DragableGridview) findViewById(R.id.device_gridview));

		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);

		adapter = new DeviceAdapter(this);

		
		// initTestData();
		addGridViewListener();
		
		requestlist();

	}

	public void requestlist() {
		XinServerManager.gethome_detailweather(this, application.getUserid(),
				home.homeid, new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				home = application.getHome(home.homeid);
				((TextView)findViewById(R.id.device_manager_title)).setText(home.homename);
				XinServerManager.getSingleHomeFromJson(home, response);
				home.xiaoxins = XinServerManager.getXiaoxinFromJson(response.getJSONArray("xiaoxin"));
				DeviceManageActivity.this.updateDetailHome(home);
				
			}
		});
	}

	public void updateDetailHome(Home home)
	{
		this.home = home;
		adapter.syncDevices(this.home);
		adapter.notifyDataSetChanged();
		
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

				final Device device = (Device) adapter.getItem(index);
				final String snString = device.sn;
				if (snString.equals("")) {

					
					 Intent localIntent = new Intent(DeviceManageActivity.this,DeviceAddActivity.class);
					 localIntent.putExtra("homeId", home.homeid);
					 localIntent.putExtra("vsn", "");
					 startActivity(localIntent);
					 overridePendingTransition(R.anim.left_enter,  R.anim.alpha_out);
		

				} else 
				{
					if (editMode) 
					{
						//TODO
						XinServerManager.unbindhome_xiaoxin(DeviceManageActivity.this, application.getUserid(), home.homeid, device.id,
								new XinServerManager.onSuccess() {
							
							@Override
							public void run(JSONObject response) throws JSONException {
								
								if (response.get("ret").equals("Ok")) {
									//application.removeXiaoxin(snString);
									home.removeXiaoxin(device);
									adapter.syncDevices(home);
									adapter.notifyDataSetChanged();
									if (MainActivity.instance != null) {
										MainActivity.instance
												.refreshlist();
									}
								}
								//home = application.getHome(homeidString);
								//home.xiaoxins = XinServerManager.getXiaoxinFromJson(response.getJSONArray("xiaoxin"));
								//refreshlist();
								
							}
						});


					} else {
						Intent localIntent = new Intent(
								DeviceManageActivity.this,
								DeviceModifyActivity.class);
						localIntent.putExtra("type", 2);
						XinSession.getSession().put("device", device);
						startActivity(localIntent);
						overridePendingTransition(R.anim.left_enter,
								R.anim.alpha_out);
					}
				}
			}
		});

		mGridview.setOnSwappingListener(new OnSwappingListener() {

			@Override
			public void waspping(int oldIndex, int newIndex) {

				adapter.waspping(oldIndex, newIndex);

				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.back_img:
			finish();
			break;
		case R.id.device_choose_refresh_img: {

			break;
		}
		case R.id.device_edit_btn: {
			editMode = !editMode;
			adapter.notifyDataSetChanged();
			break;
		}

		}
	}

	// private void initTestData() {
	//
	// adapter.addDevice(2,"father");
	// adapter.addDevice(3,"father");
	// adapter.addDevice(4,"father");
	//
	// adapter.addDevice(25,"father");
	// adapter.addDevice(266,"father");
	// adapter.addDevice(45,"father");
	//
	// adapter.addDevice(-1, "");
	// //books = new ArrayList<City>();
	//
	// //for (int i = 0; i < 1; i++) {
	// //setBooks();
	// //}
	// }

}
