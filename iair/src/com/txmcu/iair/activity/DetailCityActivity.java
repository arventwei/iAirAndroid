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


//	@Override
//	public void onRefresh(PullToRefreshBase<VerticalViewPager> refreshView) {
//		new GetDataTask().execute();
//	}

//	private void StartModifyView() {
//		Intent localIntent = new Intent(this, DeviceModifyActivity.class);
//		localIntent.putExtra("sn", xiaoxinDevice.sn);
//		this.startActivity(localIntent);
//		this.overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
//	
//	}
//	int speed = 0;
//	static class SamplePagerAdapter extends
//			com.handmark.verticalview.PagerAdapter {
//
//		private static int[] sDrawables = { R.layout.include_up_detail,
//				R.layout.include_down_detail };
//		private DetailCityActivity pageContext;
//
//		// private ListView listView;
//		// MainEntryAdapter adapter;
//		View xiaoxinView;
//		
//		public void updateView()
//		{
//			final Device xiaoxinDevice = pageContext.application.getXiaoxin(pageContext.xiaoxinDevice.sn);
//			TextView nameview = (TextView) xiaoxinView
//					.findViewById(R.id.xiaoxin_name);
//			nameview.setText(xiaoxinDevice.name);
//			TextView snview = (TextView) xiaoxinView
//					.findViewById(R.id.xiaoxin_sn);
//			snview.setText("���к�:"+xiaoxinDevice.sn);
//			TextView pm25 = (TextView) xiaoxinView
//					.findViewById(R.id.xiaoxin_pm25);
//			pm25.setText(pageContext.getString(R.string.detail_device_pm25) + (int)xiaoxinDevice.pm25);
//			TextView temp = (TextView) xiaoxinView
//					.findViewById(R.id.xiaoxin_temp);
//			temp.setText(pageContext.getString(R.string.detail_device_temp) + xiaoxinDevice.temp );
//			TextView humi = (TextView) xiaoxinView
//					.findViewById(R.id.xiaoxin_humi);
//			humi.setText(pageContext.getString(R.string.detail_device_humi) + xiaoxinDevice.humi );
//			TextView form = (TextView) xiaoxinView
//					.findViewById(R.id.xiaoxin_form);
//			form.setText(pageContext.getString(R.string.detail_device_form) + xiaoxinDevice.form );
//		
//	
//			// nameview.setText()
//			//final EditText editspeedEditText = (EditText)xiaoxinView.findViewById(R.id.input_speed);
//			//editspeedEditText.setText(String.valueOf(pageContext.xiaoxinDevice.speed));
//			Button speedButton = (Button)xiaoxinView.findViewById(R.id.btn_speed);
//			
//			speedButton.setOnClickListener(new OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					String sn = xiaoxinDevice.sn;
//					//int speed = Integer.parseInt(editspeedEditText.getText().toString());
//					int newSpeed = xiaoxinDevice.speed+1;
//					if(newSpeed>=10)
//						newSpeed=1;
//					xiaoxinDevice.speed = newSpeed;
//					XinServerManager.setxiaoxin_speed(pageContext, sn, newSpeed, null);
//					
//
//				}
//
//			});
//			CheckBox switchoff = (CheckBox) xiaoxinView
//					.findViewById(R.id.xiaoxin_switch);
//			switchoff.setChecked(xiaoxinDevice.switchOn != 0);
//			switchoff.setOnClickListener(new OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					String sn = xiaoxinDevice.sn;
//					int isOn = 0;
//					if (((CheckBox) v).isChecked()) {
//						isOn = 1;
//					}
//					XinServerManager.setxiaoxin_switch(pageContext, sn, isOn, null);
//					
//
//				}
//
//			});
//			
//			CheckBox networkoff = (CheckBox) xiaoxinView
//					.findViewById(R.id.xiaoxin_network);
//			
//
//			//pageContext.wifiHotM.
//			networkoff.setChecked(pageContext.wifiHotM.isWifiApEnable());
//			networkoff.setOnClickListener(new OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					//String sn = pageContext.xiaoxinDevice.sn;
//					//int isOn = 0;
//					if (((CheckBox) v).isChecked()) {
//						pageContext.wifiHotM.startAWifiHot(iAirConstants.Mobile_AP_SSID, iAirConstants.Mobile_AP_PWD);
//						//isOn = 1;
//					}
//					else {
//						pageContext.wifiHotM.closeAWifiHot();
//						pageContext.wifiHotM.scanWifiHot();
//					//	if(pageContext.application.getWifibackupNetId()!=-1)
//					//		pageContext.wifiHotM.enableNetWorkById(pageContext.application.getWifibackupNetId());
//					}
//					//XinServerManager.setxiaoxin_switch(pageContext, sn, isOn, null);
//					
//
//				}
//
//			});
//
//		}
//
//		public SamplePagerAdapter(DetailCityActivity paramContext) {
//			pageContext = paramContext;
//		}
//
//		@Override
//		public int getCount() {
//			return sDrawables.length;
//		}
//
//		@Override
//		public View instantiateItem(ViewGroup container, int position) {
//			// ImageView imageView = new ImageView(container.getContext());
//			// imageView.setImageResource(sDrawables[position]);
//			LayoutInflater localLayoutInflater = LayoutInflater
//					.from(pageContext);
//			View subView = localLayoutInflater.inflate(sDrawables[position],
//					null);
//
//			if (position == 0) {
//				xiaoxinView = (View) subView
//						.findViewById(R.id.xiaoxin_info);
//
//				updateView();
//
//			} else {
//
//			}
//			// Now just add ImageView to ViewPager and return it
//			container.addView(subView, LayoutParams.MATCH_PARENT,
//					LayoutParams.MATCH_PARENT);
//
//			return subView;
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			container.removeView((View) object);
//		}
//
//		@Override
//		public boolean isViewFromObject(View view, Object object) {
//			return view == object;
//		}
//	}

	

	private void addButtonListener() {
		findViewById(R.id.back_img).setOnClickListener(this);
		// findViewById(R.id.main_share).setOnClickListener(this);
		// findViewById(R.id.main_setting).setOnClickListener(this);
	}

	

}