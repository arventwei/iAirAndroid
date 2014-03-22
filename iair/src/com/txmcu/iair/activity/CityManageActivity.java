package com.txmcu.iair.activity;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import cn.classd.dragablegrid.widget.DragableGridview;
import cn.classd.dragablegrid.widget.DragableGridview.OnItemClickListener;
import cn.classd.dragablegrid.widget.DragableGridview.OnSwappingListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.CityAdapter;
import com.txmcu.iair.common.iAirUtil;

public class CityManageActivity extends Activity implements
		AMapLocationListener, OnClickListener, OnGeocodeSearchListener {

	public static final String TAG = "CityManageActivity";

	private DragableGridview mGridview;

	private CityAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_manage);
		findViewById(R.id.back_img).setOnClickListener(this);
		mGridview = ((DragableGridview) findViewById(R.id.gridview));

		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);

		adapter = new CityAdapter(this);
		initTestData();
		addGridViewListener();
		startLocation();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopLocation();
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

		}
	}

	private void initTestData() {
		// books = new ArrayList<City>();
		adapter.addCity(1, "北京");
		adapter.addCity(2, "南京");
		// for (int i = 0; i < 1; i++) {
		// setBooks();
		// }
	}

	LocationManagerProxy locationManagerProxy;
	GeocodeSearch geocoderSearch;
	public void startLocation() {
		locationManagerProxy = LocationManagerProxy.getInstance(this);
		// locationListenner = new LocationListenner();
		locationManagerProxy.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
		
		AMapLocation location = locationManagerProxy.getLastKnownLocation(LocationProviderProxy.AMapNetwork);
		
		 if (location!=null) {


			 geocoderSearch = new GeocodeSearch(this);  
			 geocoderSearch.setOnGeocodeSearchListener(this);  
			 LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
			// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系    
			 RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,GeocodeSearch.AMAP);  
			 geocoderSearch.getFromLocationAsyn(query);
			 
			}
	        
	}

	public void stopLocation() {
		if (locationManagerProxy != null) {
			locationManagerProxy.removeUpdates(this);
			locationManagerProxy.destory();
		}
		locationManagerProxy = null;
	}

	@Override
	public void onLocationChanged(AMapLocation arg0) {
		// TODO Auto-generated method stub
		iAirUtil.toastMessage(this, arg0.toString());

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		iAirUtil.toastMessage(this, location.toString());

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		iAirUtil.toastMessage(this, provider.toString());

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		iAirUtil.toastMessage(this, provider.toString());

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		iAirUtil.toastMessage(this, provider.toString());

	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		// TODO Auto-generated method stub
		if(rCode == 0){  
         
            if(result != null&&result.getRegeocodeAddress() != null  
                     &&result.getRegeocodeAddress().getFormatAddress()!=null){  
                String addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";  
               
                iAirUtil.toastMessage(this,  result.getRegeocodeAddress().getCity());  
            }else{  
                //ToastUtil.show(GeocoderActivity.this, R.string.no_result);  
            }  
        }else{  
           // ToastUtil.show(GeocoderActivity.this, R.string.error_network);  
        }  
	}

}
