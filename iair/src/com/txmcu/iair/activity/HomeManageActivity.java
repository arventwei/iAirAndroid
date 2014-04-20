package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.adapter.HomeAdapter;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;
/**
 * 家管理界面
 * @author Administrator
 *
 */
public class HomeManageActivity extends Activity implements OnClickListener {

	public static final String TAG = "HomeManageActivity";

	public static HomeManageActivity instance;
	// private List<City> books;

	private DragableGridview mGridview;

	public HomeAdapter adapter;

	public Boolean editMode = false;
	iAirApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_manage);

		application = (iAirApplication) getApplication();
		findViewById(R.id.back_img).setOnClickListener(this);

		findViewById(R.id.home_choose_refresh_img).setOnClickListener(this);
		findViewById(R.id.home_edit_btn).setOnClickListener(this);

		mGridview = ((DragableGridview) findViewById(R.id.home_gridview));

		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);

		adapter = new HomeAdapter(this);

		refreshlist();
		// initTestData();
		addGridViewListener();

	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance = null;
	}

	public void refreshlist()
	{
		adapter.syncHomes();
		adapter.notifyDataSetChanged();
	}
	protected void addGridViewListener() {
		mGridview.setAdapter(adapter);
		mGridview.setOnItemClick(new OnItemClickListener() {

			@Override
			public void click(int index) {
				Log.d(TAG, "item : " + index + " -- clicked!");

				Home home = (Home) adapter.getItem(index);
				final String homeIdString = home.homeid;
				
					if (editMode) {

						iAirUtil.showProgressDialog(HomeManageActivity.this);
						XinServerManager.unbinduser_home(HomeManageActivity.this, application.getUserid(), homeIdString,new XinServerManager.onSuccess() {
							
							@Override
							public void run(JSONObject response) throws JSONException {
								
								XinServerManager.requesthomelist(HomeManageActivity.this,false);
								//iAirUtil.dismissDialog();
								//application.homeList = XinServerManager.getHomeFromJson(HomeManageActivity.this,response.getJSONArray("home"));
								//application.cityList = XinServerManager.getCityFromJson(response.getJSONArray("area"));
								// TODO Auto-generated method stub
								//MainActivity.instance.refreshlist();
								//HomeManageActivity.instance.refreshlist();
								
							//	synchomebb();
								
							}
						});

					} else {
						if (homeIdString.equals(""))
						{
							Intent localIntent = new Intent(
							HomeManageActivity.this,
							HomeAddActivity.class);
				
							startActivity(localIntent);
							overridePendingTransition(R.anim.left_enter,
									R.anim.alpha_out);
						}
						else {
							XinSession.getSession().put("home", home);
							Intent localIntent = new Intent(
									HomeManageActivity.this,
									HomeModifyActivity.class);
							localIntent.putExtra("type", 2);
						
							startActivity(localIntent);
							overridePendingTransition(R.anim.left_enter,
											R.anim.alpha_out);
						}
						
//						Intent localIntent = new Intent(
//								HomeManageActivity.this,
//								DeviceModifyActivity.class);
//						localIntent.putExtra("sn", device.sn);
//						startActivity(localIntent);
//						overridePendingTransition(R.anim.left_enter,
//								R.anim.alpha_out);
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
		case R.id.home_choose_refresh_img: {

			break;
		}
		case R.id.home_edit_btn: {
			editMode = !editMode;
			adapter.notifyDataSetChanged();
			break;
		}

		}
	}

//	public void requesthomelist() {
//		XinServerManager.getfirstpage_briefinfo(HomeManageActivity.this,application.getUserid(),new XinServerManager.onSuccess() {
//			
//			@Override
//			public void run(JSONObject response) throws JSONException {
//				iAirUtil.dismissDialog();
//				application.homeList = XinServerManager.getHomeFromJson(HomeManageActivity.this,response.getJSONArray("home"));
//				application.cityList = XinServerManager.getCityFromJson(response.getJSONArray("area"));
//				// TODO Auto-generated method stub
//				MainActivity.instance.refreshlist();
//				HomeManageActivity.instance.refreshlist();
//				
//			//	synchomebb();
//				
//			}
//		});
//	}
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
