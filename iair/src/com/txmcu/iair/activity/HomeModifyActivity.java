package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.XinSession;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

public class HomeModifyActivity extends Activity implements OnClickListener {

	private static final String TAG = "HomeModifyActivity";


	iAirApplication application;
	
	int type=0;	//0-add exist ,1-add new ,2-modify home

	Home home;
	TextView home_add_title_ed;
	
	EditText userNameEditText;
	EditText homeIdEditText;
	EditText homeNameEditText;
	CheckBox homeShareCheckBox;
	EditText homerefreshInterval;
	int autoadd;
	//EditText cityNameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 0);
		autoadd = intent.getIntExtra("autoadd", 0);
		home = (Home)XinSession.getSession().get("home");
		XinSession.getSession().cleanUpSession();
		
		setContentView(R.layout.activity_home_modify);

		application = (iAirApplication)getApplication();
		findViewById(R.id.ok_btn).setOnClickListener(this);
		findViewById(R.id.cancel_btn).setOnClickListener(this);
		findViewById(R.id.back_img).setOnClickListener(this);
		
		home_add_title_ed = (TextView)findViewById(R.id.home_add_title);
		
		userNameEditText = (EditText)findViewById(R.id.userName);
		homeIdEditText = (EditText)findViewById(R.id.homeId);
		homeNameEditText = (EditText)findViewById(R.id.homeName);
		homerefreshInterval = (EditText)findViewById(R.id.homeRefresh);
		homeShareCheckBox = (CheckBox)findViewById(R.id.homeShare);
		
		
		
