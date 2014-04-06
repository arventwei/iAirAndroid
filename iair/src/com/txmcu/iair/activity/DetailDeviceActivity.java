package com.txmcu.iair.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.txmcu.iair.R;
import com.txmcu.iair.activity.HomeActivity.SamplePagerAdapter;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirConstants;
import com.txmcu.xiaoxin.config.XinServerManager;
import com.txmcu.xiaoxin.config.wifi.WifiHotManager;
import com.txmcu.xiaoxin.config.wifi.WifiHotManager.OpretionsType;
import com.txmcu.xiaoxin.config.wifi.WifiHotManager.WifiBroadCastOperations;

public class DetailDeviceActivity extends Activity implements
		OnRefreshListener<VerticalViewPager>, OnClickListener,
		WifiBroadCastOperations {

	private PullToRefreshViewPager mPullToRefreshViewPager;
	private PopupWindow popWin;

	public iAirApplication application;
	public Device xiaoxinDevice;
	public SamplePagerAdapter adapter;

	private WifiHotManager wifiHotM;

	public static DetailDeviceActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Common);
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_detail_device);

		wifiHotM = WifiHotManager.getInstance(this, this);

		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		application = (iAirApplication) getApplication();
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(DetailDeviceActivity.this);

		VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		adapter = new SamplePagerAdapter(this);
		vp.setAdapter(adapter);

		addButtonListener();
		Bundle bundle = getIntent().getExtras();
		String sn = bundle.getString("sn");
		xiaoxinDevice = application.getXiaoxin(sn);

		findViewById(R.id.device_edit_btn).setOnClickListener(this);

		WifiInfo info = wifiHotM.getConnectWifiInfo();
		// wifibackupNetId=-1;
		// _scannlist = scannlist;

		// application.setWifibackupSSID("");

		if (info != null && info.getSSID() != null
				&& !info.getSSID().equals(iAirConstants.XIAOXIN_SSID)) {
			application.setWifibackupNetId(info.getNetworkId());
		}
		requestlist();
		// new GetDataTask().execute();

	}

	public void Destroy() {

		wifiHotM.unRegisterWifiConnectBroadCast();
		wifiHotM.unRegisterWifiScanBroadCast();
		wifiHotM.unRegisterWifiStateBroadCast();
		WifiHotManager.destroy();
		instance = null;

	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.back_img: {
			finish();

			break;
		}
		case R.id.device_edit_btn: {
			StartModifyView();
			break;
		}

		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<VerticalViewPager> refreshView) {

		mPullToRefreshViewPager.onRefreshComplete();
		//TODO
		requestlist();


	}

	public void refreshlist() {
		SamplePagerAdapter adapter = (SamplePagerAdapter) (mPullToRefreshViewPager
				.getRefreshableView().getAdapter());

		adapter.updateView();
	}
	public void  requestlist() {
		XinServerManager.getxiaoxin_detailweather(this, application.getUserid(),
				xiaoxinDevice.sn, new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				
			XinServerManager.getSingleDeviceFromJson(xiaoxinDevice, response);
			refreshlist();
				
			}
		});
	}

	private void StartModifyView() {
		Intent localIntent = new Intent(this, DeviceModifyActivity.class);
		localIntent.putExtra("type", 2);
		XinSession.getSession().put("device", xiaoxinDevice);
		this.startActivity(localIntent);
		this.overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);

	}

	int speed = 0;

	static class SamplePagerAdapter extends
			com.handmark.verticalview.PagerAdapter {

		private static int[] sDrawables = { R.layout.include_detail_up,
				R.layout.include_detail_down };
		private DetailDeviceActivity pageContext;

		// private ListView listView;
		// MainEntryAdapter adapter;
		View xiaoxinView;

		
		public void updateView() {
			final Device xiaoxinDevice = pageContext.application.getXiaoxin(pageContext.xiaoxinDevice.sn);
			TextView nameview = (TextView) xiaoxinView
					.findViewById(R.id.xiaoxin_name);
			nameview.setText(xiaoxinDevice.name);
			TextView snview = (TextView) xiaoxinView
					.findViewById(R.id.xiaoxin_sn);
			snview.setText(pageContext.getString(R.string.sn_number) + xiaoxinDevice.sn);
			TextView pm25 = (TextView) xiaoxinView
					.findViewById(R.id.xiaoxin_pm25);
			pm25.setText(pageContext.getString(R.string.detail_device_pm25)
					+  xiaoxinDevice.pm25);
			TextView temp = (TextView) xiaoxinView
					.findViewById(R.id.xiaoxin_temp);
			temp.setText(pageContext.getString(R.string.detail_device_temp)
					+ xiaoxinDevice.temp);
			TextView humi = (TextView) xiaoxinView
					.findViewById(R.id.xiaoxin_humi);
			humi.setText(pageContext.getString(R.string.detail_device_humi)
					+ xiaoxinDevice.humi);
			TextView form = (TextView) xiaoxinView
					.findViewById(R.id.xiaoxin_form);
			form.setText(pageContext.getString(R.string.detail_device_form)
					+ xiaoxinDevice.pa);

			// nameview.setText()
			// final EditText editspeedEditText =
			// (EditText)xiaoxinView.findViewById(R.id.input_speed);
			// editspeedEditText.setText(String.valueOf(pageContext.xiaoxinDevice.speed));
			Button speedButton = (Button) xiaoxinView
					.findViewById(R.id.btn_speed);

			speedButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String sn = xiaoxinDevice.sn;
					// int speed =
					// Integer.parseInt(editspeedEditText.getText().toString());
					int newSpeed = xiaoxinDevice.speed + 1;
					if (newSpeed >= 10)
						newSpeed = 1;
					xiaoxinDevice.speed = newSpeed;
					//TODO
//					XinServerManager.setxiaoxin_speed(pageContext,
//							pageContext.application.getUserid(), sn, newSpeed,
//							null);

				}

			});
			CheckBox switchoff = (CheckBox) xiaoxinView
					.findViewById(R.id.xiaoxin_switch);
			switchoff.setChecked(xiaoxinDevice.switchOn != 0);
			switchoff.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String sn = xiaoxinDevice.sn;
					String isOn = "0";
					if (((CheckBox) v).isChecked()) {
						isOn = "1";
					}
					XinServerManager.setxiaoxin_switch(pageContext, pageContext.application.getUserid(),
							sn, isOn, null);
					//TODO
