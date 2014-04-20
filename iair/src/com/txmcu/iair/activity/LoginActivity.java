package com.txmcu.iair.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
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
import com.sina.weibo.sdk.openapi.legacy.UsersAPI;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tendcloud.tenddata.TCAgent;
import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirConstants;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinServerManager;
/**
 * 登陆界面
 * @author Administrator
 *
 */
public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "LoginActivity";

	private iAirApplication application;
	public WeiboAuth mWeiboAuth;
	public Oauth2AccessToken sinaAccessToken; //
	private UsersAPI mUsersAPI;
	public SsoHandler mSsoHandler;
	public Tencent mTencent;
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

		this.application = ((iAirApplication) getApplication());
		
		

		
//		PushLink.start(this, R.drawable.ic_launcher, "qk3ne7ortm8emgdf", iAirUtil.guid(this));
//
//		//Changing default notification messages
//		StatusBarStrategy sbs =  (StatusBarStrategy) PushLink.getCurrentStrategy();
//		sbs.setStatusBarTitle(getString(R.string.hasnewversion));
//		sbs.setStatusBarDescription(getString(R.string.clickupdate));
		
		this.loginQQ = ((Button) findViewById(R.id.loginQQRL));
		this.loginSina = ((Button) findViewById(R.id.loginSinaRL));
		this.loginQQ.setOnClickListener(this);
		this.loginSina.setOnClickListener(this);

		mWeiboAuth = new WeiboAuth(this,
				com.txmcu.iair.common.iAirConstants.APP_KEY,
				com.txmcu.iair.common.iAirConstants.REDIRECT_URL,
				com.txmcu.iair.common.iAirConstants.SCOPE);

		sinaAccessToken = AccessTokenKeeper.readAccessToken(this);
		mUsersAPI = new UsersAPI(sinaAccessToken);
		// if (accessToken!=null) {
		// Toast.makeText(this,
		// accessToken.getToken(),Toast.LENGTH_LONG).show();
		// Log.w(TAG, accessToken.getToken());
		// }
		// mAppid = "101017203";
		mTencent = Tencent.createInstance(iAirConstants.QQ_APP_ID,
				this.getApplicationContext());

		checkSessionAdnLogin();
		// updateUserInfo();

		// this.loginQQ = ((Button)findViewById(R.id.button_login));

	}
	

	//Only for popups (FRIENDLY_POPUP or ANNOYING_POPUP)
	//You MUST do this, otherwise popups will not work.
	//Call it in the Activity you want to show the popup.
	//You can show the popup in many screens by adding this in more than one Activity.
