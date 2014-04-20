package com.txmcu.iair.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.txmcu.iair.R;
/**
 * 设置界面
 * @author Administrator
 *
 */
public class SettingActivity extends Activity implements OnClickListener {

	private static final String TAG = "iair";




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		findViewById(R.id.return_btn).setOnClickListener(this);

		((TextView)findViewById(R.id.check_update_setting_sum)).setText(getVersion());
	}

	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        String version = info.versionName+"."+info.versionCode;
	        return "当前版本:" + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "";
	    }
	}

	public void onClick(View view) {
	
		if (view.getId()==R.id.return_btn) {
			finish();
		}

	}

}
