
package com.txmcu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.txmcu.iair.R;
import com.txmcu.view.LoadingImgView;

public class LoadingActivity extends FragmentActivity
{
  private LoadingImgView loadingImgView = null;
  //private SharedPreferences sp = null;
  //private ViewFlipper vf;

  public void gotoNextActivity()
  {
    Intent localIntent = new Intent();
    //todo ... add usermanager system
   // if (getUserID() > 0)
    //    localIntent.setClass(this, HomepageActivity.class);
    //  else
    localIntent.setClass(this, LoginActivity.class);
    startActivityForResult(localIntent, 1);
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_loading);
   // this.vf = ((ViewFlipper)findViewById(com.hjwordgames.R.id.welcome_flipper_slogan));
    this.loadingImgView = ((LoadingImgView)findViewById(R.id.mainLoading));
    int[] arrayOfInt = { R.drawable.loader_frame_1,R.drawable.loader_frame_2, R.drawable.loader_frame_3, R.drawable.loader_frame_4, R.drawable.loader_frame_5, R.drawable.loader_frame_6 };
    this.loadingImgView.setImageIds(arrayOfInt);
    this.loadingImgView.startAnim();
   
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          LoadingActivity.this.gotoNextActivity();
        }
      }
      , 1000L);
    
  }

  protected void onDestroy()
  {
    this.loadingImgView.stopAnim();
    super.onDestroy();
  }

}
