package com.txmcu.iair.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.AccessTokenKeeper;
import com.sina.weibo.sdk.openapi.LogoutAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tendcloud.tenddata.TCAgent;
import com.txmcu.iair.R;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirConstants;
import com.txmcu.iair.common.iAirUtil;
public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "iair";
	
	private iAirApplication application;
	public WeiboAuth mWeiboAuth;
	public  Oauth2AccessToken accessToken; // 
	public SsoHandler mSsoHandler;
	public  Tencent mTencent;
	String SCOPE = "get_user_info,get_simple_userinfo,get_user_profile,get_app_friends";

	public Button loginQQ;
	public Button loginSina;

	// public View loginTenWb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		TCAgent.init(this);
		TCAgent.setReportUncaughtExceptions(true);
		
		this.application = ((iAirApplication)getApplication());

		this.loginQQ = ((Button) findViewById(R.id.loginQQRL));
		this.loginSina = ((Button) findViewById(R.id.loginSinaRL));
		this.loginQQ.setOnClickListener(this);
		this.loginSina.setOnClickListener(this);

		mWeiboAuth = new WeiboAuth(this,
				com.txmcu.iair.common.iAirConstants.APP_KEY,
				com.txmcu.iair.common.iAirConstants.REDIRECT_URL,
				com.txmcu.iair.common.iAirConstants.SCOPE);
		
		accessToken = AccessTokenKeeper.readAccessToken(this);
		//if (accessToken!=null) {
			//Toast.makeText(this, accessToken.getToken(),Toast.LENGTH_LONG).show();
			//Log.w(TAG, accessToken.getToken());
		//}
		//mAppid = "101017203";
		mTencent = Tencent.createInstance(iAirConstants.QQ_APP_ID, this.getApplicationContext());

		checkSessionAdnLogin();
		//updateUserInfo();

		// this.loginQQ = ((Button)findViewById(R.id.button_login));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		TryLoadMainActivity("sina_123d");
		return true;
	}

	public void onClick(View paramAnonymousView) {
		if (paramAnonymousView.getId() == R.id.loginQQRL) {
			if (!mTencent.isSessionValid()) {
				IUiListener listener = new BaseUiListener() {
					@Override
					protected void doComplete(JSONObject values) {
						checkSessionAdnLogin();
						
						//updateLoginButton();
					}
				};
				mTencent.login(this,SCOPE, listener);
			} else {
			//	mTencent.logout(this);
			//	updateUserInfo();
			//	updateLoginButton();
			}
		} else if (paramAnonymousView.getId() == R.id.loginSinaRL) {
			// mWeiboAuth.anthorize(new AuthListener());
			if(accessToken==null || !accessToken.isSessionValid()){
				
				mSsoHandler = new SsoHandler(LoginActivity.this, mWeiboAuth);
				mSsoHandler.authorize(new AuthListener());
			}
			else {
				
				//new LogoutAPI(accessToken).logout(new LogOutRequestListener());
			}

		}

	}

	
