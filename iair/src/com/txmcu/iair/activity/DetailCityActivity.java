package com.txmcu.iair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.txmcu.iair.R;
import com.txmcu.iair.common.iAirApplication;

public class DetailCityActivity extends Activity implements OnClickListener {

	//private PullToRefreshViewPager mPullToRefreshViewPager;
	//private PopupWindow popWin;

	iAirApplication application;
	//public Device xiaoxinDevice;
	//public SamplePagerAdapter adapter;
	
	//private WifiHotManager wifiHotM;
	
	public static DetailCityActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Common);
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_detail_city);
		
		//wifiHotM = WifiHotManager.getInstance(this, this);
		
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		application = (iAirApplication) getApplication();
		//mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		//mPullToRefreshViewPager.setOnRefreshListener(DetailCityActivity.this);

		//VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		//adapter = new SamplePagerAdapter(this);
		//vp.setAdapter(adapter);

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
		case R.id.device_edit_btn:
		{
			//StartModifyView();
			break;
		}
		

		}
	}



	

	private void addButtonListener() {
		findViewById(R.id.back_img).setOnClickListener(this);
		// findViewById(R.id.main_share).setOnClickListener(this);
		// findViewById(R.id.main_setting).setOnClickListener(this);
	}

	

}
