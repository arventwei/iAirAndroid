package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.DeviceHomeEntry;
import com.txmcu.iair.adapter.DeviceHomeEntryAdapter;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

/**
 * 添加设备方式的选择界面
 * @author Administrator
 *
 */
public class DeviceMenuAddActivity extends Activity implements OnClickListener {

	private static final String TAG = "DeviceMenuAddActivity";


	iAirApplication application;

	EditText device_entry_name_EditText;
	//EditText cityNameEditText;
	DeviceHomeEntryAdapter adapter;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_menu_add);

		application = (iAirApplication)getApplication();
		findViewById(R.id.device_entry_add).setOnClickListener(this);
		//findViewById(R.id.add_new_home_btn).setOnClickListener(this);
		findViewById(R.id.back_img).setOnClickListener(this);
		
		device_entry_name_EditText = (EditText)findViewById(R.id.device_entry_name);
	//	cityNameEditText = (EditText)findViewById(R.id.deviceId);
		
		//userNameEditText.setText(application.getNickName());
		//cityNameEditText.setText("北京");
		initListView();

	}
	
	public void initListView() {
		adapter = new DeviceHomeEntryAdapter(this);//

		listView = (ListView) findViewById(R.id.home_menu_list);// 实例化ListView
		listView.setAdapter(adapter);//
		// listView.setDividerHeight(0);
		adapter.syncDeviceLogs();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				// Log.i("sfs", "asfasds");
				
				DeviceHomeEntry bObject = (DeviceHomeEntry)adapter.getItem(position);
				if (bObject.type == 0) {
					XinSession.getSession().put("home", bObject.home);
					Intent localIntent = new Intent(DeviceMenuAddActivity.this, DeviceManageActivity.class);
					startActivity(localIntent);
					overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
					DeviceMenuAddActivity.this.finish();
				}

			}
		});
	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.device_entry_add) {
			
			String homeid = device_entry_name_EditText.getText().toString();
			//String citName = cityNameEditText.getText().toString();
			
			iAirUtil.showProgressDialogCancelable(DeviceMenuAddActivity.this);
			
			XinServerManager.gethome_detailweather(this, application.getUserid(),
					homeid, new XinServerManager.onSuccess() {
				
				@Override
				public void run(JSONObject response) throws JSONException {
					
					iAirUtil.dismissDialog();
					final Home home = new Home();
					XinServerManager.getSingleHomeFromJson(home, response);
					if (home.homeid.equals("")) {
						iAirUtil.toastMessage(DeviceMenuAddActivity.this, "没有找到指定的家");
						return;
					}
					String messageString=home.homename;
					for (Device device : home.xiaoxins) {
						messageString+="/";
						messageString+=device.name;
						
					}
					iAirUtil.showResultDialog(DeviceMenuAddActivity.this,messageString, "添加家",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							XinServerManager.binduser_home(DeviceMenuAddActivity.this, application.getUserid(), home.homeid, home.homename, new XinServerManager.onSuccess() {
								
								@Override
								public void run(JSONObject response) throws JSONException {
									// TODO Auto-generated method stub
									
									if (response.get("ret").equals("Ok")) {
										
										iAirUtil.toastMessage(DeviceMenuAddActivity.this, "添加别人家成功");
										
										
										XinServerManager.requesthomelist(DeviceMenuAddActivity.this,true);
									}
									else {
										iAirUtil.dismissDialog();
										iAirUtil.toastMessage(DeviceMenuAddActivity.this, "添加别人家失败");
									}
									
								}
							});
						}
					});
					//home.xiaoxins = XinServerManager.getXiaoxinFromJson(response.getJSONArray("xiaoxin"));
					//refreshlist();
					
				}
			});
			
//			XinServerManager.gethome_basedata (this,application.getUserid(),homeid,new XinServerManager.onSuccess() {
//				
//				@Override
//				public void run(JSONObject response) throws JSONException {
//					
//					iAirUtil.dismissDialog();
//					if (response.get("ret").equals("Fail")) {
//						iAirUtil.toastMessage(DeviceMenuAddActivity.this, "没有找到指定的家");
//					}
//					List<Home>homeList = XinServerManager.getHomeFromJson(DeviceMenuAddActivity.this,response.getJSONArray("home"));
//					//application.cityList = XinServerManager.getCityFromJson(response.getJSONArray("area"));
//					// TODO Auto-generated method stub
//					//refreshlist();
//				//	synchomebb();
//					if (homeList.size()==0) {
//						iAirUtil.toastMessage(DeviceMenuAddActivity.this, "没有找到指定的家");
//					}
//					else {
//						XinSession.getSession().put("home", homeList.get(0));
//						
//						Intent localIntent = new Intent(
//								DeviceMenuAddActivity.this,
//								HomeModifyActivity.class);
//						localIntent.putExtra("type", 0);
//					
//						startActivity(localIntent);
//						overridePendingTransition(R.anim.left_enter,
//										R.anim.alpha_out);
//						DeviceMenuAddActivity.this.finish();
//					}
//					
//				}
//			});
			
			
		}
		
		else if (view.getId()==R.id.back_img) {
			finish();
		}
		

	}

}
