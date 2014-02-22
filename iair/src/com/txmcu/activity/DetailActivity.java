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
import android.widget.ListView;
import android.widget.PopupWindow;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.extras.viewpager.PullToRefreshViewPager;
import com.handmark.verticalview.VerticalViewPager;
import com.txmcu.adapter.MainEntryAdapter;
import com.txmcu.iair.R;

public class DetailActivity extends  Activity
implements OnRefreshListener<VerticalViewPager>,OnClickListener
{

	
	private PullToRefreshViewPager mPullToRefreshViewPager;
	private PopupWindow popWin;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Common);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_device_main);

		mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
		mPullToRefreshViewPager.setOnRefreshListener(DetailActivity.this);

		VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
		vp.setAdapter(new SamplePagerAdapter(this));
		
		addButtonListener();
		
		
		
	}
	
	@Override
	public void onClick(View paramView)
	{
	    switch (paramView.getId())
	    {
	    	case R.id.back_img:
	    		finish();
	    		break;
	    
	    }
	}
	  
	@Override
	public void onRefresh(PullToRefreshBase<VerticalViewPager> refreshView) {
		new GetDataTask().execute();
	}

	static class SamplePagerAdapter extends com.handmark.verticalview.PagerAdapter {

		private static int[] sDrawables = { R.layout.include_up_main, R.layout.include_down_main };
		private Context pageContext;
		
		
		//private ListView listView; 
		//MainEntryAdapter adapter;
		
		
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
			 
			 if (position ==0) {
				// adapter = new MainEntryAdapter(pageContext);//创建一个适配器  
			     
			     //listView = (ListView) subView.findViewById(R.id.listView1);//实例化ListView  
			     //listView.setAdapter(adapter);//为ListView控件绑定适配器
			     //listView.setDividerHeight(0);
			     //adapter.addDevice(1, "小新家");
			}
			 else {
				
			}
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



	private void addButtonListener() {
		findViewById(R.id.back_img).setOnClickListener(this);
	//	findViewById(R.id.main_share).setOnClickListener(this);
		//findViewById(R.id.main_setting).setOnClickListener(this);
	}
	
	
}
