package com.txmcu.iair.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.City;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;
/**
 * 城市详情界面
 * @author Administrator
 *
 */
public class DetailCityActivity extends Activity implements OnClickListener {

	//private PullToRefreshViewPager mPullToRefreshViewPager;
	//private PopupWindow popWin;

	iAirApplication application;
	//public Device xiaoxinDevice;
	//public SamplePagerAdapter adapter;
	
	//private WifiHotManager wifiHotM;
	City city;
	public static DetailCityActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Common);
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_detail_city);
		city = (City)XinSession.getSession().get("city");
		//Intent intent = getIntent();
		//String areaidString = intent.getStringExtra("area_id");
		//wifiHotM = WifiHotManager.getInstance(this, this);
		
		
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		application = (iAirApplication) getApplication();
		//mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		//mPullToRefreshViewPager.setOnRefreshListener(DetailCityActivity.this);

		//VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		//adapter = new SamplePagerAdapter(this);
		//vp.setAdapter(adapter);

		XinServerManager.getarea_detailweather(this, application.getUserid(), city.areaId, 
		new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				// TODO Auto-generated method stub
				List<City> citesCities = application.cityList = XinServerManager.getCityFromJson(response.getJSONArray("area"));
				if (citesCities.size()>0) {
					city = citesCities.get(0);
				}
				//XinServerManager.getSingleCityFromJson(city, response
				updateview();
			}
		});
		addButtonListener();
		
		
		//Bundle bundle = getIntent().getExtras();
		//String sn = bundle.getString("sn");
		//xiaoxinDevice = application.getXiaoxin(sn);
		
		//findViewById(R.id.back_img).setOnClickListener(this);
		
		

		
		//WifiInfo info = wifiHotM.getConnectWifiInfo();
		//wifibackupNetId=-1;
		//_scannlist = scannlist;
		
		//application.setWifibackupSSID("");

		//if (info!=null&&info.getSSID()!=null&&!info.getSSID().equals(iAirConstants.XIAOXIN_SSID)) {
		//	application.setWifibackupNetId(info.getNetworkId());
		//}
		// new GetDataTask().execute();

	}

	private void updateview() {
		((TextView)findViewById(R.id.city_name)).setText(city.name);
		((TextView)findViewById(R.id.city_aqi)).setText(city.aqi);
		if (city.aqi_us.equals("")||city.aqi_us.equals("0")) {
			findViewById(R.id.embassy).setVisibility(View.GONE);
		}
		((TextView)findViewById(R.id.city_aqi_us)).setText(city.aqi_us);
		
		((TextView)findViewById(R.id.cityTemp)).setText("pm25:"+city.pm25+" "+city.temp_info
				+" "+city.wind_info+" \n"+city.weekstr+" "+city.datestr+" "+city.datecn);
		
		((TextView)findViewById(R.id.city_temp)).setText(city.temp);
		((TextView)findViewById(R.id.city_weather)).setText(city.weather);
		((TextView)findViewById(R.id.carlimit)).setText(city.today_car_limit);
	}
	
	public void Destroy()
	{
	
		//wifiHotM.unRegisterWifiConnectBroadCast();
		//wifiHotM.unRegisterWifiScanBroadCast();
		//wifiHotM.unRegisterWifiStateBroadCast();
		//WifiHotManager.destroy();
		instance = null;
		
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.back_img:
		{
			finish();
		
			break;
		}
//		case R.id.device_edit_btn:
//		{
//			//StartModifyView();
//			break;
//		}
		

		}
	}



	

	private void addButtonListener() {
		findViewById(R.id.back_img).setOnClickListener(this);
		// findViewById(R.id.main_share).setOnClickListener(this);
		// findViewById(R.id.main_setting).setOnClickListener(this);
	}

	

}
