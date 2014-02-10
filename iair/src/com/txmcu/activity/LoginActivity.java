package com.txmcu.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;

import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.txmcu.common.Util;
import com.txmcu.iair.R;

public class LoginActivity extends Activity implements OnClickListener 
{

	 
	  public Tencent mTencent;
	  public static String mAppid;
	
	public Button loginQQ;
	public Button loginSina;
	//public View loginTenWb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
	    this.loginQQ = ((Button)findViewById(R.id.loginQQRL));
	    this.loginSina = ((Button)findViewById(R.id.loginSinaRL));
	    this.loginQQ.setOnClickListener(this);
		mAppid="101017203";
		mTencent = Tencent.createInstance(mAppid, this.getApplicationContext());
		
		updateLoginButton();
		updateUserInfo();
		//this.loginQQ = ((Button)findViewById(R.id.button_login));

	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public void onClick(View paramAnonymousView)
     {
		 if (paramAnonymousView.getId() == R.id.loginQQRL) 
		 {
			  if (!mTencent.isSessionValid()) {
		 			IUiListener listener = new BaseUiListener() {
		 				@Override
		 				protected void doComplete(JSONObject values) {
		 					updateUserInfo();
		 					updateLoginButton();
		 				}
		 			};
		 			mTencent.login(this, "all", listener);
		 		} else {
		 			mTencent.logout(this);
		 			updateUserInfo();
		 			updateLoginButton();
		 		}
		}
   	
     }
	 private void updateLoginButton() {
			if (mTencent != null && mTencent.isSessionValid()) {
				loginQQ.setTextColor(Color.RED);
				loginQQ.setText("退出帐号");
			} else {
				loginQQ.setTextColor(Color.BLUE);
				loginQQ.setText("登录");
			}
		}

		private void updateUserInfo() {
			if (mTencent != null && mTencent.isSessionValid()) {
				IRequestListener requestListener = new IRequestListener() {

					@Override
					public void onUnknowException(Exception e, Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSocketTimeoutException(SocketTimeoutException e,
							Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onNetworkUnavailableException(
							NetworkUnavailableException e, Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onMalformedURLException(MalformedURLException e,
							Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onJSONException(JSONException e, Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onIOException(IOException e, Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onHttpStatusException(HttpStatusException e,
							Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onConnectTimeoutException(
							ConnectTimeoutException e, Object state) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onComplete(final JSONObject response, Object state) {
						// TODO Auto-generated method stub
//						Message msg = new Message();
//						msg.obj = response;
//						msg.what = 0;
//						mHandler.sendMessage(msg);
						JSONObject res = (JSONObject)response;
						if (res.has("nickname")) {
							try {
								String userName = res.getString("nickname");
								Log.i("iair", "nickname"+userName);
								//mUserInfo.setVisibility(android.view.View.VISIBLE);
								//mUserInfo.setText(response.getString("nickname"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				};
				  mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
		                    Constants.HTTP_GET, requestListener, null);
			} else {
				//mUserInfo.setText("");
				//mUserInfo.setVisibility(android.view.View.GONE);
				//mUserLogo.setVisibility(android.view.View.GONE);
			}
		}
		
		private class BaseUiListener implements IUiListener {

			@Override
			public void onComplete(JSONObject response) {
				Util.showResultDialog(LoginActivity.this, response.toString(), "登录成功");
				doComplete(response);
			}

			protected void doComplete(JSONObject values) {

			}

			@Override
			public void onError(UiError e) {
				Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
				Util.dismissDialog();
			}

			@Override
			public void onCancel() {
				Util.toastMessage(LoginActivity.this, "onCancel: ");
				Util.dismissDialog();
			}
		}
	  
}
