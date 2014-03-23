package com.txmcu.xiaoxin.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirConstants;
import com.txmcu.iair.common.iAirUtil;

public class XinServerManager {

	static String TAG = "XinServerManager";

	public abstract interface onSuccess {

		// Method descriptor #4 ()V
		public abstract void run(String response);
	}

	static private AsyncHttpClient getHttpClient()
	{
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(2000);
		return client;
	}
	static public void login(final Activity activity, final String userid,
			final onSuccess r) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {

					RequestParams post_params = new RequestParams();
					post_params.put("userid", userid);
					// post_params.put("sn", sn);

					AsyncHttpClient client = getHttpClient();
					client.post(iAirConstants.API_Login, post_params,
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(String response) {
									System.out.println(response);
									iAirUtil.toastMessage(activity, response);
									// setStopLoop(2,response);
									r.run(response);
								}

							});

				} catch (Exception e) {
					Log.d(TAG, e.toString());
				}
			}
		});
	}

	static public void bind(final Activity activity, final String userid,
			final String sn, final onSuccess r) {
		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);
		post_params.put("sn", sn);

		AsyncHttpClient client = getHttpClient();
		client.post(iAirConstants.API_Bind, post_params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						System.out.println(response);
						iAirUtil.toastMessage(activity, response);
						r.run(response);
					}
				});
	}

	static public void query_bindlist(final Activity activity, final String userid,
			final onSuccess r)
	{
		final iAirApplication application = ((iAirApplication) activity
				.getApplication());
		RequestParams post_params = new RequestParams();
		post_params.put("userid", userid);

		AsyncHttpClient client = getHttpClient();
		client.post(iAirConstants.API_QueryBindlist, post_params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						System.out.println(response);
						iAirUtil.toastMessage(activity, response);
						if (response.equals(iAirConstants.Server_Fail)) {
							r.run(response);
							return;
						}
						Map<String, String> snMap = iAirUtil
								.getQueryMapFromUrl(response);

						List<String> snlist = new ArrayList<String>();
						
						final int count = Integer.parseInt(snMap.get("count"));
						
						if (count == 0) {
							r.run(response);
							return;
						}
						
						for (int i = 0; i < count; i++) {
							String sn = snMap.get("sn" + i);
							snlist.add(sn);
						}
						

						application.setXiaoxinSnList(snlist);
						
						for (int i = 0; i < count; i++) {
							String sn = snlist.get(i);
							
							final int xiaoin_idx = i;
							getxiaoxin(activity,sn,new onSuccess() {
								
								@Override
								public void run(String response) {
									// TODO Auto-generated method stub
									
									if (xiaoin_idx == count-1) {
										r.run(response);
									}
									
								}
							});
						}
						

						
					}
				});
	}
	
	
	static public void getxiaoxin(final Activity activity, final String sn,
			final onSuccess r)
	{
		final iAirApplication application = ((iAirApplication) activity
				.getApplication());
		RequestParams post_params = new RequestParams();
		post_params.put("sn", sn);

		AsyncHttpClient client = getHttpClient();
		client.post(iAirConstants.API_GetXiaoxin, post_params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						//System.out.println(response);
						iAirUtil.toastMessage(activity, response);
						if (response.equals(iAirConstants.Server_Fail)) {
							r.run(response);
							return;
						}
						
						Map<String, String> xiaoxinMap = iAirUtil
								.getQueryMapFromUrl(response);
						
						Device xiaoxin = new Device();
						xiaoxin.sn = sn;
						xiaoxin.name = xiaoxinMap.get("name");
						xiaoxin.temp = Double.parseDouble(xiaoxinMap.get("temp"));
						xiaoxin.humi = Double.parseDouble(xiaoxinMap.get("humi"));
						xiaoxin.pm25 = Double.parseDouble(xiaoxinMap.get("pm25"));
						xiaoxin.form = Double.parseDouble(xiaoxinMap.get("form"));
						xiaoxin.switchOn = Integer.parseInt(xiaoxinMap.get("switch"));
						xiaoxin.speed = Integer.parseInt(xiaoxinMap.get("speed"));
						xiaoxin.lastUpdateStamp = Integer.parseInt(xiaoxinMap.get("last_upload_time"));
						
						

						application.setXiaoxin(xiaoxin);

						r.run(response);
					}
				});
	}
	
	static public void setxiaoxin_switch(final Activity activity, final String sn,final int isOn,
			final onSuccess r)
	{
		final iAirApplication application = ((iAirApplication) activity
				.getApplication());
		RequestParams post_params = new RequestParams();
		post_params.put("sn", sn);
		post_params.put("switch", String.valueOf(isOn));

		AsyncHttpClient client = getHttpClient();
		client.post(iAirConstants.API_SetXiaoxinSwitch, post_params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						//System.out.println(response);
						iAirUtil.toastMessage(activity, response);
						if (response.equals(iAirConstants.Server_Fail)) {
							if(r!=null)
								r.run(response);
							return;
						}
						
						Device xiaoxin = application.getXiaoxin(sn);
						xiaoxin.switchOn=isOn;

						application.setXiaoxin(xiaoxin);

						if(r!=null)
							r.run(response);
					}
				});
	}
	
	static public void setxiaoxin_speed(final Activity activity, final String sn,final int speed,
			final onSuccess r)
	{
		final iAirApplication application = ((iAirApplication) activity
				.getApplication());
		RequestParams post_params = new RequestParams();
		post_params.put("sn", sn);
		post_params.put("speed", String.valueOf(speed));

		AsyncHttpClient client = getHttpClient();
		client.post(iAirConstants.API_SetXiaoxinSeed, post_params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						//System.out.println(response);
						iAirUtil.toastMessage(activity, response);
						if (response.equals(iAirConstants.Server_Fail)) {
							if(r!=null)
								r.run(response);
							return;
						}
						

						Device xiaoxin = application.getXiaoxin(sn);
						
						xiaoxin.speed = speed;
						application.setXiaoxin(xiaoxin);

						if(r!=null)
							r.run(response);
					}
				});
	}
}
