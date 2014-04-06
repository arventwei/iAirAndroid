package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityGroup;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.City;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.adapter.MainEntryAdapter;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class MainActivity extends ActivityGroup  implements
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

		//refreshlist();
		requestlist();
	//	String authType = this.getIntent().getStringExtra("authType");
	//	String token = this.getIntent().getStringExtra("token");
	//	String openId = this.getIntent().getStringExtra("openId");
	//	String nickName = this.getIntent().getStringExtra("nickName");

//		XinServerManager.login(this, authType, token, openId, nickName,
//				new XinServerManager.onSuccess() {
//
//					@Override
//					public void run(JSONObject response) throws JSONException {
//						// TODO Auto-generated method stub
//						String ret = response.getString("ret");
//						String usertype = response.getString("usertype");
//						String userid = response.getString("userid");
//						
//						//Map<String, String> snMap = iAirUtil
//						//		.getQueryMapFromUrl(response);
//
//						application.setUserid(userid);
//						Toast.makeText(MainActivity.this,
//								R.string.xiaoxin_login_ok, Toast.LENGTH_LONG)
//								.show();
//					}
//				});

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
//	@Override
//	protected void onResume() {
//		super.onResume();
//		PushLink.setCurrentActivity(this);
//	}

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
			//finish();
			
			Intent localIntent = new Intent(MainActivity.this,
					LoginActivity.class);

			
			this.startActivity(localIntent);
			this.finish();
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
		case R.id.logout_layout:
		{
			LoginActivity.logout(this);
			//finish();
			
			Intent localIntent = new Intent(MainActivity.this,
					LoginActivity.class);

			
			this.startActivity(localIntent);
			this.finish();
			break;
		}
		case R.id.add_city_layout:
			popwin_add_city();
			break;

		case R.id.add_home_layout:
			popwin_add_home();
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
		private MainActivity  pageContext;

		public ListView listView;
		public MainEntryAdapter mainentryAdapter;
		iAirApplication application;
		public TabHost mTabHost;

		public SamplePagerAdapter(MainActivity  paramContext) {
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
				
	
//				LayoutParams localLayoutParams = new LayoutParams(500,300);
//			    localLayoutParams.width = 566;
//			    localLayoutParams.height = 300;
//			    subView.setLayoutParams(localLayoutParams);
				//subView.setLayoutParams(params)
				mainentryAdapter = new MainEntryAdapter(pageContext);//

				listView = (ListView) subView.findViewById(R.id.homelist);// 实例化ListView
				listView.setAdapter(mainentryAdapter);//
				// listView.setDividerHeight(0);

				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView parent, View view,
							int position, long id) {
						// Log.i("sfs", "asfasds");
						if (position == 0) {
							return;
						}
						Object bObject = mainentryAdapter.getItem(position);
						if (bObject.getClass() == City.class) {
							Intent localIntent = new Intent(pageContext,
									DetailCityActivity.class);
							// localIntent.putExtra("sn", b.sn);
							pageContext.startActivity(localIntent);
							pageContext.overridePendingTransition(
									R.anim.left_enter, R.anim.alpha_out);
						}
						else if (bObject.getClass() == Home.class) {
							Home b = (Home)bObject;
							Intent localIntent = new Intent(pageContext,
									HomeActivity.class);
							 localIntent.putExtra("homeid", b.homeid);
							pageContext.startActivity(localIntent);
							pageContext.overridePendingTransition(
									R.anim.left_enter, R.anim.alpha_out);
						}
//						if (position > 0) {
//							if (b.sn.equals("1111111")) {
//								Intent localIntent = new Intent(pageContext,
//										DetailCityActivity.class);
//								// localIntent.putExtra("sn", b.sn);
//								pageContext.startActivity(localIntent);
//								pageContext.overridePendingTransition(
//										R.anim.left_enter, R.anim.alpha_out);
//
//							} else if (b.sn.equals("1111112")) {
//								
//
//							}

					//	}
					}
				});
				// iAirApplication application =
				// (iAirApplication)pageContext.getApplication();

				// TODO
//				XinServerManager.query_bindlist(pageContext,
//						application.getUserid(),
//						new XinServerManager.onSuccess() {
//
//							@Override
//							public void run(String response) {
//
//								mainentryAdapter.syncHomes();
//								mainentryAdapter.notifyDataSetChanged();
//								// TODO Auto-generated method stub
//
//							}
//						});

			} else {

				
				mTabHost = (TabHost) subView.findViewById(android.R.id.tabhost);
				mTabHost.setup(pageContext.getLocalActivityManager());
				pageContext.synchomebb();
				
				

			}
			// Now just add ImageView to ViewPager and return it
			container.addView(subView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return subView;
		}

		private void setupTab(final Intent view, final String tag) 
		{
			View tabview = createTabView(mTabHost.getContext(), tag);
		    TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(view);
			mTabHost.addTab(setContent);
		}
		private static View createTabView(final Context context, final String text) {
			View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
			TextView tv = (TextView) view.findViewById(R.id.tabsText);
			tv.setText(text);
			return view;
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
		synchomebb();
	}

	public void requestlist()
	{
		XinServerManager.getfirstpage_briefinfo(this,application.getUserid(),new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				
				application.homeList = XinServerManager.getHomeFromJson(MainActivity.this,response.getJSONArray("home"));
				application.cityList = XinServerManager.getCityFromJson(response.getJSONArray("area"));
				// TODO Auto-generated method stub
				refreshlist();
			//	synchomebb();
				
			}
		});
	}
	public  void synchomebb() {
		
		SamplePagerAdapter adapter = (SamplePagerAdapter) (mPullToRefreshViewPager
				.getRefreshableView().getAdapter());
		
		adapter.mTabHost.clearAllTabs();
		
		
		for(Home home:application.homeList)
		{
			Intent intent = new Intent(this, MainDownChatActivity.class);
			intent.putExtra("homeid", home.homeid);
			adapter.setupTab(intent,  home.homename);
		}
		//setupTab(new Intent(pageContext, MainDownChatActivity.class),  "我的家");
		//setupTab(new Intent(pageContext, MainDownChatActivity.class), "我的办公室");
		//setupTab(new Intent(pageContext, MainDownChatActivity.class),  "朋友的家");
		if (application.homeList.size()>0) {
			adapter.mTabHost.setCurrentTab(0);
		}
	}
	private void AsyncMainEntrys() {

		mPullToRefreshViewPager.onRefreshComplete();
	//	refreshlist();
		requestlist();
		//TODO
//		XinServerManager.query_bindlist(this, application.getUserid(),
//				new XinServerManager.onSuccess() {
//
//					@Override
//					public void run(String response) {
//
//						refreshlist();
//
//
//					}
//				});
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

			localView.findViewById(R.id.add_home_layout).setOnClickListener(
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

	
	private void popwin_add_home() {
		CloseAddPopWindowAndOpenSubView(HomeManageActivity.class);
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
			localView.findViewById(R.id.setting_layout)
					.setOnClickListener(this);
			localView.findViewById(R.id.logout_layout)
			.setOnClickListener(this);
		}
		
		popWinSetting.showAsDropDown(findViewById(R.id.main_setting), 0, 0);
	}



	private void popwin_setting() {
		CloseSettingPopWindowAndOpenSubView(SettingActivity.class);
	}
}
