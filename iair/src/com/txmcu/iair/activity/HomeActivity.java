package com.txmcu.iair.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.pushlink.android.PushLink;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.adapter.HomeEntryAdapter;
import com.txmcu.iair.adapter.MessageAdapter;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class HomeActivity extends Activity implements OnRefreshListener<VerticalViewPager>,OnClickListener {

	private static final String TAG = "HomeActivity";

	//MainEntryAdapter mainentryAdapter;
	
	private PullToRefreshViewPager mPullToRefreshViewPager;
	iAirApplication application;

	public static  HomeActivity instance;
	public Home home;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		instance = this;
		application = (iAirApplication)getApplication();
		Intent intent = getIntent();
		final String homeidString = intent.getStringExtra("homeid");

		XinServerManager.gethome_detailweather(this, application.getUserid(),
				homeidString, new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				home = application.getHome(homeidString);
				home.xiaoxins = XinServerManager.getXiaoxinFromJson(response.getJSONArray("xiaoxin"));
				//application.homeList =(homes);
				mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
				mPullToRefreshViewPager.setOnRefreshListener(HomeActivity.this);

				VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
				vp.setAdapter(new SamplePagerAdapter(HomeActivity.this));
				
			}
		});
		//home = application.getHome(homeidString);
		//TODO...
		


		findViewById(R.id.back_img).setOnClickListener(this);
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
//	public void refreshlist()
//	{
//		//  mainentryAdapter.syncDevices();
//		//	mainentryAdapter.notifyDataSetChanged();
//	}
	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
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

		private static int[] sDrawables = { R.layout.include_home_up,
				R.layout.include_home_down };
		private HomeActivity pageContext;

		public ListView listView;
		public HomeEntryAdapter mainentryAdapter;
		iAirApplication application;
		
		//down chat
		public ListView chatlistView;
	  //  private List<MessageVo> meList = new ArrayList<MessageVo>();
	    private MessageAdapter messageAdapter;
		//

		public SamplePagerAdapter(HomeActivity paramContext) {
			pageContext = paramContext;
			messageAdapter = new MessageAdapter(pageContext, pageContext.home);;
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
				mainentryAdapter = new HomeEntryAdapter(pageContext,pageContext.home);//

				listView = (ListView) subView.findViewById(R.id.homelist);// 实例化ListView
				listView.setAdapter(mainentryAdapter);//
				// listView.setDividerHeight(0);

				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView parent, View view,
							int position, long id) {
						// Log.i("sfs", "asfasds");
						Device b = (Device)mainentryAdapter.getItem(position);
						if(position >0)
						{
							
								Intent localIntent = new Intent(pageContext, DetailDeviceActivity.class);
								localIntent.putExtra("sn", b.sn);
								pageContext.startActivity(localIntent);
								pageContext.overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
							
							
							
						}

					}
				});
				// iAirApplication application =
				// (iAirApplication)pageContext.getApplication();

//				XinServerManager.query_bindlist(pageContext,
//						application.getUserid(),
//						new XinServerManager.onSuccess() {
//
//							@Override
//							public void run(String response) {
//
//								mainentryAdapter.syncDevices();
//								mainentryAdapter.notifyDataSetChanged();
//								// TODO Auto-generated method stub
//
//							}
//						});
				
				mainentryAdapter.sync();
				mainentryAdapter.notifyDataSetChanged();

			} else {
				
				chatlistView = (ListView) subView.findViewById(R.id.chat_listView);// 实例化ListView
				chatlistView.setAdapter(messageAdapter);//
				
				//test data
				//SimpleDateFormat df = new SimpleDateFormat("HH:mm");
             //   String time = df.format(new Date()).toString();
             //   String sendContenta= "阿~阿~… -宝宝";
             //   String sendContentb= "刚带宝宝晒完太阳.-爷爷";
                //meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContenta, time));
                //meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContentb, time));
                //meList.add(new MessageVo(MessageVo.MESSAGE_TO, sendContenta, time));
                //meList.add(new MessageVo(MessageVo.MESSAGE_TO, sendContentb, time));

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
		adapter.mainentryAdapter.sync();
		adapter.mainentryAdapter.notifyDataSetChanged();
	}

	private void AsyncMainEntrys() {

		mPullToRefreshViewPager.onRefreshComplete();
//		XinServerManager.query_bindlist(this, application.getUserid(),
//				new XinServerManager.onSuccess() {
//
//					@Override
//					public void run(String response) {
//
//						refreshlist();
//
//						
//
//					}
//				});
	}

}
