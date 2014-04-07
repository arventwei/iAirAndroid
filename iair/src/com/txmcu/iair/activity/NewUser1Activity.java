package com.txmcu.iair.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.txmcu.iair.R;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;

public class NewUser1Activity extends Activity implements OnClickListener {

	private static final String TAG = "NewUser1Activity";


	iAirApplication application;

	EditText userNameEditText;
	EditText cityNameEditText;
	public static NewUser1Activity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser_1);
		instance  = this;
		application = (iAirApplication)getApplication();
		findViewById(R.id.next_btn).setOnClickListener(this);
		userNameEditText = (EditText)findViewById(R.id.userName);
		cityNameEditText = (EditText)findViewById(R.id.user1_city_name);
		
		userNameEditText.setText(application.getNickName());
		cityNameEditText.setText("北京");
		findViewById(R.id.user1_city_name).setOnClickListener(this);

	}


	 
		@Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
	    {  
	        //可以根据多个请求代码来作相应的操作  
	        if(20==resultCode)  
	        {  
	            String area_id=data.getExtras().getString("area_id");  
	            String area_name=data.getExtras().getString("area_name");
	            cityNameEditText.setText(area_name);
	           // iAirUtil.toastMessage(this, area_id);
	            
//	            XinServerManager.binduser_area(this, application.getUserid(), area_id, new XinServerManager.onSuccess() {
//					
//					@Override
//					public void run(JSONObject response) throws JSONException {
//						// TODO Auto-generated method stub
//						requestlist();
//					}
//				});
	          //  TextView_result.setText("书籍名称:"+bookname+"书籍价钱"+booksale+"元");  
	        }  
	        super.onActivityResult(requestCode, resultCode, data);  
	    }  
	public void onClick(View view) {
	
		if (view.getId()==R.id.next_btn) {
			
			String userName = userNameEditText.getText().toString();
			String citName = cityNameEditText.getText().toString();
			String areaIdString = application.getAreaIdbyName(citName);
			application.setNickName(userName);
			
			XinServerManager.setuser_nickname(this, application.getUserid(), userName, null);
			XinServerManager.binduser_area(this, application.getUserid(), areaIdString,null);

			Intent localIntent = new Intent(this,
					NewUser2Activity.class);
			
			this.startActivity(localIntent);
			//this.finish();
			
		}
		else if (view.getId()==R.id.user1_city_name) {
			 Intent localIntent = new Intent(NewUser1Activity.this,CityListActivity.class);
				// localIntent.putExtra("homeId", home.homeid);
				// localIntent.putExtra("vsn", "");
				 startActivityForResult(localIntent,100);
				// startActivity(localIntent);
				 overridePendingTransition(R.anim.left_enter,  R.anim.alpha_out);
		}

	}

}