//					XinServerManager.setxiaoxin_switch(pageContext,
//									pageContext.application.getUserid(), sn,
//									isOn, null);

				}

			});

			CheckBox networkoff = (CheckBox) xiaoxinView
					.findViewById(R.id.deviceShare);

			// pageContext.wifiHotM.
			networkoff.setChecked(pageContext.wifiHotM.isWifiApEnable());
			networkoff.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// String sn = pageContext.xiaoxinDevice.sn;
					// int isOn = 0;
					if (((CheckBox) v).isChecked()) {
						pageContext.wifiHotM.startAWifiHot(
								iAirConstants.Mobile_AP_SSID,
								iAirConstants.Mobile_AP_PWD);
						// isOn = 1;
					} else {
						pageContext.wifiHotM.closeAWifiHot();
						pageContext.wifiHotM.scanWifiHot();
						// if(pageContext.application.getWifibackupNetId()!=-1)
						// pageContext.wifiHotM.enableNetWorkById(pageContext.application.getWifibackupNetId());
					}


				}

			});

		}

		public SamplePagerAdapter(DetailDeviceActivity paramContext) {
			pageContext = paramContext;
		}

		@Override
		public int getCount() {
			return sDrawables.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			// ImageView imageView = new ImageView(container.getContext());
			// imageView.setImageResource(sDrawables[position]);
			LayoutInflater localLayoutInflater = LayoutInflater
					.from(pageContext);
			View subView = localLayoutInflater.inflate(sDrawables[position],
					null);

			if (position == 0) {
				xiaoxinView = (View) subView.findViewById(R.id.xiaoxin_info);

				updateView();

			} else {
				
				

			}
			// Now just add ImageView to ViewPager and return it
			container.addView(subView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return subView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

//	private class GetDataTask extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// Simulates a background job.
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			mPullToRefreshViewPager.onRefreshComplete();
//			super.onPostExecute(result);
//		}
//	}

	private void addButtonListener() {
		findViewById(R.id.back_img).setOnClickListener(this);
		// findViewById(R.id.main_share).setOnClickListener(this);
		// findViewById(R.id.main_setting).setOnClickListener(this);
	}

	@Override
	public void disPlayWifiScanResult(List<ScanResult> wifiList) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean disPlayWifiConResult(boolean result, WifiInfo wifiInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void operationByType(OpretionsType type, String SSID, String pWd) {
		// TODO Auto-generated method stub

	}

}
