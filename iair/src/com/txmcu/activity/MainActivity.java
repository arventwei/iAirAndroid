package com.txmcu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.txmcu.iair.R;

public class MainActivity extends  Activity
implements OnRefreshListener<VerticalViewPager>,OnClickListener
{

	
	private PullToRefreshViewPager mPullToRefreshViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Common);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(MainActivity.this);

		VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setAdapter(new SamplePagerAdapter(this));
		
		addButtonListener();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    { 	
    	//check selected menu item
    	// R.id.exit is @+id/exit
    	if(item.getItemId() == R.id.action_quit){
    		//close the Activity
    		
    		LoginActivity.logout(this);
    		return true;
    	}
    	return false;
    }
	@Override
	public void onClick(View paramView)
	{
	    switch (paramView.getId())
	    {
	    	case R.id.add_city:
	    		popWindowAdding();
	    		break;
	    	case R.id.main_share:
	    		main_top_share();
	    		break;
	    	case R.id.main_setting:
	    		popWindowSetting();
	    		break;
	    }
	}
	  
	@Override
	public void onRefresh(PullToRefreshBase<VerticalViewPager> refreshView) {
		new GetDataTask().execute();
	}

	static class SamplePagerAdapter extends com.handmark.verticalview.PagerAdapter {

		private static int[] sDrawables = { R.layout.include_main_up, R.layout.include_main_down };
		private Context pageContext;
		public SamplePagerAdapter(Context paramContext) {
			pageContext = paramContext;
		}
		@Override
		public int getCount() {
			return sDrawables.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			//ImageView imageView = new ImageView(container.getContext());
			//imageView.setImageResource(sDrawables[position]);
			 LayoutInflater localLayoutInflater = LayoutInflater.from(pageContext);
			 View subView  = localLayoutInflater.inflate(sDrawables[position], null);
			
			 
			// Now just add ImageView to ViewPager and return it
			container.addView(subView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

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
				Thread.sleep(500);
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

	public static void TryLoadMainActivity(Activity paramContext) {

	    paramContext.startActivity(new Intent(paramContext, MainActivity.class));
	    paramContext.finish();
	}

	private void addButtonListener() {
		findViewById(R.id.add_city).setOnClickListener(this);
		findViewById(R.id.main_share).setOnClickListener(this);
		findViewById(R.id.main_setting).setOnClickListener(this);
	}
	
	private void popWindowAdding() {
		
		View localView = getLayoutInflater().inflate(R.layout.popup_window_add_layout, null, false);
		PopupWindow popWin = new PopupWindow(localView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
	    popWin.setBackgroundDrawable(new ColorDrawable(0));
	    popWin.setFocusable(true);
	    popWin.setTouchable(true);
	    popWin.setOutsideTouchable(true);
	    popWin.update();
	    popWin.setAnimationStyle(R.style.popwindow_anim_style);
	    //localView.findViewById(net.qihoo.launcher.widget.clockweather.R.id.main_setting_layout).setOnClickListener(new hX(this));
	    //localView.findViewById(net.qihoo.launcher.widget.clockweather.R.id.barrage_setting_layout).setOnClickListener(new hY(this));
	 
	    popWin.showAsDropDown(findViewById(R.id.add_city), 0, 0);
	}
	private void main_top_share() {
		
	}
	private void popWindowSetting() {
		Toast.makeText(this, "asfasd", Toast.LENGTH_LONG).show();
	}
}
