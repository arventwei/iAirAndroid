package com.txmcu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.txmcu.iair.R;

public class LoginActivity extends Activity
 implements View.OnClickListener
{

	
	public View btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		this.btnLogin = ((Button)findViewById(R.id.button_login));
		this.btnLogin.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View paramView)
	{
		if (paramView ==btnLogin) {
		    Intent localIntent = new Intent();

		    localIntent.setClass(this, MainActivity.class);
		    startActivityForResult(localIntent, 1);
		    finish();
		}
	}
	  

}
