package com.txmcu.iair.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Device;
import com.txmcu.iair.adapter.MainEntryAdapter;
import com.txmcu.xiaoxin.config.XinServerManager;

public class HomeManageActivity extends Activity implements OnClickListener {

	private static final String TAG = "iair";

	MainEntryAdapter mainentryAdapter;
	

	public static  HomeManageActivity instance;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_manage);
		instance = this;
		 mainentryAdapter = new MainEntryAdapter(this);//
	     
	     listView = (ListView) findViewById(R.id.home_xiaoxinlist);//
	     listView.setAdapter(mainentryAdapter);//
	    // listView.setDividerHeight(0);
	     
	     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
	     {
			@Override
			public void onItemClick(AdapterView parent, View view,int position, long id){
				//Log.i("sfs", "asfasds");
				Device b = (Device)mainentryAdapter.getItem(position);
				if(position >0)
				{
					
						Intent localIntent = new Intent(HomeManageActivity.this, DetailDeviceActivity.class);
						localIntent.putExtra("sn", b.sn);
						HomeManageActivity.this.startActivity(localIntent);
						HomeManageActivity.this.overridePendingTransition(R.anim.left_enter, R.anim.alpha_out);
					
					
					
				}
			}
	     });
	    // iAirApplication application = (iAirApplication)pageContext.getApplication();
	    
	   
	     refreshlist();
	    

	

		findViewById(R.id.back_img).setOnClickListener(this);
	}
	public void refreshlist()
	{
		  mainentryAdapter.syncDevices();
			mainentryAdapter.notifyDataSetChanged();
	}
	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
		}

	}

}
