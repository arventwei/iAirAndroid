package com.txmcu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.txmcu.iair.R;

public class MainActivity extends  Activity implements OnRefreshListener<VerticalViewPager> {

	
	private PullToRefreshViewPager mPullToRefreshViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(MainActivity.this);

		VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setAdapter(new SamplePagerAdapter(this));
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
		Intent localIntent = new Intent();
	
	    localIntent.setClass(paramContext, MainActivity.class);
	    paramContext.startActivityForResult(localIntent, 1);
	    paramContext.finish();
	}

}
