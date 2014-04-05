package com.txmcu.iair.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;

public class NewUser4Activity extends Activity implements OnClickListener {

	private static final String TAG = "NewUser4Activity";

	TextView titleEditText;

	iAirApplication application;

	Home home;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser_4);
		
		application = (iAirApplication)getApplication();
		titleEditText = (TextView)findViewById(R.id.finish_title);
		findViewById(R.id.finish_btn).setOnClickListener(this);
		findViewById(R.id.share_btn).setOnClickListener(this);
		
		List<Home> homes = application.homeList;
		if (homes.size()==0) {
			iAirUtil.toastMessage(this, "homes.size()==0 error");
			return;
		}
		home = homes.get(0);

		titleEditText.setText("完成,进入\n"+home.homename);
	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.finish_btn) {
			//finish();
			Intent localIntent = new Intent(this,
					MainActivity.class);
			
			this.startActivity(localIntent);
			this.finish();
			
		}

	}

}
