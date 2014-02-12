package com.txmcu.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.demo.openapi.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.LogoutAPI;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.txmcu.common.Util;
import com.txmcu.iair.R;

public class LoginActivity extends Activity implements OnClickListener {

	public WeiboAuth mWeiboAuth;
	public static Oauth2AccessToken accessToken; // 访问token
	public SsoHandler mSsoHandler;
	public Tencent mTencent;
	public static String mAppid;

	public Button loginQQ;
	public Button loginSina;

	// public View loginTenWb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		this.loginQQ = ((Button) findViewById(R.id.loginQQRL));
		this.loginSina = ((Button) findViewById(R.id.loginSinaRL));
		this.loginQQ.setOnClickListener(this);
		this.loginSina.setOnClickListener(this);

		mWeiboAuth = new WeiboAuth(this,
				com.sina.weibo.sdk.demo.openapi.Constants.APP_KEY,
				com.sina.weibo.sdk.demo.openapi.Constants.REDIRECT_URL,
				com.sina.weibo.sdk.demo.openapi.Constants.SCOPE);
		
		accessToken = AccessTokenKeeper.readAccessToken(this);

		mAppid = "101017203";
		mTencent = Tencent.createInstance(mAppid, this.getApplicationContext());

		updateLoginButton();
		updateUserInfo();

		// this.loginQQ = ((Button)findViewById(R.id.button_login));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View paramAnonymousView) {
		if (paramAnonymousView.getId() == R.id.loginQQRL) {
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
		} else if (paramAnonymousView.getId() == R.id.loginSinaRL) {
			// mWeiboAuth.anthorize(new AuthListener());
			if(accessToken==null || !accessToken.isSessionValid()){
				
				mSsoHandler = new SsoHandler(LoginActivity.this, mWeiboAuth);
				mSsoHandler.authorize(new AuthListener());
			}
			else {
				new LogoutAPI(accessToken).logout(new LogOutRequestListener());
			}

		}

	}

	/**
	 * 注销按钮的监听器,接收注销处理结果。(API请求结果的监听器)
	 */
	private class LogOutRequestListener implements RequestListener {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				try {
					JSONObject obj = new JSONObject(response);
					String value = obj.getString("result");
					if ("true".equalsIgnoreCase(value)) {
						AccessTokenKeeper.clear(LoginActivity.this);
						// mTokenView.setText(R.string.weibosdk_demo_logout_success);
						accessToken = null;
						updateLoginButton();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		  @Override
          public void onComplete4binary(ByteArrayOutputStream responseOS) {
              //LogUtil.e(TAG, "onComplete4binary...");
              // Do nothing
          }
  
          @Override
          public void onIOException(IOException e) {
             // LogUtil.e(TAG, "onIOException： " + e.getMessage());
              // 注销失败
              //setText(R.string.com_sina_weibo_sdk_logout);
              
             // if (mLogoutListener != null) {
             // 	mLogoutListener.onIOException(e);
             // }
          }
  
          @Override
          public void onError(WeiboException e) {
              //LogUtil.e(TAG, "WeiboException： " + e.getMessage());
              // 注销失败
             // setText(R.string.com_sina_weibo_sdk_logout);
              
             // if (mLogoutListener != null) {
             // 	mLogoutListener.onError(e);
             // }
          }

	}
	/**
	 * 当 SSO 授权 Activity 退出时,该函数被调用。 *
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// SSO 授权回调
		// 重要:发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	class AuthListener implements WeiboAuthListener {
		@Override
		public void onCancel() {
			// Oauth2.0认证过程中，如果认证窗口被关闭或认证取消时调用
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
			updateLoginButton();

		}

		@Override
		public void onWeiboException(WeiboException e) {
			// 当认证过程中捕获到WeiboException时调用
			Toast.makeText(getApplicationContext(),
					"Auth exception:" + e.getMessage(), Toast.LENGTH_LONG)
					.show();

		}

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			accessToken = Oauth2AccessToken.parseAccessToken(values);
			if (accessToken.isSessionValid()) {
				// 显示 Token
				//mWeiboAuth.getAuthInfo()
				// updateTokenView(false);
				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this,
						accessToken);
				updateLoginButton();
				// .........
			} else {
				// 当您注册的应用程序签名不正确时,就会收到 Code,请确保签名正确
				// String code = values.getString("code", ""); .........
				updateLoginButton();
			}
		}
		// .........
	}

	private void updateLoginButton() {
		if (mTencent != null && mTencent.isSessionValid()) {
			loginQQ.setTextColor(Color.RED);
			loginQQ.setText("退出帐号QQ");
		} else {
			loginQQ.setTextColor(Color.BLUE);
			loginQQ.setText("登录QQ");
		}
		
		if(accessToken!= null && accessToken.isSessionValid()){
			
			loginSina.setTextColor(Color.RED);
			loginSina.setText("退出帐号SINA");
		} else {
			loginSina.setTextColor(Color.BLUE);
			loginSina.setText("登录SINA");
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
					// Message msg = new Message();
					// msg.obj = response;
					// msg.what = 0;
					// mHandler.sendMessage(msg);
					JSONObject res = (JSONObject) response;
					if (res.has("nickname")) {
						try {
							String userName = res.getString("nickname");
							Log.i("iair", "nickname" + userName);
							// mUserInfo.setVisibility(android.view.View.VISIBLE);
							// mUserInfo.setText(response.getString("nickname"));
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
			// mUserInfo.setText("");
			// mUserInfo.setVisibility(android.view.View.GONE);
			// mUserLogo.setVisibility(android.view.View.GONE);
		}
	}

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
