package com.txmcu.iair.activity;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class DetailActivity extends Activity implements
		OnRefreshListener<VerticalViewPager>, OnClickListener {

	private PullToRefreshViewPager mPullToRefreshViewPager;
	private PopupWindow popWin;

	iAirApplication application;
	public Device xiaoxinDevice;
	SamplePagerAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Common);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_device_main);

		application = (iAirApplication) getApplication();
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(DetailActivity.this);

		VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		adapter = new SamplePagerAdapter(this);
		vp.setAdapter(adapter);

		addButtonListener();
		Bundle bundle = getIntent().getExtras();
		String sn = bundle.getString("sn");
		xiaoxinDevice = application.getXiaoxin(sn);

		// new GetDataTask().execute();

	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.back_img:
			finish();
			break;

		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<VerticalViewPager> refreshView) {
		new GetDataTask().execute();
	}

	static class SamplePagerAdapter extends
			com.handmark.verticalview.PagerAdapter {

		private static int[] sDrawables = { R.layout.include_up_detail,
				R.layout.include_down_detail };
		private DetailActivity pageContext;

		// private ListView listView;
		// MainEntryAdapter adapter;

		public SamplePagerAdapter(DetailActivity paramContext) {
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
				View xiaoxinView = (View) subView
						.findViewById(R.id.xiaoxin_info);

				TextView nameview = (TextView) xiaoxinView
						.findViewById(R.id.xiaoxin_name);
				nameview.setText(pageContext.xiaoxinDevice.name);
				TextView pm25 = (TextView) xiaoxinView
						.findViewById(R.id.xiaoxin_pm25);
				pm25.setText("PM2.5:" + pageContext.xiaoxinDevice.pm25);
				TextView temp = (TextView) xiaoxinView
						.findViewById(R.id.xiaoxin_temphumi);
				temp.setText("�¶�:" + pageContext.xiaoxinDevice.temp + " ʪ��:"
						+ pageContext.xiaoxinDevice.humi);
				// nameview.setText()
				final EditText editspeedEditText = (EditText)xiaoxinView.findViewById(R.id.input_speed);
				editspeedEditText.setText(String.valueOf(pageContext.xiaoxinDevice.speed));
				Button speedButton = (Button)xiaoxinView.findViewById(R.id.btn_speed);
				speedButton.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						String sn = pageContext.xiaoxinDevice.sn;
						int speed = Integer.parseInt(editspeedEditText.getText().toString());
						
						
						XinServerManager.setxiaoxin_speed(pageContext, sn, speed, null);
						

					}

				});
				CheckBox switchoff = (CheckBox) xiaoxinView
						.findViewById(R.id.xiaoxin_switch);
				switchoff.setChecked(pageContext.xiaoxinDevice.switchOn != 0);
				switchoff.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						String sn = pageContext.xiaoxinDevice.sn;
						int isOn = 0;
						if (((CheckBox) v).isChecked()) {
							isOn = 1;
						}
						XinServerManager.setxiaoxin_switch(pageContext, sn, isOn, null);
						

					}

				});

				// adapter = new MainEntryAdapter(pageContext);

				// listView = (ListView) subView.findViewById(R.id.listView1);
				// listView.setAdapter(adapter);//
				// listView.setDividerHeight(0);
				// adapter.addDevice(1, "");
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

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mPullToRefreshViewPager.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	private void addButtonListener() {
		findViewById(R.id.back_img).setOnClickListener(this);
		// findViewById(R.id.main_share).setOnClickListener(this);
		// findViewById(R.id.main_setting).setOnClickListener(this);
	}

}
