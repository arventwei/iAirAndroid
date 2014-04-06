package com.txmcu.iair.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

public class HomeAddActivity extends Activity implements OnClickListener {

	private static final String TAG = "HomeAddActivity";


	iAirApplication application;

	EditText exist_homeid_ed;
	//EditText cityNameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_add);

		application = (iAirApplication)getApplication();
		findViewById(R.id.add_exist_home_btn).setOnClickListener(this);
		findViewById(R.id.add_new_home_btn).setOnClickListener(this);
		findViewById(R.id.back_img).setOnClickListener(this);
		
		exist_homeid_ed = (EditText)findViewById(R.id.exist_homeid);
	//	cityNameEditText = (EditText)findViewById(R.id.deviceId);
		
		//userNameEditText.setText(application.getNickName());
		//cityNameEditText.setText("北京");

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.add_exist_home_btn) {
			
			String homeid = exist_homeid_ed.getText().toString();
			//String citName = cityNameEditText.getText().toString();
			
			iAirUtil.showProgressDialogCancelable(HomeAddActivity.this);
			XinServerManager.gethome_basedata (this,application.getUserid(),homeid,new XinServerManager.onSuccess() {
				
				@Override
				public void run(JSONObject response) throws JSONException {
					
					iAirUtil.dismissDialog();
					if (response.get("ret").equals("Fail")) {
						iAirUtil.toastMessage(HomeAddActivity.this, "没有找到指定的家");
					}
					List<Home>homeList = XinServerManager.getHomeFromJson(HomeAddActivity.this,response.getJSONArray("home"));
					//application.cityList = XinServerManager.getCityFromJson(response.getJSONArray("area"));
					// TODO Auto-generated method stub
					//refreshlist();
				//	synchomebb();
					if (homeList.size()==0) {
						iAirUtil.toastMessage(HomeAddActivity.this, "没有找到指定的家");
					}
					else {
						XinSession.getSession().put("home", homeList.get(0));
						
						Intent localIntent = new Intent(
								HomeAddActivity.this,
								HomeModifyActivity.class);
						localIntent.putExtra("type", 0);
					
						startActivity(localIntent);
						overridePendingTransition(R.anim.left_enter,
										R.anim.alpha_out);
						HomeAddActivity.this.finish();
					}
					
				}
			});
			
			
		}
		else if (view.getId()==R.id.add_new_home_btn) {
			Intent localIntent = new Intent(
					HomeAddActivity.this,
					HomeModifyActivity.class);
			localIntent.putExtra("type", 1);
		
			startActivity(localIntent);
			overridePendingTransition(R.anim.left_enter,
							R.anim.alpha_out);
			HomeAddActivity.this.finish();
		}
		else if (view.getId()==R.id.back_img) {
			finish();
		}
		

	}

}