//	@Override
//	protected void onResume() {
//	    super.onResume();
//	    PushLink.setCurrentActivity(this);
//	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		TryLoadMainActivity("qq", "test_token", "test_openid"+iAirUtil.getRandomString(5), "test_name");
		return true;
	}

	public void onClick(View paramAnonymousView) {
		if (paramAnonymousView.getId() == R.id.loginQQRL) {
			if (!mTencent.isSessionValid()) {
				IUiListener listener = new BaseUiListener() {
					@Override
					protected void doComplete(JSONObject values) {
						checkSessionAdnLogin();

						// updateLoginButton();
					}
				};
				mTencent.login(this, SCOPE, listener);
			} else {
				checkSessionAdnLogin();
				// mTencent.logout(this);
				// updateUserInfo();
				// updateLoginButton();
			}
		} else if (paramAnonymousView.getId() == R.id.loginSinaRL) {
			// mWeiboAuth.anthorize(new AuthListener());
			if (sinaAccessToken == null || !sinaAccessToken.isSessionValid()) {

				mSsoHandler = new SsoHandler(LoginActivity.this, mWeiboAuth);
				mSsoHandler.authorize(new AuthListener());
			} else {

				checkSessionAdnLogin();
				// new LogoutAPI(accessToken).logout(new
				// LogOutRequestListener());
			}

		}

	}


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
			sinaAccessToken = Oauth2AccessToken.parseAccessToken(values);
			mUsersAPI = new UsersAPI(sinaAccessToken);
			if (sinaAccessToken.isSessionValid()) {
				// 显示 Token
				// mWeiboAuth.getAuthInfo()
				// updateTokenView(false);
				// 保存 Token �?SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this,
						sinaAccessToken);
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

			// NO NEED MORE INFO,JUSE OPENID IS OK

			iAirUtil.showProgressDialog(this);
			// Util.showProgressDialog(this, "Loading", "Please wait...");
			// final ProgressDialog dialog = ProgressDialog.show(this, "",
			// "Loading. Please wait...", true);

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
					// Message msg = new Message();
					// msg.obj = response;
					// msg.what = 0;
					// mHandler.sendMessage(msg);
					JSONObject res = (JSONObject) response;
					if (res.has("nickname")) {
						try {
							String userName = res.getString("nickname");
							// Log.i("iair", "nickname" + userName);
							// String openId = res.getString("");
							// Util.showResultDialog(LoginActivity.this,
							// res.toString(), "aa");
							// mUserInfo.setVisibility(android.view.View.VISIBLE);
							// mUserInfo.setText(response.getString("nickname"));
							// dialog.dismiss();
							iAirUtil.dismissDialog();

							TryLoadMainActivity("qq",
									mTencent.getAccessToken(),
									mTencent.getOpenId(), userName);
							// mTencent.()
							// MainActivity.TryLoadMainActivity(LoginActivity.this,"qq_");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			};
			//
			mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
					Constants.HTTP_GET, requestListener, null);

		} else {
			// loginQQ.setTextColor(Color.BLUE);

		}

		if (sinaAccessToken != null && sinaAccessToken.isSessionValid()) {


			
            long uid = Long.parseLong(sinaAccessToken.getUid());
            LogUtil.i(TAG, sinaAccessToken.getUid());
            mUsersAPI.show(uid, mListener);

			//TryLoadMainActivity("sina", sinaAccessToken.getToken(),
			//		sinaAccessToken.getUid(), "sina_test");
		} else {
			// loginSina.setTextColor(Color.BLUE);

		}
	}

	// private void updateUserInfo() {
	// if (mTencent != null && mTencent.isSessionValid()) {
	//
	//
	// } else {
	// // mUserInfo.setText("");
	// // mUserInfo.setVisibility(android.view.View.GONE);
	// // mUserLogo.setVisibility(android.view.View.GONE);
	// }
	// }

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(JSONObject response) {

			// Util.showResultDialog(LoginActivity.this, response.toString(),
			// "登录成功");
			doComplete(response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			iAirUtil.toastMessage(LoginActivity.this, "onError: "
					+ e.errorDetail);
			iAirUtil.dismissDialog();
		}

		@Override
		public void onCancel() {
			iAirUtil.toastMessage(LoginActivity.this, "onCancel: ");
			iAirUtil.dismissDialog();
		}
	}

	static public void logout(final Activity paramContext) {
		final Oauth2AccessToken accessToken = AccessTokenKeeper
				.readAccessToken(paramContext);
		Tencent tencent = Tencent.createInstance(iAirConstants.QQ_APP_ID,
				paramContext.getApplicationContext());

		if (accessToken != null && accessToken.isSessionValid()) {
			// Toast.makeText(paramContext, "1",Toast.LENGTH_LONG).show();;
			new LogoutAPI(accessToken).logout(new RequestListener() {
				@Override
				public void onComplete(String response) {
					if (!TextUtils.isEmpty(response)) {
						try {
							// Toast.makeText(paramContext,
							// response,Toast.LENGTH_LONG).show();;
							JSONObject obj = new JSONObject(response);
							String value = obj.getString("result");
							if ("true".equalsIgnoreCase(value)) {
								// Toast.makeText(paramContext,
								// response+"d",Toast.LENGTH_LONG).show();;
								AccessTokenKeeper.clear(paramContext);
								// Log.w(TAG, "logout"+accessToken.getToken());
								// mTokenView.setText(R.string.weibosdk_demo_logout_success);
								// Log.accessToken = null;

								// Intent localIntent = new Intent();

								// localIntent.setClass(paramContext,
								// LoginActivity.class);
								// paramContext.startActivityForResult(localIntent,
								// 1);

								paramContext.startActivity(new Intent(
										paramContext, LoginActivity.class));
								paramContext.finish();
								// if (accessToken!=null) {
								// Log.w(TAG, accessToken.getToken());
								// }
								// updateLoginButton();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onComplete4binary(ByteArrayOutputStream responseOS) {
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
		} else if (tencent.isSessionValid()) {
			tencent.logout(paramContext);

			paramContext.startActivity(new Intent(paramContext,
					LoginActivity.class));

			// Intent localIntent = new Intent();

			// localIntent.setClass(paramContext, LoginActivity.class);
			// paramContext.startActivityForResult(localIntent, 1);

			paramContext.finish();
		}

	}
//	
	 /**
     * 微博 OpenAPI 回调接口。
     */
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
        	LogUtil.i(TAG, response);
            if (!TextUtils.isEmpty(response)) {
                
                // 调用 User#parse 将JSON串解析成User对象
                com.sina.weibo.sdk.openapi.models.User user = com.sina.weibo.sdk.openapi.models.User.parse(response);
                if (user != null) {
                	
                	
                	TryLoadMainActivity("sina",
                			sinaAccessToken.getToken(),
                					sinaAccessToken.getUid(), user.screen_name);
                   // Toast.makeText(WBUserAPIActivity.this, 
                  //          "获取User信息成功，用户昵称：" + user.screen_name, 
                   //         Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

      
		@Override
		public void onComplete4binary(ByteArrayOutputStream arg0) {
			// TODO Auto-generated method stub
			LogUtil.i(TAG, "onComplete4binary");
			
		}

		@Override
		public void onError(WeiboException arg0) {
			// TODO Auto-generated method stub
			LogUtil.i(TAG, "onError"+arg0.toString());
        	//TryLoadMainActivity("sina",
        	//		sinaAccessToken.getToken(),
        	//				sinaAccessToken.getUid(), getString(R.string.test));
		}

		@Override
		public void onIOException(IOException arg0) {
			// TODO Auto-generated method stub
			LogUtil.i(TAG, "onIOException"+arg0.toString());
		}
    };

	public void TryLoadMainActivity(final String authType,final String token,
			final String openId,final String nickName) {
		
		
		
		XinServerManager.login(this, authType, token, openId, nickName,
				new XinServerManager.onSuccess() {

					@Override
					public void run(JSONObject response) throws JSONException {
						// TODO Auto-generated method stub
						String ret = response.getString("ret");
						String usertype = response.getString("usertype");
						String userid = response.getString("userid");
						

						application.setUserid(userid);
						application.setNickName(nickName);
						
						if (usertype.equals("new")) {
							
							
							XinServerManager.gethome_structdata(LoginActivity.this, userid, new XinServerManager.onSuccess() {
								
								@Override
								public void run(JSONObject response) throws JSONException {
									
									List<Home> homes = XinServerManager.getHomeFromJson(LoginActivity.this,response.getJSONArray("home"));
									application.homeList =(homes);
									
									Intent localIntent = new Intent(LoginActivity.this,
											NewUser1Activity.class);
									
									LoginActivity.this.startActivity(localIntent);
									LoginActivity.this.finish();
								}
							});
							
							
						}
						else {
							
							Intent localIntent = new Intent(LoginActivity.this,
									MainActivity.class);
							
							LoginActivity.this.startActivity(localIntent);
							LoginActivity.this.finish();
						}
						
						
					}
				});
		

//		XinServerManager.login(this, authType, token, openId, nickName,
//				new XinServerManager.onSuccess() {
//
//					@Override
//					public void run(String response) {
//						// TODO Auto-generated method stub
//						Map<String, String> snMap = iAirUtil
//								.getQueryMapFromUrl(response);
//
//						application.setUserid(snMap.get("userid"));
//
//						Intent localIntent = new Intent(LoginActivity.this,
//								MainActivity.class);
//						localIntent.putExtra("authType", authType);
//						localIntent.putExtra("token", token);
//						localIntent.putExtra("openId", openId);
//						localIntent.putExtra("nickName", nickName);
//						application.setNickName(nickName);
//						LoginActivity.this.startActivity(localIntent);
//						LoginActivity.this.finish();
//						// Toast.makeText(MainActivity.this,
//						// R.string.xiaoxin_login_ok, Toast.LENGTH_LONG).show();
//					}
//				});

	}
}