//	private class LogOutRequestListener implements RequestListener {
//		@Override
//		public void onComplete(String response) {
//			if (!TextUtils.isEmpty(response)) {
//				try {
//					JSONObject obj = new JSONObject(response);
//					String value = obj.getString("result");
//					if ("true".equalsIgnoreCase(value)) {
//						AccessTokenKeeper.clear(LoginActivity.this);
//						// mTokenView.setText(R.string.weibosdk_demo_logout_success);
//						accessToken = null;
//						//updateLoginButton();
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		  @Override
//          public void onComplete4binary(ByteArrayOutputStream responseOS) {
//              //LogUtil.e(TAG, "onComplete4binary...");
//              // Do nothing
//          }
//  
//          @Override
//          public void onIOException(IOException e) {
//             // LogUtil.e(TAG, "onIOException�?" + e.getMessage());
//              // 注销失败
//              //setText(R.string.com_sina_weibo_sdk_logout);
//              
//             // if (mLogoutListener != null) {
//             // 	mLogoutListener.onIOException(e);
//             // }
//          }
//  
//          @Override
//          public void onError(WeiboException e) {
//              //LogUtil.e(TAG, "WeiboException�?" + e.getMessage());
//              // 注销失败
//             // setText(R.string.com_sina_weibo_sdk_logout);
//              
//             // if (mLogoutListener != null) {
//             // 	mLogoutListener.onError(e);
//             // }
//          }
//
//	}
	/**
	 * 
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// SSO 授权回调
		// 重要:发起 SSO 登陆�?Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	class AuthListener implements WeiboAuthListener {
		@Override
		public void onCancel() {
			// Oauth2.
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
			checkSessionAdnLogin();

		}

		@Override
		public void onWeiboException(WeiboException e) {
			// 
			Toast.makeText(getApplicationContext(),
					"Auth exception:" + e.getMessage(), Toast.LENGTH_LONG)
					.show();

		}

		@Override
		public void onComplete(Bundle values) {
			// 
			accessToken = Oauth2AccessToken.parseAccessToken(values);
			if (accessToken.isSessionValid()) {
				// 显示 Token
				//mWeiboAuth.getAuthInfo()
				// updateTokenView(false);
				// 保存 Token �?SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this,
						accessToken);
				checkSessionAdnLogin();
				// .........
			} else {
				// 
				// String code = values.getString("code", ""); .........
				checkSessionAdnLogin();
			}
		}
		// .........
	}

	private void checkSessionAdnLogin() {
		if (mTencent != null && mTencent.isSessionValid()) {
			
			TryLoadMainActivity("qq_"+mTencent.getOpenId());
			
			// NO NEED MORE INFO,JUSE OPENID IS OK
			
//			Util.showProgressDialog(this);
//			//Util.showProgressDialog(this, "Loading", "Please wait...");
//			//final ProgressDialog dialog = ProgressDialog.show(this, "", 
//            //        "Loading. Please wait...", true);
//			
//			IRequestListener requestListener = new IRequestListener() {
//
//				@Override
//				public void onUnknowException(Exception e, Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onSocketTimeoutException(SocketTimeoutException e,
//						Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onNetworkUnavailableException(
//						NetworkUnavailableException e, Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onMalformedURLException(MalformedURLException e,
//						Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onJSONException(JSONException e, Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onIOException(IOException e, Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onHttpStatusException(HttpStatusException e,
//						Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onConnectTimeoutException(
//						ConnectTimeoutException e, Object state) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onComplete(final JSONObject response, Object state) {
//					// TODO Auto-generated method stub
//					// Message msg = new Message();
//					// msg.obj = response;
//					// msg.what = 0;
//					// mHandler.sendMessage(msg);
//					JSONObject res = (JSONObject) response;
//					//if (res.has("nickname")) {
//						//try {
//							//String userName = res.getString("nickname");
//							//Log.i("iair", "nickname" + userName);
//							//String openId = res.getString("");
//							Util.showResultDialog(LoginActivity.this, res.toString(), "aa");
//							// mUserInfo.setVisibility(android.view.View.VISIBLE);
//							// mUserInfo.setText(response.getString("nickname"));
//							//dialog.dismiss();
//							Util.dismissDialog();
//							//mTencent.()
//							//MainActivity.TryLoadMainActivity(LoginActivity.this,"qq_");
//						//} catch (JSONException e) {
//							// TODO Auto-generated catch block
//						//	e.printStackTrace();
//						//}
//					//}
//
//				}
//			};
////
//			mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
//					Constants.HTTP_GET, requestListener, null);
			
		} else {
			//loginQQ.setTextColor(Color.BLUE);
			//loginQQ.setText("登录QQ");
		}
		
		if(accessToken!= null && accessToken.isSessionValid()){
			
			//loginSina.setTextColor(Color.RED);
			//loginSina.setText("�?��帐号SINA");
			
			TryLoadMainActivity("sina_"+accessToken.getUid());
		} else {
			//loginSina.setTextColor(Color.BLUE);
			//loginSina.setText("登录SINA");
		}
	}

//	private void updateUserInfo() {
//		if (mTencent != null && mTencent.isSessionValid()) {
//			
//
//		} else {
//			// mUserInfo.setText("");
//			// mUserInfo.setVisibility(android.view.View.GONE);
//			// mUserLogo.setVisibility(android.view.View.GONE);
//		}
//	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(JSONObject response) {
			
			//Util.showResultDialog(LoginActivity.this, response.toString(),
				//	"登录成功");
			doComplete(response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			iAirUtil.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
			iAirUtil.dismissDialog();
		}

		@Override
		public void onCancel() {
			iAirUtil.toastMessage(LoginActivity.this, "onCancel: ");
			iAirUtil.dismissDialog();
		}
	}
	
	static public void logout(final Activity paramContext)
	{
		final Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(paramContext);
		Tencent tencent = Tencent.createInstance(iAirConstants.QQ_APP_ID, paramContext.getApplicationContext());

		if (accessToken != null
				&& accessToken.isSessionValid()) {
			//Toast.makeText(paramContext, "1",Toast.LENGTH_LONG).show();;
			new LogoutAPI(accessToken)
					.logout(new RequestListener() {
						@Override
						public void onComplete(String response) {
							if (!TextUtils.isEmpty(response)) {
								try {
									//Toast.makeText(paramContext, response,Toast.LENGTH_LONG).show();;
									JSONObject obj = new JSONObject(response);
									String value = obj.getString("result");
									if ("true".equalsIgnoreCase(value)) {
										//Toast.makeText(paramContext, response+"d",Toast.LENGTH_LONG).show();;
										AccessTokenKeeper.clear(paramContext);
										//Log.w(TAG, "logout"+accessToken.getToken());
										// mTokenView.setText(R.string.weibosdk_demo_logout_success);
										//Log.accessToken = null;
										
										//Intent localIntent = new Intent();
										
									    //localIntent.setClass(paramContext, LoginActivity.class);
									    //paramContext.startActivityForResult(localIntent, 1);
									    
										paramContext.startActivity(new Intent(paramContext, LoginActivity.class));
										paramContext.finish();
										//if (accessToken!=null) {
											//Log.w(TAG, accessToken.getToken());
										//}
										// updateLoginButton();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}

						@Override
						public void onComplete4binary(
								ByteArrayOutputStream responseOS) {
							// LogUtil.e(TAG, "onComplete4binary...");
							// Do nothing
						}

						@Override
						public void onIOException(IOException e) {
							// LogUtil.e(TAG, "onIOException�?" +
							// e.getMessage());
							// 注销失败
							// setText(R.string.com_sina_weibo_sdk_logout);

							// if (mLogoutListener != null) {
							// mLogoutListener.onIOException(e);
							// }
						}

						@Override
						public void onError(WeiboException e) {
							// LogUtil.e(TAG, "WeiboException�?" +
							// e.getMessage());
							// 注销失败
							// setText(R.string.com_sina_weibo_sdk_logout);

							// if (mLogoutListener != null) {
							// mLogoutListener.onError(e);
							// }
						}
					});
		}
		else if (tencent.isSessionValid()) {
			tencent.logout(paramContext);
			
			paramContext.startActivity(new Intent(paramContext, LoginActivity.class));
			
			//Intent localIntent = new Intent();
			
		   // localIntent.setClass(paramContext, LoginActivity.class);
		   // paramContext.startActivityForResult(localIntent, 1);
		    
			paramContext.finish();
		}
		
	}

	
	public void TryLoadMainActivity(String userid) {

		application.setUserid(userid);
		this.finish();
	    this.startActivity(new Intent(this, MainActivity.class));
	    
	}
}