		if (type==0) {
			home_add_title_ed.setText("添加别人家");
			userNameEditText.setText(home.ownernickname);
			homeIdEditText.setText(home.homeid);
			homeNameEditText.setText(home.homename);
			homeShareCheckBox.setChecked(home.share);
			homerefreshInterval.setText(home.refresh_interval);
			userNameEditText.setEnabled(false);
			homeIdEditText.setEnabled(false);
			homeNameEditText.setEnabled(true);
			homerefreshInterval.setEnabled(false);
			homeShareCheckBox.setEnabled(false);			
			
		}
		else if (type == 1) {
			home_add_title_ed.setText("创建新家");
			userNameEditText.setText(application.getNickName());
			homeIdEditText.setText("");
			homeNameEditText.setText(application.getNickName()+"的家");
			homeShareCheckBox.setChecked(true);
			homerefreshInterval.setText("30");
			userNameEditText.setEnabled(false);
			homeIdEditText.setEnabled(false);
			homeNameEditText.setEnabled(true);
			homerefreshInterval.setEnabled(true);
			homeShareCheckBox.setEnabled(true);	
			
		}
		else if (type == 2) {
			home_add_title_ed.setText("修改家");
			userNameEditText.setText(home.ownernickname);
			homeIdEditText.setText(home.homeid);
			homeNameEditText.setText(home.homename);
			homeShareCheckBox.setChecked(home.share);
			homerefreshInterval.setText(home.refresh_interval);
			userNameEditText.setEnabled(false);
			homeIdEditText.setEnabled(false);
			homeNameEditText.setEnabled(true);
			if (home.own.equals("1")) {
				homerefreshInterval.setEnabled(true);
				homeShareCheckBox.setEnabled(true);
			}
			else {
				homerefreshInterval.setEnabled(false);
				homeShareCheckBox.setEnabled(false);
			}

		}
		
		
//		cityNameEditText = (EditText)findViewById(R.id.deviceId);
//		
//		userNameEditText.setText(application.getNickName());
//		cityNameEditText.setText("北京");

	}



	public void onClick(View view) {
	
		if (view.getId()==R.id.ok_btn) {
			
			if (type==0) {
				iAirUtil.showProgressDialogCancelable(this);
				String homenickname = homeNameEditText.getText().toString();
				XinServerManager.binduser_home(this, application.getUserid(), home.homeid, homenickname, new XinServerManager.onSuccess() {
					
					@Override
					public void run(JSONObject response) throws JSONException {
						// TODO Auto-generated method stub
						
						if (response.get("ret").equals("Ok")) {
							
							iAirUtil.toastMessage(HomeModifyActivity.this, "添加别人家成功");
							
							
							XinServerManager.requesthomelist(HomeModifyActivity.this,true);
						}
						else {
							iAirUtil.dismissDialog();
							iAirUtil.toastMessage(HomeModifyActivity.this, "添加别人家失败");
						}
						
					}
				});
			}
			else if (type==1)
			{
				iAirUtil.showProgressDialogCancelable(this);
				String homenickname = homeNameEditText.getText().toString();
				String shareString  = homeShareCheckBox.isChecked()?"1":"0";
				String refreshString = homerefreshInterval.getText().toString();
				XinServerManager.addhome(this, application.getUserid(), homenickname, shareString,refreshString, new XinServerManager.onSuccess() 
				{
					
					@Override
					public void run(JSONObject response) throws JSONException {
						// TODO Auto-generated method stub
						
						if (response.get("ret").equals("Ok")) {
							
							final String homeidNewString = response.get("homeid").toString();
							iAirUtil.toastMessage(HomeModifyActivity.this, "新建家成功");
							
							
							XinServerManager.requesthomelist(HomeModifyActivity.this,true,new XinServerManager.onSuccess() {
								
								@Override
								public void run(JSONObject response) throws JSONException {
									// TODO Auto-generated method stub
									if (autoadd==1) {
										//TODO....
										Home newHome = application.getHome(homeidNewString);
										Intent localIntent = new Intent(
												HomeModifyActivity.this,
												DeviceManageActivity.class);
										XinSession.getSession().put("home", newHome);
										localIntent.putExtra("autoadd", 1);
									
										HomeModifyActivity.this.startActivity(localIntent);
										HomeModifyActivity.this.overridePendingTransition(R.anim.left_enter,
														R.anim.alpha_out);
									}
								}
							});
							
							
						}
						else {
							iAirUtil.dismissDialog();
							iAirUtil.toastMessage(HomeModifyActivity.this, "新建家失败");
						}
						
						
					}
				});
				//addhome
			}
			else if (type==2) {
				iAirUtil.showProgressDialogCancelable(this);
				String homenickname = homeNameEditText.getText().toString();
				String shareString  = homeShareCheckBox.isChecked()?"1":"0";
				String refreshString = homerefreshInterval.getText().toString();
				XinServerManager.sethome_baseinfo(this, application.getUserid(),home.homeid, homenickname, shareString,refreshString, new XinServerManager.onSuccess() {
					
					@Override
					public void run(JSONObject response) throws JSONException {
						// TODO Auto-generated method stub
						
						if (response.get("ret").equals("Ok")) {
							
							iAirUtil.toastMessage(HomeModifyActivity.this, "修改家成功");
							
							
							XinServerManager.requesthomelist(HomeModifyActivity.this,true);
						}
						else {
							iAirUtil.dismissDialog();
							iAirUtil.toastMessage(HomeModifyActivity.this, "修改家失败");
						}
						
					}
				});
				//XinServerManager.sethome_baseinfo(this, application.getUserid(),home.homeid,homeName,
				//		shareString, homerefresh, null);
				//sethome_baseinfo
			}
//			String userName = userNameEditText.getText().toString();
//			String citName = cityNameEditText.getText().toString();
//			
//			application.setNickName(userName);
//			
//			XinServerManager.setuser_nickname(this, application.getUserid(), userName, null);
//			XinServerManager.binduser_area(this, application.getUserid(), "001",null);
//
//			Intent localIntent = new Intent(this,
//					NewUser2Activity.class);
//			
//			this.startActivity(localIntent);
			//this.finish();
			
		}
		else if (view.getId()==R.id.cancel_btn) {
			this.finish();
		}
		else if (view.getId()==R.id.back_img) {
			this.finish();
		}
		

	}




	//}

}
