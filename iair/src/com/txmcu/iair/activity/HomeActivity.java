package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.adapter.HomeEntryAdapter;
import com.txmcu.iair.adapter.MessageAdapter;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;

public class HomeActivity extends Activity implements OnRefreshListener<VerticalViewPager>,OnClickListener {

	private static final String TAG = "HomeActivity";

	//MainEntryAdapter mainentryAdapter;
	
	private PullToRefreshViewPager mPullToRefreshViewPager;
	iAirApplication application;

	public static  HomeActivity instance;
	String homeidString;
	public Home home;
	ListView listView;
	private PopupWindow popWinSetting;
	public SamplePagerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		instance = this;
		application = (iAirApplication)getApplication();
		Intent intent = getIntent();
		homeidString = intent.getStringExtra("homeid");

		
		//application.homeList =(homes);
		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(HomeActivity.this);

		VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setAdapter(new SamplePagerAdapter(HomeActivity.this));
		
		
		requestlist();
		


		findViewById(R.id.back_img).setOnClickListener(this);
		findViewById(R.id.home_edit_top_btn).setOnClickListener(this);
		findViewById(R.id.home_refresh_top_btn).setOnClickListener(this);
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
//	public void refreshlist()
//	{
//		//  mainentryAdapter.syncDevices();
//		//	mainentryAdapter.notifyDataSetChanged();
//	}
	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
		}
		else if (view.getId()==R.id.home_edit_top_btn) {
			main_top_setting();
		}
		else if (view.getId()==R.id.set_broad_layout) {
			popwin_set_broad();
		}
		
	}
	
	private void main_top_setting() {

		if (popWinSetting == null) {
			View localView = getLayoutInflater().inflate(
					R.layout.popup_window_home_layout, null, false);
			popWinSetting = new PopupWindow(localView, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, true);
			popWinSetting.setBackgroundDrawable(new ColorDrawable(0));
			popWinSetting.setFocusable(true);
			popWinSetting.setTouchable(true);
			popWinSetting.setOutsideTouchable(true);
			popWinSetting.update();
			popWinSetting.setAnimationStyle(R.style.popwindow_anim_style);
			localView.findViewById(R.id.set_broad_layout).setOnClickListener(
					this);
//			localView.findViewById(R.id.add_device_layout).setOnClickListener(
//					this);
			
		}

		popWinSetting.showAsDropDown(findViewById(R.id.home_edit_top_btn), 0, 0);
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
				String content = ((EditText)dialog.findViewById(R.id.barrage_edit_text)).getText().toString();
				content = content.trim();
				XinServerManager.addhomenotice(HomeActivity.this, application.getUserid(), homeidString,
						content, new XinServerManager.onSuccess() {
							
							@Override
							public void run(JSONObject response) throws JSONException {
								// TODO Auto-generated method stub
								requestlist();
								
							}
						});
				

			}
		});

		dialog.show();
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
			messageAdapter = new MessageAdapter(pageContext);
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
				mainentryAdapter = new HomeEntryAdapter(pageContext);//

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

	public void requestlist()
	{
		XinServerManager.gethome_detailweather(this, application.getUserid(),
				homeidString, new XinServerManager.onSuccess() {
			
			@Override
			public void run(JSONObject response) throws JSONException {
				home = application.getHome(homeidString);
				home.xiaoxins = XinServerManager.getXiaoxinFromJson(response.getJSONArray("xiaoxin"));
				home.notices = XinServerManager.getNoticeFromJson(application,response.getJSONArray("notice"));
				refreshlist();
				((TextView)findViewById(R.id.home_title)).setText(home.homename);
				
			}
		});
	}
	public void refreshlist() {
		SamplePagerAdapter adapter = (SamplePagerAdapter) (mPullToRefreshViewPager
				.getRefreshableView().getAdapter());
		adapter.mainentryAdapter.sync(home);
		adapter.mainentryAdapter.notifyDataSetChanged();
		adapter.messageAdapter.syncMessage(home);
		adapter.messageAdapter.notifyDataSetChanged();
	}

	private void AsyncMainEntrys() {

		mPullToRefreshViewPager.onRefreshComplete();
		requestlist();
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
