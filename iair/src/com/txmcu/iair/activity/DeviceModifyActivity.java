package com.txmcu.iair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.txmcu.iair.R;

public class DeviceModifyActivity extends Activity implements OnClickListener {

	private static final String TAG = "iair";




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_modify);

		findViewById(R.id.back_img).setOnClickListener(this);

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.back_img) {
			finish();
		}

	}

}
