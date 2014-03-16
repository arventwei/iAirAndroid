package com.txmcu.iair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.txmcu.iair.R;

public class SettingActivity extends Activity implements OnClickListener {

	private static final String TAG = "iair";




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		findViewById(R.id.return_btn).setOnClickListener(this);

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.return_btn) {
			finish();
		}

	}

}
