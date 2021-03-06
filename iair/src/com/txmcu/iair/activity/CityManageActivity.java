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
import com.txmcu.iair.adapter.City;
import com.txmcu.iair.adapter.CityAdapter;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

/**
 * 城市管理界面
 * @author Administrator
 *
 */
public class CityManageActivity extends Activity implements
		 OnClickListener {

	public static final String TAG = "CityManageActivity";

	private DragableGridview mGridview;

	private CityAdapter adapter;
	public Boolean editMode = false;
	public iAirApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_manage);
		findViewById(R.id.back_img).setOnClickListener(this);
		findViewById(R.id.edit_btn).setOnClickListener(this);
		mGridview = ((DragableGridview) findViewById(R.id.gridview));

		application = (iAirApplication)getApplication();
		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);

		adapter = new CityAdapter(this);
		adapter.sync();
		//initTestData();
		addGridViewListener();
	//	startLocation();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	//	stopLocation();
	}
	
	
    
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
        //可以根据多个请求代码来作相应的操作  
        if(20==resultCode)  
        {  
            String area_id=data.getExtras().getString("area_id");  
            String area_name=data.getExtras().getString("area_name");
            iAirUtil.toastMessage(this, area_id);
            
            XinServerManager.binduser_area(this, application.getUserid(), area_id, new XinServerManager.onSuccess() {
				
				@Override
				public void run(JSONObject response) throws JSONException {
					// TODO Auto-generated method stub
					requestlist();
				}
			});
          //  TextView_result.setText("书籍名称:"+bookname+"书籍价钱"+booksale+"元");  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  
	 
    
	protected void addGridViewListener() {
		mGridview.setAdapter(adapter);

		mGridview.setOnItemClick(new OnItemClickListener() {

			@Override
			public void click(int index) {
				Log.d(TAG, "item : " + index + " -- clicked!");
				final City city = (City) adapter.getItem(index);
				final String areaId = city.areaId;
				if (areaId.equals("")) {

					
					 Intent localIntent = new Intent(CityManageActivity.this,CityListActivity.class);
					// localIntent.putExtra("homeId", home.homeid);
					// localIntent.putExtra("vsn", "");
					 startActivityForResult(localIntent,100);
					// startActivity(localIntent);
					 overridePendingTransition(R.anim.left_enter,  R.anim.alpha_out);
		

				} else 
				{
					if (editMode) 
					{
						
						XinServerManager.unbinduser_area(CityManageActivity.this, application.getUserid(), areaId,
								new XinServerManager.onSuccess() {
							
							@Override
							public void run(JSONObject response) throws JSONException {
								
								if (response.get("ret").equals("Ok")) {
									//application.removeXiaoxin(snString);
									requestlist();
								}
								
								
							}
						});
						


					} else {
						//UNDO...
//						Intent localIntent = new Intent(
//								DeviceManageActivity.this,
//								DeviceModifyActivity.class);
//						localIntent.putExtra("type", 2);
//						XinSession.getSession().put("device", device);
//						startActivity(localIntent);
//						overridePendingTransition(R.anim.left_enter,
//								R.anim.alpha_out);
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
		case R.id.edit_btn:
			editMode = !editMode;
			adapter.notifyDataSetChanged();
			break;
		}
	}

	private void requestlist() {
		XinServerManager.getfirstpage_briefinfo(CityManageActivity.this, application.getUserid(), new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				// TODO Auto-generated method stub
				application.cityList  = XinServerManager.getCityFromJson(response.getJSONArray("area"));
				adapter.sync();
				adapter.notifyDataSetChanged();
				
				if (MainActivity.instance!=null) {
					MainActivity.instance.refreshlist();
				}
			}
		});
	}

//	private void initTestData() {
//		// books = new ArrayList<City>();
//		adapter.addCity( getString(R.string.beijing));
//		//adapter.addCity(2, "南京");
//		// for (int i = 0; i < 1; i++) {
//		// setBooks();
//		// }
//	}

//	LocationManagerProxy locationManagerProxy;
//	GeocodeSearch geocoderSearch;
//	public void startLocation() {
//		locationManagerProxy = LocationManagerProxy.getInstance(this);
//		// locationListenner = new LocationListenner();
//		locationManagerProxy.requestLocationUpdates(
//				LocationProviderProxy.AMapNetwork, 5000, 10, this);
//		
//		AMapLocation location = locationManagerProxy.getLastKnownLocation(LocationProviderProxy.AMapNetwork);
//		
//		 if (location!=null) {
//
//
//			 geocoderSearch = new GeocodeSearch(this);  
//			 geocoderSearch.setOnGeocodeSearchListener(this);  
//			 LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
//			// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系    
//			 RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,GeocodeSearch.AMAP);  
//			 geocoderSearch.getFromLocationAsyn(query);
//			 
//			}
//	        
//	}
//
//	public void stopLocation() {
//		if (locationManagerProxy != null) {
//			locationManagerProxy.removeUpdates(this);
//			locationManagerProxy.destory();
//		}
//		locationManagerProxy = null;
//	}
//
//	@Override
//	public void onLocationChanged(AMapLocation arg0) {
//		// TODO Auto-generated method stub
//		iAirUtil.toastMessage(this, arg0.toString());
//
//	}
//
//	@Override
//	public void onLocationChanged(Location location) {
//		// TODO Auto-generated method stub
//		iAirUtil.toastMessage(this, location.toString());
//
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//		iAirUtil.toastMessage(this, provider.toString());
//
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//		iAirUtil.toastMessage(this, provider.toString());
//
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//		iAirUtil.toastMessage(this, provider.toString());
//
//	}
//
//	@Override
//	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
//		// TODO Auto-generated method stub
//		if(rCode == 0){  
//         
//            if(result != null&&result.getRegeocodeAddress() != null  
//                     &&result.getRegeocodeAddress().getFormatAddress()!=null){  
//                String addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";  
//               
//                iAirUtil.toastMessage(this,  result.getRegeocodeAddress().getCity());  
//            }else{  
//                //ToastUtil.show(GeocoderActivity.this, R.string.no_result);  
//            }  
//        }else{  
//           // ToastUtil.show(GeocoderActivity.this, R.string.error_network);  
//        }  
//	}

}
