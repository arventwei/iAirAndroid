package com.txmcu.iair.activity;

import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.pushlink.android.PushLink;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.MainEntryAdapter;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

public class MainActivity extends Activity implements
		OnRefreshListener<VerticalViewPager>, OnClickListener {

	private iAirApplication application;

	private PullToRefreshViewPager mPullToRefreshViewPager;
	private PopupWindow popWin;
	private PopupWindow popWinSetting;

	public static MainActivity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Common);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;

		this.application = ((iAirApplication) getApplication());

		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(MainActivity.this);

		VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setAdapter(new SamplePagerAdapter(this));

		addButtonListener();

		String authType = this.getIntent().getStringExtra("authType");
		String token = this.getIntent().getStringExtra("token");
		String openId = this.getIntent().getStringExtra("openId");
		String nickName = this.getIntent().getStringExtra("nickName");

		XinServerManager.login(this, authType, token, openId, nickName,
				new XinServerManager.onSuccess() {

					@Override
					public void run(String response) {
						// TODO Auto-generated method stub
						Map<String, String> snMap = iAirUtil
								.getQueryMapFromUrl(response);

						application.setUserid(snMap.get("userid"));
						Toast.makeText(MainActivity.this,
								R.string.xiaoxin_login_ok, Toast.LENGTH_LONG)
								.show();
					}
				});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		instance = null;
	}

	// Only for popups (FRIENDLY_POPUP or ANNOYING_POPUP)
	// You MUST do this, otherwise popups will not work.
	// Call it in the Activity you want to show the popup.
	// You can show the popup in many screens by adding this in more than one
	// Activity.
	@Override
	protected void onResume() {
		super.onResume();
		PushLink.setCurrentActivity(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// check selected menu item
		// R.id.exit is @+id/exit
		if (item.getItemId() == R.id.action_quit) {
			// close the Activity

			LoginActivity.logout(this);
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.add_city:
			main_top_add();
			break;
		case R.id.main_share:
			main_top_share();
			break;
		case R.id.main_setting:
			main_top_setting();
			break;

		case R.id.add_city_layout:
			popwin_add_city();
			break;
		case R.id.add_device_layout:
			popwin_add_device();
			break;
		case R.id.set_broad_layout:
			popwin_set_broad();
			break;
		case R.id.setting_layout:
			popwin_setting();
			break;
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<VerticalViewPager> refreshView) {
		mPullToRefreshViewPager.onRefreshComplete();
		AsyncMainEntrys();
		// new GetDataTask().execute(this);
	}

	static class SamplePagerAdapter extends
			com.handmark.verticalview.PagerAdapter {

		private static int[] sDrawables = { R.layout.include_main_up,
				R.layout.include_main_down };
		private Activity pageContext;

		public ListView listView;
		public MainEntryAdapter mainentryAdapter;
		iAirApplication application;

		public SamplePagerAdapter(Activity paramContext) {
			pageContext = paramContext;
			application = (iAirApplication) pageContext.getApplication();
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
				mainentryAdapter = new MainEntryAdapter(pageContext);//

				listView = (ListView) subView.findViewById(R.id.listView1);// 实例化ListView
				listView.setAdapter(mainentryAdapter);//
				// listView.setDividerHeight(0);

				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView parent, View view,
							int position, long id) {
						// Log.i("sfs", "asfasds");
						Device b = (Device) mainentryAdapter.getItem(position);
						if (position > 0) {
							if (b.sn.equals("1111111")) {
								Intent localIntent = new Intent(pageContext,
										DetailCityActivity.class);
								// localIntent.putExtra("sn", b.sn);
								pageContext.startActivity(localIntent);
								pageContext.overridePendingTransition(
										R.anim.left_enter, R.anim.alpha_out);

							} else if (b.sn.equals("1111112")) {
								Intent localIntent = new Intent(pageContext,
										HomeActivity.class);
								// localIntent.putExtra("sn", b.sn);
								pageContext.startActivity(localIntent);
								pageContext.overridePendingTransition(
										R.anim.left_enter, R.anim.alpha_out);

							}

						}
					}
				});
				// iAirApplication application =
				// (iAirApplication)pageContext.getApplication();

				XinServerManager.query_bindlist(pageContext,
						application.getUserid(),
						new XinServerManager.onSuccess() {

							@Override
							public void run(String response) {

								mainentryAdapter.syncHomes();
								mainentryAdapter.notifyDataSetChanged();
								// TODO Auto-generated method stub

							}
						});
				// mainentryAdapter.addDevice(1, "小新");
				// mainentryAdapter.addDevice(2, "爸爸家");
				// adapter.addDevice(3, "北京");
				// adapter.addDevice(4, "南京");
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

	public void refreshlist() {
		SamplePagerAdapter adapter = (SamplePagerAdapter) (mPullToRefreshViewPager
				.getRefreshableView().getAdapter());
		adapter.mainentryAdapter.syncHomes();
		adapter.mainentryAdapter.notifyDataSetChanged();
	}

	private void AsyncMainEntrys() {

		mPullToRefreshViewPager.onRefreshComplete();
		XinServerManager.query_bindlist(this, application.getUserid(),
				new XinServerManager.onSuccess() {

					@Override
					public void run(String response) {

						refreshlist();

						// TODO Auto-generated method stub

					}
				});
	}

	// private class GetDataTask extends AsyncTask<MainActivity, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(MainActivity... params) {
	// // Simulates a background job.
	// try {
	// final MainActivity viewBase = params[0];
	//
	//
	// //Thread.sleep(500);
	// } catch (Exception e) {
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	// mPullToRefreshViewPager.onRefreshComplete();
	// super.onPostExecute(result);
	// }
	// }

	private void addButtonListener() {
		findViewById(R.id.add_city).setOnClickListener(this);
		findViewById(R.id.main_share).setOnClickListener(this);
		findViewById(R.id.main_setting).setOnClickListener(this);
	}

	private void main_top_add() {

		if (popWin == null) {
			View localView = getLayoutInflater().inflate(
					R.layout.popup_window_add_layout, null, false);
			popWin = new PopupWindow(localView, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, true);
			popWin.setBackgroundDrawable(new ColorDrawable(0));
			popWin.setFocusable(true);
			popWin.setTouchable(true);
			popWin.setOutsideTouchable(true);
			popWin.update();
			popWin.setAnimationStyle(R.style.popwindow_anim_style);
			localView.findViewById(R.id.add_city_layout).setOnClickListener(
					this);
			localView.findViewById(R.id.add_device_layout).setOnClickListener(
					this);
		}

		popWin.showAsDropDown(findViewById(R.id.add_city), 0, 0);
	}

	protected void CloseAddPopWindowAndOpenSubView(Class<?> cls) {
		if (popWin != null) {
			popWin.dismiss();
			popWin = null;
		}

		Intent localIntent = new Intent(this, cls);
		startActivity(localIntent);
		overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
	}

	protected void CloseSettingPopWindowAndOpenSubView(Class<?> cls) {
		if (popWinSetting != null) {
			popWinSetting.dismiss();
			popWinSetting = null;
		}

		Intent localIntent = new Intent(this, cls);
		startActivity(localIntent);
		overridePendingTransition(R.anim.right_enter, R.anim.alpha_out);
	}

	private void popwin_add_city() {
		CloseAddPopWindowAndOpenSubView(CityManageActivity.class);

	}

	private void popwin_add_device() {
		CloseAddPopWindowAndOpenSubView(DeviceManageActivity.class);
	}

	private void main_top_share() {

	}

	private void main_top_setting() {

		if (popWinSetting == null) {
			View localView = getLayoutInflater().inflate(
					R.layout.popup_window_setting_layout, null, false);
			popWinSetting = new PopupWindow(localView,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			popWinSetting.setBackgroundDrawable(new ColorDrawable(0));
			popWinSetting.setFocusable(true);
			popWinSetting.setTouchable(true);
			popWinSetting.setOutsideTouchable(true);
			popWinSetting.update();
			popWinSetting.setAnimationStyle(R.style.popwindow_anim_style);
			localView.findViewById(R.id.set_broad_layout).setOnClickListener(
					this);
			localView.findViewById(R.id.setting_layout)
					.setOnClickListener(this);
		}

		popWinSetting.showAsDropDown(findViewById(R.id.main_setting), 0, 0);
	}

	private void popwin_set_broad() {

		if (popWinSetting != null) {
			popWinSetting.dismiss();
			popWinSetting = null;
		}

		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.broad_dialog);
		dialog.setTitle(R.string.broad_dialog_text_title);

		// set the custom dialog components - text, image and button
		// TextView text = (TextView) dialog.findViewById(R.id.text);
		// text.setText("Android custom dialog example!");
		// ImageView image = (ImageView) dialog.findViewById(R.id.image);
		// image.setImageResource(R.drawable.ic_launcher);

		Button dialogCancelButton = (Button) dialog
				.findViewById(R.id.correct_cancel_btn);
		// if button is clicked, close the custom dialog
		dialogCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		Button dialogOkButton = (Button) dialog
				.findViewById(R.id.correct_ok_btn);
		// if button is clicked, close the custom dialog
		dialogOkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		dialog.show();
	}

	private void popwin_setting() {
		CloseSettingPopWindowAndOpenSubView(SettingActivity.class);
	}
}
