package com.txmcu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.txmcu.iair.R;

public class CityManageActivity extends Activity 
implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_manage_activity);
		findViewById(R.id.back_img).setOnClickListener(this);
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
	
	
}
